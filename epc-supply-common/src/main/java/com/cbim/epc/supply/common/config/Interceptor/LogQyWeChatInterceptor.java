package com.cbim.epc.supply.common.config.Interceptor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.cbim.epc.supply.common.domain.CommonLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogQyWeChatInterceptor implements LogInterceptor {

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Value("${spring.application.name}")
    private String projectName;

    @Value("${epc.system.log-host}")
    private String projectHost;

    @Value("${epc.system.log-webhook}")
    private String logWebhook;

    @Override
    public void handleLog(CommonLog log) {
        if (log.getResultCode() == 0 && StrUtil.isNotBlank(log.getResultDetails())) {
            sendMessage(log);
        }
    }

    private void sendMessage(CommonLog log) {
        HttpUtil.post(logWebhook, "{\n" +
                "        \"msgtype\": \"markdown\",\n" +
                "        \"markdown\": {\n" +
                "            \"content\": \"" + buildMdContent(log) + "\"\n" +
                "        }\n" +
                "   }");
    }

    private String buildMdContent(CommonLog log) {
        return "错误日志报警 <font color=\\\"warning\\\">" + projectName + "-" + profileActive + "</font>，[查看日志详情](" + getDetailUrl(log.getRequestId()) + ")\\n\n" +
                "         >请求时间:<font color=\\\"comment\\\">" + DateUtil.format(log.getRequestTime(), "YYYY-MM-dd HH:mm:ss") + "</font>\n" +
                "         >请求地址:<font color=\\\"comment\\\">" + log.getRequestUri() + "</font>\n" +
                "         >请求方法:<font color=\\\"comment\\\">" + log.getRequestMethod().split(" ")[1] + "</font>\n" +
                "         >请求参数:<font color=\\\"comment\\\">" + this.getReplaced(log.getRequestParam()) + "</font>\n" +
                "         >请求结果:<font color=\\\"comment\\\">" + this.getReplaced(log.getRequestResult()) + "</font>\n" +
                "         >请求耗时:<font color=\\\"comment\\\">" + log.getCostTime() + "ms" + "</font>\n\n" +
                "<font color=\\\"comment\\\">" + log.getRequestId() + "</font>";
    }

    private String getDetailUrl(String requestId) {
        return projectHost + requestId;
    }

    private String getReplaced(String result) {
        return result.replaceAll("\\{", "\\\\\\\\{")
                .replaceAll("}", "\\\\}")
                .replaceAll("\"", "\\\\\"");
    }
}
