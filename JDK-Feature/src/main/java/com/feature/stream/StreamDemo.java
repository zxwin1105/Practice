package com.feature.stream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaixinwei
 * @date 2022/7/27
 * @description jdk流式操作
 */
public class StreamDemo {


    /**
     * 创建流
     * 在使用流之前，我们需要有一个<b>数据源</b>，再通过streamAPI获取数据源的流对象。
     *
     * <p> <b>数据源<b/>有多种形式包括：集合、数值、多个值、文件、iterator</p>
     */
    public void createStream() throws IOException {
        // 集合获取流对象
        List<String> list = new ArrayList<>();
        Stream<String> listStream = list.stream();

        // 数值获取流对象
        String[] array = new String[10];
        Arrays.stream(array);

        // 值获取流对象
        Stream<String> valueStream = Stream.of("stream");

        // 文件获取流对象
        Stream<String> lines = Files.lines(Paths.get("stream/test"), Charset.defaultCharset());

        // 迭代器获取流对象
        Stream<Integer> iterate = Stream.iterate(0, n -> n + 1);
    }


    /**
     * 流操作过滤器
     */
    public static void streamFilter() {
        Stream<String> stream = Stream.of("test", "stream", "filter");
        System.out.println("filter before:" + stream.toString());
        List<String> collect = stream.filter(item -> item.length() > 4).collect(Collectors.toList());
        System.out.println("filter after:" + collect);
    }


    public static void streamDistinct() {
        Stream<String> stream = Stream.of("test", "stream", "test", "distinct", "test", "distinct");
        System.out.println(stream.distinct().collect(Collectors.toList()));
    }

    public static void streamLimit() {
        Stream<String> stream = Stream.of("test", "stream", "test", "distinct", "test", "distinct");
        System.out.println(stream.limit(4).collect(Collectors.toList()));
    }

    public static void streamSkip() {
        Stream<String> stream = Stream.of("test", "stream", "test", "distinct", "test", "distinct");
        System.out.println(stream.skip(2).collect(Collectors.toList()));
    }

    public static void streamMap() {
        Stream<String> stream = Stream.of("test", "stream", "test", "distinct", "test", "distinct");
        System.out.println(stream.map(item -> item + "1").collect(Collectors.toList()));
    }

    public static void mergeStream() {
        Stream<String> stream = Stream.of("I am a boy", "I am a girl", "But the girl loves another girl");
        Stream<String> stream1 = stream.map(item -> item.split(" ")).flatMap(Arrays::stream);
        System.out.println(stream1.collect(Collectors.toList()));

    }

    public static void test() {
        Stream<String> stream = Stream.of("test", "stream", "test", "distinct", "test", "distinct");
        Optional<String> any = stream.findAny();
    }

    public static void main(String[] args) {
        streamFilter();
        streamDistinct();
        streamLimit();
        streamSkip();
        streamMap();
        mergeStream();
    }
}
