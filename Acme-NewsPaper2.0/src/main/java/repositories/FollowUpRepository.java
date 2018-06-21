package repositories;

import domain.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp,Integer> {


    @Query("select a.followUps from Article a where a.newsPaper.publisher.id = ?1 ")
    Collection<FollowUp> findFollowupsByUserId(int id);
}
