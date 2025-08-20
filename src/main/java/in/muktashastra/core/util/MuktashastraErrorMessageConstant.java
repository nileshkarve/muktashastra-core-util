package in.muktashastra.core.util;

/**
 * Constants class containing standardized error message templates for the Muktashastra application.
 * All messages use String.format() placeholders (%s) for dynamic values.
 * 
 * @author Nilesh
 */
public class MuktashastraErrorMessageConstant {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private MuktashastraErrorMessageConstant(){}

    /** Error message for user not found scenario. Expects username parameter. */
    private static final String USER_NOT_FOUND = "User with username %s not found";
    
    /** Error message for expired password. Expects username parameter. */
    private static final String PASSWORD_EXPIRED = "Password for user %s has expired";
    
    /** Error message for incorrect password. Expects username parameter. */
    private static final String INCORRECT_PASSWORD = "Password entered for user %s is incorrect";
    
    /** Error message for insufficient create privileges. Expects username and resource parameters. */
    private static final String USER_DOES_NOT_HAVE_CREATE_RIGHTS = "User %s DOES NOT have create privileges on %s";
    
    /** Error message for insufficient update privileges. Expects username and resource parameters. */
    private static final String USER_DOES_NOT_HAVE_UPDATE_RIGHTS = "User %s DOES NOT have update privileges on %s";
    
    /** Error message for insufficient delete privileges. Expects username and resource parameters. */
    private static final String USER_DOES_NOT_HAVE_DELETE_RIGHTS = "User %s DOES NOT have delete privileges on %s";
    
    /** Error message for insufficient view privileges. Expects username and resource parameters. */
    private static final String USER_DOES_NOT_HAVE_VIEW_RIGHTS = "User %s DOES NOT have view privileges on %s";

    /** Error message for invalid date format. Expects date string parameter. */
    private static final String INVALID_DATE_FORMAT = "Incorrect date format for given date %s";
    
    /** Error message for invalid date time format. Expects date time string parameter. */
    private static final String INVALID_DATE_TIME_FORMAT = "Incorrect date time format for given date time %s";
}
