/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailserver;

import java.rmi.RemoteException;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class Main {

    public static void main(String[] args) throws RemoteException
    {
            MailServer server = new MailServer();
    }
    
}
