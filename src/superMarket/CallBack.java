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
public interface CallBack extends Remote {
	public void notifyMe(String userName, String itemName) throws RemoteException;
}

