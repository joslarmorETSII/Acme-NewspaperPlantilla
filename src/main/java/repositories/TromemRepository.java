package repositories;

import domain.Tromem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TromemRepository extends JpaRepository<Tromem,Integer> {


    @Query("select r from Tromem r where r.ticker=?1")
    Tromem findByTicker(String ticker);

    @Query("select n from Tromem n where n.newsPaper.id =?1 and (n.displayMoment =null or n.displayMoment<=CURRENT_TIMESTAMP)")
    Collection<Tromem> findTromemsToDisplay(int newsPaperId);


    /*
        Query 1.
        select count(n1)*1.0 /(select count(n2)*1.0 from NewsPaper n2 ) from NewsPaper n1 where n1.tromems.size >0;

        Query 2.
        1. select a from Administrator a where a.tromems.size>=(select max(a2.tromems.size) from Administrator a2 );



select DISTINCT n.tromems from NewsPaper n join n.tromems a where n.id = 154 and ( a.displayMoment = null or a.displayMoment <= CURRENT_TIMESTAMP);

     */
}
