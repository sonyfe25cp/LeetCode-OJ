LeetCode Online Judge Practise

Update frequency: one day one problem.

All answers are done by myself, in other words, answers are may be not the best but passed.

##EASY

###Nim Game

    You are playing the following Nim Game with your friend: There is a heap of stones on the table, each time one of you take turns to remove 1 to 3 stones. The one who removes the last stone will be the winner. You will take the first turn to remove the stones.
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


