package pl.polsl.brijesh.ejb.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Bean for performing operations on User table
 *
 * @author Brijesh Varsani
 * @version 1.0
 */
@Stateless
@LocalBean
public class UserBean {

    /**
     * Entity manager, perform operation
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Finding a User with id
     *
     * @param id id of user to look
     * @return User Object
     */
    public User findUserById(int id) {
        return em.find(User.class, id);
    }

    /**
     * Adding User to table
     *
     * @param user object to add
     */
    public void addUser(User user) {
        em.persist(user);
    }

    /**
     * Find all User
     *
     * @return list of user
     */
    public List<User> getAllUsers() {
        return em.createNamedQuery(User.FIND_ALL_USER_QUERY).getResultList();
    }

    /**
     * Update User
     *
     * @param user object to update
     */
    public void updateUser(User user) {
        em.merge(user);
    }

    /**
     * Delete user with by id
     *
     * @param id to be delete
     */
    public void deleteUser(Integer id) {
        em.remove(findUserById(id));
    }
}
