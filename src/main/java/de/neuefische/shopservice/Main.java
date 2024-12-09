package de.neuefische.shopservice;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        ShopService shopService = new ShopService(new OrderMapRepo(), initProductRepo(), new IdService());

        // Define three concrete orders and add them all to the ShopService.
        shopService.placeOrder(List.of("Toothpaste", "Floss"));
        shopService.placeOrder(List.of("Nail Polish"));
        shopService.placeOrder(List.of("TP", "TP"));

        System.out.println(shopService.listOrders());
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
