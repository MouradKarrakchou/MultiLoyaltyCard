package fr.polytech.controllers;

import fr.polytech.controllers.dto.ErrorDTO;
import fr.polytech.exceptions.*;
import fr.polytech.exceptions.discount.DiscountNotFoundException;
import fr.polytech.exceptions.discount.NoDiscountsFoundException;
import fr.polytech.exceptions.payment.NegativeAmountException;
import fr.polytech.exceptions.payment.PaymentAlreadyExistsException;
import fr.polytech.exceptions.payment.PaymentInBankException;
import fr.polytech.exceptions.store.StoreNotFoundException;
import fr.polytech.exceptions.store.StoreSiretAlreadyUsedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {CustomerAccountController.class, CatalogController.class,
        ClientPaymentController.class, PartnerStoreController.class})
public class GlobalControllerAdvice {

    @ExceptionHandler({MailAlreadyUsedException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(MailAlreadyUsedException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Mail already used!");
        errorDTO.setDetails(e.getMail() + " has already been used to register to our multi-fidelity card service...");
        return errorDTO;
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleExceptions(BadCredentialsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Wrong credentials!");
        errorDTO.setDetails("The mail or the password is wrong.");
        return errorDTO;
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(CustomerNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("User not found!");
        errorDTO.setDetails("The account associated to the id #" + e.getMail() + " has not been found...");
        return errorDTO;
    }

    @ExceptionHandler({NegativeAmountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleExceptions(NegativeAmountException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Negative or zero amount!");
        errorDTO.setDetails("You cannot process with a " + e.getAmount() + " amount or price. It must be positive...");
        return errorDTO;
    }

    @ExceptionHandler({PaymentInBankException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleExceptions(PaymentInBankException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Payment declined!");
        errorDTO.setDetails("The payment of an amount of " + e.getAmount() + " has been refused...");
        return errorDTO;
    }

    @ExceptionHandler({NoDiscountsFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(NoDiscountsFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Discounts not found!");
        errorDTO.setDetails("No discounts were found...");
        return errorDTO;
    }

    @ExceptionHandler({DiscountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(DiscountNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Discount not found!");
        if (e.getId() != null) errorDTO.setDetails("The discount with id #" + e.getId() + "has not been found...");
        else errorDTO.setDetails("The discount with with name" + e.getName() + "has not been found...");
        return errorDTO;
    }

    @ExceptionHandler({StoreNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(StoreNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Store not found!");
        errorDTO.setDetails("The store with with id #" + e.getId() + " has not been found...");
        return errorDTO;
    }

    @ExceptionHandler({PaymentAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(PaymentAlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Payment already exists!");
        errorDTO.setDetails("The payment with id #" + e.getId() + "already exists...");
        return errorDTO;
    }

    @ExceptionHandler({StoreSiretAlreadyUsedException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(StoreSiretAlreadyUsedException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Siret already exists!");
        errorDTO.setDetails(e.getSiret() + " has already been used to register to our multi-fidelity card service...");
        return errorDTO;
    }

    @ExceptionHandler({IllegalDateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(IllegalDateException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Illegal date!");
        errorDTO.setDetails("The date " + e.getDate().toString() + "is past the today date...");
        return errorDTO;
    }

    @ExceptionHandler({NotEnoughBalanceException.class})
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorDTO handleExceptions(NotEnoughBalanceException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Not enough balance!");
        errorDTO.setDetails(e.getAmount() + " is needed but balance is: " + e.getBalance());
        return errorDTO;
    }

}