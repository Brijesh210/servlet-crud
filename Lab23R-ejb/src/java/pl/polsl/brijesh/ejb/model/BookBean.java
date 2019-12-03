package pl.polsl.brijesh.ejb.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Bean for performing operations on book table
 *
 * @author Brijesh Varsani
 * @version 1.0
 */
@Stateless
@LocalBean
public class BookBean {

    /**
     * Entity manager, perform operation
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Adding Book to table
     *
     * @param book object to add
     */
    public void addBook(Book book) {
        em.persist(book);
    }

    /**
     * Finding a Book with id
     *
     * @param id id of book to look
     * @return Book Object
     */
    public Book findBookById(int id) {
        return em.find(Book.class, id);
    }

     /**
     * Find all Books
     *
     * @return list of books
     */
    public List<Book> getAllBooks() {
        return em.createNamedQuery(Book.FIND_ALL_QUERY).getResultList();
    }
    
     /**
     * Update Book
     *
     * @param book object to update
     */
    public void updateBook(Book book) {
        em.merge(book);
    }

    /**
     * Delete Book
     * @param id book to be deleted 
     */
    public void deleteBook(Integer id) {
        em.remove(findBookById(id));
    }

}
