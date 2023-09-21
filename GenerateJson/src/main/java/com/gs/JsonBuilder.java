package com.gs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author zxwin
 * @date 2022/12/29
 */
public class JsonBuilder {

    public static final String JSON_HEADER = "{\"EQ_CODE\":\"\",\"TYPE_CODES\":[{\"TYPE_CODE\":\"\"}]}";


    public JSONObject generate(List<RowContent> params, String outDir) {

        JSONArray paramsJson = new JSONArray();
        for (RowContent param : params) {
            JSONObject object = new JSONObject();
            object.put(param.getKey(), param.getValue().toString());
            paramsJson.add(object);
        }

        JSONObject jsonObject = JSONObject.parseObject(JSON_HEADER);
        JSONArray typeCodes = jsonObject.getJSONArray("TYPE_CODES");
        JSONObject jsonObject1 = typeCodes.getJSONObject(0);
        jsonObject1.put("params", paramsJson);
        System.out.println("生成json" + jsonObject);
        return jsonObject;
    }

    public void write(JSONObject jsonObject, File file) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            String res = JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(res);
            writer.flush();
            System.out.println("输出到：" + file.getPath());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
