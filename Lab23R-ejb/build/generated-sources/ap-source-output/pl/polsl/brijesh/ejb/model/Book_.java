package pl.polsl.brijesh.ejb.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.brijesh.ejb.model.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-30T19:05:42")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile SingularAttribute<Book, String> author;
    public static volatile SingularAttribute<Book, String> name;
    public static volatile SingularAttribute<Book, Integer> id;
    public static volatile SingularAttribute<Book, String> type;
    public static volatile SingularAttribute<Book, User> user;

}