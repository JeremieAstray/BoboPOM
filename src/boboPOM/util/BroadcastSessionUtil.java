package boboPOM.util;

import java.io.IOException;
import java.net.*;

/**
 * Created by Jeremie on 14-3-8.
 */
public class BroadcastSessionUtil {

    private String ip;
    private int port;

    public BroadcastSessionUtil(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void sendBroadcast() {
        try {
            DatagramSocket ds = new DatagramSocket(this.port);
            DatagramPacket dp = new DatagramPacket(ip.getBytes(), ip.getBytes().length,
                    InetAddress.getByName("255.255.255.0"), this.port);
            ds.send(dp);
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
            DatagramSocket ds = new DatagramSocket();
            byte[] buf = new byte[30];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);
            strRecv = new String(dp.getData(), 0, dp.getLength()) + " from "
                    + dp.getAddress().getHostAddress() + ":" + dp.getPort();
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
