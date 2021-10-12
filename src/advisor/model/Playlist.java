package advisor.model;

public class Playlist {
    private String playlistName;
    private String externalUrl;

    public Playlist(String playlistName, String externalUrl) {
        this.playlistName = playlistName;
        this.externalUrl = externalUrl;
    }

    @Override
    public String toString() {
        return playlistName + '\n'
                + externalUrl + '\n';
    }
}
