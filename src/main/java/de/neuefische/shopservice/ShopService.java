package de.neuefische.shopservice;

public class ShopService {
    private final OrderListRepo olr;
    private final ProductRepo pr;

    public ShopService() {
        this.olr = new OrderListRepo();
        this.pr = new ProductRepo();
    }

    public OrderListRepo getOlr() {
        return olr;
    }

    public ProductRepo getPr() {
        return pr;
    }

    public void placeOrder(Order order) {
        for (Product product : order.products()) {
            if (!pr.getAll().contains(product)) {
                System.out.println(product.name() + " is not available");
                order = order.withoutProduct(product);
            }
        }
        olr.add(order);
    }
}
