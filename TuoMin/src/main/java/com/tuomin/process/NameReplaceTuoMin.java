package com.tuomin.process;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhaixinwei
 * @date 2023/1/5
 */
public class NameReplaceTuoMin implements TuoMin{

    public static final Map<String,String> NAME_REPLACE_MAP = new HashMap<String,String>(){{
       put("第一采油厂","淄博管理区");
       put("第二采油厂","富满管理区");
       put("第三采油厂","管理区一");
       put("第四采油厂","管理区二");
       put("第五采油厂","管理区三");
       put("第六采油厂","管理区四");
       put("对外合作项目部","管理区");
       put("小一联","塔里木-一联");
       put("官一联","塔里木-二联");
       put("枣一联","塔里木-三联");
       put("孔店联合站","塔里木-四联");
       put("羊三木联合站","塔里木-五联");
       put("南一联","塔里木-六联");
       put("庄一联","塔里木-七联");
       put("西一联","塔里木-八联");
       put("埕海联","塔里木-九联");
       put("滨海储运库","塔里木-十联");
       put("港东联","塔里木-十一联");
       put("板一联","塔里木-十二联");

    }};

    @Override
    public void doTuoMin(JSONObject jsonObject, List<String> filterKey) {
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            if(filterKey.contains(key)){
                continue;
            }
            Object value = jsonObject.get(key);
            if(value instanceof String ){
                String mapKey = String.valueOf(value);
                if(NAME_REPLACE_MAP.containsKey(mapKey)){
                    System.out.println("替换名称："+ mapKey);
                    jsonObject.put(key, NAME_REPLACE_MAP.get(value));
                }

                if(mapKey.contains("大港")){
                    mapKey = mapKey.replace("大港","塔里木");
                    jsonObject.put(key, mapKey);
                }

                if(mapKey.contains("天津")){
                    mapKey = mapKey.replace("天津","新疆");
                    jsonObject.put(key, mapKey);

                }
            }
        }
    }
}
