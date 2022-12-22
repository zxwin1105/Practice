package com.gs.replace;

import java.util.Map;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class ReplaceTemplate {

    private final Map<String,String> replaceMap;

    private final String rootPath;

    private final static String DEVICE_PATH = "/deviceList";
    private final static String CONFIG_PATH = "/config";
    private final static String TELEMETRY_PATH = "/telemetry";

    public ReplaceTemplate(Map<String,String> map, String rootPath) {
        this.replaceMap = map;
        this.rootPath = rootPath;
    }

    public void replace(){
        // 替换 DeviceList文件
        ReplaceDeviceList replaceDeviceList = new ReplaceDeviceList();
        replaceDeviceList.replace(replaceMap, rootPath + DEVICE_PATH);
        // 替换 Config文件
        ReplaceConfig replaceConfig = new ReplaceConfig();
        replaceConfig.replace(replaceMap, rootPath + CONFIG_PATH);

        // 替换 telemetry
        replaceConfig.replace(replaceMap, rootPath + TELEMETRY_PATH);
    }
}
