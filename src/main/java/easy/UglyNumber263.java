package easy;

/**
 * Created by omar on 15/11/9.
 */
public class UglyNumber263 {
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
                        System.out.println(num + "  " + x + " " + i);
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
}
