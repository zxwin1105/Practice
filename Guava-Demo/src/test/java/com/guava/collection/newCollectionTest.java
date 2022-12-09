package com.guava.collection;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zhaixinwei
 * @date 2022/12/9
 */
public class newCollectionTest {

    private final newCollection collection = new newCollection();

    @Test
    public void countWordTest() {
        collection.countWordOld();

        collection.countWordNew();
    }


    @Test
    public void sortMultiSet() {
        collection.sortMultiSet();
    }

    @Test
    public void multiMap() {
        collection.multiMap();
    }


}