package io.k8scale.coral.benchmark;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ServerMessage {
    String Command;
    String Data;
    String SessionId;
}
