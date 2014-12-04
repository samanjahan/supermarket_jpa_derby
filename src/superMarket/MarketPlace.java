/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MarketPlace extends Remote {
	public Person createPerson(String name, String password)
			throws RemoteException;

	// public boolean deletePerson(String name) throws RemoteException;
	public boolean addItem(String name, float price) throws RemoteException;

	public boolean deleteItem(String itemName) throws RemoteException;

	public Person checkLogin(String name, String password)
			throws RemoteException;

	public List<Item> listAllItem() throws RemoteException;

	public boolean buyItem(String item, Person buyer, String seller)
			throws RemoteException;

	public void wish(String name, String price, String ClientName,CallBack client)
			throws RemoteException;

	public void checkWish() throws RemoteException;
}