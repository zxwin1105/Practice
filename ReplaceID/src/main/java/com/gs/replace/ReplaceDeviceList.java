package com.gs.replace;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class ReplaceDeviceList implements Replace {

    private final static String FILE_NAME = "/deviceList.json";

    private final static String MACHINE_ERPID_KEY ="machineERPID";

    @Override
    public void replace(Map<String, String> replaceMap, String path){
        // 读取文件
        path = path + FILE_NAME;
        InputStream inputStream = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        // 替换json值
        try {
            inputStream = new FileInputStream(path);
            jsonObject = JSONObject.parseObject(inputStream, JSONObject.class);
            jsonArray = jsonObject.getJSONArray("body");

            jsonArray.forEach(item ->{
                JSONObject json =(JSONObject) item;
                String oldId = json.get(MACHINE_ERPID_KEY).toString();
                String newId = replaceMap.get(oldId);
                if(!StringUtils.isBlank(newId)){
                    System.out.println("deviceList.json文件 替换："+oldId);
                    json.replace(MACHINE_ERPID_KEY,newId);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 写出到文件
        write(jsonObject,new File(path));
    }
}
