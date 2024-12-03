package de.neuefische.shopservice;

import java.util.ArrayList;
import java.util.List;

public record Order(int id, List<Product> products) {


    public Order withoutProduct(Product product){
        List<Product> newProductList = new ArrayList<>(List.copyOf(products));
        newProductList.remove(product);
        return new Order(id, newProductList);
    }
}
