package com.drazzib.gremlin;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 * @author Damien Raude-Morvan
 */
public class Neo4jWrapper {

    private final Graph graph;

    public Neo4jWrapper(final GraphDatabaseService service) {
        graph = new Neo4jGraph(service);
    }

    public Graph getGraph() {
        return graph;
    }

    public void shutdown() {
        graph.shutdown();
    }
}
