package repositories;

import domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NoteRepository extends JpaRepository<Note,Integer> {


    @Query("select r from Note r where r.ticker=?1")
    Note findByTicker(String ticker);

    @Query("select n from Note n where n.newsPaper.id =?1 and (n.displayMoment =null or n.displayMoment<=CURRENT_TIMESTAMP)")
    Collection<Note> findNotesToDisplay(int newsPaperId);


    /*
        Query 1.
        select count(n1)*1.0 /(select count(n2)*1.0 from NewsPaper n2 ) from NewsPaper n1 where n1.notes.size >0;

        Query 2.
        1. select a from Administrator a where a.notes.size>=(select max(a2.notes.size) from Administrator a2 );

     */
}
