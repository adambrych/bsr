package bsr.dao;


import bsr.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for users table
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {
    /**
     * Get user using his login check password
     * @param login
     * @param password
     * @return
     */
    User findByLoginAndPassword(String login, String password);

    /**
     * Get user using his login
     * @param login
     * @return
     */
    User findByLogin(String login);
}
