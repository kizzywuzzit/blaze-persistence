/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.impl.function.trunc.year;

/**
 * @author Jan-Willem Gmelig Meyling
 * @since 1.4.0
 */
public class MySQLTruncYearFunction extends TruncYearFunction {

    public MySQLTruncYearFunction() {
        super("date_add('1900-01-01', interval TIMESTAMPDIFF(YEAR, '1900-01-01', ?1) YEAR)");
    }

}
