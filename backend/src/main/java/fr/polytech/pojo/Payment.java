package fr.polytech.pojo;

import ch.qos.logback.core.net.server.Client;
import fr.polytech.pojo.item.Item;
import fr.polytech.components.customer.CustomerFidelityManager;
import fr.polytech.pojo.item.Product;
import fr.polytech.pojo.structure.Employee;
import fr.polytech.pojo.structure.Store;

import java.util.Set;

public class Payment {
    private int id;
    private Customer customer;
    private Store store;
    private Employee employee;

    private Set<Item> shoppingList;

    private boolean isSettled;

    public boolean isSettled() {
        return isSettled;
    }

    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<Item> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(Set<Item> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Store getStore() {
        return store;
    }
}