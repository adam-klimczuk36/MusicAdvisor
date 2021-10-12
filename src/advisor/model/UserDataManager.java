package advisor.model;

import com.sun.net.httpserver.HttpServer;
import advisor.Constants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UserDataManager {
    private String USER_AUTHCODE = "";
    public static String ACCESS_SERVER;
    public static String API_SERVER;
    public static String CLIENT_SECRET;

    public UserDataManager(String ACCESS_SERVER, String API_SERVER, String CLIENT_SECRET) {
        UserDataManager.ACCESS_SERVER = ACCESS_SERVER;
        UserDataManager.API_SERVER = API_SERVER;
        UserDataManager.CLIENT_SECRET = CLIENT_SECRET;
    }

    public String getAuthCode() throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.start();

        server.createContext("/",
                exchange -> {
                    String query =  exchange.getRequestURI().getQuery();
                    String responseText = "Authorization code not found. Try again.";
                    if (query != null && query.contains("code")) {
                        USER_AUTHCODE = query.substring(5);
                        responseText = "Got the code. Return back to your program.";
                    }
                    exchange.sendResponseHeaders(200, responseText.length());
                    exchange.getResponseBody().write(responseText.getBytes());
                    exchange.getResponseBody().close();
                    countDownLatch.countDown();
                }
        );

        if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
            throw new InterruptedException();
        }
        countDownLatch.await(10, TimeUnit.SECONDS);

        server.stop(10);
        return USER_AUTHCODE;
    }

    public String requestAccessToken(String code) throws IOException {
        String urlParameters  = String.format("client_id=%s" +
                        "&client_secret=%s" +
                        "&grant_type=authorization_code" +
                        "&code=%s" +
                        "&redirect_uri=%s"
                ,Constants.CLIENT_ID, UserDataManager.CLIENT_SECRET, code, Constants.REDIRECT_URI);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        URL url = new URL(ACCESS_SERVER + "/api/token");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
            wr.write( postData );
        }

        StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        con.disconnect();
        return response.toString();
    }

    String requestHandler(String path, UserData userData) throws IOException {

        URL url = new URL(API_SERVER + path);
        String auth_base = userData.getTokenType() + " " + userData.getAccessToken();
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", auth_base);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", "0");

        if (con.getResponseCode() == 404) {
            throw new IOException();
        }

        StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        con.disconnect();
        return response.toString();
    }
}
