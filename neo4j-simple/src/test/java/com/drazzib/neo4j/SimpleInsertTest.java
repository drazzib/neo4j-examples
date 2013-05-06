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
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @see <a href="https://github.com/neo4j/neo4j/blob/master/community/embedded-examples/src/test/java/org/neo4j/examples/Neo4jBasicDocTest.java">Neo4jBasicDocTest</a>
 */
public class SimpleInsertTest extends AbstractNeo4JTest {

    @Test
    public void testInsertThenGetById() throws Exception {
        Transaction tx = getDb().beginTx();

        Node n = null;
        try {
            n = getDb().createNode();
            n.setProperty("name", "NantesJUG");
            tx.success();
        } catch (Exception e) {
            tx.failure();
        } finally {
            tx.finish();
        }

        // The node should have an id greater than 0, which is the id of the
        // reference node.
        assertThat(n.getId(), is(greaterThan(0L)));

        // Retrieve a node by using the id of the created node. The id's and
        // property should match.
        Node foundNode = getDb().getNodeById(n.getId());
        assertThat(foundNode.getId(), is(n.getId()));
        assertThat((String) foundNode.getProperty("name"), is("NantesJUG"));

    }
}
