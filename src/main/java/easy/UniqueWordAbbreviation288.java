package easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OmarTech on 15-12-7.
 */
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
