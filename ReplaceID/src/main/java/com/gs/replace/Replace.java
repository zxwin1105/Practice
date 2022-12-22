package com.gs.replace;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.util.Map;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public interface Replace {

    void replace(Map<String, String> replaceMap, String path);


    /**
     * 将jsonObject写出到文件
     */
    default void write(JSONObject jsonObject, File file){
        try {
            String res = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
