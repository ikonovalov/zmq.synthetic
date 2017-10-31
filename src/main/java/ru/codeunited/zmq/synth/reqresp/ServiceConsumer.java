package ru.codeunited.zmq.synth.reqresp;

import org.zeromq.ZMQ;

import java.util.Arrays;
import java.util.Random;

public class ServiceConsumer {

    private static final String CONSUMER_CONNECT_ADDR = "CONSUMER_CONNECT_ADDR";
    private static final String MSG_SIZE = "MSG_SIZE";
    private static final String MSG_COUNT = "MSG_COUNT";

    static {
        System.load("/usr/local/lib/libzmq.so.5.1.3");
        System.load("/usr/local/lib/libjzmq.so.0.0.0");
    }

    public static void main(String[] args) throws InterruptedException {
        String addr = System.getenv(CONSUMER_CONNECT_ADDR);
        if (addr == null)
            addr = "tcp://localhost:5559"; // ipc:///tmp/zprov.icp
        System.out.println(String.format("Connecting to %s", addr));

        String msg_size_s = System.getenv(MSG_SIZE);
        String msg_count_s = System.getenv(MSG_COUNT);
        if (msg_size_s == null)
            msg_size_s = "10240";
        if (msg_count_s == null)
            msg_count_s = "100000";

        ZMQ.Context context = ZMQ.context(1);

        //  Socket to talk to server
        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect(addr);

        long totalMsgSend = 0;
        long totalMsgReceived = 0;
        long totalByteSend = 0;
        long totalByteReceived = 0;
        long accumulatedTime = 0;

        System.out.println("Launch consumer...");

        long startPoint;
        long endPoint;
        long deviation = System.currentTimeMillis();

        int reqRespCount = Integer.valueOf(msg_count_s);
        int msgSize = Integer.valueOf(msg_size_s);
        System.out.println(String.format("RPC calls:\t\t%d\nMessage size:\t\t%d bytes\n", reqRespCount, msgSize));
        System.out.println("Running...");
        for (int request_nbr = 0; request_nbr < reqRespCount; request_nbr++) {
            byte[] payload = generatePayload(msgSize);

            startPoint = System.currentTimeMillis();
            requester.send(payload, 0);
            byte[] payloadResponse = requester.recv(0);
            endPoint = System.currentTimeMillis();

            totalByteSend += payload.length;
            totalByteReceived += payloadResponse.length;
            totalMsgSend++;
            totalMsgReceived++;
            accumulatedTime+=(endPoint - startPoint);

            boolean isEqual = Arrays.equals(payload, payloadResponse);
            assert isEqual;
        }
        deviation = System.currentTimeMillis() - deviation;

        System.out.println(String.format(
                    "Messages send/received:\t%d/%d\n" +
                            "Bytes send/received:\t%d/%d\n" +
                            "Transmission time:\t%dms\nDeviation:\t\t%dms",
            totalMsgSend, totalMsgReceived, totalByteSend, totalByteReceived, accumulatedTime, (deviation - accumulatedTime)
        ));
        System.out.println("Run complete.");
        requester.close();
        context.term();

    }

    private static byte[] generatePayload(int size) {
        byte[] array = new byte[size];
        Arrays.fill(array, (byte) new Random().nextInt());
        return array;
    }
}
