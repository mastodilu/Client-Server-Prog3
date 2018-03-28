package mailserver;

import java.util.LinkedList;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
class Account {
    private String address, msg;
    private LinkedList<Mail> sent;
    private LinkedList<Mail> received;
    private boolean newSent, newReceived, newMsg;
    
    public Account()
    {
        address = "";
        msg = "";
        sent = new LinkedList();
        received = new LinkedList();
        newSent = false;
        newReceived = false;
        newMsg = false;
    }
    
    public Account(String a, LinkedList<Mail> se, LinkedList<Mail> re)
    {
        address = a;
        msg = "";
        sent = se;
        received = re;
        newSent = false;
        newReceived = false;
        newMsg = false;
    }

    public boolean getNewSent(){
        return newSent;
    }
    
    public boolean getNewReceived(){
        return newReceived;
    }
    
    public String getAddress() {
        return address;
    }

    public LinkedList<Mail> getSent() {
        return sent;
    }

    public LinkedList<Mail> getReceived() {
        return received;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    protected void setReceived(LinkedList<Mail> received) {
        this.received = received;
    }

    protected void setSent(LinkedList<Mail> sent) {
        this.sent = sent;
    }
    
    protected void addToSent(Mail mail){
        this.sent.add(mail);
    }
    
    protected void addToReceived(Mail mail){
        this.received.add(mail);
    }
    

    protected void setNewSent(boolean newSent) {
        this.newSent = newSent;
    }

    protected void setNewReceived(boolean newInbox) {
        this.newReceived = newInbox;
    }
    
    protected void setMsg(String s){
        this.msg = s;
    }
    
    protected void writeMsg(String s){
        if(msg.equals("")){
            setMsg(s);
        }else{
            setMsg(getMsg() + " ; " + s);
        }
        newMsg = true;
    }
    
    protected String readMsg(){
        newMsg = false;
        String s = getMsg();
        setMsg("");
        return s;
    }
    
    protected String getMsg(){
        return this.msg;
    }
    
    protected boolean getNewMsg(){
        return this.newMsg;
    }
}
