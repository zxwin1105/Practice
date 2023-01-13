package com.gs.insert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.gs.insert.entity.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhaixinwei
 * @date 2023/1/10
 */
public class JsonParser {


    public void parser(String json, List<String> keyList, List<List<Value>> valList, List<String> filter) {
        JSONObject jsonObject = JSONObject.parseObject(json, Feature.OrderedField);

        JSONObject resultData = jsonObject.getJSONObject("resultData");
        if (null == resultData) {
            System.out.println("解析失败，没有数据（resultData）");
            return;
        }
        JSONArray content = resultData.getJSONArray("content");
        if (null == content) {
            System.out.println("解析失败，没有数据（content）");
            return;
        }

        for (Object o : content) {
            JSONObject obj = (JSONObject) o;
            if (keyList.isEmpty()) {
                keyList.addAll(obj.keySet());
            }
            List<Value> valueList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : obj.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                Value val = new Value(value, false);
                if (value instanceof String) {
                    val.setStr(true);

//                    // 处理转义字符
//                    String v = val.toString();
//                    if (v.contains("'")) {
//                        StringBuilder sb = new StringBuilder();
//                        for(char ch : v.toCharArray()){
//                            if(ch == '\''){
//                                sb.append("\\");
//                            }
//                            sb.append(ch);
//                        }
//                        val.setVal(sb.toString());
//                    }
                }
                if (filter.contains(key.toLowerCase()) && value == null) {
                    value = "";
                    val.setVal(value);
                    val.setStr(true);
                }



                valueList.add(val);
            }
            valList.add(valueList);
        }
    }
}
