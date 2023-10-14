package com.cbim.epc.supply.app.controller.rpc;

import cn.hutool.core.collection.IterUtil;
import com.cbim.epc.base.util.MultiResponse;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.CityDTO;
import com.cbim.epc.supply.data.domain.SupplyCity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 字典controller
 *
 * @author xiaozp
 * @since 2023/5/31
 */
@RestController
@Slf4j
@RequestMapping("/api/dict")
@RequiredArgsConstructor
public class EasyApiDictRpcController {

    private final EasyApiUtil easyApiUtil;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long TIME_OUT = 3L;

    /**
     * 获取供货城市
     */
    @GetMapping("/supplyCity")
    public MultiResponse<SupplyCity> getSupplyCity() {
        String key = "dict:material:supplyCity";
        List<SupplyCity> levelOneCities = (List<SupplyCity>) redisTemplate.opsForValue().get(key);
        if (IterUtil.isEmpty(levelOneCities)) {
            List<CityDTO> allCities = easyApiUtil.getArea();
            levelOneCities = new ArrayList<>();
            if (IterUtil.isNotEmpty(allCities)) {
                for (CityDTO city : allCities) {
                    if (city.getLevel() == 1) {
                        SupplyCity supplyCity = new SupplyCity();
                        supplyCity.setCode(city.getCode());
                        supplyCity.setLabel(city.getLabel());
                        supplyCity.setValue(city.getValue());
                        levelOneCities.add(supplyCity);
                    }
                }
            } else {
                return MultiResponse.buildSuccess(Collections.emptyList());
            }
            redisTemplate.opsForValue().set(key, levelOneCities, TIME_OUT, TimeUnit.MINUTES);
        }
        return MultiResponse.buildSuccess(levelOneCities);
    }
}
