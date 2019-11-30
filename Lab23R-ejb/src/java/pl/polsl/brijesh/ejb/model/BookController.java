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
import pl.polsl.brijesh.ejb.model.Book;

/**
 *
 * @author b___b
 */
@Stateless
@LocalBean
public class BookController {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    

    public void addBook(Book book) {
         em.persist(book);
    }
    
    
    public Book findBookById(int id) {
        return em.find(Book.class, id);
    }

  
    public List<Book> getAllBooks() {
        return em.createNamedQuery(Book.FIND_ALL_QUERY).getResultList();
    }

   
    public void updateBook(Book book) {
            em.merge(book);
    }
    
   
    public void deleteBook(Integer id){
        em.remove(findBookById(id));
    }
    
}
