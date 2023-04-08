package fr.polytech.interfaces.payment;

import fr.polytech.entities.Customer;
import fr.polytech.exceptions.payment.NegativeAmountException;
import fr.polytech.exceptions.payment.PaymentInBankException;

import java.util.Date;

public interface RefillFidelityCard {
    Date refill(Customer customer, String creditCard, double amount ) throws NegativeAmountException, PaymentInBankException;
}
