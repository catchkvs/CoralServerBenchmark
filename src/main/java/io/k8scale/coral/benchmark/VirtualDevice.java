package io.k8scale.coral.benchmark;

import com.google.gson.Gson;
import lombok.Data;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static io.k8scale.coral.benchmark.Constants.*;

/**
 * Created by karmveer on 25-Jul-2020
 */
@Data
public class VirtualDevice extends WebSocketListener {
    private static final String RECEIVE_ORDER = "ReceiveOrder";
    private String deviceId;
    private WebSocket webSocket;
    private int messagesReceiveCount;
    private String sessionId;
    private Gson gson;

    public void init(String deviceId) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(END_POINT)
                .build();
        this.webSocket = client.newWebSocket(request, this);
        this.deviceId = deviceId;
        this.gson = new Gson();
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        System.out.println("Message " + text);
        ServerMessage serverMessage = gson.fromJson(text, ServerMessage.class);
        this.messagesReceiveCount++;
        switch (serverMessage.getCommand()) {
            case RECEIVE_SESSION_ID:
                this.sessionId = serverMessage.getSessionId();
                DeviceSubscription deviceSubscription = new DeviceSubscription();
                deviceSubscription.setDeviceId(deviceId);
                deviceSubscription.setTopic("Order");
                ClientMessage msg = new ClientMessage();
                msg.setAuthToken(AUTH_TOKEN);
                msg.setCommand("GetLiveUpdates");
                msg.setData(Base64.getEncoder().encodeToString(gson.toJson(deviceSubscription).getBytes()));
                msg.setSessionId(sessionId);
                webSocket.send(gson.toJson(msg));
                break;
            case RECEIVE_ORDER:
                this.messagesReceiveCount++;
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        System.out.println("Device id: " + deviceId + " is open");
        super.onOpen(webSocket, response);

    }

}
