import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings;

    public Portfolio() {
        this.holdings = new HashMap<>();
    }

    public void buyStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public void sellStock(String symbol, int quantity) {
        if (holdings.containsKey(symbol)) {
            int currentQuantity = holdings.get(symbol);
            if (currentQuantity >= quantity) {
                holdings.put(symbol, currentQuantity - quantity);
                if (holdings.get(symbol) == 0) {
                    holdings.remove(symbol);
                }
            } else {
                System.out.println("Insufficient quantity to sell.");
            }
        } else {
            System.out.println("Stock not in portfolio.");
        }
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }
}

class Market {
    private Map<String, Stock> stocks;

    public Market() {
        this.stocks = new HashMap<>();
    }

    public void addStock(String symbol, double price) {
        stocks.put(symbol, new Stock(symbol, price));
    }

    public void updateStockPrice(String symbol, double price) {
        if (stocks.containsKey(symbol)) {
            stocks.get(symbol).setPrice(price);
        } else {
            System.out.println("Stock not found.");
        }
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void displayMarket() {
        for (Stock stock : stocks.values()) {
            System.out.printf("Symbol: %s, Price: %.2f%n", stock.getSymbol(), stock.getPrice());
        }
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Market market = new Market();
        Portfolio portfolio = new Portfolio();

        // Add some initial stocks to the market
        market.addStock("AAPL", 150.00);
        market.addStock("GOOGL", 2800.00);
        market.addStock("AMZN", 3300.00);

        while (true) {
            System.out.println("\nMarket Data:");
            market.displayMarket();
            System.out.println("\n1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.nextLine();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null) {
                        portfolio.buyStock(buySymbol, buyQuantity);
                        System.out.printf("Bought %d of %s at %.2f each.%n", buyQuantity, buySymbol, buyStock.getPrice());
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null) {
                        portfolio.sellStock(sellSymbol, sellQuantity);
                        System.out.printf("Sold %d of %s.%n", sellQuantity, sellSymbol);
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;

                case 3:
                    System.out.println("\nYour Portfolio:");
                    for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
                        Stock stock = market.getStock(entry.getKey());
                        if (stock != null) {
                            System.out.printf("Symbol: %s, Quantity: %d, Current Price: %.2f%n", entry.getKey(), entry.getValue(), stock.getPrice());
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
