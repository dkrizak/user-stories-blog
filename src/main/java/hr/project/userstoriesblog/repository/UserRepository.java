package hr.project.userstoriesblog.repository;

import hr.project.userstoriesblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE user_name = ?1 AND password = ?2", nativeQuery = true)
    User getUserByUserNameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM users WHERE user_name = ?1", nativeQuery = true)
    User findUserByUserName(String username);

}
