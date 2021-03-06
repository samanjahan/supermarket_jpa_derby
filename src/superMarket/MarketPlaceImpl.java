/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import se.kth.id2212.ex3.Account;


public class MarketPlaceImpl extends UnicastRemoteObject implements MarketPlace {
	private static final long serialVersionUID = 3966447356658262847L;
	private EntityManagerFactory emFactory;
	private Person person;
	private CallBack client;
	private EntityManager em = null;
	private Map<String, CallBack> wishList = new HashMap<String, CallBack>();
        private String clientWishName;
	public MarketPlaceImpl() throws RemoteException {
		super();
	}

	@Override
	public Person createPerson(String name, String password)
			throws RemoteException {
		em = beginTransaction();
		if (!checkUser(name) && password.length() > 7) {
			person = new Person(name, password);
			em.persist(person);
			commitTransaction(em);
		}
		// commitTransaction(em);
		return person;
	}

	@Override
	public boolean addItem(String name, float price) throws RemoteException {
		boolean rv = false;
		em = beginTransaction();
		Item item = new Item(name, price, person);
		em.persist(item);
		commitTransaction(em);
		if (em.find(Item.class, item.getId()) != null) {
			rv = true;
		}
		checkWish();
		return rv;
	}

	@Override
	public boolean deleteItem(String itemName) throws RemoteException {
		boolean rv = false;

		em = beginTransaction();
		List<Item> li = em.createNamedQuery("findItemsByUser", Item.class)
				.setParameter("owner", person).getResultList();
		for (Item i : li) {
			if (i.getName().equalsIgnoreCase(itemName)) {
				em.remove(i);
				rv = true;
			}
		}
		commitTransaction(em);

		return rv;
	}

	private EntityManager beginTransaction() {
		emFactory = Persistence.createEntityManagerFactory("HW3Market");
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		return em;
	}

	private void commitTransaction(EntityManager em) {
		em.getTransaction().commit();
	}

	public boolean checkUser(String name) {
		// em = beginTransaction();
		List<Person> liUser = em.createNamedQuery("findAllUser", Person.class)
				.getResultList();
		// commitTransaction(em);
		if (!liUser.isEmpty()) {
			for (int i = 0; i < liUser.size(); ++i) {
				if (liUser.get(i).getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public Person getUser(String name) {
		Person findName = null;
		// em = beginTransaction();
		List<Person> existingUsers = em
				.createNamedQuery("findUser", Person.class)
				.setParameter("userName", name).getResultList();
		// commitTransaction(em);
		if (!existingUsers.isEmpty()) {
			for (int i = 0; i < existingUsers.size(); ++i) {
				if (existingUsers.get(i).getName().equals(name)) {
					findName = existingUsers.get(i);
					return findName;
				}
			}
		}
		return findName;
	}

	public Person checkLogin(String name, String password)
			throws RemoteException {
		em = beginTransaction();
		List<Person> li = em.createNamedQuery("findAllUser", Person.class)
				.getResultList();
		commitTransaction(em);
		if (!li.isEmpty()) {
			for (int i = 0; i < li.size(); ++i) {
				if (li.get(i).getPassword().equals(password)
						&& li.get(i).getName().equals(name)) {
					person = li.get(i);
				}
			}
		}
		checkWish();
		return person;
	}

	@Override
	public boolean buyItem(String item, Person buyer, String seller)
			throws RemoteException {
		boolean rv = false;
		Person person = getUser(seller);
		em = beginTransaction();
		List<Item> li = em.createNamedQuery("findItemsByUser", Item.class)
				.setParameter("owner", person).getResultList();
		for (Item i : li) {
			if (i.getName().equalsIgnoreCase(item)) {

				EntityManagerFactory emFactory2 = Persistence
						.createEntityManagerFactory("HW3Bank");
				EntityManager em2 = emFactory2.createEntityManager();
				EntityTransaction transaction2 = em2.getTransaction();
				transaction2.begin();

				Account sellerAccount = null;
				Account buyerAccount = null;
				try {
					sellerAccount = em2
							.createNamedQuery("findAccountWithName",
									Account.class)
							.setParameter("ownerName", seller)
							.getSingleResult();
					buyerAccount = em2
							.createNamedQuery("findAccountWithName",
									Account.class)
							.setParameter("ownerName", buyer.getName())
							.getSingleResult();
					if (i.getPrice() <= buyerAccount.getBalance()) {
						buyerAccount.withdraw(i.getPrice());
						sellerAccount.deposit(i.getPrice());
						//em.remove(i);
						em.createNamedQuery("deleteItem",Item.class).setParameter("item",
						i).executeUpdate();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				commitTransaction(em2);
				rv = true;
			}

		}
		commitTransaction(em);

		return rv;
	}

	@Override
	public List<Item> listAllItem() throws RemoteException {

		em = beginTransaction();
		List<Item> itemList = em.createNamedQuery("findAllItems", Item.class)
				.getResultList();
		commitTransaction(em);

		return itemList;
	}

	@Override
	public void wish(String name, String price, String clientName,CallBack client)
			throws RemoteException {
		String item = name + " " + price + " " + clientName ;
                clientWishName = clientName;
		wishList.put(item, client);
	}

	@Override
	public void checkWish() throws RemoteException {
		List<Item> itemList = listAllItem();
		if (!wishList.isEmpty() && !itemList.isEmpty()) {
			for (String keyItem : wishList.keySet()) {
				client = wishList.get(keyItem);
				String[] itemNameList = keyItem.split(" ");
				String itemName = itemNameList[0].toString();
                                String wishClient = itemNameList[2].toString();
				for (int i = 0; i < itemList.size(); ++i) {
					Float price = Float.valueOf(itemNameList[1]);
					if (itemList.get(i).getName().equals(itemName) && itemList.get(i).getPrice() <= price && clientWishName.equals(wishClient)) {
						wishList.remove(keyItem);
						client.notifyMe(itemList.get(i).getPersonName(),
								itemName);
					}
				}
			}
		}
	}

}