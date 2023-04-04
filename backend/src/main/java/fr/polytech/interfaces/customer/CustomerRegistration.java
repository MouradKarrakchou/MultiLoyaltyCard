package fr.polytech.interfaces.customer;

import fr.polytech.exceptions.MailAlreadyUsedException;
import fr.polytech.entities.Customer;

public interface CustomerRegistration {
    Customer register(String name, String mail, String password) throws MailAlreadyUsedException;
}
