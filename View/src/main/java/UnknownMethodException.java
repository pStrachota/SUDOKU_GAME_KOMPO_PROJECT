import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class UnknownMethodException extends Exception {

    public static final String UNKNOWN_METHOD = "method.not.found";
    private static final ResourceBundle messages;

    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("exceptions", locale);
    }

    public UnknownMethodException(String message) {
        super(message);
    }

    public UnknownMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        String message;

        try {
            message = messages.getString(getMessage());
        } catch (MissingResourceException mre) {
            message = "No resource for " + getMessage() + "key";
        }
        return message;
    }
}
