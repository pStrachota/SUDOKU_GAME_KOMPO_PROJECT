import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FXMLLoaderException extends Exception {

    public static final String LOADER_NOT_LOAD = "loader.not.load";
    private static final ResourceBundle messages;

    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("exceptions", locale);
    }

    public FXMLLoaderException(String message) {
        super(message);
    }

    public FXMLLoaderException(String message, Throwable cause) {
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
