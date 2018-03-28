//MODEL

package mailserver;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Calendar;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class Mail implements Email, Serializable {
    private static final long serialVersionUID = 20120731125400L;
    private String sender;
    private LinkedList<String> receivers;//there could be more than one receiver
    private String subject;
    private String mailContent;
    private Calendar sendingTime;
    private String priority;
    
    public Mail()
    {
        sender = "";
        receivers = new LinkedList<>();
        subject = "";
        mailContent = "";
        sendingTime = Calendar.getInstance();
        priority = "Normal priority";
    }
    
    public Mail(Email email)
    {
        this.sender = email.getSender();
        this.receivers = email.getReceivers();
        this.subject = email.getSubject();
        this.mailContent = email.getMailContent();
        this.sendingTime = email.getSendingTime();
        this.priority = email.getPriority();
    }
    
    public Mail(String sender, LinkedList<String> receivers, String subject, String mailContent, Calendar sendingTime, String p)
    {
        this.sender = sender;
        this.receivers = receivers;
        this.subject = subject;
        this.mailContent = mailContent;
        this.sendingTime = sendingTime;
        this.priority = p;
    }
    
    public void addReceiver(String newReceiver)
    {
        receivers.add(newReceiver);
    }
    
    //SETTERS and GETTERS

    public String getSender()
    {
        return sender;
    }

    public String getEachReceiver()
    {
        String s = "";
        for(String receiver : receivers)
        {
            s += receiver + "; ";
        }
        return s;
    }
    
    public LinkedList<String> getReceivers()
    {
        return receivers;
    }

    public String getMailContent()
    {
        return mailContent;
    }

    public Calendar getSendingTime()
    {
        return sendingTime;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public void setPriority(String p)
    {
        this.priority = p;
    }
    
    public String getPriority()
    {
        return this.priority;
    }
    
    public String getSubject()
    {
        return subject;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public void setReceivers(LinkedList<String> receivers)
    {
        this.receivers = receivers;
    }

    public void setMailContent(String mailContent)
    {
        this.mailContent = mailContent;
    }

    public void setSendingTime(Calendar sendingTime)
    {
        this.sendingTime = sendingTime;
    }
    
    @Override
    public String toString()
    {
        String s;
        /*
            From ---
            To [---;---;...]
            Subject: ------
            Mail Content:
            ------- ---- ----
            --- ------ ------
            dd-MM-yy hh:mm:ss
        */
        s =         "From " + sender
                +   "\nTo " + receivers
                +   "\nSubject: " + subject
                +   "\nEmail content:\n" + mailContent + "\n";
        s +=    sendingTime.get(sendingTime.DATE)
                + "-" + String.format("%02d", (1 + sendingTime.get(sendingTime.MONTH)))
                + "-" + String.format("%02d", (sendingTime.get(sendingTime.YEAR)));
        s += " "
                + String.format("%02d", sendingTime.get(sendingTime.HOUR_OF_DAY))
                + ":" + String.format("%02d", sendingTime.get(sendingTime.MINUTE))
                + ":" + String.format("%02d", sendingTime.get(sendingTime.SECOND));
        
        return s;
    }
    
    public String toShortString()
    {
        String s = priority;
        s += "\nFROM " + sender;
        for(String receiver : receivers){
            s += "\nTO " + receiver;
        }
        s += "\nSUBJECT " + subject;
        s += "\nWHEN ";
        s +=    sendingTime.get(sendingTime.DATE)
                + "-" + String.format("%02d", (1 + sendingTime.get(sendingTime.MONTH)))
                + "-" + String.format("%02d", (sendingTime.get(sendingTime.YEAR)));
        s += " "
                + String.format("%02d", sendingTime.get(sendingTime.HOUR_OF_DAY))
                + ":" + String.format("%02d", sendingTime.get(sendingTime.MINUTE))
                + ":" + String.format("%02d", sendingTime.get(sendingTime.SECOND));
        return s;
    }
    
    
    
    public boolean equals(Mail m){
        return 
                this.sender.equals(m.getSender())
                && this.receivers.equals(m.getReceivers())
                && this.subject.equals(m.getSubject())
                && this.mailContent.equals(m.getMailContent())
                && this.sendingTime.equals(m.getSendingTime());
    }
}
