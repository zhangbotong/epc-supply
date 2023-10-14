package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.collection.IterUtil;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.CityDTO;
import com.cbim.epc.supply.app.service.DictService;
import com.cbim.epc.supply.data.domain.SupplyCity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 字典服务实现
 *
 * @author xiaozp
 * @since 2023/6/1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final EasyApiUtil easyApiUtil;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long TIME_OUT = 3L;

    @Override
    public List<SupplyCity> getSupplyCity() {
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
                return Collections.emptyList();
            }
            redisTemplate.opsForValue().set(key, levelOneCities, TIME_OUT, TimeUnit.MINUTES);
        }
        return levelOneCities;
    }
}
