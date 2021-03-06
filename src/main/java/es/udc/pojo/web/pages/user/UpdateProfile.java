package es.udc.pojo.web.pages.user;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.userprofile.UserProfile;
import es.udc.pojo.model.userservice.UserProfileDetails;
import es.udc.pojo.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.pages.Index;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;
import es.udc.pojo.web.util.UserSession;

/**
 * The Class UpdateProfile.
 */
@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UpdateProfile {

    /** The first name. */
    @Property
    private String      firstName;

    /** The last name. */
    @Property
    private String      lastName;

    /** The email. */
    @Property
    private String      email;

    /** The user session. */
    @SessionState(create = false)
    private UserSession userSession;

    /** The user service. */
    @Inject
    private UserService userService;

    /**
     * On prepare for render.
     *
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    void onPrepareForRender() throws InstanceNotFoundException {

        UserProfile userProfile;

        userProfile = userService.findUserProfile(userSession
                .getUserProfileId());
        firstName = userProfile.getFirstName();
        lastName = userProfile.getLastName();
        email = userProfile.getEmail();

    }

    /**
     * On success.
     *
     * @return the object
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    Object onSuccess() throws InstanceNotFoundException {

        userService.updateUserProfileDetails(userSession.getUserProfileId(),
                new UserProfileDetails(firstName, lastName, email));
        userSession.setFirstName(firstName);
        return Index.class;

    }

}