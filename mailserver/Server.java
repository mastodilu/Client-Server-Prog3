package mailserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public interface Server extends Remote
{
    void send(Mail Mail) throws RemoteException;
    LinkedList<Mail> receive(String address) throws RemoteException;
    LinkedList<Mail> getSentList(String address) throws RemoteException;
    boolean deleteFromInbox(Mail mail, String address) throws RemoteException;
    boolean deleteFromSentList(Mail mail, String address) throws RemoteException;
    boolean getNewSent(String address) throws RemoteException;
    boolean getNewInbox(String address) throws RemoteException;
    boolean getNewMsg(String address) throws RemoteException;
    String getMsg(String address) throws RemoteException;
}
