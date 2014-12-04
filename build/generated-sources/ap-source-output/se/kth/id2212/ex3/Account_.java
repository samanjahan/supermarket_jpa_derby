package se.kth.id2212.ex3;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.kth.id2212.ex3.Owner;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-04T16:11:06")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Float> balance;
    public static volatile SingularAttribute<Account, Long> accountId;
    public static volatile SingularAttribute<Account, Owner> owner;
    public static volatile SingularAttribute<Account, Integer> versionNum;

}