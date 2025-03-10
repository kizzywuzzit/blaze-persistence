/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence;

/**
 * The builder interface for a on predicate container that connects predicates with the OR operator.
 *
 * @param <T> The builder type that is returned on terminal operations
 * @author Christian Beikov
 * @since 1.0.0
 */
public interface JoinOnOrBuilder<T> extends BaseJoinOnBuilder<JoinOnOrBuilder<T>> {

    /**
     * Finishes the OR predicate and adds it to the parent predicate container represented by the type <code>T</code>.
     *
     * @return The parent predicate container builder
     */
    public T endOr();

    /**
     * Starts a on and builder which connects it's predicates with the AND operator.
     * When the builder finishes, the predicate is added to this predicate container as disjunct.
     *
     * @return The on and builder
     */
    public JoinOnAndBuilder<JoinOnOrBuilder<T>> onAnd();
}
