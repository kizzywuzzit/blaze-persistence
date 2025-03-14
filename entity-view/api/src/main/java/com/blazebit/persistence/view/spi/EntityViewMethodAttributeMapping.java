/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.view.spi;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.AttributeFilterProvider;
import com.blazebit.persistence.view.CascadeType;
import com.blazebit.persistence.view.InverseRemoveStrategy;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Mapping of an entity view method attribute.
 *
 * @author Christian Beikov
 * @since 1.2.0
 */
public interface EntityViewMethodAttributeMapping extends EntityViewAttributeMapping {

    /**
     * Returns the name of this attribute.
     *
     * @return The attribute name
     */
    public String getName();

    /**
     * Returns the getter method represented by this attribute mapping.
     *
     * @return The getter method represented by this attribute mapping
     */
    public Method getMethod();

    /**
     * Returns whether the attribute is updatable i.e. the JPA attribute to which the attribute is mapped
     * via the mapping is updatable. If <code>null</code>(the default), whether the attribute is updatable is determined
     * during the building phase({@link EntityViewConfiguration#createEntityViewManager(CriteriaBuilderFactory)}).
     *
     * @return Whether the attribute is updatable or <code>null</code> if updatability should be determined during building phase
     */
    public Boolean getUpdatable();

    /**
     * Returns whether the elements that are removed from the attribute should be deleted.
     * If <code>null</code>(the default), whether the attribute is updatable is determined
     * during the building phase({@link EntityViewConfiguration#createEntityViewManager(CriteriaBuilderFactory)}).
     *
     * @return Whether the attribute should do orphan removal or <code>null</code> if that should be determined during building phase
     */
    public Boolean getOrphanRemoval();

    /**
     * Returns the cascade types that are configured for this attribute.
     *
     * @return The cascade types
     */
    public Set<CascadeType> getCascadeTypes();

    /**
     * Set whether the attribute is updatable along with cascading configuration and the allowed subtypes.
     *
     * @param updatable Whether the attribute should be updatable
     * @param orphanRemoval Whether orphaned objects should be deleted
     * @param cascadeTypes The enabled cascade types
     * @param subtypes The allowed subtypes for both, persist and update cascades
     * @param persistSubtypes The allowed subtypes for persist cascades
     * @param updateSubtypes The allowed subtypes for update cascades
     */
    public void setUpdatable(boolean updatable, boolean orphanRemoval, CascadeType[] cascadeTypes, Class<?>[] subtypes, Class<?>[] persistSubtypes, Class<?>[] updateSubtypes);

    /**
     * Returns the mapping to the inverse attribute relative to the element type or <code>null</code> if there is none.
     *
     * @return The mapping to the inverse attribute
     */
    public String getMappedBy();

    /**
     * Set the mapping to the inverse attribute.
     *
     * @param mappedBy The mapping
     */
    public void setMappedBy(String mappedBy);

    /**
     * Returns the inverse remove strategy to use if this is an inverse mapping. Returns {@link InverseRemoveStrategy#SET_NULL} by default.
     *
     * @return the inverse remove strategy
     */
    public InverseRemoveStrategy getInverseRemoveStrategy();

    /**
     * Sets the inverse remove strategy.
     *
     * @param inverseRemoveStrategy The strategy
     */
    public void setInverseRemoveStrategy(InverseRemoveStrategy inverseRemoveStrategy);

    /**
     * Returns the attribute filter providers.
     *
     * @return The attribute filter providers
     * @since 1.5.0
     */
    Map<String, Class<? extends AttributeFilterProvider<?>>> getAttributeFilterProviders();

    /**
     * Sets the attribute filter providers.
     *
     * @param attributeFilterProviders The attribute filter providers
     * @since 1.5.0
     */
    void setAttributeFilterProviders(Map<String, Class<? extends AttributeFilterProvider<?>>> attributeFilterProviders);
}
