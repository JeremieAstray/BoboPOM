package boboPOM.code.prepareContainer;

import java.util.LinkedList;

/**
 * @author yorlbgy
 */
public class QueueList<T> extends LinkedList<T> {

    public T take() {
        T t = super.getFirst();
        super.removeFirst();
        return t;
    }

    public T look() {
        return super.getFirst();
    }
}
