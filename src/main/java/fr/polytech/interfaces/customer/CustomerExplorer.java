package fr.polytech.interfaces.customer;

import fr.polytech.exceptions.BadCredentialsException;
import fr.polytech.exceptions.MalformedCredentialsExceptions;

public interface CustomerExplorer {
    /**
     * Check the given credentials
     * @param name The name of the customer
     * @param password The password of the customer
     * @throws BadCredentialsException The credentials are mismatched
     * @throws MalformedCredentialsExceptions The credentials are malformed
     */
    void checkCredentials(String name, String password) throws BadCredentialsException, MalformedCredentialsExceptions;
}
