package com.answer.leetcode.guier.models;

import java.util.List;

public class NestedIntegeror implements NestedInteger{

    Integer i;
    List<NestedInteger> nestedIntegers;

    public NestedIntegeror(Integer i, List<NestedInteger> nestedIntegers) {
        if (i == null && nestedIntegers == null) {
            throw new RuntimeException();
        }
        if (i != null && nestedIntegers != null) {
            throw new RuntimeException();
        }
        this.i = i;
        this.nestedIntegers = nestedIntegers;
    }

    @Override
    public boolean isInteger() {
        return i != null;
    }

    @Override
    public Integer getInteger() {
        return i;
    }

    @Override
    public List<NestedInteger> getList() {
        return nestedIntegers;
    }
}
