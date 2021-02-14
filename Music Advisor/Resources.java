package advisor;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resources {

    private static String API_SERVER_PATH = "https://api.spotify.com";
    private final String accessToken;

    protected Resources(String[] args, String accToken) {
        if (Arrays.asList(args).contains("-resource")) {
            API_SERVER_PATH = args[3];
        }
        accessToken = accToken;
//        API_SERVER_PATH = "http://127.0.0.1:8080";
    }

    public List<String> getNew() {
        HttpResponse<String> response = getStringHttpResponse("/v1/browse/new-releases");
        List<String> result = new ArrayList<>();
        if (response != null) {
            JsonArray items = JsonParser.parseString(response.body()).getAsJsonObject().getAsJsonObject("albums").getAsJsonArray("items");
            for (int i = 0; i < items.size(); i++) {
                JsonObject item = items.get(i).getAsJsonObject();
                String albumName = item.get("name").getAsString() + "\n";
                StringBuilder artistNames = new StringBuilder();
                JsonArray artists = item.getAsJsonArray("artists");
//                artistNames.append(artists.getAsJsonObject().get("name").getAsString());
                for (JsonElement artist : artists) {
                    artistNames.append(artist.getAsJsonObject().get("name").getAsString());
                }
                artistNames.append("\n");
                String externalUrls = item.getAsJsonObject("external_urls").get("spotify").getAsString();
                result.add((albumName + artistNames + externalUrls));
            }
        }
        return result;
    }

    public List<String> getFeatured() {
        HttpResponse<String> response = getStringHttpResponse("/v1/browse/featured-playlists");
        List<String> result = new ArrayList<>();
        if (response != null) {
            JsonArray items = JsonParser.parseString(response.body()).getAsJsonObject().getAsJsonObject("playlists").getAsJsonArray("items");
            for (int i = 0; i < items.size(); i++) {
                JsonObject item = items.get(i).getAsJsonObject();
                String playlistName = item.get("name").getAsString() + "\n";
                String externalUrls = item.getAsJsonObject("external_urls").get("spotify").getAsString();
                result.add((playlistName + externalUrls));
            }
        }
        return result;
    }

    public List<String> getCategories() {
        HttpResponse<String> response = getStringHttpResponse("/v1/browse/categories");
        List<String> result = new ArrayList<>();
        if (response != null) {
            JsonArray categories = JsonParser.parseString(response.body()).getAsJsonObject().getAsJsonObject("categories").getAsJsonArray("items");
            for (int i = 0; i < categories.size(); i++) {
                JsonObject item = categories.get(i).getAsJsonObject();
                String category = item.get("name").getAsString();
                result.add((category));
            }
        }
        return result;
    }

    private HttpResponse<String> getStringHttpResponse(String targetUrl) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(API_SERVER_PATH + targetUrl))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }


}
