package mailserver;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
/**
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class MailServer extends UnicastRemoteObject implements Server
{
    private static LinkedList<Account> accounts;
    private static Log log;
    private int countReport;

    public MailServer() throws RemoteException
    {
        super();
        countReport = 0;
        accounts = new LinkedList();
        log = new Log();
        
        newRMIRegistry();
        
        try{
            Naming.rebind("//localhost/MailServer", this);
        }catch(MalformedURLException e){}
            
        System.out.println("stub bound in registry");
        writeLog("stub bound in registry");
        
        try {
            //create a MailServer object in RMI regystry called MailServer
            Naming.rebind("//localhost/MailServer", this);
            System.out.println("Server: " + this + " bound in registry");
            writeLog("Server: " + this + " bound in registry");
        }catch(MalformedURLException e){
            System.out.println("URL error:");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("Error:");
            e.printStackTrace();
        }
        
        
        //add valid known accounts to account list
        accounts.add(
                new Account(
                        "matteo.dilucchio@edu.unito.it",
                        new LinkedList<Mail>(),
                        new LinkedList<Mail>()
                )
        );
        System.out.println("Account added: matteo.dilucchio@edu.unito.it");
        writeLog("Account added: matteo.dilucchio@edu.unito.it");
        
        
        accounts.add(
                new Account(
                        "mastodilu@outlook.it",
                        new LinkedList<Mail>(),
                        new LinkedList<Mail>()
                )
        );
        System.out.println("Account added: mastodilu@outlook.it");
        writeLog("Account added: mastodilu@outlook.it");
        
        accounts.add(
                new Account(
                        "dilu.93@hotmail.it",
                        new LinkedList<Mail>(),
                        new LinkedList<Mail>()
                )
        );
        System.out.println("Account added: dilu.93@hotmail.it");
        writeLog("Account added: dilu.93@hotmail.it");
        
        
        
        
        new Thread(){
            public void run()
            {
                while(true){
                    try{
                        sleep(30 * 1000);
                        reportMsgCount();
                    }catch(InterruptedException e){}
                }
            }
        }.start();
        
        
        
    } //end of main(..) method
    
    
    private synchronized void reportMsgCount()
    {
        countReport++;
        String s = "REPORT n° " + countReport;
        
        for(Account acc : accounts){
            s += "\n -- " + acc.getAddress();
            s += " - [IN  " + acc.getReceived().size();
            s += "; OUT " + acc.getSent().size() + "]";
        }
        
        writeLog(s);
    }
    
    
    
    
    public void send(Mail mail)
    {
//        System.out.println("called MailServer.send");
        
        new Thread(){
                public synchronized void run()
                {
                    boolean flag = false;
                    LinkedList<String> dest = new LinkedList();//valid addresses found in server list
                    
                    //for each destination address in server known addresses --> send
                    for(String receiverAddress : mail.getReceivers()) {//for each destination account
                        for(Account acc : accounts) {
                            if(receiverAddress.equals(acc.getAddress())) {
                                int index = accounts.indexOf(acc);
                                accounts.get(index).addToReceived(mail);//write new email in inbox
                                accounts.get(index).setNewReceived(true);
                                dest.add(receiverAddress);
                                flag = true;
                            }
                        }     
                    }
                    mail.setReceivers(dest);//mail sent to these addresses

                    //add email to sent list
                    int index = 0;
                    for(Account acc : accounts) {
                        if( acc.getAddress().equals(mail.getSender()) ) {
                            index = accounts.indexOf(acc);
                            if(flag == true){
                                accounts.get(index).addToSent(mail);
                                accounts.get(index).setNewSent(true);
                                System.out.println("Mail added to sent list for " + mail.getSender() + ", sent to " + dest.size() + " accounts");
                                writeLog("Mail added to sent list for " + mail.getSender() + ", sent to " + dest.size() + " accounts");
                            }
                            accounts.get(index).writeMsg("Mail sent to " + dest.size() + " addresses");
                        }
                    }
                }//end of run
        }.start();
    } //end of send(..) method
    
    
    
    public synchronized LinkedList<Mail> receive(String address) throws RemoteException
    {    
        LinkedList<Mail> list = new LinkedList();
        int index = -1;
        for(Account acc:accounts){
            if(acc.getAddress().equals(address)){
                index = accounts.indexOf(acc);
                System.out.println("Retrieving inbox for account " + address);
                writeLog("Retrieving inbox for account " + address);
                list = accounts.get(index).getReceived();
                accounts.get(index).setNewReceived(false);
            }
        }
        return list;//return new linked list to avoid errors when it doesn't find the right email address in accounts
    }
    
    
    private static void newRMIRegistry()
    {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created");
        }
        catch(RemoteException e) {
            //do nothing
            System.out.println("java RMI registry already existing");
        }
    }
    
    
    public synchronized LinkedList<Mail> getSentList(String address)
    {
        LinkedList<Mail> list = new LinkedList();
        for(Account acc : accounts){
            if(acc.getAddress().equals(address)){
                int index = accounts.indexOf(acc);
                System.out.println("Retrieving sent list for account " + acc.getAddress());
                writeLog("Retrieving sent list for account " + acc.getAddress());
                list = acc.getSent();
                accounts.get(index).setNewSent(false);
            }
        }
        return list;
    }
    
    
    
    public synchronized boolean deleteFromInbox(Mail mail, String address)
    {
        int indexAcc, indexMail, initialSize, finalSize;
        initialSize = -1;
        finalSize = -1;
        indexMail = 0;
        
        indexAcc = 0;
        while(!accounts.get(indexAcc).getAddress().equals(address)){
            indexAcc++;
        }
//        System.out.println("Deleting one email for user " + accounts.get(indexAcc).getAddress());
        
        initialSize = accounts.get(indexAcc).getReceived().size();
        
        indexMail = 0;
        while(!accounts.get(indexAcc).getReceived().get(indexMail).equals(mail)){
            indexMail++;
        }
        System.out.println("Deleting email from inbox " + accounts.get(indexAcc).getReceived().get(indexMail).toString() + "for user " + accounts.get(indexAcc).getAddress());
        writeLog("Deleting email from inbox " + accounts.get(indexAcc).getReceived().get(indexMail).toString() + "for user " + accounts.get(indexAcc).getAddress());
        
        accounts.get(indexAcc).getReceived().remove(indexMail);
        
        finalSize = accounts.get(indexAcc).getReceived().size();
        
        return finalSize == (initialSize - 1);
    }
    
    
    public synchronized boolean deleteFromSentList(Mail mail, String address)
    {        
        int indexAcc, indexMail, initialSize, finalSize;
        initialSize = -1;
        finalSize = -1;
        indexMail = 0;
        
        indexAcc = 0;
        while(!accounts.get(indexAcc).getAddress().equals(address)){
            indexAcc++;
        }
        
        initialSize = accounts.get(indexAcc).getSent().size();
        
        indexMail = 0;
        while(!accounts.get(indexAcc).getSent().get(indexMail).equals(mail)){
            indexMail++;
        }
        System.out.println("Deleting email from sent list" + accounts.get(indexAcc).getSent().get(indexMail).toString() + "for user " + accounts.get(indexAcc).getAddress());
        writeLog("Deleting email from sent list" + accounts.get(indexAcc).getSent().get(indexMail).toString() + "for user " + accounts.get(indexAcc).getAddress());
        accounts.get(indexAcc).getSent().remove(indexMail);
        
        finalSize = accounts.get(indexAcc).getSent().size();
        
        return finalSize == (initialSize - 1);
    }
    
    
    
    
    public synchronized boolean getNewSent(String address)
    {
        boolean flag = false;
        
        for(Account acc : accounts){
            if(acc.getAddress().equals(address)){
                int index = accounts.indexOf(acc);
                flag = accounts.get(index).getNewSent();
            }
        }
        
        return flag;
    }
    
    
    public synchronized boolean getNewInbox(String address)
    {
        boolean flag = false;
        
        for(Account acc : accounts){
            if(acc.getAddress().equals(address)){
                int index = accounts.indexOf(acc);
                flag = accounts.get(index).getNewReceived();
            }
        }
        
        return flag;
    }
    
    
    public boolean getNewMsg(String address){
        int i = 0;
        while(!accounts.get(i).getAddress().equals(address) && i < accounts.size()){
            i++;
        }
        System.out.println("Account " + accounts.get(i).getAddress() + " - newMsg " + accounts.get(i).getNewMsg());
        return accounts.get(i).getNewMsg();
    }

    
    public String getMsg(String address){
        int i = 0;
        while(!accounts.get(i).getAddress().equals(address) && i < accounts.size()){
            i++;
        }
        System.out.println("Account " + accounts.get(i).getAddress() + " - msg " + accounts.get(i).getMsg());
        return accounts.get(i).readMsg();
    }
    
    
    
    private void writeLog(String msg){
        log.writeLog(msg);
    }
}
