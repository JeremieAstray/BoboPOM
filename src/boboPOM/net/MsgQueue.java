package boboPOM.net;

import java.util.Vector;

/**
 * Created by Jeremie on 14-2-28.
 */
public class MsgQueue<T extends Object> {

    private Vector<T> queue = null;

    /**
     * 工具类：消息队列
     */
    public MsgQueue(){
        queue  = new   Vector<T>();
    }

    public synchronized void send(T o){
        System.out.println(o.toString());
        queue.addElement(o);
    }

    public synchronized T recv(){
        if(queue.size()==0)
            return null;
        T o = queue.firstElement();
        queue.removeElementAt(0);
        return o;
    }

    public synchronized boolean isEmpty(){
        return queue.isEmpty();
    }
}
