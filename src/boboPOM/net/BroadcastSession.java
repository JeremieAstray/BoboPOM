package boboPOM.net;

import java.io.IOException;
import java.net.*;
import java.util.TimerTask;

/**
 * Created by Jeremie on 14-3-8.
 */
public class BroadcastSession extends TimerTask {

    private boolean run = true;
    private int port;
    private boolean isServer = false;
    private MsgQueue<String> broadcaseMsg;
    private boolean connected = false;

    public BroadcastSession(int port){
        isServer = true;
        this.port = port;
    }

    public BroadcastSession(int port, MsgQueue<String> broadcaseMsg) {
        isServer = false;
        this.port = port;
        this.broadcaseMsg = broadcaseMsg;
    }

    @Override
    public void run() {
        while (run){
            if (isServer) {
                try {
                    sendBroadcast();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String msg = receiveBroadcast();
                    broadcaseMsg.send(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendBroadcast() {
        try {
            String msg = "bobopomGames:" + (connected ? "isconnected":"notconnected");
            DatagramSocket ds = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket datagramPacket = new DatagramPacket(
                    msg.getBytes(), msg.length(), inetAddress, 7001);
            ds.send(datagramPacket);
            ds.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receiveBroadcast() {
        String strRecv = null;
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(7001);// 创建接收数据报套接字并将其绑定到本地主机上的指定端口
            ds.setSoTimeout(1000);
            byte[] buf = new byte[128];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);
            String recv = new String(dp.getData(), 0, dp.getLength());
            String[] recvs = recv.split(":");
            if(recvs.length == 2 && "bobopomGames".equals(recvs[0])) {
                strRecv = new String(dp.getAddress().getHostAddress() + " " + recvs[1] );
            }else {
                strRecv = "error message!";
            }
            ds.close();
        } catch (SocketTimeoutException e) {
            strRecv = "receive time out!";
            ds.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strRecv;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean isServer) {
        this.isServer = isServer;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
