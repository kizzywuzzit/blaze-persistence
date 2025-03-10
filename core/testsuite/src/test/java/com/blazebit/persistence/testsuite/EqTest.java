/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.testsuite;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.testsuite.AbstractCoreTest;
import com.blazebit.persistence.testsuite.entity.Document;
import com.blazebit.persistence.testsuite.entity.Person;

/**
 *
 * @author Christian Beikov
 * @author Moritz Becker
 * @since 1.0.0
 */
public class EqTest extends AbstractCoreTest {

    @Test
    public void testEqualTo() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        criteria.where("d.age").eq(20L);

        assertEquals("SELECT d FROM Document d WHERE d.age = :param_0", criteria.getQueryString());
        criteria.getResultList();
    }

    @Test
    public void testEqualToNull() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        verifyException(criteria.where("d.age"), NullPointerException.class, r -> r.eq((Object) null));
    }

    @Test
    public void testEqualToExpression() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        criteria.where("d.creationDate").eqExpression("d.versions.date");

        assertEquals("SELECT d FROM Document d LEFT JOIN d.versions versions_1 WHERE d.creationDate = versions_1.date", criteria
                     .getQueryString());
        criteria.getResultList();
    }

    @Test
    public void testEqualToEmptyExpression() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        verifyException(criteria.where("d.age"), IllegalArgumentException.class, r -> r.eqExpression(""));
    }

    @Test
    public void testEqualToNullExpression() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        verifyException(criteria.where("d.age"), NullPointerException.class, r -> r.eqExpression(null));
    }

    @Test
    public void testNotEqualTo() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        criteria.where("d.age").notEq(20L);

        assertEquals("SELECT d FROM Document d WHERE d.age <> :param_0", criteria.getQueryString());
        criteria.getResultList();
    }

    @Test
    public void testNotEqualToNull() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        verifyException(criteria.where("d.age"), NullPointerException.class, r -> r.notEq((Object) null));
    }

    @Test
    public void testNotEqualToExpression() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        criteria.where("d.creationDate").notEqExpression("d.versions.date");

        assertEquals("SELECT d FROM Document d LEFT JOIN d.versions versions_1 WHERE d.creationDate <> versions_1.date", criteria.getQueryString());
        criteria.getResultList();
    }

    @Test
    public void testNotEqualToEmptyExpression() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        verifyException(criteria.where("d.age"), IllegalArgumentException.class, r -> r.notEqExpression(""));
    }

    @Test
    public void testNotEqualToNullExpression() {
        CriteriaBuilder<Document> criteria = cbf.create(em, Document.class, "d");
        verifyException(criteria.where("d.age"), NullPointerException.class, r -> r.notEqExpression(null));
    }

    @Test
    public void testEqAll() {
        CriteriaBuilder<Document> crit = cbf.create(em, Document.class, "d");
        crit.where("id").eq().all().from(Person.class, "p").select("id").where("name").eqExpression("d.name").end();
        String expected = "SELECT d FROM Document d WHERE d.id = ALL(SELECT p.id FROM Person p WHERE p.name = d.name)";

        assertEquals(expected, crit.getQueryString());
        crit.getResultList();
    }

    @Test
    public void testEqAny() {
        CriteriaBuilder<Document> crit = cbf.create(em, Document.class, "d");
        crit.where("id").eq().any().from(Person.class, "p").select("id").where("name").eqExpression("d.name").end();
        String expected = "SELECT d FROM Document d WHERE d.id = ANY(SELECT p.id FROM Person p WHERE p.name = d.name)";

        Assert.assertEquals(expected, crit.getQueryString());
        crit.getResultList();
    }

    @Test
    public void testEqOne() {
        CriteriaBuilder<Document> crit = cbf.create(em, Document.class, "d");
        crit.where("id").eq().from(Person.class, "p").select("id").where("name").eqExpression("d.name").end();
        String expected = "SELECT d FROM Document d WHERE d.id = (SELECT p.id FROM Person p WHERE p.name = d.name)";

        Assert.assertEquals(expected, crit.getQueryString());
    }
    
    @Test
    public void testEqSubqueryWithSurroundingExpression() {
        CriteriaBuilder<Document> crit = cbf.create(em, Document.class, "d");
        crit.where("age").eq("alias", "1 + alias").from(Person.class, "p").select("COUNT(id)").end();     
        
        assertEquals("SELECT d FROM Document d WHERE d.age = 1 + (SELECT COUNT(p.id) FROM Person p)", crit.getQueryString());
        crit.getResultList();
    }

    @Test
    public void testEqMultipleSubqueryWithSurroundingExpression() {
        CriteriaBuilder<Document> crit = cbf.create(em, Document.class, "d");
        crit.where("age").eq("alias", "alias * alias").from(Person.class, "p").select("COUNT(id)").end();     
        
        assertEquals("SELECT d FROM Document d WHERE d.age = (SELECT COUNT(p.id) FROM Person p) * (SELECT COUNT(p.id) FROM Person p)", crit.getQueryString());
        crit.getResultList();
    }
    
    @Test
    public void testNotEqSubqueryWithSurroundingExpression() {
        CriteriaBuilder<Document> crit = cbf.create(em, Document.class, "d");
        crit.where("age").notEq("alias", "1 + alias").from(Person.class, "p").select("COUNT(id)").end();     
        
        assertEquals("SELECT d FROM Document d WHERE d.age <> 1 + (SELECT COUNT(p.id) FROM Person p)", crit.getQueryString());
        crit.getResultList();
    }

    @Test
    public void testNotEqMultipleSubqueryWithSurroundingExpression() {
        CriteriaBuilder<Document> crit = cbf.create(em, Document.class, "d");
        crit.where("age").notEq("alias", "alias * alias").from(Person.class, "p").select("COUNT(id)").end();     
        
        assertEquals("SELECT d FROM Document d WHERE d.age <> (SELECT COUNT(p.id) FROM Person p) * (SELECT COUNT(p.id) FROM Person p)", crit.getQueryString());
        crit.getResultList();
    }
}
