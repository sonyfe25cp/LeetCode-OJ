package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OmarTech on 15-12-7.
 */
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
