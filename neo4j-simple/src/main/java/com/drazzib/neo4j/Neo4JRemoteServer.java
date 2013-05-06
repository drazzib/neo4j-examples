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

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.rest.graphdb.RestGraphDatabase;

/**
 * Remote Neo4j server, using REST API.
 *
 * @author Damien Raude-Morvan
 */
public class Neo4JRemoteServer {

    public static final String DEFAULT_DB_URI = "http://localhost:7474/db/data";

    /**
     * Neo4j database.
     */
    private final GraphDatabaseService db;

    /**
     * @see <a href="https://github.com/neo4j/java-rest-binding">Java binding for the Neo4j Server</a>
     */
    public Neo4JRemoteServer() {
        db = new RestGraphDatabase(DEFAULT_DB_URI);
        // To make sure Neo4j is shut down properly you can add a shutdown hook:
        registerShutdownHook(db);
    }

    public GraphDatabaseService getDb() {
        return db;
    }

    private void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

}
