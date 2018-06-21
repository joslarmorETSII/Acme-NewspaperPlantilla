package repositories;

import domain.Advertisement;
import domain.Chirp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Integer> {

    @Query("select a from Advertisement a where a.taboo = true")
    Collection<Advertisement> findTabooAdvertisement();
}
