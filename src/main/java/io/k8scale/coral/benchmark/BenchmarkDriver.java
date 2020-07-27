package io.k8scale.coral.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by karmveer on 25-Jul-2020
 */
public class BenchmarkDriver {

    public static void main(String args[]) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        List<VirtualDevice> devices = new ArrayList<>();
        for(int i=0; i<5;i++) {
            VirtualDevice device = new VirtualDevice();
            device.init("device-" + i);
            devices.add(device);
        }
        FactGenerator generator = new FactGenerator();
        generator.init();
        executorService.submit(generator);
        try {
            Thread.sleep(300*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
