package com.answer.leetcode.guier.solutions;

/**
 * @see <a href="https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/submissions/">无重复的最长子串</>
 *
 * 类型：解题思路滑动窗口
 */
public class S0003 {
    public int lengthOfLongestSubstring(String s) {
        int[] numsOfChars = new int[1028];
        int leftIndex = 0;
        int maxNum = 0;
        int currentNum = 0;
        for (int i = 0; i < s.length(); i ++) {
            char charNum = s.charAt(i);
            numsOfChars[charNum] ++;
            currentNum ++;
            if (numsOfChars[charNum] > 1) {
                for (; leftIndex < i && numsOfChars[charNum] > 1; leftIndex ++) {
                    numsOfChars[s.charAt(leftIndex)] --;
                    currentNum --;
                }
            }
            maxNum = Math.max(currentNum, maxNum);
        }
        return maxNum;
    }

    public static void main(String[] args) {
        S0003 s0003 = new S0003();
        System.out.println(s0003.lengthOfLongestSubstring("pwwkew"));
    }
}
