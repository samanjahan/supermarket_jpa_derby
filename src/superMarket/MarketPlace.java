/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author syst3m
 */
public interface MarketPlace extends Remote {
    public boolean creatPerson(String name, String password) throws RemoteException;
    public boolean deletePerson(String name) throws RemoteException;
    public boolean addItem(String name,float price,String clientName) throws RemoteException;
    public boolean deleteItem(String name,String clientName) throws RemoteException;
    public boolean logging (String name, String password) throws RemoteException;
    public String listAllItem(String clientName) throws RemoteException;
    public void wish (String name , String price,String clientName,CallBack client) throws RemoteException;
    public void chechWish() throws RemoteException;
}
