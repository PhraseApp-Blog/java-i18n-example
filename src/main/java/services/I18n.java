package services;

import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public final class I18n {
    private final static String MESSAGES_KEY = "messages";
    private I18n() {
    }
    private static ResourceBundle bundle;
    private static MessageFormat messageForm = new MessageFormat("");

    public static Locale getLocale() {
        Locale defaultLocale = Locale.getDefault();
        return defaultLocale;
    }

    public static boolean isSupported(Locale l) {
        Locale[] availableLocales = Locale.getAvailableLocales();
        return Arrays.asList(availableLocales).contains(l);
    }

    public static void setLocale(Locale l) {
        Locale.setDefault(l);
        messageForm.setLocale(l);
    }

    public static String getMessage(String key) {
        if(bundle == null) {
            bundle = ResourceBundle.getBundle(MESSAGES_KEY);
        }
        return bundle.getString(key);
    }

    public static void applyPattern(String pattern, ChoiceFormat choiceForm) {
        messageForm.applyPattern(pattern);
        Format[] formats = {choiceForm, NumberFormat.getInstance()};
        messageForm.setFormats(formats);
    }

    public static String getPatternMessage(Object[] messageArguments) {
        return messageForm.format(messageArguments);
    }

    public static String getMessage(String key, Object ... arguments) {
        return MessageFormat.format(getMessage(key), arguments);
    }
}
