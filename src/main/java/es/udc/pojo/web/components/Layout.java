package es.udc.pojo.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import es.udc.pojo.web.pages.Index;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;
import es.udc.pojo.web.util.CookiesManager;
import es.udc.pojo.web.util.UserSession;

/**
 * The Class Layout.
 */
public class Layout {

    /** The menu explanation. */
    @Property
    @Parameter(required = false, defaultPrefix = "message")
    private String      menuExplanation;

    /** The page title. */
    @Property
    @Parameter(required = true, defaultPrefix = "message")
    private String      pageTitle;

    /** The user session. */
    @Property
    @SessionState(create = false)
    private UserSession userSession;

    /** The cookies. */
    @Inject
    private Cookies     cookies;

    /**
     * On action from logout.
     *
     * @return the object
     */
    @AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
    Object onActionFromLogout() {
        userSession = null;
        CookiesManager.removeCookies(cookies);
        return Index.class;
    }

    /**
     * Gets the administrador.
     *
     * @return the administrador
     */
    public boolean getAdministrador() {
        return userSession.isAdministrador();
    }

    /**
     * Gets the taquillero.
     *
     * @return the taquillero
     */
    public boolean getTaquillero() {
        return userSession.isTaquillero();
    }

    /**
     * Gets the espectador.
     *
     * @return the espectador
     */
    public boolean getEspectador() {
        return userSession.isEspectador();
    }
}