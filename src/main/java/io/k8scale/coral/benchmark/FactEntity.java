package io.k8scale.coral.benchmark;

import lombok.Data;

/**
 * Created by karmveer on 25-Jul-2020
 */
@Data
public class FactEntity {

    String id;
    String type;
    String name;
    Order data;
}
