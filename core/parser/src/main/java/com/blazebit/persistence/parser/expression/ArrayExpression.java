/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.parser.expression;

/**
 *
 * @author Christian Beikov
 * @author Moritz Becker
 * @since 1.0.0
 */
public class ArrayExpression extends AbstractExpression implements PathElementExpression {

    public static final String ELEMENT_NAME = "_";

    // Can be either a PropertyExpression or a EntityLiteral
    private Expression base;
    private Expression index;

    public ArrayExpression(Expression base, Expression index) {
        this.base = base;
        this.index = index;
    }

    @Override
    public ArrayExpression copy(ExpressionCopyContext copyContext) {
        return new ArrayExpression(base.copy(copyContext), index.copy(copyContext));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getBase() {
        return base;
    }

    public void setBase(Expression base) {
        this.base = base;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.base != null ? this.base.hashCode() : 0);
        hash = 61 * hash + (this.index != null ? this.index.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArrayExpression other = (ArrayExpression) obj;
        if (this.base != other.base && (this.base == null || !this.base.equals(other.base))) {
            return false;
        }
        if (this.index != other.index && (this.index == null || !this.index.equals(other.index))) {
            return false;
        }
        return true;
    }

}
