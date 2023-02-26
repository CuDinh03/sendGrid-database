package com.Webhook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class WebhookListener {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demosendmail";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "crmviet123456";

    public static void main(String[] args) throws Exception {
        String endpointUrl = "https://webhook.site/8aa0126d-965e-4621-b4d9-add872539af0";
        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder responseBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        String response = responseBuilder.toString();
        System.out.println(response);

        saveDataToDatabase(response);
    }

    private static void saveDataToDatabase(String jsonPayload) {
        // Parse the JSON payload and extract the relevant data
        // For example, you could use a JSON library like Jackson to parse the payload

        // Connect to the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Insert the data into the database
            String query = "INSERT INTO email_events (event_type, recipient_email, timestamp) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "open");
            statement.setString(2, "dinhhuycu0305@gmail.com");
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
        }
    }
}

