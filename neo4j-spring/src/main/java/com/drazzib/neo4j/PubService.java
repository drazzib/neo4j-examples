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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Damien Raude-Morvan
 */
@Service
public class PubService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BrewingCompanyRepository brewingCompanyRepository;

    public long getBeersCount() {
        return beerRepository.count();
    }

    public Beer createBeer(final String name, final int alcoholPercentage) {
        return beerRepository.save(new Beer(name, alcoholPercentage));
    }

    public BrewingCompany createBrewingCompany(final String name) {
        return brewingCompanyRepository.save(new BrewingCompany(name));
    }

    public Iterable<Beer> getAllBeers() {
        return beerRepository.findAll();
    }

    public Beer findBeerById(final long id) {
        return beerRepository.findOne(id);
    }

    public Beer findBeerByName(final String name) {
        return beerRepository.findByPropertyValue("name", name);
    }

    public Iterable<Beer> findBeerByAlcoholPercentage(final int alcoholPercentage) {
        return beerRepository.findAllByPropertyValue("alcoholPercentage", alcoholPercentage);
    }

    public Collection<Beer> populateData() {
        Collection<Beer> beers = new ArrayList<Beer>();

        beers.add(createBeer("Leffe Blond", 0));
        beers.add(createBeer("Leffe Radieuse", 0));

        Beer leffeTriple = createBeer("Leffe Tripel", 1);
        Beer leffeRuby = createBeer("Leffe Ruby", 2);
        BrewingCompany company = createBrewingCompany("AB-InBev");
        leffeRuby.setBrewingCompany(company);
        beerRepository.save(leffeRuby);
        leffeTriple.setBrewingCompany(company);
        beerRepository.save(leffeTriple);
        beers.add(leffeTriple);
        beers.add(leffeRuby);

        beers.add(createBeer("Chimay Rood", 7));
        beers.add(createBeer("Florival Bruin", 7));

        return beers;
    }

}
