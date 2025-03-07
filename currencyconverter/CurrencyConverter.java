package currencyconverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CurrencyConverter {
    public static final String API_KEY = "475652b88aafe5be2643d3b0";
    public static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        converter.inputCurrency();
    }

    public void inputCurrency() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the currency you want to convert from (e.g., USD): ");
        String fromCurrency = scanner.next().toUpperCase();

        System.out.print("Enter the currency you want to convert to (e.g., EUR): ");
        String toCurrency = scanner.next().toUpperCase();

        System.out.print("Enter the amount you want to convert: ");
        double amount = scanner.nextDouble();

        convertCurrency(fromCurrency, toCurrency, amount);

        scanner.close(); // Close after all inputs
    }

    public void convertCurrency(String fromCurrency, String toCurrency, double amount) {
        double conversionRate = getConversionRate(fromCurrency, toCurrency);

        if (conversionRate == -1) {
            System.out.println("Error fetching exchange rates. Please try again.");
        } else {
            double convertedAmount = amount * conversionRate;
            System.out.printf("%.2f %s = %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
        }
    }

    public double getConversionRate(String fromCurrency, String toCurrency) {
        String urlStr = API_URL + fromCurrency;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                System.out.println("Error fetching exchange rates. HTTP Code: " + conn.getResponseCode());
                return -1;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());

            if (jsonObject.has("error")) {
                System.out.println("API Error: " + jsonObject.getString("error"));
                return -1;
            }

            JSONObject conversionRates = jsonObject.getJSONObject("conversion_rates");
            if (!conversionRates.has(toCurrency)) {
                System.out.println("Invalid target currency: " + toCurrency);
                return -1;
            }

            return conversionRates.getDouble(toCurrency);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return -1;
        }
    }
}
