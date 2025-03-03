package currencyConverter;

import java.util.Scanner;

public class CurrencyConverter {
    public static final String API_KEY = "475652b88aafe5be2643d3b0";
    public static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";


}
public void inputCurrency() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the currency you want to convert from: ");
    String fromCurrency = scanner.nextLine();
    System.out.println("Enter the currency you want to convert to: ");
    String toCurrency = scanner.nextLine();
    System.out.println("Enter the amount you want to convert: ");
    double amount = scanner.nextDouble();
    scanner.close();
    convertCurrency(fromCurrency, toCurrency, amount);
}