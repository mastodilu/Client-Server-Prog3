/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailserver;

import java.util.LinkedList;
import java.util.Calendar;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public interface Email {
    
    void        addReceiver(String newReceiver);
    void        setPriority(String priority);
    String      getPriority();
    String      getSender();
    String      getEachReceiver();
    LinkedList<String> getReceivers();
    String      getMailContent();
    Calendar    getSendingTime();
    void        setSubject(String subject);
    String      getSubject();
    void        setSender(String sender);
    void        setReceivers(LinkedList<String> receivers);
    void        setMailContent(String mailContent);
    void        setSendingTime(Calendar sendingTime);
    String      toShortString();
}

