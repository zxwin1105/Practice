package com.gs.replace;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.Map;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class ReplaceConfig implements Replace{
    private final static String MACHINE_ERPID_KEY = "machineERPID";

    @Override
    public void replace(Map<String, String> replaceMap, String path) {
        // 获取当前path路径下的所有文件名称
        File filePath = new File(path);

        File[] files = filePath.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            // 获取文件名称（oldId）
            String oldId = getOldId(file);
            String newId = replaceMap.get(oldId);
            if (!StringUtils.isBlank(newId)) {
                // 修改文件名称
                String newFileName = file.toString().replace(oldId, newId);
                if(file.renameTo(new File(newFileName))){
                    // 修改json内容
                    replaceJson(oldId, newId, newFileName);
                }else{
                    System.out.println("修改文件名失败，old:" + file.getPath() + "new:" + newFileName);
                }
            }
        }
    }

    private void replaceJson(String oldId, String newId, String newFileName) {
        try {
            InputStream inputStream = new FileInputStream(newFileName);

            JSONObject jsonObject = JSONObject.parseObject(inputStream,JSONObject.class);
            if(oldId.equals(jsonObject.get(MACHINE_ERPID_KEY).toString())){
                jsonObject.replace(MACHINE_ERPID_KEY, newId);
                write(jsonObject,new File(newFileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getOldId(File fileName) {
        String name = fileName.toString();
        return name.substring(name.lastIndexOf("\\") + 1, name.lastIndexOf("."));
    }
}
