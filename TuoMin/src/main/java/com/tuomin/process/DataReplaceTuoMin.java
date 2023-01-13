package com.tuomin.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tuomin.Context;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhaixinwei
 * @date 2023/1/5
 */
public class DataReplaceTuoMin implements TuoMin {
    private final BigDecimal rate = new BigDecimal("1.5");


    @Override
    public void doTuoMin(JSONObject jsonObject, List<String> filterKey) {
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            if(filterKey.contains(key)){
                continue;
            }
            Object value = jsonObject.get(key);
            if (isNum(value)) {
                jsonObject.put(key, compute(value));
            }
        }
    }

    private String compute(Object convert) {
        BigDecimal bigDecimal = null;
        if (convert instanceof BigDecimal) {
            bigDecimal = (BigDecimal) convert;
        } else {
            bigDecimal = new BigDecimal(String.valueOf(convert));
        }

        bigDecimal = bigDecimal.multiply(rate);
        if (bigDecimal.toString().startsWith("0E-")) {
            return "0.000000";
        }
        return bigDecimal.toString();
    }

    private boolean isNum(Object num) {
        if (num instanceof Number) {
            return true;
        } else if (num instanceof String) {
            try {
                Double.parseDouble((String) num);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
