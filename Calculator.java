class InvalidExprException extends Exception {

}

class EmptyStackException extends Exception {

}

class InvalidPostfixException extends Exception {
}

public class Calculator {

    public static int evaluatePostFix(String s) throws InvalidPostfixException {
        try {
            MyStack stk = new MyStack();
            // 1 2 3 45 ..
            int ct_num = 0;
            int ct_op = 0;
            if (s.length() < 2) {
                throw new InvalidPostfixException();
            }
            int space = 0;
            for (int i = 0; i < s.length(); i++) {
                int c = s.charAt(i) - '0';

                if (s.charAt(i) == ' ') {
                    ++space;
                    continue;
                }
                if (i == s.length() - 1 && space == 0) {
                    return stringTonum(s);
                }
                if (s.charAt(0) == '+' || s.charAt(0) == '-' || s.charAt(0) == '*' || s.charAt(0) == '/') {
                    throw new InvalidPostfixException();
                }
                if (c >= 0 && c <= 9) {
                    String t = "";
                    while (s.charAt(i) != ' ') {
                        if (i == s.length() - 1 && space == 0) {
                            return stringTonum(s);
                        }
                        t += s.charAt(i);
                        ++i;
                    }
                    ++space;
                    int sum = stringTonum(t);
                    stk.push(sum);
                    ct_num++;
                    continue;
                }
                if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/') {
                    ct_op++;
                    if (ct_num <= ct_op) {
                        throw new InvalidPostfixException();
                    }

                    int tp = (int) stk.pop();
                    int tp2 = (int) stk.pop();
                    switch (s.charAt(i)) {
                        case '+':
                            stk.push(tp2 + tp);
                            break;
                        case '-':
                            stk.push(tp2 - tp);
                            break;
                        case '*':
                            stk.push(tp2 * tp);
                            break;
                        case '/':
                            stk.push(tp2 / tp);
                            break;
                    }
                } else {
                    throw new InvalidPostfixException();
                }
            }

            int z = (int) stk.top();

            return z;
        } catch (EmptyStackException e) {
        }
        return 0;

    }

    public static int stringTonum(String t) {
        int sum = 0;
        int k = 0;
        while (k < t.length()) {
            sum *= 10;
            sum += (t.charAt(k++) - '0');
        }
        return sum;
    }

    public static boolean isOper(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == ')') {
            return true;
        }
        return false;
    }

    public static boolean isNum(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    public static int preference(char s) {
        if (s == '*' || s == '/') {
            return 2;
        }
        if (s == '+' || s == '-') {
            return 1;
        }
        return 0;
    }

    public static String conv(String s) {
        String s2 = "";
        for (int i = 0; i < s.length() - 1; i++) {
            s2 += s.charAt(i);
        }
        return s2;
    }

    // .. + *
    public static String convertExpression(String s) throws InvalidExprException {
        String s1 = "";
        MyStack stk = new MyStack();
        try {
            for (int i = 0; i < s.length();) {
                char c = s.charAt(i);
                if (c == ' ') {
                    i++;
                    continue;
                }
                if (stk.isEmpty() && isOper(c)) {
                    if (i == 0) {
                        throw new InvalidExprException();
                    }
                    stk.push(c);
                    i++;
                    continue;
                }
                if (isOper(c)) {
                    if (c == ')') {
                        while ((char) stk.top() != '(') {
                            s1 += (char) stk.pop() + " ";
                        }
                        stk.pop();
                        i++;
                        continue;
                    }

                    int p = preference((char) stk.top());
                    if (p == 0) {
                        stk.push(c);
                        i++;
                        continue;
                    }
                    while (!stk.isEmpty() && preference(c) <= preference((char) stk.top())) {
                        s1 += (char) stk.pop() + " ";
                    }
                    stk.push(c);
                    i++;
                    continue;
                }
                if (c == '(') {
                    if (isOper(s.charAt(i + 1))) {
                        throw new InvalidExprException();
                    }
                    stk.push(s.charAt(i));
                    i++;
                    continue;
                }

                if (c >= '0' && c <= '9' && i < s.length()) {
                    if (i == s.length() - 1) {
                        s1 += s.charAt(i) + " ";
                        break;
                    }
                    if (i != s.length() - 1) {
                        if (!isNum(s.charAt(i + 1))) {
                            s1 += s.charAt(i) + " ";
                            i++;
                            continue;
                        }
                        if (isNum(s.charAt(i + 1))) {
                            String t = "";
                            while (i < s.length() && !isOper(s.charAt(i))) {
                                t += s.charAt(i);
                                ++i;
                            }
                            s1 += t + " ";
                        }
                    }
                    if (i != s.length()) {
                        if (s.charAt(i) == ')') {
                            while ((char) stk.top() != '(') {
                                s1 += (char) stk.pop() + " ";
                            }
                            stk.pop();
                            i++;
                        }
                    }

                    continue;
                }

            }
            if ((char) stk.top() == '(') {
                throw new InvalidExprException();
            }
            while (!stk.isEmpty() && stk.top1 > 1) {
                if ((char) stk.top() == '(') {
                    throw new InvalidExprException();
                }
                s1 += (char) stk.pop() + " ";
            }
            s1 += (char) stk.pop() + " ";
            s1 = conv(s1);
            return s1;
        } catch (

        EmptyStackException e) {
        }
        s1 = conv(s1);
        return s1;
    }

    public static void main(String[] args) throws InvalidExprException, InvalidPostfixException {
        String s = convertExpression("1+(2-3)/(4*5)*6+7");
        System.out.println(s);
    }
}
