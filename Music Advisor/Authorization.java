package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Authorization {
    private static String SERVER_PATH = "https://accounts.spotify.com";

    private final static String CLIENT_ID = "0e90298f01c545f3a4d7c47f15ce0a13";
    private final static String CLIENT_SECRET = "1eaaa70c1bbc43b89c7986cab5a6d10b";
    private final static String REDIRECT_URI = "http://127.0.0.1:8080";
    private final static String RESPONSE_TYPE = "code";
    private final static String GRANT_TYPE = "authorization_code";

    private String authorizationCode;

    protected Authorization(String[] args) {
        if (Arrays.asList(args).contains("-access")) {
            SERVER_PATH = args[1];
        }
//        SERVER_PATH = "http://127.0.0.1:8080";
    }

    protected boolean getAuthorization() throws IOException, InterruptedException {
        System.out.println("use this link to request the access code:");
        System.out.println(SERVER_PATH + "/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=" + RESPONSE_TYPE);
        AtomicBoolean isAuthorized = new AtomicBoolean(false);
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.start();
        System.out.println("waiting for code...");
        server.createContext("/",
                exchange -> {
                    String query = exchange.getRequestURI().getQuery();
                    String response;
                    if (query != null && query.contains("code")) {
                        authorizationCode = query.substring(5);
                        response = "Got the code. Return back to your program.";
                        System.out.println("code received");
                        isAuthorized.set(true);
                    } else {
                        response = "Authorization code not found. Try again.";
                    }
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.getResponseBody().close();
                }
        );
        while (authorizationCode == null) {
            Thread.sleep(100);
        }
        server.stop(1);
        return isAuthorized.get();
    }

    protected String getAccessToken() {
        System.out.println("making http request for access_token...");
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&grant_type=" + GRANT_TYPE + "&code=" + authorizationCode + "&redirect_uri=" + REDIRECT_URI))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("response:");
        if (response != null) {
            System.out.println(response.body());
            System.out.println("---SUCCESS---");
            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            String accToken = jo.get("access_token").toString();
            accToken = accToken.substring(1, accToken.length() - 1);
            return accToken;
        }
        return null;
    }
}
