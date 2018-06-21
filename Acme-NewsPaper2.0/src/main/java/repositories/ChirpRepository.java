package repositories;

import domain.Article;
import domain.Chirp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp,Integer> {

    @Query("select c from Chirp c where c.user.id = ?1")
    Collection<Chirp> findChirpsByUserId(int userId);

    @Query("select f.chirps from User u join u.followings f where u.id =?1")
    Collection<Chirp> findAllChirpsByFollowings(int userId);

    @Query("select c from Chirp c where c.taboo = true and c.posted = true")
    Collection<Chirp> findfindTabooChirps();

    @Query("select c from Chirp c where c.posted = true")
    Collection<Chirp> findPublishedChirps();
}
