package io.k8scale.coral.benchmark;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import com.google.gson.Gson;

import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by karmveer on 25-Jul-2020
 */
public class FactGenerator implements Runnable {
    @NonNull
    WebSocket webSocket;
    private int messagesPerSecond;
    private Gson gson;
    private WebsocketClient websocketClient;

    public void init() {
        this.messagesPerSecond = 1;
        this.gson = new Gson();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(Constants.END_POINT)
                .build();
        CountDownLatch latch = new CountDownLatch(1);
        websocketClient = new WebsocketClient(latch);
        this.webSocket = client.newWebSocket(request,websocketClient);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        FactEntity factEntity = new FactEntity();
        factEntity.name = "Order";
        factEntity.data = new Order();

        ClientMessage clientMessage = new ClientMessage();
        clientMessage.setData(Base64.getEncoder().encodeToString(gson.toJson(factEntity).getBytes()));
        clientMessage.setAuthToken(Constants.AUTH_TOKEN);
        clientMessage.setCommand("BroadcastFact");
        clientMessage.setSessionId(websocketClient.getSessionId());

        while(true) {
            for(int i=0; i<messagesPerSecond; i++) {
                System.out.println("Sending message: " +clientMessage);
                webSocket.send(gson.toJson(clientMessage));
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
