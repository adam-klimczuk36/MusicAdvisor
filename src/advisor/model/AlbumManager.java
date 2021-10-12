package advisor.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlbumManager {

    public List<Album> getNewReleases(UserDataManager userDataManager, UserData userData) throws IOException {
        String query = userDataManager.requestHandler("/v1/browse/new-releases", userData);
        return parseQueryNewReleases(query);
    }

    private List<Album> parseQueryNewReleases(String query) {
        List<Album> albums = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(query).getAsJsonObject();
        JsonObject joAlbums = jo.getAsJsonObject("albums");
        for (JsonElement album : joAlbums.getAsJsonArray("items")) {
            String albumName = album.getAsJsonObject().get("name").getAsString();
            String externalUrl = album.getAsJsonObject().getAsJsonObject("external_urls").get("spotify").getAsString();

            List<String> artistNames = new ArrayList<>();
            for (JsonElement artist : album.getAsJsonObject().getAsJsonArray("artists")) {
                artistNames.add(artist.getAsJsonObject().get("name").getAsString());
            }
            albums.add(new Album(albumName, artistNames, externalUrl));
        }
        return albums;
    }
}
