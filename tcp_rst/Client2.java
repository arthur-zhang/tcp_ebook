import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client2 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("c2", 9999));

        OutputStream out = socket.getOutputStream();

        System.out.println("start sleep. kill server process now!");

        // 这个时候 kill 掉服务端进程
        TimeUnit.SECONDS.sleep(5);

        System.out.println("start first write");
        // 第一次 write, 触发 RST
        out.write("hello".getBytes());

        TimeUnit.SECONDS.sleep(2);
        System.out.println("start second write");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = reader.readLine();
        System.out.println(line);
        // 第二次 write, 触发 Broken Pipe
//        out.write("world".getBytes());

        System.in.read();
    }
}
