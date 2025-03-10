/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence;

import javax.persistence.TypedQuery;

/**
 * A builder for criteria queries. This is the entry point for building queries.
 *
 * @param <T> The query result type
 * @author Christian Beikov
 * @since 1.0.0
 */
public interface CriteriaBuilder<T> extends FullQueryBuilder<T, CriteriaBuilder<T>>, BaseCriteriaBuilder<T, CriteriaBuilder<T>>, CTEBuilder<CriteriaBuilder<T>>, SetOperationBuilder<LeafOngoingSetOperationCriteriaBuilder<T>, StartOngoingSetOperationCriteriaBuilder<T, LeafOngoingFinalSetOperationCriteriaBuilder<T>>> {

    /**
     * Returns a query that counts the distinct query root results that would be produced if the current query was run.
     *
     * @return A query for determining the count of the distinct query root result list represented by this query builder
     * @since 1.3.0
     */
    public TypedQuery<Long> getQueryRootCountQuery();

    /**
     * Returns the query string that selects the distinct count of query root elements.
     *
     * @return The query string
     * @since 1.3.0
     */
    public String getQueryRootCountQueryString();

    /**
     * Returns a query that counts the distinct query root results and counts up to the maximum value that is given that would be produced if the current query was run.
     *
     * @param maximumCount the maximum value up to which should be counted
     * @return A query for determining the count of the distinct query root result list represented by this query builder
     * @since 1.5.0
     */
    public TypedQuery<Long> getQueryRootCountQuery(long maximumCount);

    /**
     * Returns the query string that selects the distinct count of query root elements and counts up to the maximum value that is given.
     *
     * @param maximumCount the maximum value up to which should be counted
     * @return The query string
     * @since 1.5.0
     */
    public String getQueryRootCountQueryString(long maximumCount);

    @Override
    public <Y> CriteriaBuilder<Y> copy(Class<Y> resultClass);

    @Override
    public <Y> SelectObjectBuilder<CriteriaBuilder<Y>> selectNew(Class<Y> clazz);

    @Override
    public <Y> CriteriaBuilder<Y> selectNew(ObjectBuilder<Y> builder);
}
