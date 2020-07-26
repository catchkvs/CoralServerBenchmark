package io.k8scale.coral.benchmark;

import lombok.Data;
import lombok.ToString;

/**
 * Created by karmveer on 25-Jul-2020
 */
@Data
@ToString
public class ClientMessage {
    private String sessionId;
    private String data;
    private String authToken;
    private String command;

}
