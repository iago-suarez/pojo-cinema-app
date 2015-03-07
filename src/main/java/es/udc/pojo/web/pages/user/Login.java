package es.udc.pojo.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import es.udc.pojo.model.userprofile.UserProfile;
import es.udc.pojo.model.userservice.UserService;
import es.udc.pojo.model.util.IncorrectPasswordException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.pages.Index;
import es.udc.pojo.web.pages.compra.ComprarEntradas;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;
import es.udc.pojo.web.util.CookiesManager;
import es.udc.pojo.web.util.UserSession;

/**
 * The Class Login.
 */
@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Login {

    /** The login name. */
    @Property
    private String          loginName;

    /** The password. */
    @Property
    private String          password;

    /** The remember my password. */
    @Property
    private boolean         rememberMyPassword;

    /** The user session. */
    @SessionState(create = false)
    private UserSession     userSession;

    /** The cookies. */
    @Inject
    private Cookies         cookies;

    /** The login form. */
    @Component
    private Form            loginForm;

    /** The messages. */
    @Inject
    private Messages        messages;

    /** The user service. */
    @Inject
    private UserService     userService;

    /** The user profile. */
    private UserProfile     userProfile = null;

    /** The id sesion. */
    private Long            idSesion;

    /** The comprar entradas. */
    @InjectPage
    private ComprarEntradas comprarEntradas;

    /**
     * On validate from login form.
     */
    void onValidateFromLoginForm() {

        if (!loginForm.isValid()) {
            return;
        }

        try {
            userProfile = userService.login(loginName, password, false);
        } catch (InstanceNotFoundException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        } catch (IncorrectPasswordException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        }

    }

    /**
     * On passivate.
     *
     * @return the long
     */
    Long onPassivate() {
        return this.idSesion;
    }

    /**
     * On activate.
     *
     * @param idSesion
     *            the id sesion
     */
    void onActivate(Long idSesion) {
        this.idSesion = idSesion;
    }

    /**
     * On success.
     *
     * @return the object
     */
    Object onSuccess() {

        userSession = new UserSession();
        userSession.setUserProfileId(userProfile.getUserProfileId());
        userSession.setFirstName(userProfile.getFirstName());
        userSession.setTipoUsuario(userProfile.getTipo());

        if (rememberMyPassword) {
            CookiesManager.leaveCookies(cookies, loginName,
                    userProfile.getEncryptedPassword());
        }

        if (this.idSesion == null) {
            return Index.class;
        } else {
            comprarEntradas.setId(idSesion);
            return comprarEntradas;
        }

    }

}
