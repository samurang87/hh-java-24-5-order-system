package de.neuefische.shopservice;

import java.util.List;

public interface OrderRepo {

    List<Order> getAll();

    Order getSingle(int id);

    void add(Order order);

    void remove(Order order);

}
