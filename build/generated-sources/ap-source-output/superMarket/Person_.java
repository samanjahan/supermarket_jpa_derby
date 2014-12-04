package superMarket;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import superMarket.Item;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-04T16:11:06")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, Long> id;
    public static volatile ListAttribute<Person, Item> items;
    public static volatile SingularAttribute<Person, String> name;
    public static volatile SingularAttribute<Person, String> password;

}