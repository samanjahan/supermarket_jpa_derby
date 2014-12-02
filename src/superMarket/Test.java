/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author syst3m
 */
public class Test {
    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("HW3PU2");
    public static void main(String[] args) throws Exception{
     Test t = new Test();
     Person p = new Person("Andre");
     Person p2 = new Person("alex");
     Person p3 = new Person("Saman");
     EntityManager em = null;
     em = t.beginTransaction();
     em.persist(p);
     em.persist(p2);
     em.persist(p3);
     t.commitTransaction(em);
     em = t.beginTransaction();
    // Person pr = t.getPerson("Andre",em);
   //  System.out.println("TESTTT " + pr);
    // t.commitTransaction(em);
   
   // em = t.beginTransaction();   
     float price = 200;
     
  //  Item item = new Item("Tv", price,p2);
    p2.newItem("Tv", price);
    p2.newItem("Tv", price);
    p.newItem("Tv", price);
    
    p.deleteItem("Tv");
    
   // em.persist(p2);
   //  em.persist(item);
   t.commitTransaction(em);

          
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
