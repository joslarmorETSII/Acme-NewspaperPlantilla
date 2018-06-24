package repositories;

import domain.Audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Integer> {

    @Query("select a from Audit a where a.ticker=?1")
    Audit findByTicker(String code);

    @Query("select DISTINCT a from NewsPaper n join n.audits a where n.id = ?1 and ( a.moment = null or a.moment <= CURRENT_TIMESTAMP) ")
    Collection<Audit> AuditForDisplay(int newsPaperId);


/*
    Query 1.
    select count(n1)*1.0 /(select count(n2)*1.0 from NewsPaper n2 ) from NewsPaper n1 where n1.audits.size >0;

    Query 2.
            1. select a from Administrator a where a.audits.size>=(select max(a2.audits.size) from Administrator a2 );
*/
}
