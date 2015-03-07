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

public class Layout {
    @Property
    @Parameter(required = false, defaultPrefix = "message")
    private String      menuExplanation;

    @Property
    @Parameter(required = true, defaultPrefix = "message")
    private String      pageTitle;

    @Property
    @SessionState(create = false)
    private UserSession userSession;

    @Inject
    private Cookies     cookies;

    @AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
    Object onActionFromLogout() {
        userSession = null;
        CookiesManager.removeCookies(cookies);
        return Index.class;
    }

    public boolean getAdministrador() {
        return userSession.isAdministrador();
    }

    public boolean getTaquillero() {
        return userSession.isTaquillero();
    }

    public boolean getEspectador() {
        return userSession.isEspectador();
    }
}