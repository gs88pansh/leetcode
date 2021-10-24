package com.answer.leetcode.guier.solutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class S0001 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.get(nums[i]).add(i);
            }else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(nums[i], list);
            }
        }
        int[] res = new int[2];
        for (int num : map.keySet()) {
            if (map.containsKey(target - num)) {
                res[0] = map.get(num).get(0);
                if ((target - num) == num &&   map.get(num).size() > 0) {
                    res[1] = map.get(num).get(1);
                    return res;
                }else if ((target - num) != num){
                    res[1] = map.get(num).get(0);
                    return res;
                }
            }
        }
        return null;
    }
}
