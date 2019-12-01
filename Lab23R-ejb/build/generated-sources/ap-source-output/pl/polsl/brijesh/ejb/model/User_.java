package pl.polsl.brijesh.ejb.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.brijesh.ejb.model.Book;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-30T20:12:37")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> address;
    public static volatile ListAttribute<User, Book> books;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, Integer> id;

}