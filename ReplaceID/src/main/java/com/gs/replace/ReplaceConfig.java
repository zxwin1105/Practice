package com.gs.replace;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class ReplaceConfig implements Replace{
    private final static String MACHINE_ERPID_KEY = "machineERPID";

    @Override
    public void replace(Multimap<String, String> replaceMap, String readPath, String outPath) {
        // 获取当前path路径下的所有文件名称
        File filePath = new File(readPath);

        File[] files = filePath.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            // 获取文件名称（oldId）
            String oldId = getOldId(file);
            Collection<String> values = replaceMap.get(oldId);
            for (String value : values) {
                JSONObject jsonObject = replaceJson(oldId, value, file.getPath());
                write(jsonObject, new File(outPath+"/"+value+".json"));
                System.out.println(value+"替换"+oldId);
            }
        }
    }

    private JSONObject replaceJson(String oldId, String newId, String newFileName) {
        try {
            InputStream inputStream = new FileInputStream(newFileName);

            JSONObject jsonObject = JSONObject.parseObject(inputStream,JSONObject.class);
            if(oldId.equals(jsonObject.get(MACHINE_ERPID_KEY).toString())){
                jsonObject.replace(MACHINE_ERPID_KEY, newId);
            }
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getOldId(File fileName) {
        String name = fileName.toString();
        return name.substring(name.lastIndexOf("\\") + 1, name.lastIndexOf("."));
    }
}
