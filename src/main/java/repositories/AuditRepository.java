package repositories;

import domain.Audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Integer> {

    @Query("select a from Audit a where a.code=?1")
    Audit findByCode(String code);

    @Query("select a from Audit a where a.moment < CURRENT_TIMESTAMP")
    Collection<Audit> AuditForDisplay();



}
