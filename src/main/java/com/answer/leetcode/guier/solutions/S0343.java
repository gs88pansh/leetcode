package com.answer.leetcode.guier.solutions;

public class S0343 {

    public static int[] tmp = new int[59];
    public int integerBreak(int n) {
        if (n == 2) {
            return 1;
        }
        if (tmp[n] > 0) {
            return tmp[n];
        }
        int min = 1;
        for (int i = 1; i < n; i++) {
            min = Math.max(min, i * integerBreak(n - i));
            min = Math.max(min, i * (n - i));
        }
        tmp[n] = min;
        return min;
    }

    public static void main(String[] args) {
        System.out.println(new S0343().integerBreak(3));
    }
}
