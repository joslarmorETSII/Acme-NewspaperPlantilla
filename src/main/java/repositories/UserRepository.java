package repositories;

import domain.NewsPaper;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.userAccount.id = ?1")
    User findByUserAccountId(int userAccountId);

}
