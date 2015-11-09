##LeetCode Online Judge Practise

Update frequency: one day one problem.

All answers are done by myself, in other words, answers are may be not the best but passed.

##Medium

###Single Number III

    Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once.

    For example:

    Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].

    Note:
    The order of the result is not important. So in the above example, [5, 3] is also correct.
    Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?

There are more than one single number in the array, I think hashset is the only solution.

```java
public class Solution {
    public int[] singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int n : nums){
            if(set.contains(n)){
                set.remove(n);
            }else{
                set.add(n);
            }
        }
        int[] result = new int[set.size()];
        int i = 0;
        for(int x : set){
            result[i] = x;
            i ++;
        }
        return result;
    }
}
```

###Two Sum

    Given an array of integers, find two numbers such that they add up to a specific target number.

    The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.

    You may assume that each input would have exactly one solution.

    Input: numbers={2, 7, 11, 15}, target=9
    Output: index1=1, index2=2

Because of each input has one solution, compute each and others is ok. Any better algorithms?

```java
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] array = new int[2];
        for(int i = 0 ; i < nums.length - 1; i ++){
            int current = nums[i];

            for(int j = i+1; j < nums.length; j ++){
                int another = nums[j];
                if((current + another) == target){
                    array[0] = i+1;
                    array[1] = j+1;
                }
            }

        }
        return array;
    }
}
```

###Single Number
    
    Given an array of integers, every element appears twice except for one. Find that single one.

    Note:

    Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
    
To find the duplicate, hashset is helpful but need extra memory. Where's better algorithm?

```java
public class Solution {
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int total = 0;
        for(int i : nums){
            if(set.contains(i)){
                total -= i;
            }else{
                total += i;
            }
            set.add(i);
        }
        return total;
    }
}
```

##EASY

###Ugly Number

    Write a program to check whether a given number is an ugly number.

    Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.

    Note that 1 is typically treated as an ugly number.

The most important thing is the precision of float is not enough than double.

```java
public boolean isUgly(int num) {

        if (num == 0) {
            return false;
        } else {
            double[] array = new double[]{5.0, 3.0, 2.0};
            for (double i : array) {
                boolean flg = false;
                do {
                    double x = num / i;
                    flg = isTrue(x);
                    if (flg) {
                        num = (int)Math.round(x);
                    }
                } while (flg);
            }
            if (num == 2 || num == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean isTrue(double x) {
        long xi = Math.round(x);
        double theta = 0.0001;
        if (Math.abs(x - xi) > theta) {
            return false;
        } else {
            return true;
        }
    }
```

###Happy Number

    Write an algorithm to determine if a number is "happy".

    A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy numbers.

    Example: 19 is a happy number

    12 + 92 = 82
    82 + 22 = 68
    62 + 82 = 100
    12 + 02 + 02 = 1

Using a recursive program can solve this problem.

```java
public class Solution {
    public boolean isHappy(int n) {

        String numStr = n +"";
        int result = 0;
        for(char c : numStr.toCharArray()){
            int cnum = Integer.parseInt(""+c);
            result += (cnum * cnum);
        }
        if(result > 9){
            return isHappy(result);
        }else{
            if(result == 1){
                return true;
            }else{
                return false;
            }
        }
    }
}
```

###Majority element

    Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

    You may assume that the array is non-empty and the majority element always exist in the array.

Use a map to record the number of each num, once someone is over than n/2, return it.

```java
public class Solution {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        float theta = nums.length / 2.0f ;
        for(int num : nums){
            int sum = 0;
            if(map.containsKey(num)){
                sum = map.get(num);
            }
            sum += 1;
            if(sum > theta){
                return num;
            }
            map.put(num, sum);
        }
        return 0;
    }
} 
```

###Valid Anagram

    Given two strings s and t, write a function to determine if t is an anagram of s.

    For example,
    s = "anagram", t = "nagaram", return true.
    s = "rat", t = "car", return false.

    Note:
    You may assume the string contains only lowercase alphabets.

First, the subject aims to judge anagram, not contains. Anagram means two string with the same length and same characters.
Second, use two hashmap to compare is ok. After sorting, compare each character is also ok.

```java
public class Solution {
    public boolean isAnagram(String s, String t) {
        if(s == null || t == null){
            return false;
        }
        List<String> string = toString(s);
        List<String> tstring = toString(t);
        Collections.sort(string);
        Collections.sort(tstring);
        if(string.size() == tstring.size()){
            for(int i = 0; i < string.size(); i ++){
                String s1 = string.get(i);
                String s2 = tstring.get(i);
                if(!s1.equals(s2)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public List<String> toString(String s){
        char[] sChildren = s.toCharArray();
        List<String> list = new ArrayList<>();
        for(char c : sChildren){
            list.add(c+"");
        }
        return list;
    }
}
```



###Contains Duplicate

    Given an array of integers, find if the array contains any duplicates. Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.

Use hashset is easy, but there may be another better algorithm to do this.

```java
public class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int i : nums){
            if(set.contains(i)){
                return true;
            }
            set.add(i);
        }
        return false;
    }
}
```

###Invert Binary Tree
    Invert a binary tree.

                  4
                /   \
               2     7
              / \   / \
             1   3 6   9
    to
                  4
                /   \
               7     2
              / \   / \
             9   6 3   1

Just switch the position of leftNode and rightNode, and return the root.

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root != null){

            TreeNode leftNode = root.left;
            TreeNode rightNode = root.right;

            TreeNode middle = leftNode;

            root.left = invertTree(rightNode);
            root.right = invertTree(middle);
        }
        return root;

    }
}
```

###Move Zeroes

    Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

    For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].

    * You must do this in-place without making a copy of the array.
    * Minimize the total number of operations.

For this subject, two cursor are used. First one is used to remember the current index of number equal 0. The second one is used to find the next non-0 number. Switch them.

```java
    public void moveZeroes(int[] nums) {
        if (nums == null) {
            return;
        }
        for(int i = 0; i < nums.length; i ++){
            int a = nums[i];
            if(a == 0){
                for(int j = i+1; j < nums.length; j ++){
                    int b = nums[j];
                    if(b != 0){
                        nums[i] = b;
                        nums[j] = a;
                        break;
                    }
                }
            }
        }
    }
```


###Same Tree

    Given two binary trees, write a function to check if they are equal or not.

    Two binary trees are considered equal if they are structurally identical and the nodes have the same value.

This subject is easy to implement. I think this is the only way to solve this problem.

ps: This question is appeared in a interview with Microsoft China.

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        boolean flag = false;
        if(p == null && q == null){
            return true;
        }else if(p == null || q == null){
            return false;
        }else{
            int pv = p.val;
            int qv = q.val;

            if(pv == qv){
                flag =  true;
            }else{
                flag = false;
            }
        }    
        TreeNode pleft = p.left;
        TreeNode pright = p.right;

        TreeNode qleft = q.left;
        TreeNode qright = q.right;

        return flag && isSameTree(pleft, qleft) && isSameTree(pright, qright);
    }
}
```

###Maximum Depth of Binary Tree

    Given a binary tree, find its maximum depth.

    The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

This subject is very easy.

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public int maxDepth(TreeNode root) {

        if(root == null){
            return 0;
        }
        TreeNode leftNode = root.left;
        TreeNode rightNode = root.right;
        int depth = 1;
        if(leftNode != null || rightNode !=null){
            depth += Math.max(maxDepth(leftNode), maxDepth(rightNode));
        }else{
            return 1;
        }
        return depth;

    }
}
```

###Add Digits
    Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

    For example:
    Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

Recursive program is easy.

```java
   public int addDigits(int num) {
        if (num < 10) {
            return num;
        }

        String str = num + "";
        int count = 0;
        for (char c : str.toCharArray()) {
            int ic = Integer.parseInt(c + "");
            count += ic;
        }
        return addDigits(count);
    }
```

There is a hidden information, the result is a cycle about 9.

```java
    public int addDigits_v2(int num) {
        if (num < 10) {
            return num;
        } else {
            int x = num % 9;
            if (x == 0) {
                return 9;
            } else {
                return x;
            }
        }
    }
```

###Nim Game

	You are playing the following Nim Game with your friend: There is a heap of stones on the table, each time one of you take turns to remove 1 to 3 stones. 
    The one who removes the last stone will be the winner. You will take the first turn to remove the stones.
    
	Both of you are very clever and have optimal strategies for the game. Write a function to determine whether you can win the game given the number of stones in the heap.
    
	For example, if there are 4 stones in the heap, then you will never win the game: no matter 1, 2, or 3 stones you remove, the last stone will always be removed by your friend.

If you want to get a recursive program, you can follow this:

```java
    public boolean canWinNim(int n) {
        boolean flag = false;
        if (n <= 3) {
            flag = true;
        } else {
            flag = !(canWinNim(n - 1) && canWinNim(n - 2) && canWinNim(n - 3));
        }
        return flag;
    }
```

The recursive version is too slow.
Once thinking deeper, you can find that "do not left 4 to your friend".

```java
    public boolean canWinNim_v2(int n) {
        if (n % 4 == 0) {
            return false;
        } else {
            return true;
        }
    }
```


