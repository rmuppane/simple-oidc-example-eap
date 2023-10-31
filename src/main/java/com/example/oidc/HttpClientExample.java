package com.example.oidc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class HttpClientExample {

    public static void main(String[] args) throws Exception {
        httpPostRequest();
    }

    public static void httpPostRequest() throws URISyntaxException, IOException, InterruptedException {
        
        String tokenEndpointUrl = "http://localhost:8180/auth/realms/example_realm/protocol/openid-connect/token";

        String clientId = "my_jbeap";

        // Define the request parameters
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", "user1");
        params.put("password", "Password@123");
        
        // Encode the request parameters
        String body = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tokenEndpointUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                // Successful response; parse the token
                System.out.println("Token response: " + response.body());
            } else {
                // Error response
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
