package boboPOM.util;

import java.io.IOException;
import java.net.*;

/**
 * Created by Jeremie on 14-3-8.
 */
public class BroadcastSessionUtil {

    private int port;

    public BroadcastSessionUtil(int port) {
        this.port = port;
    }

    public void sendBroadcast() {
        try {
            String msg = "尚未连接";
            DatagramSocket ds= new  DatagramSocket();
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

    public String receiveBroadcast() {
        String strRecv = null;
        try {
            DatagramSocket ds = new DatagramSocket(7001);// 创建接收数据报套接字并将其绑定到本地主机上的指定端口
            byte[] buf = new byte[128];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);
            strRecv = new String(dp.getAddress().getHostAddress());
            System.out.println(new String(dp.getData(), 0, dp.getLength()) + " from "
                    + dp.getAddress().getHostAddress() + ":" + dp.getPort());
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
}
