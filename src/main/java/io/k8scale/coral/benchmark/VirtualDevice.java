package io.k8scale.coral.benchmark;

import lombok.Data;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Created by karmveer on 25-Jul-2020
 */
@Data
public class VirtualDevice {
    private String deviceId;
    private WebsockClient client;
    private WebSocket webSocket;
    private int messagesReceiveCount;
    public void start() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(Constants.END_POINT)
                .build();
        client.newWebSocket(request, new WebsockClient());
    }


    private class WebsockClient extends WebSocketListener {
        private VirtualDevice virtualDevice;

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
            super.onMessage(webSocket, text);
            virtualDevice.messagesReceiveCount++;
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
}
