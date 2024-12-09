package de.neuefische.shopservice;

import lombok.With;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public record Order(int id, @With List<Product> products, @With OrderStatus status, @With Instant timestamp) {

    public Order(int id, List<Product> products) {
        this(id, products, OrderStatus.PROCESSING, Instant.now(Clock.systemDefaultZone()));
    }

    public Order(int id, List<Product> products, Instant orderTimestamp) {
        this(id, products, OrderStatus.PROCESSING, orderTimestamp);
    }

    public Order withoutProduct(Product product){
        List<Product> newProductList = new ArrayList<>(List.copyOf(products));
        newProductList.remove(product);
        return new Order(id, newProductList, status, timestamp);
    }

    public BigDecimal totalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Product product : products) {
            totalPrice = totalPrice.add(product.price());
        }
        return totalPrice;
    }



}
