package org.example;

public class CoinPriceTracker {
    public static void main(String[] args) {
        String[] coins = {"aave", "bitcoin", "ethereum", "near"};
        for (String coin : coins) {
            try {
                double price = CoinConfig.getCoinPrice(coin);
                CoinConfig.savePriceToCSV(coin, price);
            } catch (Exception e) {
                System.err.println("Ошибка при получении цены для монеты " + coin + ": " + e.getMessage());
            }
        }
    }
}



