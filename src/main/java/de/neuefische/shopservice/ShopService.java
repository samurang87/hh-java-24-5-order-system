package de.neuefische.shopservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ShopService {
    private final OrderRepo olr;
    private final ProductRepo pr;
    private final IdService ids;

    public ShopService() {
        this.olr = new OrderListRepo();
        this.pr = new ProductRepo();
        this.ids = new IdService();
    }

    public ShopService(OrderRepo olr) {
        this.olr = olr;
        this.pr = new ProductRepo();
        this.ids = new IdService();
    }

    public ShopService(ProductRepo pr) {
        this.olr = new OrderListRepo();
        this.pr = pr;
        this.ids = new IdService();
    }

    public ShopService(OrderRepo olr, ProductRepo pr) {
        this.olr = olr;
        this.pr = pr;
        this.ids = new IdService();
    }

    // Orders

    public BigDecimal addOrder(Order order) throws ProductNotFoundException {
        for (Product product : order.products()) {
            if (!pr.getAll().contains(product)) {
                throw new ProductNotFoundException(product.name() + " not available");
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
        Order order = new Order(ids.generateUUID(), finalCart);
        return addOrder(order);
    }

    public Order getOrder(UUID id) {
        return olr.getSingle(id);
    }

    public List<Order> listOrders() {
        return olr.getAll();
    }

    public Order updateOrder(UUID id, OrderStatus status) {
        Order order = getOrder(id);
        return order.withStatus(status);
    }

    // Products

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
