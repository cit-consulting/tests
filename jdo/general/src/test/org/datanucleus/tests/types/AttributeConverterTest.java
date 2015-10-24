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
package org.datanucleus.tests.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.datanucleus.samples.types.converters.CollectionConverterHolder;
import org.datanucleus.samples.types.converters.MyType1;
import org.datanucleus.tests.JDOPersistenceTestCase;

/**
 * Test for use of JDO 3.2 AttributeConverters.
 */
public class AttributeConverterTest extends JDOPersistenceTestCase
{
    /**
     * @param name
     */
    public AttributeConverterTest(String name)
    {
        super(name);
    }

    /**
     * Tests for conversion of an element of a collection.
     */
    public void testCollectionElement()
    {
        try
        {
            PersistenceManager pm = pmf.getPersistenceManager();
            Transaction tx = pm.currentTransaction();
            try
            {
                tx.begin();

                CollectionConverterHolder h1 = new CollectionConverterHolder(1, "First");
                h1.getConvertedElementCollection().add(new MyType1("A", "B"));
                h1.getConvertedElementCollection().add(new MyType1("C", "D"));
                pm.makePersistent(h1);

                CollectionConverterHolder h2 = new CollectionConverterHolder(2, "Second");
                pm.makePersistent(h2);

                tx.commit();
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

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            try
            {
                tx.begin();

                Query<CollectionConverterHolder> q = pm.newQuery("SELECT FROM " + CollectionConverterHolder.class.getName() + " WHERE this.name == :name");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("name", "First");
                q.setNamedParameters(params);
                List<CollectionConverterHolder> results = q.executeList();
                assertEquals(1, results.size());
                CollectionConverterHolder h = results.get(0);
                Collection<MyType1> hcoll = h.getConvertedElementCollection();
                assertNotNull(hcoll);
                assertEquals(2, hcoll.size());

                boolean elem1Present = false;
                boolean elem2Present = false;
                for (MyType1 elem : hcoll)
                {
                    if (elem.getName1().equals("A") && elem.getName2().equals("B"))
                    {
                        elem1Present = true;
                    }
                    else if (elem.getName1().equals("C") && elem.getName2().equals("D"))
                    {
                        elem2Present = true;
                    }
                }
                assertTrue(elem1Present);
                assertTrue(elem2Present);

                CollectionConverterHolder h2 = pm.getObjectById(CollectionConverterHolder.class, 2);
                Collection<MyType1> h2coll = h2.getConvertedElementCollection();
                assertNotNull(h2coll);
                h2coll.add(new MyType1("H", "J"));

                tx.commit();
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

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            try
            {
                tx.begin();

                CollectionConverterHolder h2 = pm.getObjectById(CollectionConverterHolder.class, 2);
                Collection<MyType1> h2coll = h2.getConvertedElementCollection();
                assertNotNull(h2coll);
                assertEquals(1, h2coll.size());
                MyType1 elem = h2coll.iterator().next();
                assertEquals("H", elem.getName1());
                assertEquals("J", elem.getName2());

                tx.commit();
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
            // Cleanup
            clean(CollectionConverterHolder.class);
        }
    }

    /**
     * Tests for conversion of collection to a String.
     */
    public void testCollectionFull()
    {
        try
        {
            PersistenceManager pm = pmf.getPersistenceManager();
            Transaction tx = pm.currentTransaction();
            try
            {
                tx.begin();

                CollectionConverterHolder h1 = new CollectionConverterHolder(1, "First");
                h1.getConvertedCollection().add("A:B");
                h1.getConvertedCollection().add("C:D");
                pm.makePersistent(h1);

                CollectionConverterHolder h2 = new CollectionConverterHolder(2, "Second");
                pm.makePersistent(h2);

                tx.commit();
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

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            try
            {
                tx.begin();

                Query<CollectionConverterHolder> q = pm.newQuery("SELECT FROM " + CollectionConverterHolder.class.getName() + " WHERE this.name == :name");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("name", "First");
                q.setNamedParameters(params);
                List<CollectionConverterHolder> results = q.executeList();
                assertEquals(1, results.size());
                CollectionConverterHolder h = results.get(0);
                Collection<String> hcoll = h.getConvertedCollection();
                assertNotNull(hcoll);
                assertEquals(2, hcoll.size());
                assertTrue(hcoll.contains("A:B"));
                assertTrue(hcoll.contains("C:D"));

                CollectionConverterHolder h2 = pm.getObjectById(CollectionConverterHolder.class, 2);
                Collection<String> h2coll = h2.getConvertedCollection();
                assertNotNull(h2coll);
                h2coll.add("H:J");

                tx.commit();
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

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            try
            {
                tx.begin();

                CollectionConverterHolder h2 = pm.getObjectById(CollectionConverterHolder.class, 2);
                Collection<String> h2coll = h2.getConvertedCollection();
                assertNotNull(h2coll);
                assertEquals(1, h2coll.size());
                assertTrue(h2coll.contains("H:J"));

                tx.commit();
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
            // Cleanup
            clean(CollectionConverterHolder.class);
        }
    }
}