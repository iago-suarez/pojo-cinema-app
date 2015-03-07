package es.udc.pojo.web.util;

import org.apache.tapestry5.services.Cookies;

public class CookiesManager {

    private static final String LOGIN_NAME_COOKIE         = "loginName";
    private static final String ENCRYPTED_PASSWORD_COOKIE = "encryptedPassword";
    private static final String ID_CINE_PREFERIDO_COOKIE  = "idCinePreferido";
    private static final int    REMEMBER_MY_PASSWORD_AGE  = 30 * 24 * 3600;     // 30
                                                                                 // days
                                                                                 // in
                                                                                 // seconds

    public static void leaveCookies(Cookies cookies, String loginName,
            String encryptedPassword) {
        cookies.writeCookieValue(LOGIN_NAME_COOKIE, loginName,
                REMEMBER_MY_PASSWORD_AGE);
        cookies.writeCookieValue(ENCRYPTED_PASSWORD_COOKIE, encryptedPassword,
                REMEMBER_MY_PASSWORD_AGE);
    }

    public static void removeCookies(Cookies cookies) {
        cookies.removeCookieValue(LOGIN_NAME_COOKIE);
        cookies.removeCookieValue(ENCRYPTED_PASSWORD_COOKIE);
    }

    public static String getLoginName(Cookies cookies) {
        return cookies.readCookieValue(LOGIN_NAME_COOKIE);
    }

    public static String getEncryptedPassword(Cookies cookies) {
        return cookies.readCookieValue(ENCRYPTED_PASSWORD_COOKIE);
    }

    /* Metodos de cookies de cine preferido */

    public static void leaveCookieCinePref(Cookies cookies, Long idCinePreferido) {
        cookies.writeCookieValue(ID_CINE_PREFERIDO_COOKIE,
                String.valueOf(idCinePreferido), REMEMBER_MY_PASSWORD_AGE);
    }

    public static Long getCinePreferido(Cookies cookies) {
        String cineIdAsString = cookies
                .readCookieValue(ID_CINE_PREFERIDO_COOKIE);
        if (cineIdAsString != null) {
            return Long.valueOf(cineIdAsString);
        } else
            return null;
    }

    public static void removeCinePreferidoCookies(Cookies cookies) {
        cookies.removeCookieValue(ID_CINE_PREFERIDO_COOKIE);
    }

}
