package org.filewriter;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartServer {
    public static void main(String[] args) throws Exception {
        FileRepository repository = new FileRepository();
        FileService service = new FileService(repository);
        FileController controller = new FileController(service);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/upload", controller);
        server.createContext("/files/", controller);
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();

        System.out.println("Сервер запущен на http://localhost:8080");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
                service::cleanupExpiredFiles,
                1,
                1,
                TimeUnit.HOURS
        );
    }
}
