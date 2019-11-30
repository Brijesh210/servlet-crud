/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.brijesh.ejb.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.brijesh.ejb.model.User;

/**
 *
 * @author b___b
 */
@Stateless
@LocalBean
public class UserController {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;

    public User findUserById(int id) {
        return em.find(User.class, id);
    }

    public void addUser(User user) {
        em.persist(user);
    }

    public List<User> getAllUsers() {
        return em.createNamedQuery(User.FIND_ALL_USER_QUERY).getResultList();
    }

    public void updateuser(User user) {
        em.merge(user);
    }

    public void deleteUser(Integer id) {
        em.remove(findUserById(id));
    }
}
