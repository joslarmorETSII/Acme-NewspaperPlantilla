package repositories;

import domain.Customer;
import domain.NewsPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where c.userAccount.id = ?1")
    Customer findByCustomerAccountId(int customerAccountId);

    @Query("select s.newsPaper from SubscribeNewsPaper s where s.customer.id=?1")
    Collection<NewsPaper> newsPapersSubscribed(int custometId);

}

