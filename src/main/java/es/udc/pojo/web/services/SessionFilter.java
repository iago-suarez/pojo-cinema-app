package es.udc.pojo.web.services;

import java.io.IOException;

import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import es.udc.pojo.model.userprofile.UserProfile;
import es.udc.pojo.model.userservice.UserService;
import es.udc.pojo.model.util.IncorrectPasswordException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.util.CookiesManager;
import es.udc.pojo.web.util.UserSession;

/**
 * The Class SessionFilter.
 */
public class SessionFilter implements RequestFilter {

    /** The application state manager. */
    private ApplicationStateManager applicationStateManager;

    /** The cookies. */
    private Cookies                 cookies;

    /** The user service. */
    private UserService             userService;

    /**
     * Instantiates a new session filter.
     *
     * @param applicationStateManager
     *            the application state manager
     * @param cookies
     *            the cookies
     * @param userService
     *            the user service
     */
    public SessionFilter(ApplicationStateManager applicationStateManager,
            Cookies cookies, UserService userService) {

        this.applicationStateManager = applicationStateManager;
        this.cookies = cookies;
        this.userService = userService;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.tapestry5.services.RequestFilter#service(org.apache.tapestry5
     * .services.Request, org.apache.tapestry5.services.Response,
     * org.apache.tapestry5.services.RequestHandler)
     */
    public boolean service(Request request, Response response,
            RequestHandler handler) throws IOException {

        if (!applicationStateManager.exists(UserSession.class)) {

            String loginName = CookiesManager.getLoginName(cookies);
            if (loginName != null) {

                String encryptedPassword = CookiesManager
                        .getEncryptedPassword(cookies);
                if (encryptedPassword != null) {

                    try {

                        UserProfile userProfile = userService.login(loginName,
                                encryptedPassword, true);
                        UserSession userSession = new UserSession();
                        userSession.setUserProfileId(userProfile
                                .getUserProfileId());
                        userSession.setFirstName(userProfile.getFirstName());
                        userSession.setTipoUsuario(userProfile.getTipo());
                        applicationStateManager.set(UserSession.class,
                                userSession);

                    } catch (InstanceNotFoundException e) {
                        CookiesManager.removeCookies(cookies);
                    } catch (IncorrectPasswordException e) {
                        CookiesManager.removeCookies(cookies);
                    }

                }

            }

        }

        handler.service(request, response);

        return true;
    }

}
