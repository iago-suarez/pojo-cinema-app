package es.udc.pojo.test.experiments;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * The Class HibernateUtil.
 */
public class HibernateUtil {

    /** The Constant sessionFactory. */
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from
            // hibernate-config-experiments.xml
            Configuration configuration = new Configuration()
                    .configure("hibernate-config-experiments.xml");
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Gets the session factory.
     *
     * @return the session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Shutdown.
     */
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}
