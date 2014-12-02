package superMarket;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import superMarket.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-02T15:48:44")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Long> id;
    public static volatile SingularAttribute<Item, Float> price;
    public static volatile SingularAttribute<Item, String> name;
    public static volatile SingularAttribute<Item, Person> owner;

}