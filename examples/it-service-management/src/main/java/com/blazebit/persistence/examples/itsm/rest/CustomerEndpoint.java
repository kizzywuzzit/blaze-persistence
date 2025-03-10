/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.examples.itsm.rest;

import com.blazebit.persistence.examples.itsm.model.customer.repository.CustomerDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blazebit.persistence.view.EntityViewManager;

import com.blazebit.persistence.examples.itsm.model.customer.view.AbstractCustomerDetail;
import com.blazebit.persistence.examples.itsm.model.customer.view.CustomerDetail;

/**
 * @author Giovanni Lovato
 * @since 1.4.0
 */
@RestController
@RequestMapping("/customers")
public class CustomerEndpoint
        implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private EntityViewManager evm;

    @Autowired
    private CustomerDetailRepository repository;

    @GetMapping("/{name}")
    public AbstractCustomerDetail getCustomer(
            @PathVariable("name") String name) {
        return this.repository.findByName(name).get();
    }

    @PatchMapping("/{name}/hours/{hours}")
    public AbstractCustomerDetail patchCustomer(
            @PathVariable("name") String name,
            @PathVariable("hours") String hours) {
        AbstractCustomerDetail customer = this.repository.findByName(name).get();
        customer.getServiceDetail().setServiceHours(hours);
        return this.repository.save(customer);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        CustomerDetail customer = this.evm.create(CustomerDetail.class);
        customer.setName("foo");
        this.repository.save(customer);
    }

}
