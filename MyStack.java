
public class MyStack implements StackInterface {
    Object a[] = new Object[1];
    int top1; // keeping track of the topmost element. (pointer not element)

    public static void main(String[] args) throws EmptyStackException {
    }

    public boolean isEmpty() {
        return top1 <= 0; // returns empty if top1 is less than or equal to0.
    }

    MyStack() {
        top1 = -1;
    }

    public Object top() throws EmptyStackException {
        if (top1 > 0) {
            return a[top1];
        } else {
            throw new EmptyStackException();
        }
    }

    /*
     * 3,2,1
     * push 3 ->
     */

    public void push(Object o) {
        if (o == null) {
            return;
        }
        if (top1 == -1) {
            a[++top1] = o;
        }
        if (top1 >= a.length - 1) {
            Object[] s = new Object[2 * top1 + 2];
            for (int i = 0; i < top1 + 1; i++) {
                s[i] = a[i];
            }
            a = s;
            a[++top1] = o;
        }

        else {
            a[++top1] = o;
        }
    }

    public Object pop() throws EmptyStackException {
        if (top1 > 0) {
            Object x = a[top1--];
            return x;

        }

        throw new EmptyStackException();

    }

    public String toString() {
        String str1 = "[";
        if (isEmpty()) {
            return "[]";
        }
        for (int i = top1; i > 0; i--) {
            if (i == 1) {
                str1 += a[i];
                continue;
            }
            str1 += a[i] + ", ";
        }
        str1 += "]";
        return str1;
    }

}