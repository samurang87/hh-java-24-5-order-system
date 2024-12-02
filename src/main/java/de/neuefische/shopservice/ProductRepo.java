package de.neuefische.shopservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductRepo {
    private final List<Product> products;

    public ProductRepo() {
        this.products = new ArrayList<Product>();
    }

    public List<Product> getAll() {
        return products;
    }

    public void add(Product product) {
        this.products.add(product);
    }

    public void remove(Product product) {
        this.products.remove(product);
    }

    public Product getSingle(int id) {
        return this.products.stream()
                .filter(product -> id == product.id())
                .findFirst()
                .orElse(null);
    }

    public Product getSingle(String name) {
        return this.products.stream()
                .filter(product -> Objects.equals(name, product.name()))
                .findFirst()
                .orElse(null);
    }
}
