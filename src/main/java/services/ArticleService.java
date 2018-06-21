package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ArticleRepository;
import repositories.NewsPaperRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ArticleService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private ArticleRepository articleRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private FollowUpService followUpService;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private PictureService pictureService;

    // Constructors -----------------------------------------------------------

    public ArticleService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Article create(){
        Article res= null;

        Collection<FollowUp> followUps= new ArrayList<>();
        res= new Article();
        res.setFollowUps(followUps);
        res.setMoment(new Date());
        return res;
    }

    public Article save (Article article, NewsPaper newsPaper){
        Article res= null;
        Assert.notNull(article);
        article.setNewsPaper(newsPaper);
        Assert.isTrue(checkByPrincipal(article));
        Assert.isTrue(!article.getNewsPaper().getPublished());
        if(article.getId() == 0) {
            newsPaper.getArticles().add(article);
        }
        if(isTabooArticle(article)){
            article.setTaboo(true);
        }

        res = articleRepository.save(article);
        return res;

    }

    public Collection<Article> findAll(){
        return articleRepository.findAll();
    }

    public Article findOne(int articleId){
        Article res= null;
        res= articleRepository.findOne(articleId);
        return res;
    }

    public Article findOneToEdit(int articleId){
        Article res= null;
        res= articleRepository.findOne(articleId);
        Assert.isTrue(checkByPrincipal(res) || checkByPrincipalAdmin(res));
        return res;
    }

    public void delete(Article article){
        Assert.isTrue(checkByPrincipalAdmin(article) || checkByPrincipal(article));

        Collection<FollowUp> followUps = article.getFollowUps();
        followUpService.deleteAll(followUps);

        articleRepository.delete(article);
    }

    public void deleteAll(Collection<Article> articles){
        for(Article a: articles){
            Collection<FollowUp> followUps = a.getFollowUps();
            for(FollowUp f: followUps){
                this.pictureService.deleteAll(f);
            }
            followUpService.deleteAll(followUps);
            for(Picture p : a.getPictures()){
                this.pictureService.delete(p);
            }
            this.articleRepository.delete(a);
        }
    }

    // Other business methods -------------------------------------------------

    public boolean checkByPrincipal(Article article) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (article.getNewsPaper().getPublisher().equals(principal))
            res = true;

        return res;
    }

    public boolean checkByPrincipalAdmin(Article article){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        if(administrator!=null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("ADMINISTRATOR");
        }
        return res;

    }

    public Collection<Article> findArticleByUser(User user){
        return this.articleRepository.findArticlesByUser(user);
    }

    private boolean isTabooArticle(final Article article) {
        boolean result = false;
        Pattern p;
        Matcher isAnyMatcherTitle;
        Matcher isAnyMatcherSummary;
        Matcher isAnyMatcherBody;

        p = this.tabooWords();
        isAnyMatcherBody = p.matcher(article.getBody());
        isAnyMatcherSummary = p.matcher(article.getSummary());
        isAnyMatcherTitle = p.matcher(article.getTitle());

        if (isAnyMatcherTitle.find() || isAnyMatcherBody.find() || isAnyMatcherSummary.find())
            result = true;

        return result;
    }

    public Pattern tabooWords() {
        Pattern result;
        List<String> tabooWords;

        final Collection<String> taboolist = this.configurationService.findAll().iterator().next().getTabooWords();
        tabooWords = new ArrayList<>(taboolist);

        String str = ".*\\b(";
        for (int i = 0; i <= tabooWords.size(); i++)
            if (i < tabooWords.size())
                str += tabooWords.get(i) + "|";
            else
                str += tabooWords.iterator().next() + ")\\b.*";

        result = Pattern.compile(str, Pattern.CASE_INSENSITIVE);

        return result;
    }

    public Collection<Article> findPublishArticles() {

        return articleRepository.findPublishArticles();
    }

    public Collection<Article> findPublishArticlesByUserId(int id) {
        return articleRepository.findArticlesByUserId(id);
    }

   public Collection<Article> findbyTitleAndBodyAndSummary(String keyword){
        return articleRepository.findByTitleOrSummaryOrBody(keyword,keyword,keyword);
    }

   public Collection<Article> findArticleByTabooIsTrue(){
        Assert.isTrue(actorService.isAdministrator());
        return articleRepository.findArticleByTabooIsTrue();
   }

    public void flush() {
        articleRepository.flush();
    }
}
