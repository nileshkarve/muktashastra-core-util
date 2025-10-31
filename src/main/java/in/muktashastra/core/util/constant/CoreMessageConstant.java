package in.muktashastra.core.util.constant;

/**
 * Constants class containing standardized message templates for the Muktashastra application.
 * All messages are mapped with  keys in message properties file
 * 
 * @author Nilesh
 */
public class CoreMessageConstant {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private CoreMessageConstant(){}

    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String PASSWORD_EXPIRED = "password.expired";
    public static final String INCORRECT_PASSWORD = "incorrect.password";

    public static final String USER_NO_CREATE_RIGHTS = "user.no.create.rights";
    public static final String USER_NO_UPDATE_RIGHTS = "user.no.update.rights";
    public static final String USER_NO_DELETE_RIGHTS = "user.no.delete.rights";
    public static final String USER_NO_VIEW_RIGHTS = "user.no.view.rights";

    public static final String INVALID_DATE_FORMAT = "invalid.date.format";
    public static final String INVALID_DATETIME_FORMAT = "invalid.datetime.format";
}
