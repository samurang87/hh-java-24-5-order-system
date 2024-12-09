// java -cp target/your-jar-file.jar de.neuefische.shopservice.ShopServiceCLI

package de.neuefische.shopservice;

import picocli.CommandLine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

@CommandLine.Command(name = "shopservice", mixinStandardHelpOptions = true, description = "Runs the ShopService CLI")
public class ShopServiceCLI implements Runnable {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    private final ShopService shopService;

    public ShopServiceCLI(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public void run() {
        System.out.println("Hello! We have these products in the shop today");
        for (Product item : shopService.listProducts()) {
            System.out.println(CYAN + item + ANSI_RESET);
        }

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> cart = new ArrayList<>();
        System.out.println("Would you like to add an item to your cart? (y/n) ");
        String nextItem = scanner.nextLine().toLowerCase();
        while (true) {
            if (nextItem.equals("y")) {
                System.out.println("What would you like to buy? ");
                cart.add(scanner.nextLine());
            } else if (nextItem.equals("n")) {
                break;
            } else {
                System.out.println(RED + "Invalid input. Please enter 'y' or 'n'." + ANSI_RESET);
            }
            System.out.println("Would you like to add another item? (y/n) ");
            nextItem = scanner.nextLine().toLowerCase();
        }
        BigDecimal totalPrice = shopService.placeOrder(cart);
        if (totalPrice.equals(BigDecimal.ZERO)) {
            System.out.println(GREEN + "Alright, no shopping for you today it seems \uD83D\uDC4D" + ANSI_RESET);
            return;
        }
        System.out.println(GREEN + "Please pay " + totalPrice + " € \uD83D\uDCB8" + ANSI_RESET);
    }

    public static void main(String[] args) {
        ProductRepo productRepo = initProductRepo();
        OrderRepo orderRepo = new OrderListRepo();
        ShopService shopService = new ShopService(orderRepo, productRepo);

        int exitCode = new CommandLine(new ShopServiceCLI(shopService)).execute(args);
        System.exit(exitCode);
    }

    private static ProductRepo initProductRepo() {
        ProductRepo productRepo = new ProductRepo();
        productRepo.add(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)));
        productRepo.add(new Product(2, "Floss", BigDecimal.valueOf(1.29)));
        productRepo.add(new Product(3, "TP", BigDecimal.valueOf(2.69)));
        productRepo.add(new Product(4, "Nail Polish", BigDecimal.valueOf(1.89)));
        return productRepo;
    }
}