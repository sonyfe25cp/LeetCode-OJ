package easy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OmarTech on 15-12-7.
 */
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
