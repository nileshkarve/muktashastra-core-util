package in.muktashastra.core.util;

public class MuktashastraErrorMessageConstant {

    private MuktashastraErrorMessageConstant(){}

    private static final String USER_NOT_FOUND = "User with username %s not found";
    private static final String PASSWORD_EXPIRED = "Password for user %s has expired";
    private static final String INCORRECT_PASSWORD = "Password entered for user %s is incorrect";
    private static final String USER_DOES_NOT_HAVE_CREATE_RIGHTS = "User %s DOES NOT have create privileges on %s";
    private static final String USER_DOES_NOT_HAVE_UPDATE_RIGHTS = "User %s DOES NOT have update privileges on %s";
    private static final String USER_DOES_NOT_HAVE_DELETE_RIGHTS = "User %s DOES NOT have delete privileges on %s";
    private static final String USER_DOES_NOT_HAVE_VIEW_RIGHTS = "User %s DOES NOT have view privileges on %s";

    private static final String INVALID_DATE_FORMAT = "Incorrect date format for given date %s";
    private static final String INVALID_DATE_TIME_FORMAT = "Incorrect date time format for given date time %s";
}
