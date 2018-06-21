package repositories;

import domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Integer> {


    @Query("select r from Note r where r.ticker=?1")
    Note findByTicker(String ticker);
}
