package com.answer.leetcode.guier.utils;

import com.answer.leetcode.guier.models.NestedInteger;
import com.answer.leetcode.guier.models.NestedIntegeror;

import java.util.ArrayList;
import java.util.List;

public class NestedIntegerBuilder {

    public static class Index {
        public Integer index;
        public Index(Integer index) {
            this.index = index;
        }
    }

    public static NestedInteger buildNestedInteger(String cases) {
        return buildNestedInteger(cases, new Index(0));
    }

    private static NestedInteger buildNestedInteger(String cases, Index index) {
        NestedInteger nestedInteger = new NestedIntegeror(null, new ArrayList<>());
        index.index ++;
        int tmpIndex = index.index;
        while (index.index < cases.length()) {
            if (cases.charAt(index.index) == ']') {
                index.index++;
                return nestedInteger;
            }else if (cases.charAt(index.index) == '[') {
                NestedInteger nn = buildNestedInteger(cases, index);
                nestedInteger.getList().add(nn);
                tmpIndex = index.index + 1;
            }else {
                index.index ++;
                if (cases.charAt(index.index) != ',') {
                    continue;
                }

                if (index.index > tmpIndex) {
                    String number = cases.substring(tmpIndex, index.index);
                    Integer n = Integer.valueOf(number);
                    nestedInteger.getList().add(new NestedIntegeror(n, null));
                }
                tmpIndex = index.index + 1;
            }
        }
        return nestedInteger;
    }

    public static void main(String[] args) {
        NestedInteger n;
        n = buildNestedInteger("[[]]");
        System.out.println(n);
        n = buildNestedInteger("[1,2,[1,[1,[[[],[],[1,2,3,[[]],4]]],2,[[]]]]]");
        System.out.println(n);
    }
}
