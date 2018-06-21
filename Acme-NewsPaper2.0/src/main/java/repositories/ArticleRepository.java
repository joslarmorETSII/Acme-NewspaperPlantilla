package repositories;

import domain.Article;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {

    @Query("select a from NewsPaper n join n.articles a where n.publisher=?1")
    Collection<Article> findArticlesByUser(User user);
    @Query("select a from Article a where a.finalMode = true and a.newsPaper.published = true ")
    Collection<Article> findPublishArticles();

    @Query("select n.articles from NewsPaper n where n.publisher.id = ?1 and n.published=true")
    Collection<Article> findArticlesByUserId(int id);

    @Query("select a from Article a where (a.title like %?1%  or a.summary like %?2% or a.body like %?3%) and a.newsPaper.published = true and a.finalMode = true")
    Collection<Article> findByTitleOrSummaryOrBody(String title,String summary, String body);

    @Query("select a from Article a where a.taboo =true")
    Collection<Article> findArticleByTabooIsTrue();
}
