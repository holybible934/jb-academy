package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyHTTPServer {
    String myCode = "";
    String createServer(int port, String urlGet) {

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", new HttpHandler() {

            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String str ;
                String query;
                query = exchange.getRequestURI().getQuery();
                if (query == null || query.equals("error=access_denied")) {
                    str = "Authorization code not found. Try again.";
                } else {
                    str = "Got the code. Return back to your program.\n";
                    System.out.println("waiting for code...\n" +
                            "code received");
                    myCode = query;
                }
                exchange.sendResponseHeaders(200, str.length());
                exchange.getResponseBody().write(str.getBytes());
                exchange.getResponseBody().close();
            }
        });

        server.start();
        requestGet(urlGet);

        while (myCode.equals("") || myCode.equals("error=access_denied")) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        server.stop(10);

        return myCode;
    }

    void requestGet(String url) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET().build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("use this link to request the access code:");
        System.out.println(response.uri());

    }
    void requestPostClient( String requestPost, String urlPost) {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestPost))
                .uri(URI.create(urlPost))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("making http request for access_token...\n" +
                "response:");
        System.out.println(response.body());
        System.out.println("---SUCCESS---");

    }
}
