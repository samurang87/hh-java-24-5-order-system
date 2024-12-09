package de.neuefische.shopservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record Order(int id, List<Product> products, OrderStatus status) {

    public Order(int id, List<Product> products) {
        this(id, products, OrderStatus.PROCESSING);
    }

    public Order withStatus(OrderStatus status) {
        return new Order(this.id, this.products, status);
    }

    public Order withoutProduct(Product product){
        List<Product> newProductList = new ArrayList<>(List.copyOf(products));
        newProductList.remove(product);
        return new Order(id, newProductList, status);
    }

    public BigDecimal totalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Product product : products) {
            totalPrice = totalPrice.add(product.price());
        }
        return totalPrice;
    }



}
