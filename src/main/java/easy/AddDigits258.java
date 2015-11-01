package easy;

/**
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.
 * <p/>
 * For example:
 * <p/>
 * Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.
 * Created by omar on 15/10/29.
 */
public class AddDigits258 {
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

    public static void main(String[] args) {
        AddDigits258 addDigits258 = new AddDigits258();
        for (int x = 0; x < 10000; x++) {
            int i = addDigits258.addDigits(x);
            System.out.println(x + " ==> " + i + " ---- " + x % 9);
        }
    }
}
