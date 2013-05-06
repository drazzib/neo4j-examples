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

/**
 * @author Damien Raude-Morvan
 */
@NodeEntity
public class Beer {

    /**
     * Relationship with producer.
     */
    private final static String PRODUCED_BY = "PRODUCED_BY";

    @GraphId
    private Long id;

    @Indexed
    private String name;

    @Indexed(indexName = "alcohol-index")
    private int alcoholPercentage;

    @Fetch
    @RelatedTo(type = PRODUCED_BY, direction = Direction.OUTGOING)
    private BrewingCompany brewingCompany;

    public Beer(final String name, final int alcoholPercentage) {
        this.name = name;
        this.alcoholPercentage = alcoholPercentage;
    }

    public Beer() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(int alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public BrewingCompany getBrewingCompany() {
        return brewingCompany;
    }

    public void setBrewingCompany(BrewingCompany brewingCompany) {
        this.brewingCompany = brewingCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beer that = (Beer) o;
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
                .add("alcoholPercentage", this.alcoholPercentage)
                .toString();
    }
}
