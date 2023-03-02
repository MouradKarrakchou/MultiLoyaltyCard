package fr.polytech.interfaces.store;

import fr.polytech.exceptions.store.InvalidDayException;
import fr.polytech.exceptions.store.InvalidHourException;
import fr.polytech.pojo.Schedule;

public interface ScheduleModifier {
    void changeDayOpeningHours(Schedule schedule, String Day, String openingHour, String closingHour) throws NotEnoughPermissionException, InvalidDayException, InvalidHourException;
    void changeDayStatus(Schedule schedule, String Day,Boolean open) throws NotEnoughPermissionException, InvalidDayException, InvalidHourException;

}
