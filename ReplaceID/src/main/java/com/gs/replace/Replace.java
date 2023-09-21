package com.gs.replace;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Multimap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public interface Replace {

    void replace(Multimap<String, String> replaceMap, String readPath, String outPath);


    /**
     * 将jsonObject写出到文件
     */
    default void write(JSONObject jsonObject, File file) {
        try {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            String res = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
