package io.k8scale.coral.benchmark;

import okhttp3.WebSocket;

/**
 * Created by karmveer on 25-Jul-2020
 */
public class FactGenerator implements Runnable {
    WebsocketClient client;
    WebSocket webSocket;
    private int messagesPerSecond;

    @Override
    public void run() {
        Order order = new Order();

        while(true) {
            for(int i=0; i<messagesPerSecond; i++) {
                //webSocket.send()
            }
        }
    }

}
