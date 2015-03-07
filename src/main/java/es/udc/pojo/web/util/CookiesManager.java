package es.udc.pojo.web.util;

import org.apache.tapestry5.services.Cookies;

/**
 * The Class CookiesManager.
 */
public class CookiesManager {

    /** The Constant LOGIN_NAME_COOKIE. */
    private static final String LOGIN_NAME_COOKIE         = "loginName";

    /** The Constant ENCRYPTED_PASSWORD_COOKIE. */
    private static final String ENCRYPTED_PASSWORD_COOKIE = "encryptedPassword";

    /** The Constant ID_CINE_PREFERIDO_COOKIE. */
    private static final String ID_CINE_PREFERIDO_COOKIE  = "idCinePreferido";

    /** The Constant REMEMBER_MY_PASSWORD_AGE. */
    private static final int    REMEMBER_MY_PASSWORD_AGE  = 30 * 24 * 3600;     // 30
                                                                                 // days
                                                                                 // in
                                                                                 // seconds

    /**
     * Leave cookies.
     *
     * @param cookies
     *            the cookies
     * @param loginName
     *            the login name
     * @param encryptedPassword
     *            the encrypted password
     */
    public static void leaveCookies(Cookies cookies, String loginName,
            String encryptedPassword) {
        cookies.writeCookieValue(LOGIN_NAME_COOKIE, loginName,
                REMEMBER_MY_PASSWORD_AGE);
        cookies.writeCookieValue(ENCRYPTED_PASSWORD_COOKIE, encryptedPassword,
                REMEMBER_MY_PASSWORD_AGE);
    }

    /**
     * Removes the cookies.
     *
     * @param cookies
     *            the cookies
     */
    public static void removeCookies(Cookies cookies) {
        cookies.removeCookieValue(LOGIN_NAME_COOKIE);
        cookies.removeCookieValue(ENCRYPTED_PASSWORD_COOKIE);
    }

    /**
     * Gets the login name.
     *
     * @param cookies
     *            the cookies
     * @return the login name
     */
    public static String getLoginName(Cookies cookies) {
        return cookies.readCookieValue(LOGIN_NAME_COOKIE);
    }

    /**
     * Gets the encrypted password.
     *
     * @param cookies
     *            the cookies
     * @return the encrypted password
     */
    public static String getEncryptedPassword(Cookies cookies) {
        return cookies.readCookieValue(ENCRYPTED_PASSWORD_COOKIE);
    }

    /* Metodos de cookies de cine preferido */

    /**
     * Leave cookie cine pref.
     *
     * @param cookies
     *            the cookies
     * @param idCinePreferido
     *            the id cine preferido
     */
    public static void leaveCookieCinePref(Cookies cookies, Long idCinePreferido) {
        cookies.writeCookieValue(ID_CINE_PREFERIDO_COOKIE,
                String.valueOf(idCinePreferido), REMEMBER_MY_PASSWORD_AGE);
    }

    /**
     * Gets the cine preferido.
     *
     * @param cookies
     *            the cookies
     * @return the cine preferido
     */
    public static Long getCinePreferido(Cookies cookies) {
        String cineIdAsString = cookies
                .readCookieValue(ID_CINE_PREFERIDO_COOKIE);
        if (cineIdAsString != null) {
            return Long.valueOf(cineIdAsString);
        } else
            return null;
    }

    /**
     * Removes the cine preferido cookies.
     *
     * @param cookies
     *            the cookies
     */
    public static void removeCinePreferidoCookies(Cookies cookies) {
        cookies.removeCookieValue(ID_CINE_PREFERIDO_COOKIE);
    }

}
