package com.interview.java.spi;

import java.util.ServiceLoader;

/**
 * @author zhaixinwei
 * @date 2023/1/12
 */
public class SPIApplication {

    public static void main(String[] args) {
        ServiceLoader<Search> serviceLoader = ServiceLoader.load(Search.class);
        serviceLoader.iterator();
        for (Search next : serviceLoader) {
            next.search();
        }
    }
}
