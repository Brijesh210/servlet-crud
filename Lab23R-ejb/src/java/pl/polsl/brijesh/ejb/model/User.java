/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.brijesh.ejb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User table (entity class)
 *
 * @author Brijesh Varsani
 * @version 1.0
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findAllUsers",
            query = "SELECT u FROM User u")
})
public class User implements Serializable {

    public static String FIND_ALL_USER_QUERY = "User.findAllUsers";
    /**
     * Primary key value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * User name column
     */
    @Column(name = "name")
    private String name;

    /**
     * User address column
     */
    @Column(name = "address")
    private String address;

    /**
     * List of all books (One to Many)
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public User(String userName, String userAddress) {
        this.name =userName;
        this.address = userAddress;
    }

    public User() {
    }

  

    /**
     * get address of user
     *
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * set name for user
     *
     * @param name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get address of the user
     *
     * @return Address of user
     */
    public String getAddress() {
        return address;
    }

    /**
     * set address of the user
     *
     * @param address of user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * id getter
     *
     * @return id of the user
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set an id of user
     *
     * @param id set id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * override to base hashCode() method
     *
     * @return int of hash code
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
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
        return "id = " + this.id + " \n"
                + "user name = " + this.name + " \n"
                + "user address = " + this.address + " \n"
                + "";
    }

}