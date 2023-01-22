package fr.polytech.interfaces.Payment;

import fr.polytech.exceptions.MalformedBankInformation;
import fr.polytech.exceptions.NotEnoughBalanceException;
import fr.polytech.pojo.BankTransaction;
import fr.polytech.pojo.FidelityAccount;

public interface BalanceModifier {
    void decreaseBalance(FidelityAccount fidelityAccount, float amount) throws NotEnoughBalanceException;
    void rechargeBalance(FidelityAccount fidelityAccount, BankTransaction bankTransaction, float amount) throws MalformedBankInformation;
}
