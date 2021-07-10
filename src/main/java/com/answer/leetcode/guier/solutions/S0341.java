package com.answer.leetcode.guier.solutions;


import com.answer.leetcode.guier.models.NestedInteger;
import com.answer.leetcode.guier.models.NestedIterator;
import com.answer.leetcode.guier.utils.NestedIntegerBuilder;

public class S0341 {

    public static void main(String[] args) {
        NestedInteger nestedInteger = NestedIntegerBuilder.buildNestedInteger("[1,2,2,[1,2,3,[1,2,3],3],3]");
        System.out.println(nestedInteger);
        NestedIterator nestedIterator = new NestedIterator(nestedInteger.getList());
        while (nestedIterator.hasNext()) {
            System.out.println(nestedIterator.next());
        }
    }

}
