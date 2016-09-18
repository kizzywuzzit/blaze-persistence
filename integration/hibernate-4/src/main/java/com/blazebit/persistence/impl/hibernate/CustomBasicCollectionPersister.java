package com.blazebit.persistence.impl.hibernate;

import org.hibernate.MappingException;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SubselectFetch;
import org.hibernate.loader.collection.CollectionInitializer;
import org.hibernate.mapping.Collection;
import org.hibernate.persister.collection.BasicCollectionPersister;

/**
 * @author Jan-Willem Gmelig Meyling
 * @since 1.2.0
 */
public class CustomBasicCollectionPersister extends BasicCollectionPersister {

    public CustomBasicCollectionPersister(Collection collection, CollectionRegionAccessStrategy cacheAccessStrategy, Configuration cfg, SessionFactoryImplementor factory) throws MappingException, CacheException {
        super(collection, cacheAccessStrategy, cfg, factory);
    }

    @Override
    protected CollectionInitializer createSubselectInitializer(SubselectFetch subselect, SessionImplementor session) {
        return new CustomSubselectCollectionLoader(
            this,
            subselect.toSubselectString( getCollectionType().getLHSPropertyName() ),
            subselect.getResult(),
            subselect.getQueryParameters(),
            subselect.getNamedParameterLocMap(),
            session.getFactory(),
            session.getLoadQueryInfluencers()
        );
    }
}
