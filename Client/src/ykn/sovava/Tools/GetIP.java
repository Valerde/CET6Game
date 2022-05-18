package ykn.sovava.Tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @className: GetIP
 * @description:
 * @author: ykn
 * @date: 2022/5/17
 **/
public class GetIP {
    public static String getIP(){
        try {
            InetAddress address = InetAddress.getByName("DESKTOP-ISIK7DF");
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
}
