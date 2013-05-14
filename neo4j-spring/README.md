Ideas
---
http://www.neotechnology.com/2013/01/neo4j-and-graph-databases-explained-with-belgian-beer/
https://docs.google.com/spreadsheet/ccc?key=0Ah0qwaznhrupdEpxdXBCSmZuUjA2TTJZRm1ORy0zcUE#gid=3


GraphRepository also support Query and Finder
---
interface MovieRepository extends GraphRepository<Movie> {
 @Query(“START movie={0}
         MATCH m<-[rating:RATED]-user
         RETURN rating”)
 Iterable<Rating> getRatings(Movie movie);

 // Co-Actors
 Iterable<Person> findByActorsMoviesActorName(name)
}

