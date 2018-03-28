/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Observable;
import mailserver.Mail;
import mailserver.Server;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class Model extends Observable
{
    private LinkedList<Mail> inbox;
    private LinkedList<Mail> sent;
    private boolean newInbox, newSent, newMsg;
    private String messageToDisplay;
    private boolean orderByDate, orderByPriority, locked;
    
    public Model()
    {
        inbox = new LinkedList();
        sent = new LinkedList();
        messageToDisplay = "";
        newInbox = false;
        newSent = false;
        newMsg = false;
        orderByDate = true;
        orderByPriority = false;
        locked = false;
    }

    public synchronized LinkedList<Mail> getInbox()
    {
        System.out.println("Called Model getInbox");
        return inbox;
    }

    public synchronized LinkedList<Mail> getSent()
    {
        System.out.println("Called Model getSent");
        return sent;
    }

    private synchronized void setInbox(LinkedList<Mail> ll)
    {
        System.out.println("Called Model setInbox");
        if(orderByDate == true){
            this.inbox = orderByDate(ll);
        }else{
            this.inbox = orderByPriority(ll);
        }
//        this.inbox = ll;
        newInbox = true;
        setChanged();
        notifyObservers();
        newInbox = false;
        setMessageToDisplay("inbox updated");
    }

    private synchronized void setSent(LinkedList<Mail> ll)
    {
        System.out.println("Called Model setSent");
        if(orderByDate == true){
            this.sent = orderByDate(ll);
        }else{
            this.sent = orderByPriority(ll);
        }
//        this.sent = ll;
        newSent = true;
        setChanged();
        notifyObservers();
        newSent = false;
        setMessageToDisplay("sent list updated");
    }
    
    
    public boolean getNewInbox()
    {
        System.out.println("Called Model getNewInbox");
        return this.newInbox;
    }
    
    public boolean getNewSent()
    {
        System.out.println("Called Model getNewSent");
        return this.newSent;
    }
    

    public void updateAtStart(String address, Server s)
    {
        System.out.println("Called Model updateAtStart");
        try{
            setInbox(s.receive(address));//get inbox
            setSent(s.getSentList(address));//get sent mails
        }catch(RemoteException e){
            System.out.println("Remote Exception:"); e.printStackTrace();
        }
    }
    
    
    public void receive(String address, Server s, int millis)
    {
        System.out.println("Called Model receive");
        //constantly receive new emails
        Thread t = new Thread()
        {
            public void run(){
                setName("updateInboxThread");
                try{
                    sleep(2000);//offset between updates of sent list and inbox
                    while(true){
//                        System.out.println(getName());
                        this.sleep(millis);
                        if(s.getNewInbox(address) == true){
                            setInbox(s.receive(address));
                        }
                    }
                }catch(RemoteException e){
                    System.out.println("RemoteException in model:"); e.printStackTrace();
                }catch(InterruptedException e){
                    System.out.println("InterruptedException in model:"); e.printStackTrace();
                }
            }
        };
        t.start(); //download emails every n seconds
    }
    
    
    
    public void send(Server s, Mail mail)
    {
        System.out.println("Called Model send");
        try{
            s.send(mail);
        }catch(RemoteException e){
            System.out.println("Remote Exception:"); e.printStackTrace();
        }
    }
    
    
    
    public void updateSentList(Server s, String address, int millis)
    {
        System.out.println("Called Model updateSentList");
        Thread t = new Thread(){
            public void run(){
                setName("updateSentThread");
                try{
                    while(true){
//                        System.out.println(getName());
                        this.sleep(millis);
                        if(s.getNewSent(address) == true){
                            setSent(s.getSentList(address));
                        }
                    }
                }catch(RemoteException e){
                    System.out.println("Remote Exception:"); e.printStackTrace();
                }catch(InterruptedException e){ System.out.println("InterruptedException:"); e.printStackTrace();}
            }
        };
        t.start();
    }
    
    
    
    public synchronized String getMessageToDisplay()
    {
//        System.out.println("Called Model getMessageToDisplay");
        newMsg = false;
        return messageToDisplay;
    }
    
    
    
    private synchronized void setMessageToDisplay(String msg)
    {
//        System.out.println("Called Model getNewMsg");
        messageToDisplay += msg + " ;  ";
        newMsg = true;
    }

    
    public boolean getNewMsg() {
//        System.out.println("Called Model getNewMsg");
        return newMsg;
    }
    
    
    
    public synchronized void resetMessageToDisplay()
    {
//        System.out.println("Called Model resetMessageToDisplay");
        messageToDisplay = "";
        newMsg = true;
    }
    
    
    
    
    public LinkedList<String> extractAddresses(String text)
    {
        System.out.println("Called Model extractAddresses");
        //there must be ; after each address
        
        LinkedList<String> addresses = new LinkedList();
        
        text = text.replaceAll("\\s+","");//remove white spaces
        
        String[] splitted = text.split(";");
        for(String item : splitted){
            addresses.add(item);
        }
        return addresses;//return a linked list of addresses
    }
    
    
    
    
    
    public boolean deleteFromInbox(Server s, Mail mail, String address)
    {
        System.out.println("called Model deleteFromInbox");
        boolean flag = false;
        try{
            flag = s.deleteFromInbox(mail, address);
            if(flag == true){
                System.out.println("Model deleteFromInbox semaphore acquired");
                setInbox(s.receive(address));//if deleted then update local inbox
            }
        }catch(RemoteException e){
            System.out.println("RemoteException:"); e.printStackTrace();
        }
        return flag;
    }
    
    
    
    
    
    public boolean deleteFromSentList(Server s, Mail mail, String address)
    {
        System.out.println("Called Model deleteFromSentList");
        boolean flag = false;
        try{
            flag = s.deleteFromSentList(mail, address);
            if(flag == true){
                setSent(s.getSentList(address));//update sent list
            }
        }catch(RemoteException e){
            System.out.println("RemoteException:"); e.printStackTrace();
        }
        return flag;
    }
    
    
    
    
    
    //most recent email first
    private synchronized LinkedList<Mail> orderByDate(LinkedList<Mail> casual)
    {   
//        System.out.println("Called Model orderByDate");
        if(casual.size() < 2){
//            System.out.println("casual size < 2");
            return casual;
        }
        //casual has at least 2 items

        
        LinkedList<Mail> ordered = new LinkedList();
        ordered.add(casual.pop());
        
        boolean done;
        for(Mail mail : casual)
        {
            int index = 0;
            done = false;
//            System.out.println("looping...");
            while(!done)
            {
                if( time(mail) < time(ordered.get(index)) ){
                    index++;
                }else{
                    ordered.add(index, mail);
//                    System.out.println("mail added to ordered list");
                    done = true;
                }
                if(index == ordered.size() && !done){
                    ordered.add(mail);
//                    System.out.println("mail added to ordered list");
                    done = true;
                }
            }
        }
//        System.out.println("ordered list size is " + ordered.size());
        return ordered;
    }
    
    
    
    
    
    private synchronized LinkedList<Mail> orderByPriority(LinkedList<Mail> casual)
    {
        System.out.println("Called Model orderByPriority");
        LinkedList<Mail> high = new LinkedList();
        LinkedList<Mail> normal = new LinkedList();
        LinkedList<Mail> low = new LinkedList();
        
        for(Mail m : casual){
            if(m.getPriority().equals("high priority")){
                high.add(0, m);
            } else if(m.getPriority().equals("normal priority")){
                normal.add(0, m);
            } else if(m.getPriority().equals("low priority")){
                low.add(0, m);
            }
        }
        
        high.addAll(normal);
        high.addAll(low);
        
        return high;
    }
    
    
    
    private long time(Mail m)
    {
        return m.getSendingTime().getTimeInMillis();
    }
    
    
    
    
    public synchronized void setOrderByDate(){
        System.out.println("Called Model setOrderByDate");
        orderByDate = true;
        orderByPriority = false;
        setMessageToDisplay("order by date");
        setInbox(this.inbox);
        setSent(this.sent);
    }
    
    
    
    
    public synchronized void setOrderByPriority(){
        System.out.println("Called Model setOrderByPriority");
        orderByDate = false;
        orderByPriority = true;
        setMessageToDisplay("ordered by priority");
        setInbox(this.inbox);
        setSent(this.sent);
    }
    
    
    
    public void readMessagesFromServer(Server s, String address){
        new Thread(){
            public void run(){
                while(true){
                    try{
                        this.sleep(1 * 1000);
                        if(s.getNewMsg(address) == true){
                            setMessageToDisplay(s.getMsg(address));
                        }
                    }catch(InterruptedException e){
                    }catch(RemoteException e){}
                    
                }
            }
        }.start();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public void updateMessageBar(){
        new Thread(){
            public void run(){
                int counter = 0;
                setName("thread msg bar");
                while(true){
                    try{
                        if(getNewMsg() == true){
                            setChanged();
                            notifyObservers();
                            counter = 0;
                        }else{
                            sleep(1 * 1000);
                            counter++;
                            if(counter >= 4){
                                resetMessageToDisplay(); //this method sets newMsg to true
                                setChanged();
                                notifyObservers();
                                counter = 0;
                            }
                        }
                    }catch(InterruptedException e){
                        System.out.println("InterruptedException:");
                    }
                }
            }
        }.start();
    }
    
}
