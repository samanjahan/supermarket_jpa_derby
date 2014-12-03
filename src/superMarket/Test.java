/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author syst3m
 */
public class Test {
    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("HW3PU2");
    public static void main(String[] args) throws Exception{
     Test t = new Test();
     Person p = new Person("Andre","123");
     EntityManager em = null;
     em = t.beginTransaction();
     em.persist(p);
     t.commitTransaction(em);
 //    em = t.beginTransaction();
    // Person pr = t.getPerson("Andre",em);
   //  System.out.println("TESTTT " + pr);
    // t.commitTransaction(em);
   
    em = t.beginTransaction();   
     float price = 200;
     
  //  Item item = new Item("Tv", price,p2);
    p.newItem("Tv", price);  
    p.deleteItem("Tv");
    
   t.commitTransaction(em);
   
  // em = t.beginTransaction();
  // p = em.merge(p);
 //  em.remove(p);
 //  t.commitTransaction(em);
   //  em = t.beginTransaction();
   // List<Item> li = em.createNamedQuery("findAllItems", Item.class).getResultList();
   //     System.out.println("Bingoo " + li.get(1).getName());
   // t.commitTransaction(em);
     //   String name = p2.getName();
     //   System.out.println(name);
    //    TypedQuery<Person> existingAccounts = em.createNamedQuery("findUser", Person.class);
              
    //    System.out.println("SAMANNNNN " + existingAccounts);
    }
   
    
  /*  public Person getPerson(String name, EntityManager em){
        return em.createQuery("SELECT owner FROM PERSON owner  WHERE: owner",Person.class).setParameter("name", name).getSingleResult();
    }*/
    
    private EntityManager beginTransaction(){
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        return em;
    }

    private void commitTransaction(EntityManager em)
    {
        em.getTransaction().commit();
    }
    
}
