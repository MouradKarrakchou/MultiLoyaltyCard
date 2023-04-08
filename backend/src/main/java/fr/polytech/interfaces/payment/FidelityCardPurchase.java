package fr.polytech.interfaces.payment;

import fr.polytech.entities.item.Item;
import fr.polytech.exceptions.NotEnoughBalanceException;
import fr.polytech.entities.Customer;

import java.util.Set;

public interface FidelityCardPurchase {
    Customer buyWithFidelityCard(Customer customer, Set<Item> shoppingList) throws NotEnoughBalanceException;
}
