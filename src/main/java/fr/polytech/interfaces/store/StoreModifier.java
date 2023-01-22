package fr.polytech.interfaces.store;

import fr.polytech.exceptions.*;

public interface StoreModifier {
    void addEmployee(String name, String password,String role) throws NotEnoughPermissionException, EmployeeNotFoundException, WrongEmployeeNameOrPassword;
    void deleteEmployee(int id, String myName, String myPassword) throws NotEnoughPermissionException, EmployeeNotFoundException, WrongEmployeeNameOrPassword;
    void changeDayOpeningHours(String Day,String openingHour,String closingHour) throws NotEnoughPermissionException, WrongEmployeeNameOrPassword,NotValidDayException, NotValidHourException;
    void changeDayStatus(String Day,Boolean open) throws NotEnoughPermissionException, WrongEmployeeNameOrPassword, NotValidDayException, NotValidHourException;

}
