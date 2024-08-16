//package org.example;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import java.io.BufferedReader;
//import java.io.FileWriter;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class SomeCheck {
//    private static final String API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=";
//    private static final String CURRENCY = "usd";
//
//    public static void main(String[] args) {
//        String[] coins = {"aave", "bitcoin", "ethereum"}; // Убедитесь, что это правильные идентификаторы монет
//        for (String coin : coins) {
//            try {
//                double price = getCoinPrice(coin);
//                if (price != -1) { // Проверка на успешное получение цены
//                    savePriceToCSV(coin, price);
//                } else {
//                    System.err.println("Не удалось получить цену для монеты " + coin);
//                }
//            } catch (Exception e) {
//                System.err.println("Ошибка при получении цены для монеты " + coin + ": " + e.getMessage());
//            }
//        }
//    }
//
//    private static double getCoinPrice(String coin) throws Exception {
//        String urlString = API_URL + coin + "&vs_currencies=" + CURRENCY;
//        URL url = new URL(urlString);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//
//        int responseCode = connection.getResponseCode();
//        if (responseCode != HttpURLConnection.HTTP_OK) {
//            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
//        }
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        StringBuilder content = new StringBuilder();
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//        in.close();
//        connection.disconnect();
//
//        JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();
//
//        if (jsonObject.has(coin)) {  // Проверяем, есть ли ключ с именем монеты
//            JsonObject coinData = jsonObject.getAsJsonObject(coin);
//            if (coinData != null && coinData.has(CURRENCY)) {  // Проверяем, есть ли значение для нужной валюты
//                return coinData.get(CURRENCY).getAsDouble();
//            }
//        }
//
//        // Если не удалось найти данные по монете
//        return -1;
//    }
//
//    private static void savePriceToCSV(String coin, double price) {
//        try (FileWriter writer = new FileWriter("coin_prices.csv", true)) {
//            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
//            writer.append(date).append(",").append(coin).append(",").append(String.valueOf(price)).append("\n");
//        } catch (Exception e) {
//            System.err.println("Ошибка при записи в CSV: " + e.getMessage());
//        }
//    }
//}
