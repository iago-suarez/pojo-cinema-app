package es.udc.pojo.model.userprofile;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class UserProfileDaoHibernate.
 */
@Repository("userProfileDao")
public class UserProfileDaoHibernate extends
        GenericDaoHibernate<UserProfile, Long> implements UserProfileDao {

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.userprofile.UserProfileDao#findByLoginName(java.lang
     * .String)
     */
    public UserProfile findByLoginName(String loginName)
            throws InstanceNotFoundException {

        UserProfile userProfile = (UserProfile) getSession()
                .createQuery(
                        "SELECT u FROM UserProfile u WHERE u.loginName = :loginName")
                .setParameter("loginName", loginName).uniqueResult();
        if (userProfile == null) {
            throw new InstanceNotFoundException(loginName,
                    UserProfile.class.getName());
        } else {
            return userProfile;
        }

    }

}