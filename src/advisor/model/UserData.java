package advisor.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserData {
    private String userAuthCode;
    private final User user;
    private String accessToken;
    private String tokenType;

    public UserData() {
        this.user = new User();
    }

    public User getUser() {
        return user;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setUserAuthCode(String userAuthCode) {
        this.userAuthCode = userAuthCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setCodes(String query) {
        this.accessToken = parseQueryTokenType("access_token", query);
        this.tokenType = parseQueryTokenType("token_type", query);
    }

    private String parseQueryTokenType(String field, String query) {
        JsonObject jo = JsonParser.parseString(query).getAsJsonObject();
        return jo.get(field).getAsString();
    }
}
