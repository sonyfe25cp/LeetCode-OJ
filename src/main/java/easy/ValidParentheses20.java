package easy;

import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 * Created by OmarTech on 15-12-7.
 */
public class ValidParentheses20 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty()) {
                Character last = stack.peek();
                switch (c) {
                    case ')':
                        if (last == '(') {
                            stack.pop();
                        }else{
                            stack.push(c);
                        }
                        break;
                    case ']':
                        if (last == '[') {
                            stack.pop();
                        }else{
                            stack.push(c);
                        }
                        break;
                    case '}':
                        if (last == '{') {
                            stack.pop();
                        }else{
                            stack.push(c);
                        }
                        break;
                    default:
                        stack.push(c);
                        break;
                }
            } else {
                stack.push(c);
            }
        }
        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
