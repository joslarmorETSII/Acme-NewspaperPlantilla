package repositories;

import domain.Article;
import domain.Picture;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Integer> {

}
