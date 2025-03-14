/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.view.spi;

import java.util.Comparator;
import java.util.List;

/**
 * Mapping of an entity view attribute.
 *
 * @author Christian Beikov
 * @since 1.2.0
 */
public interface EntityViewAttributeMapping {

    /**
     * Returns the mapping of the view declaring this attribute.
     *
     * @return The declaring view mapping
     */
    public EntityViewMapping getDeclaringView();

    /**
     * Returns whether this attribute is of the plural type.
     *
     * @return <code>true</code> if this attribute is of the plural type, <code>false</code> otherwise
     */
    public boolean isCollection();

    /**
     * Returns the behavior of a plural attribute container if the attribute is plural, or <code>null</code> otherwise.
     *
     * @return The container behavior, or null if attribute is singular
     */
    public ContainerBehavior getContainerBehavior();

    /**
     * Sets the container behavior to {@link ContainerBehavior#DEFAULT}.
     */
    public void setContainerDefault();

    /**
     * Sets the container behavior to {@link ContainerBehavior#INDEXED}.
     */
    public void setContainerIndexed();

    /**
     * Sets the container behavior to {@link ContainerBehavior#ORDERED}.
     */
    public void setContainerOrdered();

    /**
     * Sets the container behavior to {@link ContainerBehavior#SORTED} using the given
     * comparator class, if given, for sorting. If none is given, the key/element type
     * is expected to implement {@link Comparable}.
     *
     * @param comparatorClass The class of the comparator to use for sorting or <code>null</code>
     */
    public void setContainerSorted(Class<? extends Comparator<?>> comparatorClass);

    /**
     * Specifies whether elements should be forcefully deduplicated if the collection allows duplicates or not.
     *
     * @return true if uniqueness of element should be forced, false otherwise
     * @since 1.3.0
     */
    public boolean isForceUniqueness();

    /**
     * Sets whether elements should be forcefully deduplicated if the collection allows duplicates or not.
     *
     * @param forceUniqueness true if uniqueness of element should be forced, false otherwise
     * @since 1.3.0
     */
    public void setForceUniqueness(boolean forceUniqueness);

    /**
     * Returns the behavior of a plural attribute element collection if the attribute is a multi-collection, or <code>null</code> otherwise.
     *
     * @return The container behavior, or null if attribute is singular
     * @since 1.6.0
     */
    public ElementCollectionBehavior getElementCollectionBehavior();

    /**
     * Sets the element collection behavior to {@link ElementCollectionBehavior#DEFAULT}.
     * @since 1.6.0
     */
    public void setElementCollectionDefault();

    /**
     * Sets the element collection behavior to {@link ElementCollectionBehavior#ORDERED}.
     * @since 1.6.0
     */
    public void setElementCollectionOrdered();

    /**
     * Sets the element collection behavior to {@link ElementCollectionBehavior#SORTED} using the given
     * comparator class, if given, for sorting. If none is given, the element type
     * is expected to implement {@link Comparable}.
     *
     * @param comparatorClass The class of the comparator to use for sorting or <code>null</code>
     * @since 1.6.0
     */
    public void setElementCollectionSorted(Class<? extends Comparator<?>> comparatorClass);

    /**
     * Specifies whether elements should be forcefully deduplicated if the element collection allows duplicates or not.
     *
     * @return true if uniqueness of element should be forced, false otherwise
     * @since 1.6.0
     */
    public boolean isElementCollectionForceUniqueness();

    /**
     * Sets whether elements should be forcefully deduplicated if the element collection allows duplicates or not.
     *
     * @param forceUniqueness true if uniqueness of element should be forced, false otherwise
     * @since 1.6.0
     */
    public void setElementCollectionForceUniqueness(boolean forceUniqueness);

    /**
     * Returns the comparator class, or <code>null</code> if there none.
     *
     * @return The comparator class
     */
    public Class<? extends Comparator<?>> getElementCollectionComparatorClass();

    /**
     * Specifies whether an updatable entity view type is disallowed for owned *ToOne relationships or not.
     *
     * @return true if disallowed, false otherwise
     * @since 1.3.0
     */
    public boolean isDisallowOwnedUpdatableSubview();

    /**
     * Sets whether an updatable entity view type is disallowed for owned *ToOne relationships or not.
     *
     * @param disallowOwnedUpdatableSubview true if updatable entity view types should be disallowed, false otherwise
     * @since 1.3.0
     */
    public void setDisallowOwnedUpdatableSubview(boolean disallowOwnedUpdatableSubview);

    /**
     * Returns the comparator class, or <code>null</code> if there none.
     *
     * @return The comparator class
     */
    public Class<? extends Comparator<?>> getComparatorClass();

    /**
     * Returns the default batch size to use for batched {@link com.blazebit.persistence.view.FetchStrategy#SELECT} fetching.
     *
     * @return The default batch size
     */
    public Integer getDefaultBatchSize();

    /**
     * Sets the default batch size to use for batched {@link com.blazebit.persistence.view.FetchStrategy#SELECT} fetching.
     *
     * @param defaultBatchSize The default batch size
     */
    public void setDefaultBatchSize(Integer defaultBatchSize);

    /**
     * Returns whether to create empty flat views or not.
     *
     * @return whether to create empty flat views or not
     * @since 1.5.0
     */
    public Boolean getCreateEmptyFlatViews();

    /**
     * Sets whether to create empty flat views.
     *
     * @param createEmptyFlatViews whether to create empty flat views
     * @since 1.5.0
     */
    public void setCreateEmptyFlatViews(Boolean createEmptyFlatViews);

    /**
     * Returns the limit expression.
     *
     * @return the limit expression
     * @since 1.5.0
     */
    public String getLimitExpression();

    /**
     * Returns the offset expression.
     *
     * @return the offset expression
     * @since 1.5.0
     */
    public String getOffsetExpression();

    /**
     * Returns the order by item expressions.
     *
     * @return The order by item expressions
     * @since 1.5.0
     */
    public List<String> getOrderByItems();

    /**
     * Sets the limit expression along with the order by expressions.
     *
     * @param limitExpression The limit expression
     * @param offsetExpression The offset expression
     * @param orderByExpressions The order by item expressions
     * @since 1.5.0
     */
    public void setLimit(String limitExpression, String offsetExpression, List<String> orderByExpressions);

    /**
     * Returns the attribute type.
     *
     * @return The attribute type
     */
    public Class<?> getDeclaredType();

    /**
     * The attribute's key type, or <code>null</code> if the attribute type is not a subtype of {@link java.util.Map}.
     *
     * @return The attribute's key type, or <code>null</code>
     */
    public Class<?> getDeclaredKeyType();

    /**
     * The attribute's element type, or <code>null</code> if the attribute type is not a subtype of {@link java.util.Collection} or {@link java.util.Map}.
     *
     * @return The attribute's element type, or <code>null</code>
     */
    public Class<?> getDeclaredElementType();

    /**
     * The behavior of a plural attribute container.
     */
    public static enum ContainerBehavior {
        /**
         * The default behavior doesn't mandate a deterministic ordering.
         */
        DEFAULT,
        /**
         * Specifies that the container's iteration order must match the element insertion order.
         */
        ORDERED,
        /**
         * Specifies that the elements of the container are indexed upon which the iteration order is based on.
         */
        INDEXED,
        /**
         * Specifies that the container's iteration order must match the sort order as defined by a comparator or a comparable element type.
         */
        SORTED;
    }

    /**
     * The behavior of a plural attribute element collections.
     */
    public static enum ElementCollectionBehavior {
        /**
         * The default behavior doesn't mandate a deterministic ordering.
         */
        DEFAULT,
        /**
         * Specifies that the container's iteration order must match the element insertion order.
         */
        ORDERED,
        /**
         * Specifies that the container's iteration order must match the sort order as defined by a comparator or a comparable element type.
         */
        SORTED;
    }
}
