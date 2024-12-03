package de.neuefische.shopservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    public ShopService(ProductRepo pr) {
        this.olr = new OrderListRepo();
        this.pr = pr;
    }

    public OrderRepo getOlr() {
        return olr;
    }

    public BigDecimal addOrder(Order order) {
        for (Product product : order.products()) {
            if (!pr.getAll().contains(product)) {
                System.out.println(product.name() + " is not available");
                order = order.withoutProduct(product);
            }
        }
        olr.add(order);
        return order.totalPrice();
    }

    public BigDecimal placeOrder(List<String> cart) {
        List<Product> finalCart = new ArrayList<>(List.of());
        for (String item : cart) {
            Product availableProduct = pr.getSingle(item);
            if (availableProduct == null) {
                System.out.println(item + " is not available");
            } else {
                finalCart.add(availableProduct);
            }
        }
        int randomId = (int) (Math.random() * 1000);
        Order order = new Order(randomId, finalCart);
        return addOrder(order);
    }

    public Order getOrder(int id) {
        return olr.getSingle(id);
    }

    public List<Order> listOrders() {
        return olr.getAll();
    }

    public void addProduct(Product product) {
        pr.add(product);
    }

    public Product getProduct(String name) {
        return pr.getSingle(name);
    }

    public List<Product> listProducts() {
        return pr.getAll();
    }
}
