package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class Resources {
    private static String API_SERVER_PATH = "https://api.spotify.com";

    private String authorizationCode;
    private String accessToken;

    protected Resources(String[] args, String accToken) {
        if (Arrays.asList(args).contains("-resource")) {
            API_SERVER_PATH = args[3];
        }
        accessToken = accToken;
//        API_SERVER_PATH = "http://127.0.0.1:8080";
    }

    public String[] getNew() {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .header("limit", "5")
                .uri(URI.create(API_SERVER_PATH + "/v1/browse/new-releases"))
                .GET()
                .build();
        System.out.println(request.headers().firstValue("Authorization").get());
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if (response != null) {
            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
        }
        return null;
    }
}
