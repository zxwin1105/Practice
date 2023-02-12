package com.tuomin.process;

import com.alibaba.fastjson.JSONObject;
import com.tuomin.Context;

import java.util.List;

/**
 * @author zhaixinwei
 * @date 2023/1/5
 */
public interface TuoMin {

    String JSON_DATA = "data";
    String JSON_CODE = "code";


//    String tuoMin(String json);

    void doTuoMin(JSONObject jsonObject, List<String> filterKey);
}
