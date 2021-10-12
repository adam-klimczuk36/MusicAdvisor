package advisor.model;

import java.util.List;

public class Album {
    private String albumName;
    private List<String> artistNames;
    private String externalUrl;

    public Album(String albumName, List<String> artistNames, String externalUrl) {
        this.albumName = albumName;
        this.artistNames = artistNames;
        this.externalUrl = externalUrl;
    }

    @Override
    public String toString() {
        return albumName + '\n'
                + artistNames + '\n'
                + externalUrl + '\n';
    }
}
