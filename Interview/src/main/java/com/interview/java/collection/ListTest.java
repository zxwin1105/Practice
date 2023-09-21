package com.interview.java.collection;

import java.util.*;

/**
 * @author seisei
 * @date 2023/7/17
 */
public class ListTest {

    public static void main(String[] args) {
        ListTest listTest = new ListTest();
        listTest.testRemove();
//        listTest.testMap();
//        listTest.testTreeSet();
    }
    /**
     * 如何安全的从集合中移除元素
     *
     * 如果删除倒数第二个元素不会fail-fast?
     * public boolean hasNext() {
     *             return cursor != size;
     * }
     * 可以看到hasNext判断方式cursor != size; cursor表示当前要遍历第几个元素
     * 如果移除了倒数第二个元素size = size-1,此时cursor==size,不会继续遍历，不会触发fail-fast
     *
     */
    private void testRemove(){
        // asList生成的List不能增删改操作
//        List<String> list = Arrays.asList("one","two","three","four");
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
//        for (int i = 0; i < list.size(); i++) {
//            String s = list.get(i);
//            if ("three".equals(s)){
//                list.remove(s);
//            }
//        }
        System.out.println(list);
        // foreach
        for (String s : list) {
            if ("two".equals(s)){
//                list.remove(s);
                list.add("tow.5");
            }
            System.out.println(s);
        }
        // 迭代器
//        Iterator<String> iterator = arrayList.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            if ("three".equals(next)) {
//                iterator.remove();
//            }
//        }

    }


    /**
     * hashMap 和 LinedHashMap
     */
    private void testMap(){

        Map<String,Integer> map = new HashMap<>(16);
        map.put("one",1);
        map.put("two",2);
        map.put("three",3);
        map.put("four",4);
        map.keySet().forEach(System.out::println);

        Map<String,Integer> linkedMap = new LinkedHashMap<>(16);
        linkedMap.put("one",1);
        linkedMap.put("two",2);
        linkedMap.put("three",3);
        linkedMap.put("four",4);
        linkedMap.keySet().forEach(System.out::println);
    }

    private void testTreeSet(){
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("one");
        treeSet.add("abs");
        treeSet.add("acc");
        treeSet.add("dfd");
        System.out.println(treeSet);
        System.out.println(treeSet.floor("c"));
    }
}
