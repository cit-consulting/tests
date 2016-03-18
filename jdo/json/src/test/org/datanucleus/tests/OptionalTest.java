/**********************************************************************
Copyright (c) 2016 Andy Jefferson and others. All rights reserved.
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

import java.util.List;
import java.util.Optional;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.datanucleus.samples.types.optional.OptionalSample1;
import org.datanucleus.samples.types.optional.OptionalSample2;
import org.datanucleus.samples.types.optional.OptionalSample3;

/**
 * Tests for persistence of Java8 Optional.
 */
public class OptionalTest extends JSONTestCase
{
    private static boolean initialised = false;

    public OptionalTest(String name)
    {
        super(name);
        if (!initialised)
        {
            addClassesToSchema(new Class[]
                {
                    OptionalSample1.class,
                    OptionalSample2.class,
                    OptionalSample3.class,
                });
            initialised = true;
        }
    }

    /**
     * Test for Optional of basic types.
     */
    public void testOptionalBasic()
    {
        try
        {
            // Create some data we can use for access
            PersistenceManager pm = pmf.getPersistenceManager();
            Transaction tx = pm.currentTransaction();

            Object id = null;
            Object id2 = null;
            try
            {
                tx.begin();
                OptionalSample1 s = new OptionalSample1(1, "First String", 123.45);
                pm.makePersistent(s);
                OptionalSample1 s2 = new OptionalSample1(2, null, 245.6);
                pm.makePersistent(s2);
                tx.commit();
                id = pm.getObjectId(s);
                id2 = pm.getObjectId(s2);
            }
            catch (Exception e)
            {
                LOG.error("Error persisting Optional samples", e);
                fail("Error persisting Optional samples");
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                pm.close();
            }
            pmf.getDataStoreCache().evictAll();

            // Retrieve the data
            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            try
            {
                tx.begin();

                OptionalSample1 s = pm.getObjectById(OptionalSample1.class, id);

                Optional<String> strField = s.getStringField();
                assertNotNull(strField);
                assertNotNull(strField.get());
                assertEquals("First String", strField.get());
                Optional<Double> dblField = s.getDoubleField();
                assertNotNull(dblField);
                assertNotNull(dblField.get());
                assertEquals(123.45, dblField.get(), 0.05);

                OptionalSample1 s2 = pm.getObjectById(OptionalSample1.class, id2);

                Optional<String> strField2 = s2.getStringField();
                assertNotNull(strField2);
                assertFalse(strField2.isPresent());
                Optional<Double> dblField2 = s2.getDoubleField();
                assertNotNull(dblField2);
                assertNotNull(dblField2.get());
                assertEquals(245.6, dblField2.get(), 0.05);

                tx.commit();
            }
            catch (Exception e)
            {
                LOG.error("Error retrieving Optional data", e);
                fail("Error retrieving Optional data : " + e.getMessage());
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                pm.close();
            }

            // Query the data
            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            try
            {
                tx.begin();

                Query q = pm.newQuery("SELECT FROM " + OptionalSample1.class.getName() + " WHERE stringField.isPresent()");
                q.setClass(OptionalSample1.class);
                List<OptionalSample1> results = q.executeList();
                assertNotNull(results);
                assertEquals(1, results.size());
                OptionalSample1 result1 = results.get(0);
                assertEquals(1, result1.getId());

                Query q2 = pm.newQuery("SELECT id, stringField.get() FROM " + OptionalSample1.class.getName());
                List results2 = q2.executeResultList();
                assertNotNull(results2);
                assertEquals(2, results2.size());

                boolean s1Present = false;
                boolean s2Present = false;
                for (Object row : results2)
                {
                    Object[] rowValues = (Object[])row;
                    if (((Number)rowValues[0]).intValue() == 1)
                    {
                        s1Present = true;
                        assertTrue(rowValues[1] instanceof String);
                        assertEquals("First String", rowValues[1]);
                    }
                    else if (((Number)rowValues[0]).intValue() == 2)
                    {
                        s2Present = true;
                        assertNull(rowValues[1]);
                    }
                }
                assertTrue(s1Present);
                assertTrue(s2Present);

                tx.commit();
            }
            catch (Exception e)
            {
                LOG.error("Error querying Optional data", e);
                fail("Error querying Optional data : " + e.getMessage());
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                pm.close();
            }
        }
        finally
        {
            clean(OptionalSample1.class);
        }
    }

    /**
     * Test for Optional of 1-1 relation.
     */
    public void testOptionalOneToOne()
    {
        try
        {
            // Create some data we can use for access
            PersistenceManager pm = pmf.getPersistenceManager();
            Transaction tx = pm.currentTransaction();

            Object id1 = null;
            Object id2 = null;
            try
            {
                tx.begin();

                OptionalSample2 s2a = new OptionalSample2(1, "First");
                OptionalSample3 s3 = new OptionalSample3(101, "First S3");
                s2a.setSample3(s3);
                pm.makePersistent(s2a);

                OptionalSample2 s2b = new OptionalSample2(2, "Second");
                pm.makePersistent(s2b);

                tx.commit();
                id1 = pm.getObjectId(s2a);
                id2 = pm.getObjectId(s2b);
            }
            catch (Exception e)
            {
                LOG.error("Error persisting Optional samples", e);
                fail("Error persisting Optional samples");
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                pm.close();
            }
            pmf.getDataStoreCache().evictAll();

            // Retrieve the data
            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            try
            {
                tx.begin();

                OptionalSample2 s2a = pm.getObjectById(OptionalSample2.class, id1);
                assertEquals("First", s2a.getName());
                Optional<OptionalSample3> s3fielda = s2a.getSample3();
                assertNotNull(s3fielda);
                assertNotNull(s3fielda.get());
                OptionalSample3 s3 = s3fielda.get();
                assertEquals("First S3", s3.getName());

                OptionalSample2 s2b = pm.getObjectById(OptionalSample2.class, id2);
                assertEquals("Second", s2b.getName());
                Optional<OptionalSample3> s3fieldb = s2b.getSample3();
                assertNotNull(s3fieldb);
                assertFalse(s3fieldb.isPresent());

                tx.commit();
            }
            catch (Exception e)
            {
                LOG.error("Error retrieving Optional data", e);
                fail("Error retrieving Optional data : " + e.getMessage());
            }
            finally
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
                pm.close();
            }
        }
        finally
        {
            clean(OptionalSample2.class);
            clean(OptionalSample3.class);
        }
    }
}