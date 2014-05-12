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
    private int port;
    private String ip;
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private MsgQueue<String> status;
    private MsgQueue<Object> gamesMsg;
    private boolean isServer;
    private boolean isDone;


    /**
     * 服务端
     *
     * @param status
     * @param gamesMsg
     * @param port
     */
    public SocketLink(MsgQueue status, MsgQueue gamesMsg, int port) {
        this.status = status;
        this.gamesMsg = gamesMsg;
        this.port = port;
        isServer = true;
    }

    /**
     * 客户端
     *
     * @param status
     * @param gamesMsg
     * @param port
     * @param ip
     */

    public SocketLink(MsgQueue status, MsgQueue gamesMsg, int port, String ip) {
        this.status = status;
        this.gamesMsg = gamesMsg;
        this.port = port;
        this.ip = ip;
        isServer = false;
    }


    /**
     *
     */
    @Override
    public void run() {
        this.isDone = false;
        try {
            if (isServer) {
                status.send("等待连接中");
                server = new ServerSocket(port);
                client = server.accept();
                System.out.println("========================test===================");
                System.out.println(server);
                System.out.println(client);
                System.out.println(out);
                System.out.println("========================test===================");
                this.waitMsg();
            } else {
                client = new Socket(ip, port);
                this.waitMsg();
            }
        } catch (IOException e) {
            if ("socket closed".equals(e.getMessage())) {
                status.send("socket closed");
            }else if("Connection refused: connect".equals(e.getMessage())){
                status.send("该服务器已被连接或不存在");
            }else {
                status.send("端口被占用");
                e.printStackTrace();
            }
        }finally {
            this.isDone = true;
        }

    }

    /**
     * 等待对方信息
     */
    private void waitMsg() {
        try {
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
            status.send("连接成功");
            while (run) {
                Object o = null;
                o = in.readObject();
                if (o != null)
                    gamesMsg.send(o);
                else {
                    if (!client.isClosed())
                        out.writeObject(null);
                    this.run = false;
                    in.close();
                    client.close();
                    status.send("连接断开");
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


    public void close() {
        this.run = false;
        try {
            if (client != null) {
                out.writeObject(null);
                out.close();
                client.close();
                if (isServer)
                    server.close();
            } else {
                if (isServer) {
                    if(server!=null) {
                        server.setSoTimeout(0);
                        Thread.sleep(200);
                    }
                }
            }
            status.send("连接断开");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeserver(){
        this.run = false;
        try {
            if(out!=null)
                out.close();
            if(client!=null)
                client.close();
            else if (isServer) {
                server.setSoTimeout(0);
                Thread.sleep(200);
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public MsgQueue<Object> getGamesMsg() {
        return gamesMsg;
    }

    /**
     * @param isServer
     */
    public void setServer(boolean isServer) {
        this.isServer = isServer;
    }

    public boolean isServer() {
        return isServer;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone;
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


    public boolean isRun() {
        return run;
    }
}
