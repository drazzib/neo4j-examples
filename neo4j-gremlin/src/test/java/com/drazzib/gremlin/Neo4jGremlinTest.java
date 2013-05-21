package com.drazzib.gremlin;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Damien Raude-Morvan
 */
public class Neo4jGremlinTest {

    private Neo4jWrapper wrapper;

    private Graph g;

    @Before
    public void setUp() throws Exception {
        // Create Gremlin graph
        Neo4JEmbeddedServer server = new Neo4JEmbeddedServer();
        wrapper = new Neo4jWrapper(server.getDb());
        g = wrapper.getGraph();
    }

    @After
    public void tearDown() throws Exception {
        wrapper.shutdown();
    }

    @Test
    @Ignore
    public void testGraphWithCustomData() throws Exception {


        // Add custom data
        Vertex a = g.addVertex(null);
        Vertex b = g.addVertex(null);
        a.setProperty("name", "marko");
        b.setProperty("name", "peter");
        Edge e = g.addEdge(null, a, b, "knows");
        e.setProperty("since", 2006);

    }

    @Test
    public void testPipeline() throws Exception {
        // Add custom data
        Vertex a = g.addVertex(null);
        Vertex b = g.addVertex(null);
        a.setProperty("name", "marko");
        b.setProperty("name", "peter");
        Edge e = g.addEdge(null, a, b, "knows");
        e.setProperty("since", 2006);

        // Create a pipeline
        GremlinPipeline pipe = new GremlinPipeline();
        pipe.start(g.getVertex(1))
                .out("knows")
                .property("name");

        pipe.next(); // the next String name in the pipe
        pipe.next(5); // the next 5 String names in the pipe as a List
        pipe.iterate(); // while(true) { pipe.next() } (useful when only side-effects are desired)
        pipe.toList(); // fill a list of all the elements in the pipe
        pipe.count(); // the number of objects in the pipe
    }
}
