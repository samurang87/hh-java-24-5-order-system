package de.neuefische.shopservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShopService shopService = new ShopService(initProductRepo());

        System.out.println("Hello! We have these products in the shop today");
        System.out.println(shopService.listProducts());

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> cart = new ArrayList<>();
        System.out.println("Would you like to add an item to your cart? (y/n) ");
        String nextItem = scanner.nextLine();;
        while (true) {
            if (nextItem.equals("y")) {
                System.out.println("What would you like to buy? ");
                cart.add(scanner.nextLine());
            } else if (nextItem.equals("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
            System.out.println("Would you like to add another item? (y/n) ");
            nextItem = scanner.nextLine();
        }
        BigDecimal totalPrice = shopService.placeOrder(cart);
        if (totalPrice.equals(BigDecimal.ZERO)) {
            System.out.println("Alright, no shopping for you today it seems \uD83D\uDC4D");
            return;
        }
        System.out.println("Please pay " + totalPrice + " € \uD83D\uDCB8");
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
