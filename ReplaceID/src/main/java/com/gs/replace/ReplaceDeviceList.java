package com.gs.replace;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Multimap;
import com.gs.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class ReplaceDeviceList implements Replace {

    private final static String FILE_NAME = "/deviceList.json";

    private final static String MACHINE_ERPID_KEY = "machineERPID";

    @Override
    public void replace(Multimap<String, String> replaceMap, String readPath, String outPath) {
        // 读取文件
        readPath = readPath + FILE_NAME;

        // 替换json值
        try {
            InputStream inputStream = new FileInputStream(readPath);
            JSONObject jsonObject = JSONObject.parseObject(inputStream, JSONObject.class);
            JSONArray jsonArray = jsonObject.getJSONArray("body");

            final JSONObject newJson = jsonObject;
            newJson.remove("body");
            newJson.put("body",new JSONArray());

            jsonArray.forEach(item -> {
                JSONObject json = (JSONObject) item;
                String oldId = json.get(MACHINE_ERPID_KEY).toString();
                if(StringUtil.isNotBlank(oldId)){
                    Collection<String> values = replaceMap.get(oldId);
                    for (String value : values) {
                        JSONObject jo = JSONObject.parseObject(json.toString());
                        jo.replace(MACHINE_ERPID_KEY, value);
                        newJson.getJSONArray("body").add(jo);
                        System.out.println("DeviceList-> "+value+"替换"+oldId);
                    }
                }
            });
            // 写出到文件
            write(newJson, new File(outPath+FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
