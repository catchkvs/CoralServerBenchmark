package io.k8scale.coral.benchmark;

import com.google.gson.Gson;
import lombok.Data;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CountDownLatch;

/**
 * Created by karmveer on 25-Jul-2020
 */
@Data
public class WebsocketClient extends WebSocketListener {
    private String sessionId;
    private Gson gson = new Gson();
    private CountDownLatch latch;

    public WebsocketClient(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        ServerMessage serverMessage = gson.fromJson(text, ServerMessage.class);
        System.out.println("Server Message: " + serverMessage);
        switch (serverMessage.getCommand()) {
            case Constants.RECEIVE_SESSION_ID:
                this.sessionId = serverMessage.getSessionId();
                latch.countDown();
                break;
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
    }
}
