##LeetCode Online Judge Practise

Update frequency: one day one problem.

All answers are done by myself, in other words, answers are may be not the best but accepted.

From these subjects, I found dynamic programming is very important.

##Hard

###Merge Intervals

    Given a collection of intervals, merge all overlapping intervals.

    For example,
    Given [1,3],[2,6],[8,10],[15,18],
    return [1,6],[8,10],[15,18].

First, sort the inputs order by its start.
Second, merge intervals one by one with a cursor.

```java
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Solution {
    public List<Interval> merge(List<Interval> intervals) {
        if(intervals == null || intervals.size() == 0)   return intervals;

        Collections.sort(intervals, new Comparator<Interval>(){
            @Override
            public int compare(Interval int1, Interval int2){
                return int1.start - int2.start;
            }
        });
        List<Interval> result = new ArrayList<>();
        Interval cursor = intervals.get(0);
        for(int i = 1; i < intervals.size(); i ++){
            if(cursor.end < intervals.get(i).start){
                result.add(cursor);
                cursor = intervals.get(i);
            }else{
                cursor.end = Math.max(cursor.end, intervals.get(i).end);
            }
        }
        result.add(cursor);
        return result;
    }
}
```

###Merge k Sorted Lists

    Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

First, let's merge them with reverse.
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;
        if(lists.length == 1) return lists[0];

        int mid = (lists.length-1) / 2;

        ListNode[] low = sub(lists, 0, mid+1);
        ListNode[] high = sub(lists, mid+1, lists.length);

        ListNode lowNode = mergeKLists(low);
        ListNode highNode = mergeKLists(high);

        return merge2(lowNode, highNode);
    }

    private ListNode[] sub(ListNode[] list, int begin, int end){
        int length = end - begin;
        ListNode[] res = new ListNode[length];
        for(int i = begin; i < end; i ++){
            res[i - begin] = list[i];
        }
        return res;
    }

    public ListNode merge2(ListNode node1, ListNode node2){
        ListNode head = new ListNode(0);
        ListNode cursor = head;
        while(node1 != null && node2 != null){
            int v1 = node1.val;
            int v2 = node2.val;

            if(v1 < v2){
                cursor.next = node1;
                node1 = node1.next;
            }else{
                cursor.next = node2;
                node2 = node1.next;
            }
            cursor = cursor.next;
        }
        if(node1 != null){
            cursor.next = node1;
        }
        if(node2 != null){
            cursor.next = node2;
        }
        return head.next;
    }
}
```
unfortunately, codes above can not pass the time requirement.
Do it with PriorityQueue
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        int length = lists.length;
        if (length == 0)
			return null;

		//PriorityQueue is a sorted queue
		PriorityQueue<ListNode> q = new PriorityQueue<ListNode>(length,
				new Comparator<ListNode>() {
					public int compare(ListNode a, ListNode b) {
						if (a.val > b.val)
							return 1;
						else if(a.val == b.val)
							return 0;
						else
							return -1;
					}
				});

		//add first node of each list to the queue
		for (ListNode list : lists) {
			if (list != null)
				q.add(list);
		}

		ListNode head = new ListNode(0);
		ListNode p = head; // serve as a pointer/cursor

		while (q.size() > 0) {
			ListNode temp = q.poll();
			//poll() retrieves and removes the head of the queue - q.
			p.next = temp;

			//keep adding next element of each list
			if (temp.next != null)
				q.add(temp.next);

			p = p.next;
		}

		return head.next;
    }
}
```

###Edit Distance

    Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)

    You have the following 3 operations permitted on a word:

    a) Insert a character
    b) Delete a character
    c) Replace a character

Another dynamic programming problem. Compare character one by one, and then dynamic programming.

```java
public class Solution {
    public int minDistance(String word1, String word2) {
        int row = word1.length()+1;
        int col = word2.length()+1;
        int[][] matrix = new int[row][col];
        for(int i = 0; i < row; i ++){
            matrix[i][0] = i;
        }
        for(int j = 0; j < col; j ++){
            matrix[0][j] = j;
        }
        for(int i = 1; i < row; i ++){
            for(int j = 1; j < col; j ++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    matrix[i][j] = matrix[i-1][j-1];
                }else{
                    matrix[i][j] = matrix[i-1][j-1]+1;
                }
                matrix[i][j] = Math.min(matrix[i][j], Math.min(matrix[i-1][j] + 1, matrix[i][j-1] + 1 ));
            }
        }
        return matrix[row-1][col-1];
    }
}
```

###Trapping Rain Water

    Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.

    For example, 
    Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.

Set a secondHigh, move cursor from end to middle and add (secondHigh - min(num[begin], num[end]).

```java
public class Solution {
    public int trap(int[] height) {
    int n = height.length;
    int secHight = 0;
    int left = 0;
    int right = n-1;
    int area = 0;
    while (left < right){
        if (height[left] < height[right]){
            secHight = Math.max(height[left], secHight);
            area += secHight-height[left];
            left++;
        } else {
            secHight = Math.max(height[right], secHight);
            area += secHight-height[right];
            right--;
        }
    }
    return area;
}
```

###Longest Consecutive Sequence

    Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

    For example,
    Given [100, 4, 200, 1, 3, 2],
    The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

    Your algorithm should run in O(n) complexity.

Because of the O(n) requirement, sort the array is not allowed.
We can search each num and increse it or decrease it to check whether the new num is also in the array.

```java
public class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int i : nums){
            set.add(i);
        }
        int res = 1;
        for(int i = 0; i < nums.length; i ++){
            int curr = nums[i];
            int curr_high = curr +1;
            int tmpLength = 1;
            while(set.contains(curr_high)){
                set.remove(curr_high);//remove it is very important to fulfill the O(n) requirement.
                tmpLength ++;
                curr_high++;
            }

            int curr_low = curr -1;
            while(set.contains(curr_low)){
                set.remove(curr_low);//remove it is very important to fulfill the O(n) requirement.
                tmpLength ++;
                curr_low--;
            }
            if(tmpLength >= res){
                res = tmpLength;
            }
        }
        return res;
    }
}
```

###Find the Duplicate number

    Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

    Note:
    You must not modify the array (assume the array is read only).
    You must use only constant, O(1) extra space.
    Your runtime complexity should be less than O(n2).
    There is only one duplicate number in the array, but it could be repeated more than once.

Let's think about this: the array from 1 to 10, let mid = 5, nums smaller than 5 should be 5, if bigger than 5, the duplicate number must exists in the lower, else, in the upper.

```java
public class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length -1 ;
        int low = 0;
        int high = n;
        int middle = 0;
        while(low < high){
            middle = low + (high-low)/2;
            int c = count(nums, middle);
            if(c <= middle){
                low = middle + 1;
            }else{
                high = middle;
            }
        }
        return nums[low];
    }
    private int count(int[] nums, int middle){
        int c = 0;
        for(int i = 0; i < nums.length; i ++){
            if(nums[i] <= middle){
                c ++;
            }
        }
        return c;
    }
}
```

###Search in Rotated Sorted array

    Suppose a sorted array is rotated at some pivot unknown to you beforehand.

    (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

    You are given a target value to search. If found in the array return its index, otherwise return -1.

    You may assume no duplicate exists in the array.

Find it with binary search, but the difference is how to move the cursor.

```java
public class Solution {
    public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0){
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while(left <= right){
            int middle = left + (right - left) / 2;
            if(nums[left] == target){
                return left;
            }else if(nums[right] == target){
                return right;
            }else 
                if(nums[middle] == target){
                    return middle;
                }
            if(nums[left] < nums[middle]){
                if(nums[left] < target && nums[middle] > target){
                    right = middle-1;
                }else{
                    left = middle + 1;
                }
            }else if(nums[left] > nums[middle]){
                if(nums[left] < target || nums[middle] > target){
                    right = middle -1;
                }else{
                    left = middle + 1;
                }
            }else{
                left ++;
            }
        }
        return -1;
    }
}
```


##Medium

###Meeting Rooms II

    Given an array of meeting time intervals consisting of start and end times [[s1,e1], [s2,e2],...] (si < ei), find the minimum number of conference rooms required.
    For example,
    Given [[0, 30],[5, 10],[15, 20]],
    return 2.

First, sort the inputs order by its abs value.
Then, check numbers in the list, if number>=0 then ++, else -- and compare it with max;

```java
public class Solution{

    public int minMeetingRooms(Interval[] intervals){
        if(intervals == null || intervals.length == 0){
            return 0;
        }
        if(intervals.length == 1){
            return 1;
        }
        List<Integer> list = new ArrayList<>();
        for(Interval inter : intervals){
            list.add(inter.start);
            list.add(-inter.end);
        }
        Collections.sort(list, new Comparator<Integer>(){
            @Override
            public int compare(Integer i1, Integer i2){
                return Math.abs(i1) - Math.abs(i2);
            }
        });
        int result = 0;
        int count = 0;
        for(int i = 0; i < list.size(); i ++){
            if(list.get(i) >= 0){
                count++;
            }else{
                count--;
            }
            result = Math.max(count, result);
        }
        return result;
    }
}

```

###Missing Ranges

    Given a sorted integer array where the range of elements are [lower, upper] inclusive, return its missing ranges.
    For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return ["2", "4->49", "51->74", "76->99"].

Scan the list, and update the lower cursor.

```java
public class Solution {
    public List<String> findMissingRanges(int[] A, int lower, int upper) {
        if(A==null){
            return null;
        }
        List<String> list = new ArrayList<>();

        for(int i = 0; i < A.length; i ++){
            while( i < A.length && A[i] == lower){
                lower++;
                i++;
            }
            if(i >= A.length){
                break;
            }
            int current = A[i];
            if(lower + 1 < current){
                list.add(lower+"->"+(current-1));
            }else{
                list.add(lower+"");
            }
            lower = current + 1;
        }
        if(lower == upper){
            list.add(lower+"");
        }else if(lower < upper){
            list.add(lower+"->"+upper);
        }
        return list;
    }
}
```

###Perfect Squares

    Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.

    For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.

Dynamic programming. Assume dp[n] is the least number of perfect square numbers.
For example. dp[9] = min(dp[9], dp[1]+dp[8]) min(dp[9], dp[2]+dp[7]) …… and so on...

```java
public class Solution {
    public int numSquares(int n) {

        int[] dp = new int[n+1];
        dp[1] = 1;
        for(int i = 2; i <= n ; i ++){
            int c = (int)Math.sqrt(i);
            if(i == c * c){
                dp[i] = 1;
            }else{
                dp[i] = i;
            }
            for(int a = 1; a <= i/2; a++){
                int b = i - a;
                dp[i] = Math.min(dp[i], dp[a] + dp[b]);
            }
        }
        return dp[n];
    }
}
```


###Number of Islands

    Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

    Example 1:

    11110
    11010
    11000
    00000
    Answer: 1

    Example 2:

    11000
    11000
    00100
    00011
    Answer: 3

Check each number of around 1, then change it to other number. DFS.

```java
public class Solution {
    int rows = 0;
    int cols = 0;
    public int numIslands(char[][] grid) {
        if(grid == null) return 0;
        rows = grid.length;
        if(rows == 0){
            return 0;
        }
        cols = grid[0].length;
        if(cols == 0){
            return 0;
        }

        int count = 0;
        for(int i = 0; i < rows; i ++){
            for(int j = 0; j < cols; j ++){
                if(grid[i][j] != '1'){
                    continue;
                }
                check(grid, i, j);
                count ++;
            }
        }
        return count;
    }

    public void check(char[][] grid, int i, int j){
        if(i >= rows || i < 0 || j >= cols || j< 0){
            return;
        }
        if(grid[i][j] == '1'){
            grid[i][j] = '2';
            check(grid, i-1, j);
            check(grid, i+1, j);
            check(grid, i, j-1);
            check(grid, i, j+1);
        }
    }

}
```


###Peeking Iterator

    Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator that support the peek() operation -- it essentially peek() at the element that will be returned by the next call to next().

    Here is an example. Assume that the iterator is initialized to the beginning of the list: [1, 2, 3].

    Call next() gets you 1, the first element in the list.

    Now you call peek() and it returns 2, the next element. Calling next() after that still return 2.

    You call next() the final time and it returns 3, the last element. Calling hasNext() after that should return false.

Use a cursor to identify the current position.

```java
// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
class PeekingIterator implements Iterator<Integer> {

    List<Integer> list = new ArrayList<>();
    int cursor = 0;

	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    while(iterator.hasNext()){
	        list.add(iterator.next());
	    }
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
        return list.get(cursor);
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
	    Integer res = list.get(cursor);
	    cursor++;
	    return res;
	}

	@Override
	public boolean hasNext() {
	    if(cursor >= list.size()){
	        return false;
	    }else{
	        return true;
	    }
	}
}
```

###Search a 2D Matrix

    Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

    Integers in each row are sorted from left to right.
    The first integer of each row is greater than the last integer of the previous row.
    For example,

    Consider the following matrix:

    [
      [1,   3,  5,  7],
      [10, 11, 16, 20],
      [23, 30, 34, 50]
    ]
    Given target = 3, return true.

First, do binary search to find the right row. Second, binary search the right row.

```java
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {

        int lowRow = 0;
        int highRow = matrix.length - 1;
        int colSize = matrix[0].length-1;
        while(lowRow < highRow){
            int midRow = lowRow + (highRow - lowRow) /2;
            int end = matrix[midRow][colSize];
            if(end < target){
                lowRow = midRow + 1;
            }else{
                highRow = midRow;
            }
        }

        int rightRow = lowRow;
        int low = 0;
        int high = colSize;

        while(low <= high){
            int mid = low + (high - low) /2;
            int cur = matrix[rightRow][mid];


            if(cur < target){
                low = mid + 1;
            }else if(cur > target){
                high = mid - 1;
            }else{
                return true;
            }
        }
        return false;
    }
}
```

###Strobogrammatic Number II

    A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

    Find all strobogrammatic numbers that are of length = n.

    For example,
    Given n = 2, return ["11","69","88","96"].

    Hint:
    Try to use recursion and notice that it should recurse with n - 2 instead of n - 1.

Generate each string like Fibonacci.

```java
public class StrobogrammaticNumber247 {
    static Map<Character, Character> map = new HashMap<>();

    static {
        map.put('0', '0');
        map.put('1', '1');
        map.put('6', '9');
        map.put('8', '8');
        map.put('9', '6');
    }

    public List<String> findStrobogrammatic(int n) {
        List<String> result = new ArrayList<>();
        if (n == 1) {
            result.add("1");
            result.add("8");
            result.add("0");
            return result;
        } else if (n == 2) {
            for (Map.Entry<Character, Character> entry : map.entrySet()) {
                Character key = entry.getKey();
                Character value = entry.getValue();
                result.add(key + "" + value);
            }
            return result;
        } else {
            List<String> around = findStrobogrammatic(2);
            for (String str : around) {
                List<String> strobogrammatic = findStrobogrammatic(n - 2);
                for (String ns : strobogrammatic) {
                    String tmp = str.charAt(0) + ns + str.charAt(1);
                    result.add(tmp);
                }
            }
            List<String> res = new ArrayList<>();
            for (String s : result) {
                if (!s.startsWith("0")) {
                    res.add(s);
                }
            }
            return res;
        }
    }
}
```


###Wiggle Sort

    Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
    For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].

The greed algorithm will be ok.

```java
public class Solution {
    public void wiggleSort(int[] nums) {
            if(nums==null || nums.length<2) return;
            for(int i=1; i<nums.length; i++) {
                if( (i%2==1 && (nums[i] < nums[i-1])) || (i%2==0) && (nums[i] > nums[i-1])) {
                    int temp = nums[i];
                    nums[i] = nums[i-1];
                    nums[i-1] = temp;
                }
            }

        }
}
```

###Word Break *

    Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.

    For example, given
    s = "leetcode",
    dict = ["leet", "code"].

    Return true because "leetcode" can be segmented as "leet code".

If we write a recurse program, it's easy.

```java
public class Solution {
    public boolean wordBreak(String s, Set<String> wordDict) {
        if(s == null){
            return false;
        }
        if(wordDict.contains(s)){
            return true;
        }
        for(int i = 0; i < s.length() ; i++){
            String sub = s.substring(0, i);
            String left = s.substring(i, s.length());
            boolean flag =  wordDict.contains(sub) && wordBreak(left, wordDict);
            if(flag){
                return true;
            }
        }
        return false;
    }
}
```

Of course, this program will Time Limited Exceeded. If use dynamic programming, will be better.
For each sentence, F(0,N) = F(0,i) && F(i,j) && F(j,N)

```java
public class Solution {
    public boolean wordBreak(String s, Set<String> wordDict) {
        if(s == null){
            return false;
        }
        int len = s.length();
        boolean[] array = new boolean[len + 1];
        array[0] = true;
        for(int i = 1; i <= len; i++){
            for(int j = 0; j < i; j ++){
                if(array[j] && wordDict.contains(s.substring(j, i))){
                    array[i] = true;
                    break;
                }
            }
        }
        return array[len];
    }
}

```

###Zigzag Iterator

    Given two 1d vectors, implement an iterator to return their elements alternately.
    For example, given two 1d vectors:
    v1 = [1, 2]
    v2 = [3, 4, 5, 6]
    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].
    Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?
    Clarification for the follow up question - Update (2015-09-18):
    The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic". For example, given the following input:
    [1,2,3]
    [4,5,6,7]
    [8,9]
    It should return [1,4,8,2,5,9,3,6,7].

Put all the list into a List and iterater it.

```java
public class Zigzag{
    List<Iterator<Integer>> list = new ArrayList<>();
    int count = 0;
    public Zigzag(List<Integer> a, List<Integer> b){
        if(!a.isEmplty()){
            list.add(a.iterator());
        }
        if(!b.isEmplty()){
            list.add(b.iterator());
        }
    }
    public boolean hasNext(){
        return !list.isEmpty();
    }
    int count = 0;
    public int next(){
        int x = list.get(count).next();
        if(!list.get(count).hasNext()){
            list.remove(count);
        }else{
            count++;
        }
        if(list.size() != 0){
            count = count % list.size();
        }
        return x;
    }
}
```

###Largest Number

    Given a list of non negative integers, arrange them such that they form the largest number.

    For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.

    Note: The result may be very large, so you need to return a string instead of an integer.

Sort the array, compare each two numbers, if a+b > b+a, then a should be place the left of b.

```java
public class Solution {
    public String largestNumber(int[] nums) {
        if(nums == null || nums.length == 0){
            return "";
        }
        String[] strs = new String[nums.length];
        for(int i=0; i < nums.length; i++){
            strs[i] = nums[i]+"";
        }
        Arrays.sort(strs, new Comparator<String>(){
                @Override  
                public int compare(String s1, String s2) {  
                String a = s1+s2;
                String b = s2+s1;
                return -a.compareTo(b);
                }
                });
        StringBuilder sb = new StringBuilder();
        for(String s : strs){
            System.out.print(s+ " ");
            sb.append(s);
        }
        return sb.toString().replaceFirst("^0+(?!$)", "");
    }
}
```

###Single Number II

    Given an array of integers, every element appears three times except for one. Find that single one.

    Note:
    Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

Because each int number has 32 bit, let create a 32 size array to identify each bit. Then, compute the number of 1 in each bit, if the count%3 not equal 0, then the number will be find.

```java
public class Solution {
    public int singleNumber(int[] nums) {
        int[] bitnum=new int[32];  
        int res=0;  
        for(int i=0; i<32; i++){  
            for(int j=0; j< nums.length; j++){  
                bitnum[i]+=(nums[j]>>i)&1;  
            }  
            res|=(bitnum[i]%3)<<i;  
        }  
        return res;  
    }
}
```

###Search Insert Position

    Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

    You may assume no duplicates in the array.

    Here are few examples.
    [1,3,5,6], 5 → 2
    [1,3,5,6], 2 → 1
    [1,3,5,6], 7 → 4
    [1,3,5,6], 0 → 0

Let's use binary search to find the upper bound and lower bound, if the number exist in the array, return the position. If not, return the lowerbound +1.

```java
public class SearchInsertPosition {
    public int searchInsert(int[] nums, int target) {
        int begin = 0;
        int end = nums.length - 1;
        int pos = 0;
        while (begin <= end) {
            if (nums[begin] >= target) {
                return begin;
            } else if (nums[end] == target) {
                return end;
            } else if (nums[end] < target) {
                return end + 1;
            } else if (nums[begin] < target && nums[begin + 1] > target) {
                return begin + 1;
            } else {
                int mid = begin + (end - begin) / 2;
                if (nums[mid] >= target) {
                    end = mid;
                } else {
                    begin = mid;
                    pos += mid;
                }
            }
        }
        return pos;
    }
}
```

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

###Climbing Stairs

    You are climbing a stair case. It takes n steps to reach to the top.

    Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

It's easy to count with dp.

```java
public class Solution {
    public int climbStairs(int n) {
        if(n <=  2){
            return n;
        }
        int[] step = new int[n+1];
        step[1] = 1;
        step[2] = 2;
        for(int i = 3; i <= n; i ++){
            step[i] = step[i-1]+ step[i-2];
        }
        return step[n];
    }
}
```

###Meeting Rooms

    Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.

    For example,
    Given [[0, 30],[5, 10],[15, 20]],
    return false.

Just sort the input with its start. Then find whether the end numbers are overlap.

```java
public class Solution{
    public boolean canAttendMeetings(List<Interval> intervals){
        if(intervals == null || intervals.size() < 1){
            return intervals;
        }
        Collections.sort(intervals, new Comparator<Interval>(){
            @Override
            public int compare(Interval int1, Interval int2){
                return int1.start - int2.start;
            }
        });
        int first = intervals.get(0).end;
        for(int i = 1; i < intervals.size(); i ++){
            if(first < intervals.get(i).end){
                first = intervals.get(i).end;
            }else{
                return false;
            }
        }
        return true;
    }
}
```

###Min Stack

    Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

    push(x) -- Push element x onto stack.
    pop() -- Removes the element on top of the stack.
    top() -- Get the top element.
    getMin() -- Retrieve the minimum element in the stack.

Use the Stack of java to implement it.

```java
class MinStack {
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minstack = new Stack<>();
    public void push(int x) {
        if(minstack.isEmpty() || minstack.peek() >= x){
            minstack.push(x);
        }
        stack.push(x);
    }

    public void pop() {
        if(minstack.peek().equals(stack.peek())){
            minstack.pop();
        }
        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minstack.peek();
    }
}
```

If use the Java function is not allowed, a tree is also ok.

```java
class MinStack{
    Node top = null;
    public void push(int x){
        if(top == null){
            top = new Node(x);
            top.min = x;
        }else{
            Node node = new Node(x);
            node.next = top;
            node.min = Math.min(x, top.val);
            top =  node;
        }
    }
    public void pop(){
        top = top.next();
    }
    public int top(){
        return top == null ? 0 : top.val;
    }
    public int getMin(){
        return top == null ? 0 : top.min;
    }
}
class Node{
    int val;
    int min;
    Node next;
    public Node(int val){
        this.val = val;
    }
}
```

###Palindrome Permutation

    Given a string, determine if a permutation of the string could form a palindrome.

    For example, "code" -> False, "aab" -> True, "carerac" -> True.

    Hint:

    Consider the palindromes of odd vs even length. What difference do you notice? Count the frequency of each character. If each character occurs even number of times, then it must be a palindrome. How about character which occurs odd number of times?

Just judge whether the string can construct a palindrome. Use a set is ok.

```java
public class Solution{

    public boolean canPermutePalindrome(String s) {
        if(s == null || s.length() == 0){
            return false;
        }
        Set<Character> set = new HashSet<>();
        for(int i = 0; i < s.length(); i ++){
            char c = s.charAt(i);
            if(set.contains(c)){
                set.remove(c);
            }else{
                set.add(c);
            }
        }
        if(set.size() <= 1){
            return true;
        }else{
            return false;
        }
    }

}
```

###Plus One

    Given a non-negative number represented as an array of digits, plus one to the number.

    The digits are stored such that the most significant digit is at the head of the list.

Use a new list to store the results.

```java
public class Solution {
    public int[] plusOne(int[] digits) {

    List<Integer> list = new ArrayList<>();

        boolean addOne = true;
        int tmp = 0;
        for(int i = digits.length-1 ; i > -1; i--){
            int cur = digits[i];
            if(tmp == 0 && addOne){
                int res = cur + 1;
                if(res  == 10 ){
                    tmp = 1;
                    list.add(0);
                }else{
                    list.add(res);
                }
                addOne = false;
            }else{
                int res = cur + tmp;
                if(res == 10){
                    tmp = 1;
                    list.add(0);
                }else{
                    list.add(res);
                    tmp = 0;
                }
            }
        }
        if(tmp == 1){
            list.add(1);
        }

        int[] array = new int[list.size()];
        for(int i = 0; i < list.size(); i ++){
            array[i] = list.get(list.size()- 1 - i);
        }
        return array;
    }
}
```

###Strobogrammatic Number I

    A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

    Write a function to determine if a number is strobogrammatic. The number is represented as a string.

    For example, the numbers "69", "88", and "818" are all strobogrammatic.

Judge it one by one.

```java
public class StrobogrammaticNumber246 {
    static Map<Character, Character> map = new HashMap<>();

    static {
        map.put('0', '0');
        map.put('1', '1');
        map.put('6', '9');
        map.put('8', '8');
        map.put('9', '6');
    }

    public boolean isStrobogrammatic(String number) {
        int begin = 0;
        int end = number.length() - 1;
        while (begin <= end) {
            char c = number.charAt(begin);
            char last = number.charAt(end);
            Character character = map.get(c);
            if (character == null || (character != last)) {
                return false;
            }
            begin++;
            end--;
        }
        return true;
    }
}
```


###Unique Word Abbreviation

    An abbreviation of a word follows the form <first letter><number><last letter>. Below are some examples of word abbreviations:
    a) it                      --> it    (no abbreviation)
         1
    b) d|o|g                   --> d1g
                  1    1  1
         1---5----0----5--8
    c) i|nternationalizatio|n  --> i18n
                  1
         1---5----0
    d) l|ocalizatio|n          --> l10n
    Assume you have a dictionary and given a word, find whether its abbreviation is unique in the dictionary.
    A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.
    Example:
    Given dictionary = [ "deer", "door", "cake", "card" ]

    isUnique("dear") -> false
    isUnique("cart") -> true
    isUnique("cane") -> false
    isUnique("make") -> true

First, put all the elements with its abbr into a map. Second find the value > 1 in the map.

```java
public class UniqueWordAbbreviation288 {
    public void findUnique(String[] dict) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, List<String>> originMap = new HashMap<>();
        for (String string : dict) {
            String abbr = string;
            if (string.length() > 2) {
                abbr = "" + string.charAt(0) + (string.length() - 2) + string.charAt(string.length() - 1);
            }
            Integer integer = map.get(abbr);
            if (integer == null) {
                integer = 0;
            }
            integer++;
            map.put(abbr, integer);

            List<String> strings = originMap.get(abbr);
            if (strings == null) {
                strings = new ArrayList<>();
            }
            strings.add(string);
            originMap.put(abbr, strings);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String abbr = entry.getKey();
            List<String> strings = originMap.get(abbr);
            if (entry.getValue() == 1) {
                System.out.println("isUnique(\"" + strings.get(0) + "\") -> true");
            } else {
                for (String s : strings) {
                    System.out.println("isUnique(\"" + s + "\") -> false");
                }
            }
        }
    }
}
```


###Valid Parentheses

    Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

    The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.

Do it with a stack.

```java
public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack();
        for(int i = 0;i < s.length(); i ++){
            char c = s.charAt(i);
            if(!stack.isEmpty()){
                Character last = stack.peek();
                switch(c){
                    case ')':
                        if(last == '('){
                            stack.pop();
                        }else{
                            stack.push(c);
                        }
                        break;
                    case ']':
                        if(last == '['){
                            stack.pop();
                        }else{
                            stack.push(c);
                        }
                        break;
                    case '}':
                        if(last == '{'){
                            stack.pop();
                        }else{
                            stack.push(c);
                        }
                        break;
                    default:
                        stack.push(c);
                        break;
                }
            }else{
                stack.push(c);
            }
        }
        if(stack.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
```


###Length of Last word

    Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string.

    If the last word does not exist, return 0.

    Note: A word is defined as a character sequence consists of non-space characters only.

    For example, 
    Given s = "Hello World",
    return 5.

There are many API can do this, such as `split`. However this problem just need the last word, so let's just compute the last one is ok, not blank.

```java
public class Solution {
    public int lengthOfLastWord(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }else{
            boolean flag = false;
            int res = 0;
            for(int i = s.length()-1; i >= 0; i --){
                char c = s.charAt(i);
                if(c == ' '){
                    if(flag){
                        break;
                    }else{
                        continue;
                    }
                }else{
                    flag = true;
                    res ++;
                }
            }
            return res;
        }
    }
}
```

###Longest Common Prefix

    Write a function to find the longest common prefix string amongst an array of strings.

Because there exist a common prefix, let's compare it one by one.

```java
public class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0){
            return "";
        }
        for(int i = 0; i < strs[0].length(); i ++){
            for(int j = 1; j < strs.length; j ++){
                if(strs[j].length() <= i || strs[j].charAt(i) != strs[0].charAt(i)){
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];   
    }
}
```

###Power of Two

    Given an integer, write a function to determine if it is a power of two.

If an integer n is a power of two, then use it do &  with n-1 should be 0.

```java
public class Solution {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && ((n & (n - 1)) == 0 );
    }
}
```

###First Bad Version

    You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.

    Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.

    You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.

Binary search is ok and be careful about the middle number : low + (high - low)/2

```java
/* The isBadVersion API is defined in the parent class VersionControl.
   boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {

        int begin = 1;
        int end = n;

        while(begin < end){
            int middle = begin + (end - begin) / 2;
            boolean bad  = isBadVersion(middle);
            if(bad){
                end = middle;
            }else{
                begin = middle + 1;
            }
        } 
        return begin;
    }
}
```

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


