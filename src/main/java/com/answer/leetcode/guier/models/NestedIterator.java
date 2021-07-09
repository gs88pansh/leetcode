package com.answer.leetcode.guier.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NestedIterator implements Iterator<Integer> {

    static class Entry {
        private List<NestedInteger> nestedIntegers;
        private int index;

        public Entry(List<NestedInteger> nestedIntegers, int index) {
            this.nestedIntegers = nestedIntegers;
            this.index = index;
        }

        public List<NestedInteger> getNestedIntegers() {
            return nestedIntegers;
        }

        public void setNestedIntegers(List<NestedInteger> nestedIntegers) {
            this.nestedIntegers = nestedIntegers;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    private final List<Entry> stack = new ArrayList<>();

    private Integer next;

    public NestedIterator(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0) {
            return;
        }
        stack.add(new Entry(nestedList, 0));
        next();
    }

    @Override
    public Integer next() {
        Integer tmp = next;
        if (stack.size() == 0) {
            next = null;
            return tmp;
        }
        Entry lastEntry = stack.get(stack.size() - 1);
        NestedInteger res = lastEntry.getNestedIntegers().get(lastEntry.getIndex());

        while (!res.isInteger()) {
            if (res.getList().size() == 0) {
                lastEntry.setIndex(lastEntry.getIndex() + 1);
                if (lastEntry.getIndex() == lastEntry.getNestedIntegers().size()) {
                    stack.remove(stack.size() - 1);
                }
                if (stack.size() == 0) {
                    next = null;
                    return tmp;
                }
                lastEntry = stack.get(stack.size() - 1);
                res = lastEntry.getNestedIntegers().get(lastEntry.getIndex());
                continue;
            }
            lastEntry.setIndex(lastEntry.getIndex() + 1);
            if (lastEntry.getIndex() == lastEntry.getNestedIntegers().size()) {
                stack.remove(stack.size() - 1);
            }
            List<NestedInteger> newNested = res.getList();
            lastEntry = new Entry(newNested, 0);
            stack.add(lastEntry);
            res = newNested.get(0);
        }
        next = res.getInteger();
        lastEntry.setIndex(lastEntry.getIndex() + 1);
        if (lastEntry.getIndex() == lastEntry.getNestedIntegers().size()) {
            stack.remove(stack.size()-1);
        }
        return tmp;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }
}