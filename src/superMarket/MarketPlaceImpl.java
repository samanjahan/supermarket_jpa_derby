/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author syst3m
 */
public class MarketPlaceImpl extends UnicastRemoteObject implements MarketPlace{
    private EntityManagerFactory emFactory;
    private Person person;
    private Item item;
   private EntityManager em = null;
    
    public MarketPlaceImpl() throws RemoteException{
        super();
    }

    @Override
    public Person creatPerson(String name, String password) throws RemoteException {
        em = beginTransaction();
        person = new Person(name,password);
        em.persist(person);
        commitTransaction(em);
        return person;
    }

    @Override
    public boolean deletePerson(String name) throws RemoteException {
        person.deleteItem(name);
        return true;
    }

    @Override
    public boolean addItem(String name, float price) throws RemoteException {
        try {
            person.newItem(name, price);
            return true;
        } catch (RejectedException ex) {
            Logger.getLogger(MarketPlaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deletItem(String name) throws RemoteException {
       if((person.deleteItem(name))){
           return true;
       }
       return false;
    }
    
    private EntityManager beginTransaction(){
        emFactory = Persistence.createEntityManagerFactory("HW3PU2");
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        return em;
    }

    private void commitTransaction(EntityManager em){
        em.getTransaction().commit();
    }
    
    
}
