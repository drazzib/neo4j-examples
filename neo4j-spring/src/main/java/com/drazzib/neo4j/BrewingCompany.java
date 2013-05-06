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

import com.google.common.base.Objects;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

/**
 * @author Damien Raude-Morvan
 */
@NodeEntity
public class BrewingCompany {

    /**
     * Relationship with producer.
     */
    private final static String HAS_PRODUCT = "HAS_PRODUCT";

    @GraphId
    private Long id;

    @Indexed
    private String name;

    @Fetch
    @RelatedTo(type = HAS_PRODUCT, direction = Direction.OUTGOING)
    private Set<Beer> producedBeers;

    public BrewingCompany(final String name) {
        this.name = name;
    }

    public BrewingCompany() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrewingCompany that = (BrewingCompany) o;
        return Objects.equal(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", this.name)
                .toString();
    }
}
