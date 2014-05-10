package boboPOM.net;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Jeremie on 14-2-26.
 */
public class SocketLink implements RunnableFuture {


    private boolean run = true;
    private boolean playgames = false;
    private int port;
    private String ip;
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private MsgQueue<String> status;
    private MsgQueue<Object> gamesMsg;
    private boolean isServer;


    /**
     * @param status
     * @param gamesMsg
     * @param port
     */
    public SocketLink(MsgQueue status, MsgQueue gamesMsg, int port) {
        this.status = status;
        this.gamesMsg = gamesMsg;
        this.port = port;
        isServer = false;
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            if (isServer) {
                status.send("等待连接中");
                server = new ServerSocket(port);
                client = server.accept();
                System.out.println(server);
                System.out.println(client);
                System.out.println(out);
                waitMsg();
                server.close();
            } else {
                client = new Socket(ip, port);
                waitMsg();
            }
        } catch (IOException e) {
            e.printStackTrace();
            status.send("端口被占用");
            isServer = false;
        }

    }

    /**
     * 等待对方信息
     */
    private void waitMsg() {
        try {
            boolean flag = false;
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            status.send("连接成功");
            while (run) {
                while(playgames) {
                    Object o = null;
                    o = in.readObject();
                    if (o != null)
                        gamesMsg.send(o);
                    else {
                        if (!client.isClosed())
                            out.writeObject(null);
                        in.close();
                        status.send("连接断开");
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向对方发送数据
     *
     * @param gamesMsg
     */
    public void send(Object gamesMsg) {
        try {
            out.writeObject(gamesMsg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     *
     * @param mayInterruptIfRunning
     * @return
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        try {
            if (client != null) {
                out.writeObject(null);
                out.close();
                client.close();
                if (isServer) {
                    server.close();
                }
            }
            status.send("连接断开");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @return
     */
    public boolean isLinked() {
        return client != null && !client.isClosed();
    }

    public MsgQueue<Object> getGamesMsg() {
        return gamesMsg;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @param isServer
     */
    public void setServer(boolean isServer) {
        this.isServer = isServer;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public String getIp() {
        return ip;
    }
}
