##LeetCode Online Judge Practise

Update frequency: one day one problem.

All answers are done by myself, in other words, answers are may be not the best but passed.

From these subjects, I found dynamic programming is very important.

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

###Rotate Array

    Rotate an array of n elements to the right by k steps.

    For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].

The first method: we can allocate a new array, and set each value with array[i + k]. Once i+k bigger than array.length, relocate with i+k-array.length.
The second method: let's find the turn point 5 in the sample. Reverse the [1,2,3,4] and [5,6,7] then we get [4,3,2,1,7,6,5], reverse the whole array is ok then we get [5,6,7,1,2,3,4].

```java
public class Solution {
    public void rotate(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k == 0 || nums.length == 1){
            return;
        }
       k = k % nums.length;
       reverse(nums, 0, nums.length - k - 1);
       reverse(nums, nums.length -k, nums.length-1);
       reverse(nums, 0, nums.length - 1);

    }
    private void reverse(int[] nums, int begin, int end){
        int tmp = 0;
        while(begin < end){
            tmp = nums[end];
            nums[end] = nums[begin];
            nums[begin] = tmp;
            begin ++;
            end --;
        }
    }
}
```

###Summary Ranges

    Given a sorted integer array without duplicates, return the summary of its ranges.

    For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].

Let's use two cursor as begin and end. if nums[end+1] = nums[end]+1 then end++, else the last number of substring is found.

```java
public class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return list;
        }
        int begin = 0;
        int last = 0;
        while (last < nums.length) {
            if (last + 1 < nums.length && nums[last + 1] == nums[last] + 1) {
                last++;
            } else {
                if (begin == last) {
                    list.add(nums[last] + "");
                } else {
                    list.add(nums[begin] + "->" + nums[last]);
                }
                last++;
                begin = last;
            }
        }
        return list;
    }
}
```

###Count Primes

    Count the number of prime numbers less than a non-negative number, n.

    Hint:
    Let's start with a isPrime function. To determine if a number is prime, we need to check if it is not divisible by any number less than n. The runtime complexity of isPrime function would be O(n) and hence counting the total prime numbers up to n would be O(n2). Could we do better?

    As we know the number must not be divisible by any number > n / 2, we can immediately cut the total iterations half by dividing only up to n / 2. Could we still do better?

Let's from i = 2 to n, and remove their times. 0 and 1 are not prime number.

```java
public class Solution {
    public int countPrimes(int n) {
        boolean[] array = new boolean[n];
        for (int i = 2; i * i  < n; i++) {
            if (!array[i]) {
                for (int j = i; i * j < n; j++) {
                    array[i * j] = true;
                }
            }
        }
        int count = 0;
        for(int i = 2; i < array.length; i ++){
            if(array[i] == false){
                count++;
            }
        }
        return count;
    }
}
```


###Missing Number

    Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

    For example,
    Given nums = [0, 1, 3] return 2.

    Note:
    Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?

Find it with the sum of arithmetic progression.

```java
public class Solution {
    public int missingNumber(int[] nums) {
        if(nums == null){
            return 0;
        }
        int total = 0; 
        for(int i : nums){
            total += i;
        }
        int length = nums.length+1;
        int sum = length*(length-1)/2;
        return sum - total;
    }
}
```


###House Robber

    You are a professional robber planning to rob houses along a street.
    Each house has a certain amount of money stashed,
    the only constraint stopping you from robbing each of them is that adjacent houses have security system connected
    and it will automatically contact the police if two adjacent houses were broken into on the same night.

    Given a list of non-negative integers representing the amount of money of each house,
    determine the maximum amount of money you can rob tonight without alerting the police.

This is a kind of DP problem.
Let's set the max value before house i is maxV[i],
then this problem can be considered as maxV[i] = Math.max(maxV[i-2]+num[i], maxV[i-1])

```java
public class Solution {
    public int rob(int[] nums) {
       if (nums == null || nums.length == 0) {
            return 0;
        } else {
            int length = nums.length;
            if (length == 1) {
                return nums[0];
            } else {
                int[] vector = new int[length];
                vector[0] = nums[0];
                vector[1] = Math.max(nums[0], nums[1]);
                for (int i = 2; i < length; i++) {
                    vector[i] = Math.max(vector[i - 2] + nums[i], vector[i - 1]);
                }
                return vector[length - 1];
            }
        }
    }
}
```


###Ugly Number

    Write a program to check whether a given number is an ugly number.

    Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.

    Note that 1 is typically treated as an ugly number.

Do it as it told us.

```java
    public boolean isUgly(int num) {
        if (num == 0) {
            return false;
        } else {
            int[] array = new int[]{5, 3, 2};
            for (int i : array) {
                while (num % i == 0) {
                    num = (num / i);
                }
            }
            return num == 1;
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


