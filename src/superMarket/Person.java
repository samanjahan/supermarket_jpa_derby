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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

/**
 *
 * @author syst3m
 */
@NamedQueries({
    @NamedQuery(
    name="findAllUser",
    query="SELECT name FROM Person Name "
    ),
    @NamedQuery(
            name = "findUser",
            query="SELECT Name FROM Person Name WHERE Name.name LIKE :userName")
    }
)
@Entity(name = "Person")
public class Person implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
    private List<Item> items;

    
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    
     @Column(name = "pass", nullable = false)
    private String password;

    public Long getId() {
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getPassword(){
        return password;
    }
    

    public Person(String name,String password){
        this.name = name;
        this.password = password;
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
         items.add(item);
         commitTransaction(em);          
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
    
    public boolean deleteItem(String name){
        EntityManager em = null;
        em = beginTransaction();
        Item item = getItem(name);
        if(item != null){
            item = em.merge(item);     
            em.remove(item);
            commitTransaction(em);
            return true;
        }
        return false;
    }
    
    public Item getItem(String names){
        System.out.println(names);
        for(int i = 0 ;i < items.size();++i){
            
            String nn =  items.get(0).getName().toString();
            System.out.println("Items size " + nn);
            if(nn.equals(names.toString())){
                System.out.println("hhhääääääääääääärrrrrrrrrrrrrrr");
                System.out.println(items.get(0));
                System.out.println("hooohhoo " + items.get(i));
                Item item = items.get(i);
                items.remove(i);
                return item;
            }
        }
        return null;
    }
    
    public ArrayList<Item> getALLItems(){
       EntityManager em = null;
       em = beginTransaction();
       List<Item> itemList = em.createNamedQuery("findAllItems", Item.class).getResultList();
       commitTransaction(em);
       ArrayList<Item> itm = new ArrayList<>();
       if(!itemList.isEmpty()){
           for(int i = 0; i < itemList.size(); ++i){
              itm.add(itemList.get(i));
           }
       }
       return itm;
    }
    
}
