package in.muktashastra.core.util.constant;

/**
 * Constants used throughout the Muktashastra application.
 * 
 * @author Nilesh
 */
public class CoreConstant {

    /**
     * Private constructor to prevent instantiation.
     */
    private CoreConstant() {}

    /** String representation for boolean true */
    public static final String STRING_FLAG_TRUE = "true";
    
    /** String representation for boolean false */
    public static final String STRING_FLAG_FALSE = "false";

    /** ISO LocalDateTime format pattern */
    public static final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    
    /** Timestamp format for ID generation */
    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS";

    /** ISO LocalDate format pattern */
    public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

    public static final String FILE_NAME_EXTENSION_PROPERTIES = ".properties";

    public static final String MULTI_LINGUAL_MESSAGE_FILES_FOLDER_NAME = "i18nMessages";

}
