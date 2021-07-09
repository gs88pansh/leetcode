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

    public String toString() {
        if (this.isInteger()) {
            return this.i.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i=0;i<this.nestedIntegers.size(); i++) {
            sb.append(this.nestedIntegers.get(i));
            if (i < this.nestedIntegers.size() - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
