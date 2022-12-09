package com.guava.collection;

import com.google.common.collect.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaixinwei
 * @date 2022/12/9
 */
public class newCollection {

    /**
     * MultiMap使用
     * 常用的ListMultiMap, SetMultiMap
     */
    public void multiMap() {
        // 构建MultiMap，需要提供key和val的展现修饰
        ListMultimap<String, Integer> listMultimap = MultimapBuilder.hashKeys().arrayListValues().build();

        listMultimap.put("a", 1);
        listMultimap.put("a", 2);
        listMultimap.put("a", 3);
        listMultimap.put("b", 5);
        listMultimap.put("c", 6);
        listMultimap.putAll("d", Arrays.asList(1, 2, 3, 4));
        System.out.println("put:" + listMultimap);
        System.out.println("get:" + listMultimap.get("a"));
        // get是获取一个视图，会保留操作结果
        listMultimap.get("a").add(4);
        listMultimap.get("e").add(4);

        listMultimap.remove("a", 1);
        System.out.println("remove:" + listMultimap);

        // 视图，对视图的remove操作会保留到原对象， 视图不支持put操作
        Map<String, Collection<Integer>> asMap = listMultimap.asMap();
        asMap.remove("a");
        System.out.println("asMap removed asMap is:" + asMap);
        System.out.println("asMap removed multiMap is" + listMultimap);

        System.out.println("key"+ listMultimap.keys());
        System.out.println("key"+ listMultimap.keySet());
        System.out.println("values:" + listMultimap.values());

    }

    /**
     * 支持排序的MultiSet
     */
    public void sortMultiSet() {
        Integer[] arr = {1, 2, 4, 5, 6, 7, 10, 79, 44};
        SortedMultiset<Integer> sortedMultiset = TreeMultiset.create();
        sortedMultiset.addAll(Arrays.asList(arr));
        System.out.println(sortedMultiset);
        // 获取范围值，BoundType.CLOSED包含 BoundType.OPEN不包含
        System.out.println(sortedMultiset.subMultiset(1, BoundType.CLOSED, 7, BoundType.CLOSED));
        // 倒叙排列
        System.out.println(sortedMultiset.descendingMultiset());
    }

    /**
     * Multiset集成自Collection，可以存在多个相同元素
     * 可以用两种方式看待MultiSet:
     * - 没有元素顺序限制的ArrayList<E>
     * - Map<E, Integer>，键为元素，值为计数
     */
    public void countWordNew() {
        String[] words = {"word", "word", "ppt", "html", "ppt", "demo", "test"};

        Multiset<String> counts = HashMultiset.create();
        counts.addAll(Arrays.asList(words));
        // 统计给定值在集合中的个数
        System.out.println(counts.count("ppt"));
        System.out.println(counts);

        // 集合中不重复的元素集合 Set
        System.out.println(counts.elementSet());
    }


    public void countWordOld() {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        String[] words = {"word", "word", "ppt", "html", "ppt", "demo", "test"};
        for (String word : words) {
            Integer count = counts.get(word);
            if (count == null) {
                counts.put(word, 1);
            } else {
                counts.put(word, count + 1);
            }
        }
        System.out.println(counts);
    }
}
