package hr.project.userstoriesblog.service;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);

    User getUserByCredentials(String username, String password);

    User getUserByUserName(String username);

}
