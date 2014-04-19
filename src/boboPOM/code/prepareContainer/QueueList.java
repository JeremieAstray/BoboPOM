
package boboPOM.code.prepareContainer;

import java.util.LinkedList;

/**
 *
 * @author yorlbgy
 */
public class QueueList<T> extends LinkedList<T> {

    public T take() {
        T t = super.get(0);
        super.remove(0);
        return t;
    }

    public T look() {
        return super.get(0);
    }
}
