package pgtest.websupport;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class MessageSourceHolder {
    static Logger log = Logger.getLogger(MessageSourceHolder.class);

    /**
     * messageSource implementation to be injected
     */
    private MessageSource messageSource;

    /**
     * singleton instance
     */
    private static MessageSourceHolder singleton = new MessageSourceHolder();

    /**
     * Gets singleton
     *
     * @return singleton
     */
    public static MessageSourceHolder getInstance() {
        return singleton;
    }

    /**
     * Setter for the messageSource implementation used to inject
     * the implementation
     *
     * @param MessageSource
     */
    public void setMessageSource(MessageSource MessageSource) {
        this.messageSource = MessageSource;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public static String getMessage(String code, Object[] args, Locale locale) {
        return singleton.getMessageSource().getMessage(code, args, locale);
    }

    public static String getMessage(String code, Object[] args, String defaultText, Locale locale) {
        return singleton.getMessageSource().getMessage(code, args, defaultText, locale);
    }


    public static String getMessage(String code, Object[] args) {
        try {
            return getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return code;
        } catch (Exception e) {
            return code;
        }
    }

    public static String getMessage(String code) {
        return getMessage(code, null);
    }
}
