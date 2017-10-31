package ru.codeunited.zmq.synth.reqresp;

import org.zeromq.ZMQ;

public class ServiceProvider {

    private static final String PROVIDER_LISTEN_ADDR = "PROVIDER_LISTEN_ADDR";

    static {
        System.load("/usr/local/lib/libzmq.so.5.1.3");
        System.load("/usr/local/lib/libjzmq.so.0.0.0");
    }

    public static void main(String[] args) {
        String addr = System.getenv(PROVIDER_LISTEN_ADDR);
        if (addr == null)
            addr = "tcp://*:5559"; // ipc:///tmp/zprov.icp
        System.out.println(String.format("Listening on %s", addr));

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket responder  = context.socket(ZMQ.REP);
        responder.bind(addr);

        System.out.println("Launch provider...");

        while (!Thread.currentThread().isInterrupted()) {
            byte[] request = responder.recv(0);
            responder.send(request, 0);
        }

        responder.close();
        context.term();
    }
}
