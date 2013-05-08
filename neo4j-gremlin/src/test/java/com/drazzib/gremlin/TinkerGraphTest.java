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
package com.drazzib.gremlin;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;

/**
 * Use {@link TinkerGraph}, an in-memory graph implementation provided by Blueprints project.
 *
 * @author Damien Raude-Morvan
 */
public class TinkerGraphTest {

    private static final Logger LOG = LoggerFactory.getLogger(TinkerGraphTest.class);

    @Test
    public void testGraphWithCustomData() throws Exception {
        // Create empty graph
        Graph graph = new TinkerGraph();
        // Add custom data
        Vertex a = graph.addVertex(null);
        Vertex b = graph.addVertex(null);
        a.setProperty("name", "marko");
        b.setProperty("name", "peter");
        Edge e = graph.addEdge(null, a, b, "knows");

        Vertex inVertex = e.getVertex(IN);
        Vertex outVertex = e.getVertex(OUT);
        StringBuilder sb = new StringBuilder();
        sb.append(outVertex.getProperty("name")).append(" ")
                .append("--|").append(e.getLabel()).append("|-->")
                .append(" ").append(inVertex.getProperty("name"));
        LOG.info(sb.toString());

    }

    @Test
    public void testIterateThroughAllEdges() {
        Graph graph = createGraphWithDefaultData();

        LOG.info("Vertices of " + graph);
        for (Vertex vertex : graph.getVertices()) {
            LOG.info("Vertex: {}", vertex);
        }
        LOG.info("Edges of " + graph);
        for (Edge edge : graph.getEdges()) {
            LOG.info("Edge: {}", edge);
        }
    }

    @Test
    public void testIterateThroughOutgoingEdges() {
        Graph graph = createGraphWithDefaultData();

        Vertex a = graph.getVertex("1");
        LOG.info("Vertex " + a.getId() + " has name " + a.getProperty("name"));
        for (Edge e : a.getEdges(OUT)) {
            LOG.info("Outgoing Edge: {}", e);
        }
    }

    /**
     * Create new Graph, populated with some data
     *
     * @see <a href="https://github.com/tinkerpop/blueprints/wiki/Property-Graph-Model">Property-Graph-Model</a>
     */
    private Graph createGraphWithDefaultData() {
        return TinkerGraphFactory.createTinkerGraph();
    }
}
