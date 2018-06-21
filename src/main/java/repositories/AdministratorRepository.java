package repositories;

import domain.Administrator;
import domain.Article;
import domain.NewsPaper;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    @Query("select a from Administrator a where a.userAccount.id = ?1")
    Administrator findByUserAccountId(int userAccountId);

    // ########################  QUERIES LEVEL C  ################################

    // 1- The average and the standard deviation of newspapers created per user.
    @Query("select avg(u.newsPapers.size),sqrt(sum(u.newsPapers.size *u.newsPapers.size)/ count(u) - (avg(u.newsPapers.size) *avg(u.newsPapers.size))) from  User u")
    Double[] avgStdOfNewspapersPerUser();

    // 2- The average and the standard deviation of articles written by writer.
    @Query("select avg(u.newsPapers.size),sqrt(sum(u.newsPapers.size *u.newsPapers.size)/ count(u) - (avg(u.newsPapers.size) *avg(u.newsPapers.size))) from  User u")
    Double[]  avgStdOfArticles();

    // 3- The average and the standard deviation of articles per newspaper.
    @Query("select avg(u.articles.size),sqrt(sum(u.articles.size *u.articles.size)/ count(u) - (avg(u.articles.size) *avg(u.articles.size))) from  NewsPaper u")
    Double[]  avgStdOfArticlesPerNewspaper();

    // 4- The newspapers that have at least 10% more articles than the average.
    @Query("select r from NewsPaper r where r.articles.size > (select avg(r1.articles.size)*1.1 from NewsPaper r1)")
    Collection<NewsPaper> newspapersWith10PercentMoreArticlesThanAvg();

    // 5- The newspapers that have at least 10% fewer articles than the average.

    @Query("select r from NewsPaper r where r.articles.size < (select avg(r1.articles.size)*1.1 from NewsPaper r1)")
    Collection<NewsPaper> newspapersWith10PercentFewerArticlesThanAvg();

    // 6- The ratio of users who have ever created a newspaper.

    @Query("select concat( 100 * ( select count(t) from User t where t.newsPapers is not empty )/ count(r), '%') from User r")
    String ratioOfUsersThatCreatedNewspaper();

    // 7- The ratio of users who have ever written an article.
    @Query("select count(distinct u)*1.0 / (select count(u1)*1.0 from User u1)from User u join u.newsPapers p where p.publisher = u and p.articles.size>0")
    Double ratioOfUserCreatingArticle();


    // ########################  QUERIES LEVEL B  ################################


    // 8- The average number of follow-ups per article.
    @Query("select avg(a.followUps.size) from Article a ")
    Double avgFollowUpsPerArticle();

    // 9- The average number of follow-ups per article up to one week after the corresponding newspaper?s been published.
    @Query("select count(a1)*1.0/(select count(a2)*1.0 from FollowUp a2 ) from FollowUp a1 where datediff(a1.moment, a1.article.newsPaper.publicationDate)<=7")
    Double avgFollowUpsPerArticleAfter1weekNewspaprerPublished();

    // 10- The average number of follow-ups per article up to two weeks after the corresponding newspaper?s been published.
     @Query("select count(a1)*1.0/(select count(a2)*1.0 from FollowUp a2 ) from FollowUp a1 where datediff(a1.moment, a1.article.newsPaper.publicationDate)<=14")
     Double avgFollowUpsPerArticleAfter2weekNewspaprerPublished();

    // 11- The average and the standard deviation of the number of chirps per user.
    @Query("select avg(u.chirps.size),sqrt(sum(u.chirps.size *u.chirps.size)/ count(u) - (avg(u.chirps.size) *avg(u.chirps.size))) from  User u")
    Double[] avgStdChirpsPerUser();

    // 12- The ratio of users who have posted above 75% the average number of chirps per user.
    //          select u from User u where u.chirps.size > (select avg(u1.chirps.size)*1.75 from User u1)
    @Query("select 1.0*(select count(u1) from User u1 where (select count(c) from User u join u.chirps c where c.posted = true and c.user = u1) >= ( select avg(r1.chirps.size)*1.75 from User r1))/(count(f)) from User f")
    Double ratioUsersWith75PercentMoreChirpsPostedThanAVG();

    // ########################  QUERIES LEVEL A  ################################

    // 13- The ratio of public versus private newspapers.

    @Query("select count(n1)*1.0 /(select count(n2)*1.0 from NewsPaper n2 where n2.modePrivate = false) from NewsPaper n1 where n1.modePrivate = true")
    Double ratioPublicVSPrivateNewspapers();

    // 14- The average number of articles per private newspapers.

    @Query("select avg(n.articles.size) from NewsPaper n where n.modePrivate = true")
    Double avgArticlesPerNewsPapersPrivate();

    // 15- The average number of articles per public newspapers.

    @Query("select avg(n.articles.size) from NewsPaper n where n.modePrivate = false")
    Double avgArticlesPerNewsPapersPublic();

    // 16- The ratio of subscribers per private newspaper versus the total number of customers.
    @Query("select count(c1.subscriptionsToNewspapers.size)*1.0 /(select count(c2)*1.0 from Customer c2) from Customer c1 where c1.subscriptionsToNewspapers.size > 0")
    Double ratioPrivateNewsPapersVsCustomers();

    // 17- The ratio of users who have posted above 75% the average number of chirps per user.
    @Query("select count(n1)*1.0 /(select count(n2)*1.0 from NewsPaper n2 where n2.modePrivate = false) from NewsPaper n1 where n1.modePrivate = true")
    Double ratioPrivateNewsPapersVsPublicPerPublisher();

    // ########################  QUERIES LEVEL C  NEWSPAPER 2################################

    // 18- The ratio of newspapers that have at least one advertisement versus the newspapers that haven?t any.
    @Query("select count(n1)*1.0 /(select count(n2)*1.0 from NewsPaper n2 where n2.advertisements.size > 0) from NewsPaper n1 where n1.advertisements.size = 0")
    Double ratioNpAdvertisementsVsNpWithOut();

    //19- The ratio of advertisements that have taboo words.
    @Query("select count(a1)*1.0 / (select count(a2)*1.0 from Advertisement a2 where a2.taboo = true) from Advertisement a1")
    Double ratioAdverTabooVsAdvertisement();

    // ########################  QUERIES LEVEL B  NEWSPAPER 2################################

    //20- The average number of newspapers per volume.
    @Query("select avg(v.newsPapersVolume.size) from Volume v where v.newsPapersVolume.size > 0")
    Double avgNewsPapersPerVolume();

    //21- The ratio of subscriptions to volumes versus subscriptions to newspapers.
    @Query("select count(v1)*1.0 / (select count(n1)*1.0 from Volume n1 where n1.newsPapersVolume.size != 0) from Volume v1 where v1.subscriptionVolumes.size !=0")
    Double rationSubscribedNewsVsSubscribedVolume();

}
