package xyz.coolblog.httpd;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * TinyHttpd
 *
 * @author coolblog.xyz
 * @date 2018-03-26 22:28:44
 */
public class TinyHttpd01 {

    private static final int DEFAULT_PORT = 8080;

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private static final String INDEX_PAGE = "index.html";

    private static final String STATIC_RESOURCE_DIR = "static";

    private static final String META_RESOURCE_DIR_PREFIX = "/meta/";

    private static final String KEY_VALUE_SEPARATOR = ":";

    private static final String CRLF = "\r\n";

    private int port;

    public TinyHttpd01() {
        this(DEFAULT_PORT);
    }

    public TinyHttpd01(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("localhost", port));
        ssc.configureBlocking(false);

        System.out.println(String.format("TinyHttpd 已启动，正在监听 %d 端口...", port));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readyNum = selector.select();
            if (readyNum == 0) {
                continue;
            }
            System.out.println("-------------------------------------");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                it.remove();

                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("accept:"+socketChannel.toString());
                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    System.out.println("readable:"+channel.toString());

                    ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
                    channel.read(buffer);

                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    String headerStr = new String(bytes);
                    System.out.println("请求数据：" + headerStr);
                    try {
                        selectionKey.attach(headerStr);
                    } catch (Exception e) {
                        selectionKey.attach("");
                    }

                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                } else if (selectionKey.isWritable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    System.out.println("writable:"+channel.toString());

                    try {
                        String req = (String) selectionKey.attachment();

                        Map<String,Object> json=new HashMap<>();
                        json.put("result","ok");
                        json.put("time",System.currentTimeMillis());
                        json.put("req",req);
                        ByteBuffer bodyBuffer = ByteBuffer.allocate(1024);
                        bodyBuffer.put(JSON.toJSONBytes(json));
                        bodyBuffer.flip();

                        ResponseHeaders headers = new ResponseHeaders(StatusCodeEnum.OK.getCode());
                        headers.setContentLength(bodyBuffer.capacity());
                        headers.setContentType("text/html");
                        ByteBuffer headerBuffer = ByteBuffer.allocate(1024);
                        headerBuffer.put(headers.toString().getBytes());
                        headerBuffer.flip();

                        channel.write(new ByteBuffer[]{headerBuffer, bodyBuffer});
                    } finally {
                        channel.close();
                    }

                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new TinyHttpd01().start();
    }
}
