package ua.training.servlet_project.model.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internationalization {
    private static final Logger LOGGER = LogManager.getLogger(Internationalization.class);

    private Internationalization() {
    }

    public static String getMessage(String key, String lang) {
        LOGGER.info("{}", getCurrentLocale(lang));

        ResourceBundle bundle = ResourceBundle.getBundle("messages",
                getCurrentLocale(lang));
        return bundle.getString(key);
    }

    public static String getMessage(Enum<?> enumVal, String lang) {
        LOGGER.info("{}", enumVal);
        String key = enumVal.getClass().getSimpleName().toLowerCase() + '.' + enumVal.name().toLowerCase();
        LOGGER.info("{}", key);
        return getMessage(key, lang);
    }

    public static Locale getCurrentLocale(String lang) {
        return new Locale(lang);
    }
}
