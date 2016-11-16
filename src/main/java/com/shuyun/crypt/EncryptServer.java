package com.shuyun.crypt;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * @author hewei
 * @version 5.0
 * @date 16/11/16  18:01
 * @desc
 */
@Slf4j
public class EncryptServer {

    private static final int PORT = 50051;

    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new EncryptImpl())
                .executor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2))
                .build()
                .start();
        log.info("Server started, listening on :{}", PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.error("*** shutting down gRPC server since JVM is shutting down");
                EncryptServer.this.stop();
                log.error("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
