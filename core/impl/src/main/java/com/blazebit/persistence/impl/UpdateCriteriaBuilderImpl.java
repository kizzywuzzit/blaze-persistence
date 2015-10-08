/*
 * Copyright 2015 Blazebit.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazebit.persistence.impl;

import java.util.Set;

import javax.persistence.EntityManager;

import com.blazebit.persistence.UpdateCriteriaBuilder;
import com.blazebit.persistence.spi.DbmsDialect;

/**
 *
 * @param <T> The query result type
 * @author Christian Beikov
 * @since 1.1.0
 */
public class UpdateCriteriaBuilderImpl<T> extends BaseUpdateCriteriaBuilderImpl<T, UpdateCriteriaBuilder<T>, Void> implements UpdateCriteriaBuilder<T> {

    public UpdateCriteriaBuilderImpl(CriteriaBuilderFactoryImpl cbf, EntityManager em, DbmsDialect dbmsDialect, Class<T> clazz, String alias, Set<String> registeredFunctions) {
        super(cbf, em, dbmsDialect, clazz, alias, registeredFunctions, new ParameterManager(), null, null, null);
    }
    
    public UpdateCriteriaBuilderImpl(CriteriaBuilderFactoryImpl cbf, EntityManager em, DbmsDialect dbmsDialect, Class<T> clazz, String alias, Set<String> registeredFunctions, ParameterManager parameterManager) {
        super(cbf, em, dbmsDialect, clazz, alias, registeredFunctions, parameterManager, null, null, null);
    }

}
