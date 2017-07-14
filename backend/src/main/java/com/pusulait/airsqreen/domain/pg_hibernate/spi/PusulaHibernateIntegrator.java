package com.pusulait.airsqreen.domain.pg_hibernate.spi;

import com.pusulait.airsqreen.domain.pg_hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.hibernate.usertype.UserType;


public class PusulaHibernateIntegrator implements Integrator {

    @Override
    public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        autoRegisterUsertypes(configuration);

    }

    private void autoRegisterUsertypes(Configuration configuration) {
        registerClass(configuration, new BoxType());
        registerClass(configuration, new CircleType());
        registerClass(configuration, new HstoreType());
        registerClass(configuration, new InetAddressType());
        registerClass(configuration, new IntegerArrayType());
        registerClass(configuration, new LineSegmentType());
        registerClass(configuration, new PointType());
        registerClass(configuration, new PolygonType());
        registerClass(configuration, new StringArrayType());
        registerClass(configuration, new DoubleStringArrayType());
    }

    private void registerClass(Configuration configuration, UserType type) {
        configuration.registerTypeOverride(type, new String[]{type.returnedClass().getName()});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    	// no-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    }

    protected void doIntegrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    }
}
