/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author syst3m
 */
public class Server {
    private static final String USAGE = "java marketrmi.Server <superMarket_rmi_url>";
	private static final String MARKETPLACE = "Blocket";
	public Server(String marketPlaceName) {
		try {
			MarketPlace marketImpl = new MarketPlaceImpl();
			// Register the newly created object at rmiregistry.
			try {
				LocateRegistry.getRegistry(1099).list();
			} catch (RemoteException e) {
				LocateRegistry.createRegistry(1099);
			}
			Naming.rebind(marketPlaceName, marketImpl);
			System.out.println(marketImpl + " is ready.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if (args.length > 1
				|| (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
			System.out.println(USAGE);
			System.exit(1);
		}
		String bankName;
		if (args.length > 0) {
			bankName = args[0];
		} else {
			bankName = MARKETPLACE;
		}
		new Server(bankName);
	}
    
}
