package advisor.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistCategoryManager {

    public List<Playlist> getCategoryPlaylists(UserDataManager userDataManager, UserData userData, String C_NAME) throws IOException {
        String query = userDataManager.requestHandler("/v1/browse/categories/" + C_NAME + "/playlists", userData);
        return parseQueryCategoryPlaylists(query);
    }

    List<Playlist> parseQueryCategoryPlaylists(String query) {
        List<Playlist> playlists = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(query).getAsJsonObject();
        JsonObject joPlaylists = jo.getAsJsonObject("playlists");
        for (JsonElement playlist : joPlaylists.getAsJsonArray("items")) {
            String playlistName = playlist.getAsJsonObject().get("name").getAsString();
            String externalUrl = playlist.getAsJsonObject().getAsJsonObject("external_urls").get("spotify").getAsString();
            playlists.add(new Playlist(playlistName, externalUrl));
        }
        return playlists;
    }
}
