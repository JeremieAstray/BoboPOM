package boboPOM.net;

import java.io.IOException;
import java.net.*;
import java.util.TimerTask;

/**
 * Created by Jeremie on 14-3-8.
 */
public class BroadcastSession extends TimerTask {

    private int port;
    private boolean isServer = false;
    private MsgQueue<String> broadcaseMsg;

    public BroadcastSession(int port, MsgQueue<String> broadcaseMsg) {
        this.port = port;
        this.broadcaseMsg = broadcaseMsg;
    }

    @Override
    public void run() {
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

    private void sendBroadcast() {
        try {
            String msg = "serverMessage";
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
            strRecv = new String(new String(dp.getData(), 0, dp.getLength()) + ":" + dp.getAddress().getHostAddress());
            System.out.println(new String(dp.getData(), 0, dp.getLength()) + " from "
                    + dp.getAddress().getHostAddress() + ":" + dp.getPort());
            ds.close();
        } catch (SocketTimeoutException e) {
            System.err.println("接收超时");
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
}
