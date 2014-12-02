/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Persistence;

/**
 *
 * @author syst3m
 */
@Entity(name = "Person")
public class Person implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="owner")
    private List<Item> items;

    
    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }
    

    public Person(String name){
        this.name = name;
    }
    
     public Person(){
        items = new ArrayList<Item>();
    }
    

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "superMarket.Person[ id=" + id + " ]";
    }
    
    public void newItem(String name,float price) throws RejectedException{
         EntityManager em = null;
         em = beginTransaction();
         Item item = new Item(name, price, Person.this);
         em.persist(item);
         commitTransaction(em);
         items.add(item);
            
    }
    
    
    
    private EntityManager beginTransaction(){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("HW3PU2");

        EntityManager em = emFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        return em;
    }

    private void commitTransaction(EntityManager em){
        em.getTransaction().commit();
    }
    
    public void deleteItem(String name){
        EntityManager em = null;
        em = beginTransaction();
        Item item = getItem(name);
        if(item != null){
            item = em.merge(item);
           // em.merge(item);
            em.remove(item);
            commitTransaction(em);
        }
        
    }
    
    public Item getItem(String name){
        for(int i = 0 ;i < items.size();++i){
            if(items.get(i).getName().equals(name)){
                System.out.println("hooohhoo " + items.get(i));
                return items.get(i);
            }
        }
        return null;
    }
    
}
