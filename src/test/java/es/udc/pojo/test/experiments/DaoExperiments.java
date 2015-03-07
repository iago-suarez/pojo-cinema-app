package es.udc.pojo.test.experiments;

import org.hibernate.Transaction;

import es.udc.pojo.model.userprofile.TipoUsuario;
import es.udc.pojo.model.userprofile.UserProfile;
import es.udc.pojo.model.userprofile.UserProfileDao;
import es.udc.pojo.model.userprofile.UserProfileDaoHibernate;
import es.udc.pojo.model.userservice.util.PasswordEncrypter;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class DaoExperiments {

    public static void main(String[] args) {

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        UserProfileDao userProfileDao = userProfileDaoHibernate;

        Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession()
                .beginTransaction();
        try {

            // Register user.
            UserProfile userProfile = new UserProfile("daoUser",
                    PasswordEncrypter.crypt("userPassword"), "name",
                    "lastName", "user@udc.es", TipoUsuario.ESPECTADOR);
            userProfileDao.save(userProfile);
            Long userId = userProfile.getUserProfileId();
            System.out.println("User with userId '" + userId
                    + "' has been created");
            System.out.println(userProfile);

            // Find user.
            userProfile = userProfileDao.find(userId);
            System.out.println("User with userId '" + userId
                    + "' has been retrieved");
            System.out.println(userProfile);

            // ... proceed in the same way for other entities / methods / use
            // cases

            tx.commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
            tx.rollback();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            tx.commit();
        } finally {
            HibernateUtil.getSessionFactory().getCurrentSession().close();
        }

        HibernateUtil.shutdown();

    }

}
