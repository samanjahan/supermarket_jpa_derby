/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author syst3m
 */
public class Client {
    static MarketPlace marketPlace;
    private static final String DEFAULT_MarketPlace_NAME = "Blocket";
    private static boolean  isConnected = false;
    private static List <String> listWord = new ArrayList<String>();
    private static String clientname;
    private static String password;
    private static String itemName;
    private static String itemPrice;
    
    static enum CommandName {
        newUser, deleteUser, logging,logOut,showAllItem, addItem, deleteItem, quit, help, listAllMarket, wish,buy,showMyItem;
    };
    private static int getCommand(String userInput){
        if(userInput.equals("newUser")){
            clientname = listWord.get(1);
            password = listWord.get(2);
            return 1;
        }
        if(userInput.equals("addItem")){
            itemName = listWord.get(1);
            itemPrice = listWord.get(2);
            return 2;
        }
        if(userInput.equals("deleteItem")){
            itemName = listWord.get(1);
            return 3;
        }
        if(userInput.equals("logOut")){
            clientname = null;
            return 4;
        }
        if(userInput.equals("logging")){
            clientname = listWord.get(1);
            password = listWord.get(2);
            return 5;
        }
        if(userInput.equals("showAllItem")){
            return 6;
        }
        return 0;
    }
    public static void main(String[] args) throws IOException {
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            marketPlace = (MarketPlace) Naming.lookup(DEFAULT_MarketPlace_NAME);
            } catch (Exception e) {
                System.out.println("The runtime failed: " + e.getMessage());
                System.exit(0);
            }
        isConnected = true;
        System.out.println("Connected to SuperMarket: " + " Blocket");
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        for (CommandName commandName : CommandName.values()) {
            System.out.println(commandName);
        }
        StringTokenizer st2;
        while (isConnected) {
	    String inputClient = consoleIn.readLine();
            if(inputClient !=null){
                st2 = new StringTokenizer(inputClient, " ");
                while(st2.hasMoreElements()){
                        String test = (String) st2.nextElement();
                        listWord.add(test);
                }
                if(clientname == null){
                    clientname = listWord.get(1).toString();
                }
            }
            int getCommend  = getCommand(listWord.get(0));
            switch (getCommend){
                case 1:
                    if(listWord.size() > 1){
                        if(marketPlace.creatPerson(clientname,password)){
                            System.out.println("Wellcome " + clientname);
                        }else{
                            System.out.println(clientname + " already exists!");
                        }
                    }
                    listWord.clear();
                    break;
                case 2:
                    marketPlace.addItem(itemName,Float.parseFloat(itemPrice),clientname);
                    System.out.println("succeeded!");
                    listWord.clear();
                    break;
                case 3:
                    if(marketPlace.deletItem(itemName)){
                        System.out.println("succeeded!");
                    }
                    listWord.clear();
                    break;
                case 4:
                    System.out.println("You have successfully logged out!");
                    listWord.clear();
                    break;
                case 5:
                    if(marketPlace.logging(clientname, password)){
                        System.out.println("Wellcome " + clientname);
                        listWord.clear();
                    }else{
                        System.out.println("SOOORRRYYYYY");
                        clientname = null;
                        listWord.clear();
                    }
                    break;
                case 6:
                  //  marketPlace.listAllItem(clientname);
                    String items = marketPlace.listAllItem(clientname);
                    StringTokenizer sto;
                    st2 = new StringTokenizer(items, " ");
                    while(st2.hasMoreElements()){
                        System.out.println(st2.nextElement());
                    }
                    listWord.clear();
                    break;
            }
        }
    }
}