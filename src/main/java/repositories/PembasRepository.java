package repositories;

import domain.Pembas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PembasRepository extends JpaRepository<Pembas,Integer> {

    @Query("select a from Pembas a where a.code=?1")
    Pembas findByCode(String code);

    @Query("select DISTINCT a from NewsPaper n join n.pembass a where n.id = ?1 and ( a.moment = null or a.moment <= CURRENT_TIMESTAMP) ")
    Collection<Pembas> PembasForDisplay(int newsPaperId);


/*
    Query 1.
    select count(n1)*1.0 /(select count(n2)*1.0 from NewsPaper n2 ) from NewsPaper n1 where n1.pembass.size >0;

    Query 2.
            1. select a from Administrator a where a.pembass.size>=(select max(a2.pembass.size) from Administrator a2 );
*/
}
