package de.neuefische.shopservice;

import java.math.BigDecimal;

public class ShopService {
    private final OrderRepo olr;
    private final ProductRepo pr;

    public ShopService() {
        this.olr = new OrderListRepo();
        this.pr = new ProductRepo();
    }

    public ShopService(OrderRepo olr) {
        this.olr = olr;
        this.pr = new ProductRepo();
    }

    public OrderRepo getOlr() {
        return olr;
    }

    public ProductRepo getPr() {
        return pr;
    }

    public BigDecimal placeOrder(Order order) {
        for (Product product : order.products()) {
            if (!pr.getAll().contains(product)) {
                System.out.println(product.name() + " is not available");
                order = order.withoutProduct(product);
            }
        }
        olr.add(order);
        return order.totalPrice();
    }
}
