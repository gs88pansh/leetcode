package com.answer.leetcode.guier.solutions;


import com.answer.leetcode.guier.models.NestedInteger;
import com.answer.leetcode.guier.models.NestedIntegeror;
import com.answer.leetcode.guier.models.NestedIterator;

import java.util.ArrayList;
import java.util.List;

public class S0341 {

    public static void main(String[] args) {
        List<NestedInteger> nestedList = new ArrayList<>();
        nestedList.add(new NestedIntegeror(null, new ArrayList<>()));
        NestedIterator nestedIterator = new NestedIterator(nestedList);
        while (nestedIterator.hasNext()) {
            System.out.println(nestedIterator.next());
        }
    }

}
