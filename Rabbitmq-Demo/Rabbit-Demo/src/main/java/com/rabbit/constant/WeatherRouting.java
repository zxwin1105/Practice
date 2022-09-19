package com.rabbit.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义Topic用到的消息
 *
 * @author zhaixinwei
 * @date 2022/9/16
 */
public class WeatherRouting {
    /** exchange名称 */
    public static final String WEATHER_EXCHANGE_ROUTING = "weather_exchange_routing";

    public static final Map<String,String> WEATHER_MAP = new HashMap<>();

    static {
        WEATHER_MAP.put("weather.xian.20220915","stream");
        WEATHER_MAP.put("weather.xian.20220914","wind");
        WEATHER_MAP.put("weather.xian.20220913","rain");
        WEATHER_MAP.put("weather.xian.20220912","sun");
        WEATHER_MAP.put("weather.xian.20220911","sun");

        WEATHER_MAP.put("weather.beijing.20220915","wind");
        WEATHER_MAP.put("weather.beijing.20220914","wind");
        WEATHER_MAP.put("weather.beijing.20220913","rain");
        WEATHER_MAP.put("weather.beijing.20220912","rain");
        WEATHER_MAP.put("weather.beijing.20220911","sun");

        WEATHER_MAP.put("weather.guangzhou.20220915","wind");
        WEATHER_MAP.put("weather.guangzhou.20220914","rain");
        WEATHER_MAP.put("weather.guangzhou.20220913","rain");
        WEATHER_MAP.put("weather.guangzhou.20220912","sun");
        WEATHER_MAP.put("weather.guangzhou.20220911","rain");
    }
}
