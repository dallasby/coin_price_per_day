package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class CoinConfig {
    public static final String API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=";
    public static final String CURRENCY = "usd";

    private CoinConfig() {
    }

    public static double getCoinPrice(String coin) throws Exception {
        String urlString = API_URL + coin + "&vs_currencies=" + CURRENCY;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();

        if (jsonObject.has(coin)) {  // Проверяем, есть ли ключ с именем монеты
            JsonObject coinData = jsonObject.getAsJsonObject(coin);
            if (coinData != null && coinData.has(CURRENCY)) {  // Проверяем, есть ли значение для нужной валюты
                return coinData.get(CURRENCY).getAsDouble();
            }
        }

        // Если не удалось найти данные по монете
        return -1;
    }

    public static void savePriceToCSV(String coin, double price) {
        try (FileWriter fileWriter = new FileWriter("prices.csv", true)) {
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            fileWriter.append(date).append(",").append(coin).append(",").append(String.valueOf(price)).append("\n");
        } catch (IOException e) {
            throw new CoinException("Error while adding to CSV: " + e.getMessage());
        }
    }
}
