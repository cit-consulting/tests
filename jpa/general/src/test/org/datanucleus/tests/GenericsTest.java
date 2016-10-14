/**********************************************************************
Copyright (c) 2015 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package org.datanucleus.tests;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.datanucleus.samples.annotations.models.company.Employee;
import org.datanucleus.samples.annotations.models.company.Manager;
import org.datanucleus.samples.annotations.models.company.Person;
import org.datanucleus.samples.annotations.generics.*;
import org.datanucleus.samples.annotations.types.enums.EnumHolder;
import org.datanucleus.samples.enums.Colour;

/**
 * Testcases for use of generics, and in particular TypeVariable usage.
 */
public class GenericsTest extends JPAPersistenceTestCase
{
    public GenericsTest(String name)
    {
        super(name);
    }

    public void testOneToOneTypeVariableUsingProperties()
    {
        try
        {
            EntityManager em = getEM();
            EntityTransaction tx = em.getTransaction();
            try
            {
                tx.begin();

                GenericOneOneRelated1 r1 = new GenericOneOneRelated1("First Related");
                r1.setId(new Long(1));
                r1.setAge(33);
                GenericOneOneSub1 s1 = new GenericOneOneSub1("First Object");
                s1.setType(GenericEnumType.TYPE_1);
                s1.setId(new Long(101));
                s1.setAge(10);
                s1.setOwner(r1);
                r1.getRelatedObjects().add(s1);
                em.persist(r1);
                em.persist(s1);

                GenericOneOneRelated1 r2 = new GenericOneOneRelated1("Second Related");
                r2.setId(new Long(2));
                r2.setAge(25);
                GenericOneOneSub1 s2 = new GenericOneOneSub1("Second Object");
                s2.setType(GenericEnumType.TYPE_2);
                s2.setId(new Long(102));
                s2.setAge(28);
                s2.setOwner(r2);
                r2.getRelatedObjects().add(s2);
                em.persist(r2);
                em.persist(s2);

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }

            em = getEM();
            tx = em.getTransaction();
            try
            {
                tx.begin();

                // First query, using join
                Query q1 = em.createQuery("SELECT s1 FROM GenericOneOneSub1 s1 JOIN s1.owner owner_1 WHERE s1.age < owner_1.age");
                List<GenericOneOneSub1> subs1 = q1.getResultList();
                assertEquals(1, subs1.size());

                // Query using enum
                Query q2 = em.createQuery("SELECT s1 FROM GenericOneOneSub1 s1 WHERE s1.type = org.datanucleus.samples.annotations.generics.GenericEnumType.TYPE_1");
                List<GenericOneOneSub1> subs2 = q2.getResultList();
                assertEquals(1, subs2.size());

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }
        }
        finally
        {
            clean(GenericOneOneRelated1.class);
            clean(GenericOneOneSub1.class);
        }
    }


    public void testOneToOneTypeVariableUsingFields()
    {
        try
        {
            EntityManager em = getEM();
            EntityTransaction tx = em.getTransaction();
            try
            {
                tx.begin();

                GenericOneOneRelated2 r1 = new GenericOneOneRelated2("First Related");
                r1.setId(new Long(1));
                r1.setAge(33);
                GenericOneOneSub2 s1 = new GenericOneOneSub2("First Object");
                s1.setType(GenericEnumType.TYPE_1);
                s1.setId(new Long(101));
                s1.setAge(10);
                s1.setOwner(r1);
                r1.getRelatedObjects().add(s1);
                em.persist(r1);
                em.persist(s1);

                GenericOneOneRelated2 r2 = new GenericOneOneRelated2("Second Related");
                r2.setId(new Long(2));
                r2.setAge(25);
                GenericOneOneSub2 s2 = new GenericOneOneSub2("Second Object");
                s2.setType(GenericEnumType.TYPE_2);
                s2.setId(new Long(102));
                s2.setAge(28);
                s2.setOwner(r2);
                r2.getRelatedObjects().add(s2);
                em.persist(r2);
                em.persist(s2);

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }

            em = getEM();
            tx = em.getTransaction();
            try
            {
                tx.begin();

                // First query, using join
                Query q1 = em.createQuery("SELECT s1 FROM GenericOneOneSub2 s1 JOIN s1.owner owner_1 WHERE s1.age < owner_1.age");
                List<GenericOneOneSub2> subs1 = q1.getResultList();
                assertEquals(1, subs1.size());

                // Query using enum
                Query q2 = em.createQuery("SELECT s1 FROM GenericOneOneSub2 s1 WHERE s1.type = org.datanucleus.samples.annotations.generics.GenericEnumType.TYPE_1");
                List<GenericOneOneSub2> subs2 = q2.getResultList();
                assertEquals(1, subs2.size());

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }
        }
        finally
        {
            clean(GenericOneOneRelated2.class);
            clean(GenericOneOneSub2.class);
        }
    }

    public void testGenericIdProps()
    {
        try
        {
            // Try Sub1
            EntityManager em = getEM();
            EntityTransaction tx = em.getTransaction();
            try
            {
                tx.begin();
                
                GenericIdPropSub1 sub1 = new GenericIdPropSub1("First sub1");
                em.persist(sub1);

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }
            emf.getCache().evictAll();

            em = getEM();
            tx = em.getTransaction();
            try
            {
                tx.begin();

                // Check Sub1 objects
                Query q1 = em.createQuery("SELECT s1 FROM GenericIdPropSub1 s1");
                List<GenericIdPropSub1> subs1 = q1.getResultList();
                assertEquals(1, subs1.size());

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }

            // Try Sub2
            em = getEM();
            tx = em.getTransaction();
            try
            {
                tx.begin();
                
                GenericIdPropSub2Sub sub2 = new GenericIdPropSub2Sub("First sub2", 1);
                em.persist(sub2);

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }
            emf.getCache().evictAll();

            em = getEM();
            tx = em.getTransaction();
            try
            {
                tx.begin();

                // Check Sub1 objects
                Query q1 = em.createQuery("SELECT s2 FROM GenericIdPropSub2Sub s2");
                List<GenericIdPropSub2Sub> subs2 = q1.getResultList();
                assertEquals(1, subs2.size());

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }
        }
        finally
        {
            clean(GenericIdPropSub1.class);
            clean(GenericIdPropSub2Sub.class);
        }
    }

    public void testType()
    {
        try
        {
            EntityManager em = getEM();
            EntityTransaction tx = em.getTransaction();
            try
            {
                tx.begin();

                Manager m1 = new Manager(1L,"Dima","Rudenko", "manager1@mail.com", 12345,"RR112");
                em.persist(m1);

                Manager m2 = new Manager(2L,"Petr","Romanov", "managet2@mail.com", 654321,"PI");
                em.persist(m2);

                Employee e1 = new Employee(3L,"Dima","Rudenk", "employee1@mail.com", 123456,"RR1122");
                em.persist(e1);

                Employee e2 = new Employee(4L,"Petr","Romano", "employee2@mail.com", 65432,"PII");
                em.persist(e2);

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }

            em = getEM();
            tx = em.getTransaction();
            try
            {
                tx.begin();
                Query q;
                List<Person> persons;

                q = em.createQuery("select p from Person_Ann p where TYPE(p) in :typesPerson");
                q.setParameter("typesPerson", Arrays.asList(Manager.class, Employee.class));
                persons = q.getResultList();
                assertEquals(4, persons.size());

                q = em.createQuery("select p from Person_Ann p where TYPE(p) in :typesPerson");
                q.setParameter("typesPerson", Arrays.asList(Manager.class));
                persons = q.getResultList();
                assertEquals(2, persons.size());

                q = em.createQuery("select p from Person_Ann p where TYPE(p) in :typesPerson");
                q.setParameter("typesPerson", Arrays.asList(Employee.class));
                persons = q.getResultList();
                assertEquals(4, persons.size());

                q = em.createQuery("select p from Person_Ann p where TYPE(p) not in :typesPerson");
                q.setParameter("typesPerson", Arrays.asList(Manager.class, Person.class));
                persons = q.getResultList();
                assertEquals(0, persons.size());

                q = em.createQuery("select p from Person_Ann p where TYPE(p) not in :typesPerson");
                q.setParameter("typesPerson", Arrays.asList(Manager.class));
                persons = q.getResultList();
                assertEquals(2, persons.size());

                q = em.createQuery("select p from Person_Ann p where TYPE(p) not in :typesPerson");
                q.setParameter("typesPerson", Arrays.asList(Employee.class));
                persons = q.getResultList();
                assertEquals(0, persons.size());
                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }
        }
        finally
        {
            clean(Employee.class);
            clean(Person.class);
            clean(Manager.class);
        }
    }

    public void testIn()
    {
        try
        {
            EntityManager em = getEM();
            EntityTransaction tx = em.getTransaction();
            try
            {
                tx.begin();

                EnumHolder eh1 = new EnumHolder();
                eh1.setId(1L);
                eh1.setColour1(Colour.GREEN);
                eh1.setColour2(Colour.GREEN);
                em.persist(eh1);

                EnumHolder eh2 = new EnumHolder();
                eh1.setId(2L);
                eh2.setColour1(Colour.RED);
                eh2.setColour2(Colour.RED);
                em.persist(eh2);

                EnumHolder eh3 = new EnumHolder();
                eh3.setId(3L);
                eh3.setColour1(Colour.BLUE);
                eh3.setColour2(Colour.BLUE);
                em.persist(eh3);

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }

            em = getEM();
            tx = em.getTransaction();
            try
            {
                tx.begin();
                // Query using enum
                Query q;
                List<EnumHolder> eh;
                List<Colour> colors = new LinkedList<>();
                colors.add(Colour.GREEN);
                colors.add(Colour.RED);

                //EnumType.ORDINAL
                q = em.createQuery("select eh from EnumHolder eh where eh.colour1 in :colors");
                q.setParameter("colors", colors);
                eh = q.getResultList();
                assertEquals(2, eh.size());

                q = em.createQuery("select eh from EnumHolder eh where eh.colour1 not in :colors");
                q.setParameter("colors", colors);
                eh = q.getResultList();
                assertEquals(1, eh.size());

                q = em.createQuery("select eh from EnumHolder eh where eh.colour1 in (org.datanucleus.samples.enums.Colour.GREEN, org.datanucleus.samples.enums.Colour.RED)");
                eh = q.getResultList();
                assertEquals(2, eh.size());

                q = em.createQuery("select eh from EnumHolder eh where eh.colour1 not in (org.datanucleus.samples.enums.Colour.GREEN, org.datanucleus.samples.enums.Colour.RED)");
                eh = q.getResultList();
                assertEquals(1, eh.size());

                //EnumType.STRING
                q = em.createQuery("select eh from EnumHolder eh where eh.colour2 in :colors");
                q.setParameter("colors", colors);
                eh = q.getResultList();
                assertEquals(2, eh.size());

                q = em.createQuery("select eh from EnumHolder eh where eh.colour2 not in :colors");
                q.setParameter("colors", colors);
                eh = q.getResultList();
                assertEquals(1, eh.size());

                q = em.createQuery("select eh from EnumHolder eh where eh.colour2 in (org.datanucleus.samples.enums.Colour.GREEN, org.datanucleus.samples.enums.Colour.RED)");
                eh = q.getResultList();
                assertEquals(2, eh.size());

                q = em.createQuery("select eh from EnumHolder eh where eh.colour2 not in (org.datanucleus.samples.enums.Colour.GREEN, org.datanucleus.samples.enums.Colour.RED)");
                eh = q.getResultList();
                assertEquals(1, eh.size());

                tx.commit();
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                em.close();
            }
        }
        finally
        {
            clean(EnumHolder.class);
        }
    }
}