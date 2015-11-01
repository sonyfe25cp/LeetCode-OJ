##LeetCode Online Judge Practise

Update frequency: one day one problem.

All answers are done by myself, in other words, answers are may be not the best but passed.

##EASY

###Maximum Depth of Binary Tree

    Given a binary tree, find its maximum depth.

    The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

This subject is very easy.

```java
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


