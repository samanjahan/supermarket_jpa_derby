/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    public boolean creatPerson(String name, String password) throws RemoteException {
        em = beginTransaction();
        if(!checkUser(name) ){//&& //password.length() > 7//){      
            person = new Person(name,password);
            em.persist(person);
            commitTransaction(em);
            return true;
        }
       // commitTransaction(em);
        return false;
    }

    @Override
    public boolean deletePerson(String name) throws RemoteException {
        person.deleteItem(name);
        return true;
    }

    @Override
    public boolean addItem(String name, float price,String clientName) throws RemoteException {
        Person person = getUser(clientName);
        if(person != null){
            try {
                person.newItem(name, price);
                return true;
            } catch (RejectedException ex) {
                Logger.getLogger(MarketPlaceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
    public boolean checkUser(String name){
      //  em = beginTransaction();
        List<Person> liUser = em.createNamedQuery("findAllUser", Person.class).getResultList();
      //  commitTransaction(em);
        if(!liUser.isEmpty()){
            for(int i = 0 ; i < liUser.size(); ++i){
                if(liUser.get(i).getName().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Person getUser(String name){
        Person findName = null;
      //  em = beginTransaction();
        List<Person> existingUsers = em.createNamedQuery("findUser", Person.class).
            setParameter("userName", name).getResultList();
      //   commitTransaction(em);
        if(!existingUsers.isEmpty()){
            for(int i = 0 ; i < existingUsers.size(); ++i){
                if(existingUsers.get(i).getName().equals(name)){
                    findName = existingUsers.get(i);
                   
                    return findName;
                }
            }
        }
        return findName;
    }
    
    public boolean checkLogging(String name,String password){
        em = beginTransaction();
        List<Person> li = em.createNamedQuery("findAllUser", Person.class).getResultList();
        System.out.println(li.size());
        commitTransaction(em);
        if(!li.isEmpty()){
            for(int i = 0 ; i < li.size(); ++i){
                if(li.get(i).getPassword().equals(password) && li.get(i).getName().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean logging(String name, String password) throws RemoteException {
        if(checkLogging(name,password)){
            return  true;
        }

        return false;
    }

    @Override
    public String listAllItem(String clientName) throws RemoteException{
        Person person = getUser(clientName);
        ArrayList<Item> li = person.getALLItems();
        StringBuilder st = new StringBuilder();
        if(!li.isEmpty()){
            for(int i = 0 ; i < li.size(); ++i){
                st.append(li.get(i).getName() + " ");
            }
        }
        return st.toString();
    }
}
