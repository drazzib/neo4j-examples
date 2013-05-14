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

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Neo4j embedded server in local JVM.
 *
 * @author Damien Raude-Morvan
 */
public class Neo4JEmbeddedServer {

    private static final String DB_PATH = "build/neo4j-db";

    /**
     * Neo4j database.
     */
    private final GraphDatabaseService db;

    /**
     * @see <a href="http://docs.neo4j.org/chunked/milestone/tutorials-java-embedded-setup.html">Include Neo4j in your project</a>
     */
    public Neo4JEmbeddedServer() {
        // Starting an embedded database with configuration settings
        Map<String, String> config = createConfig();
        db = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(DB_PATH)
                .setConfig(config)
                .newGraphDatabase();
        // To make sure Neo4j is shut down properly you can add a shutdown hook:
        registerShutdownHook(db);
    }

    public GraphDatabaseService getDb() {
        return db;
    }

    protected Map<String, String> createConfig() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("neostore.nodestore.db.mapped_memory", "10M");
        config.put("string_block_size", "60");
        config.put("array_block_size", "300");
        return config;
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
