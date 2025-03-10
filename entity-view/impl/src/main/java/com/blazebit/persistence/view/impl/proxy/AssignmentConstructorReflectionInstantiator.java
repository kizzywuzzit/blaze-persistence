/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.view.impl.proxy;

import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.impl.metamodel.ManagedViewTypeImpl;
import com.blazebit.persistence.view.impl.metamodel.ManagedViewTypeImplementor;
import com.blazebit.persistence.view.impl.metamodel.MappingConstructorImpl;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 *
 * @author Christian Beikov
 * @since 1.2.0
 */
public class AssignmentConstructorReflectionInstantiator<T> extends AbstractReflectionInstantiator<T> {

    private final Constructor<T> constructor;
    private final Object[] defaultObject;

    public AssignmentConstructorReflectionInstantiator(MappingConstructorImpl<T> mappingConstructor, ProxyFactory proxyFactory, ManagedViewTypeImplementor<T> viewType, Class<?>[] parameterTypes,
                                                       EntityViewManager entityViewManager, ManagedViewTypeImpl.InheritanceSubtypeConfiguration<T> configuration, MappingConstructorImpl.InheritanceSubtypeConstructorConfiguration<T> subtypeConstructorConfiguration) {
        super(configuration.getMutableBasicUserTypes(), configuration.getTypeConverterEntries(), parameterTypes);
        Class<T> proxyClazz = (Class<T>) proxyFactory.getProxy(entityViewManager, viewType);
        Constructor<T> javaConstructor;
        Object[] defaultObject;
        int[] assignment;
        int[] overallPositionAssignment = configuration.getOverallPositionAssignment(viewType);
        if (mappingConstructor == null) {
            assignment = overallPositionAssignment;
        } else {
            int[] overallConstructorPositionAssignment = subtypeConstructorConfiguration.getOverallPositionAssignment(viewType);
            assignment = new int[overallPositionAssignment.length + overallConstructorPositionAssignment.length];
            System.arraycopy(overallPositionAssignment, 0, assignment, 0, overallPositionAssignment.length);
            for (int i = 0; i < overallConstructorPositionAssignment.length; i++) {
                assignment[overallPositionAssignment.length + i] = overallPositionAssignment.length + overallConstructorPositionAssignment[i];
            }
        }
        try {
            if (mappingConstructor == null) {
                javaConstructor = proxyClazz.getDeclaredConstructor(proxyClazz, int.class, int[].class, Object[].class);
                defaultObject = new Object[] { null, 0, assignment, null };
            } else {
                int parameterSize = subtypeConstructorConfiguration.getOverallPositionAssignment(viewType).length;
                Class[] types = new Class[parameterSize + 4];
                types[0] = proxyClazz;
                types[1] = int.class;
                types[2] = int[].class;
                types[3] = Object[].class;
                System.arraycopy(parameterTypes, parameterTypes.length - parameterSize, types, 4, parameterSize);
                javaConstructor = proxyClazz.getDeclaredConstructor(types);
                defaultObject = AbstractReflectionInstantiator.createDefaultObject(4, parameterTypes, parameterSize);
                defaultObject[1] = 0;
                defaultObject[2] = assignment;
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new IllegalArgumentException("The given mapping constructor '" + mappingConstructor + "' does not map to a constructor of the proxy class: " + proxyClazz
                    .getName(), ex);
        }

        if (javaConstructor == null) {
            throw new IllegalArgumentException("The given mapping constructor '" + mappingConstructor + "' does not map to a constructor of the proxy class: " + proxyClazz
                    .getName());
        }

        this.constructor = javaConstructor;
        this.defaultObject = defaultObject;
    }

    @Override
    public T newInstance(Object[] tuple) {
        try {
            prepareTuple(tuple);
            Object[] array = Arrays.copyOf(defaultObject, defaultObject.length);
            array[3] = tuple;
            T instance = constructor.newInstance(array);
            finalizeInstance(instance);
            return instance;
        } catch (Exception ex) {
            String[] types = new String[tuple.length];
            
            for (int i = 0; i < types.length; i++) {
                if (tuple[i] == null) {
                    types[i] = null;
                } else {
                    types[i] = tuple[i].getClass().getName();
                }
            }
            throw new RuntimeException("Could not invoke the proxy constructor '" + constructor + "' with the given tuple: " + Arrays.toString(tuple) + " with the types: " + Arrays.toString(types), ex);
        }
    }

}
