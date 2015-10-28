package easy;

/**
 * You are playing the following Nim Game with your friend:
 * There is a heap of stones on the table, each time one of you take turns to remove 1 to 3 stones.
 * The one who removes the last stone will be the winner. You will take the first turn to remove the stones.
 * <p/>
 * Both of you are very clever and have optimal strategies for the game.
 * Write a function to determine whether you can win the game given the number of stones in the heap.
 * <p/>
 * For example, if there are 4 stones in the heap, then you will never win the game:
 * no matter 1, 2, or 3 stones you remove, the last stone will always be removed by your friend.
 * <p/>
 * Created by OmarTech on 15-10-28.
 */
public class NimGame292 {

    /**
     * Version 1
     *
     * @param n
     * @return
     */
    public boolean canWinNim(int n) {
        boolean flag = false;
        if (n <= 3) {
            flag = true;
        } else {
            flag = !(canWinNim(n - 1) && canWinNim(n - 2) && canWinNim(n - 3));
        }
        return flag;
    }

    /**
     * Version 2
     * 如果有余数，就行
     *
     * @param n
     * @return
     */
    public boolean canWinNim_v2(int n) {
        if (n % 4 == 0) {
            return false;
        } else {
            return true;
        }
    }


    public static void main(String[] args) {
        NimGame292 ng = new NimGame292();
        for (int i = 1; i < 100; i++) {
            boolean flag = ng.canWinNim(i);
            String ans = flag ? "能" : "不能";
            System.out.println("总数：" + i + " , 先手 " + ans + " 赢");
        }

        for (int i = 1; i < 100; i++) {
            boolean flag = ng.canWinNim_v2(i);
            String ans = flag ? "能" : "不能";
            System.out.println("总数：" + i + " , 先手 " + ans + " 赢");
        }
    }
}
