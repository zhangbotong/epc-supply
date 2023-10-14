package com.cbim.epc.supply.data.handler;

import com.cbim.epc.supply.data.domain.SupplyCity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 供货城市及费用-类型转换器
 *
 * @author xiaozp
 * @since 2023/5/16
 */
public class SupplyCityListTypeHandler extends BaseTypeHandler<List<SupplyCity>> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<SupplyCity> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, OBJECT_MAPPER.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error while converting List<SupplyCityInfo> to JSON", e);
        }
    }

    @Override
    public List<SupplyCity> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return json == null ? null : parseJson(json);
    }

    @Override
    public List<SupplyCity> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return json == null ? null : parseJson(json);
    }

    @Override
    public List<SupplyCity> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return json == null ? null : parseJson(json);
    }

    private List<SupplyCity> parseJson(String json) throws SQLException {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<SupplyCity>>() {});
        } catch (JsonProcessingException e) {
            throw new SQLException("Error while parsing JSON to List<SupplyCityInfo>", e);
        }
    }
}
