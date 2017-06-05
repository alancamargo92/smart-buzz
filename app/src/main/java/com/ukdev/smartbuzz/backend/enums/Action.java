package com.ukdev.smartbuzz.backend.enums;

/**
 * An intent action
 *
 * @author Alan Camargo
 */
public enum Action {

    /**
     * Calls Sleep Checker
     */
    CALL_SLEEP_CHECKER("com.ukdev.smartbuzz.ACTION_CALL_SLEEP_CHECKER"),

    /**
     * Cancels an alarm
     */
    CANCEL_ALARM("com.ukdev.smartbuzz.ACTION_CANCEL_ALARM"),

    /**
     * Creates a new alarm
     */
    CREATE_ALARM("com.ukdev.smartbuzz.ACTION_CREATE_ALARM"),

    /**
     * Delays an alarm (snooze operation)
     */
    DELAY_ALARM("com.ukdev.smartbuzz.ACTION_DELAY_ALARM"),

    /**
     * Schedules an alarm
     */
    SCHEDULE_ALARM("com.ukdev.smartbuzz.ACTION_SCHEDULE_ALARM"),

    /**
     * Triggers an alarm
     */
    TRIGGER_ALARM("com.ukdev.smartbuzz.ACTION_TRIGGER_ALARM"),

    /**
     * Triggers Sleep Checker
     */
    TRIGGER_SLEEP_CHECKER("com.ukdev.smartbuzz.ACTION_TRIGGER_SLEEP_CHECKER"),

    /**
     * Wakes up the device
     */
    WAKE_UP("com.ukdev.smartbuzz.ACTION_WAKE_UP");

    private final String value;

    /**
     * Default constructor for {@code Action}
     * @param value the string value
     */
    Action(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
