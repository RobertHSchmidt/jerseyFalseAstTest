package jerseyFalseAstTest;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Petr Bouda (petr.bouda at oracle.com)
 */
public class App {

    private static final URI BASE_URI = URI.create("http://localhost:8080/base/");

    public static final String ROOT_PATH = "base";

    public static void main(String[] args) {
        try {
            System.out.println("\"Hello World\" Jersey-Spring Example App");

            final JerseyConfig resourceConfig = new JerseyConfig();
            resourceConfig.property("contextConfig", new AnnotationConfigApplicationContext(SpringAnnotationConfig.class));

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));
            server.start();

            System.out.println(String.format("Application started.\nTry out %s%s\nStop the application using CTRL+C",
                    BASE_URI, ROOT_PATH));

            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}