package sock;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WeTalkUtils {

    private static final int BUFFER_SIZE = 1024;

    public static void sendMsg(SocketChannel channel, String msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.put(msg.getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    public static String recvMsg(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        channel.read(buffer);

        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        return new String(bytes);
    }
}