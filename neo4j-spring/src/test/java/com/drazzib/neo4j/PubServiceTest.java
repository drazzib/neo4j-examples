/*
 * (C) Copyright 2013 Damien Raude-Morvan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.drazzib.neo4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.internal.matchers.StringContains.containsString;

/**
 * @author Damien Raude-Morvan
 */
@ContextConfiguration(locations = "classpath:/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PubServiceTest {

    @Autowired
    private PubService service;

    @Autowired
    private Neo4jTemplate template;

    @Rollback(false)
    @BeforeTransaction
    public void cleanDb() {
        Neo4jHelper.cleanDb(template);
    }

    @Test
    public void testCreateBeer() {
        assertEquals(0, service.getBeersCount());
        Beer myBeer = service.createBeer("mine", 0);
        assertEquals(1, service.getBeersCount());

        Iterable<Beer> founds = service.getAllBeers();
        Beer mine = founds.iterator().next();
        assertEquals(myBeer.getName(), mine.getName());
    }

    @Test
    public void testBeerCount() {
        service.populateData();

        assertEquals(6, service.getBeersCount());
    }

    @Test
    public void testFindBeerById() {
        service.populateData();

        for (Beer beer : service.getAllBeers()) {
            Beer foundBeer = service.findBeerById(beer.getId());
            assertNotNull(foundBeer);
        }
    }

    @Test
    public void testFindBeerByName_positive() {
        service.populateData();

        for (Beer beer : service.getAllBeers()) {
            Beer foundBeer = service.findBeerByName(beer.getName());
            assertNotNull(foundBeer);
        }
    }

    @Test
    public void testGetAllBeers() {
        Collection<Beer> madeBeers = service.populateData();
        Iterable<Beer> foundBeers = service.getAllBeers();

        int founds = 0;
        for (Beer foundBeer : foundBeers) {
            assertTrue(madeBeers.contains(foundBeer));
            founds++;
        }

        assertEquals(madeBeers.size(), founds);
    }

    @Test
    public void testFindBeerByAlcoholPercentage() {
        service.populateData();

        for (Beer beer : service.findBeerByAlcoholPercentage(7)) {
            assertThat(
                    beer.getName(),
                    is(anyOf(containsString("Chimay Rood"), containsString("Florival Bruin"))));
        }
    }

    @Test
    public void testFindBeerByName_negative() {
        service.populateData();
        Beer notFound = service.findBeerByName("Unknown");
        assertNull(notFound);
    }

}
