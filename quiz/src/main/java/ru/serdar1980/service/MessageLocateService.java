package ru.serdar1980.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageLocateService {

    private static Locale locale = Locale.getDefault();
    private static MessageSource messageSource;

    private MessageLocateService() {
    }

    public static String getMessage(String code) {
        String res = "";
        res = messageSource.getMessage(code, new Object[]{}, locale);
        return res;
    }

    public static String getMessage(String code, Object[] param) {
        String res = "";
        res = messageSource.getMessage(code, param, locale);
        return res;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageLocateService.messageSource = messageSource;
    }

    public void setLocale(Locale locale) {
        MessageLocateService.locale = locale;
    }

}
