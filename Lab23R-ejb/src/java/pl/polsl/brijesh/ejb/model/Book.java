package pl.polsl.brijesh.ejb.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Book table (entity class)
 *
 * @author Brijesh Varsani
 * @version 1.0
 */
@NamedQueries({
    @NamedQuery(name = "Book.findAllBooks",
            query = "SELECT b FROM Book b")
})
@Entity
@Table(name = "books")
public class Book implements Serializable {

    /*
    String containing name of the named query
    */
    public static String FIND_ALL_QUERY = "Book.findAllBooks";

    /**
     * Primary key value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Book name Column
     */
    @Column(name = "name")
    private String name;

    /**
     * Book Type Column
     */
    @Column(name = "type")
    private String type;

    /**
     * Book author column
     */
    @Column(name = "author")
    private String author;

    /**
     * User's id column
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    /**
     * Default Constructor
     */
    public Book() {
    }

    /**
     * Constructor with para
     * 
     * @param bookName book name
     * @param bookType book type
     * @param bookAuthor book author
     * @param user user object
     */
    public Book(String bookName, String bookType, String bookAuthor,User user) {
        this.name = bookName;
        this.author = bookAuthor;
        this.type = bookType;
        this.user = user;
    }

    /**
     * Book user getter
     *
     * @return User
     */
    public User getUser() {
        if (user != null) {
            return user;
        } else {
            return new User();
        }
    }

    /**
     * Book user setter
     *
     * @param user New User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * getter book name
     *
     * @return String book name
     */
    public String getName() {
        return name;
    }

    /**
     * set book's name
     *
     * @param name of the book
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter Type of the book
     *
     * @return String book's type
     */
    public String getType() {
        return type;
    }

    /**
     * setter type of the book
     *
     * @param type of the book
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter author of the book
     *
     * @return String book's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * setter author of the book
     *
     * @param author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * getter id of book
     *
     * @return Integer id of book
     */
    public Integer getId() {
        return id;
    }

    /**
     * setter id of the book
     *
     * @param id of the book
     */
    public void setId(Integer id) {
        this.id = id;
    }

     /**
     * override of base hashCode() method
     * @return int hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * override to base equals() method
     *
     * @param object compare object
     * @return true or false of object
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Override of toString() method
     *
     * @return String containing all the fields
     */
    @Override
    public String toString() {

        return "Id = " + id + "\n"
                + "Book Name: " + name + "\n"
                + "Book Author: " + author + "\n"
                + "Book type: " + type + "\n"
                + "User id: " + user + "\n";
    }

}
