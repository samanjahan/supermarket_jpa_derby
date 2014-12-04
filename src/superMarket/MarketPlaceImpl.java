/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private  CallBack client;
    private Map<String,CallBack> wisheList = new HashMap<String, CallBack>();

    
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
        commitTransaction(em);
        return false;
    }

    @Override
    public boolean deletePerson(String name) throws RemoteException {
        System.out.println("personn " + person.getName());
        person.deleteItem(name);
        return true;
    }

    @Override
    public boolean addItem(String name, float price,String clientName) throws RemoteException {
        person = getUser(clientName);
        if(person != null){
            try {
                person.newItem(name, price);
                chechWish();
                return true;
            } catch (RejectedException ex) {
                Logger.getLogger(MarketPlaceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return false;
    }

    @Override
    public boolean deleteItem(String itemName, String clientName) throws RemoteException {
        person = getUser(clientName);
        System.out.println("Item name MarketPalce " + itemName);
        System.out.println("personn Name MarketPlace " + person.getName());
       if((person.deleteItem(itemName))){
           System.out.println("TRUUUEEEEEEEEE");
           return true;
       }
        System.out.println("FALSSSEEEEEEEE");
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
                    String personName = li.get(i).getName();
                    person = getUser(personName);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean logging(String name, String password) throws RemoteException {
        if(checkLogging(name,password)){
            chechWish();
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
                st.append("Name: " + li.get(i).getName() + " " + "price: " + String.valueOf(li.get(i).getPrice())  + " " + "userName: " +li.get(i).getPersonName() + "-");
            }
        }
        return st.toString();
    }

    @Override
    public void wish(String name, String price,CallBack client) throws RemoteException {
        String item = name + " " + price;
        wisheList.put(item , client);
    }

    @Override
    public void chechWish() throws RemoteException {
        ArrayList<Item> itemList = person.getALLItems();
        if(!wisheList.isEmpty() && !itemList.isEmpty()){
            for(String keyItem : wisheList.keySet()){
                client  = wisheList.get(keyItem);
                String[] itemNameList = keyItem.split(" ");
                String itemName = itemNameList[0].toString();
                
                for(int i = 0 ; i < itemList.size();++i){
                    Float price = Float.valueOf(itemNameList[1]);
                    if(itemList.get(i).getName().equals(itemName) && itemList.get(i).getPrice() <= price){
                        wisheList.remove(keyItem);
                        client.notifyMe(itemList.get(i).getPersonName(), itemName);
                    }
                }
                
            }
        }
    }
}
