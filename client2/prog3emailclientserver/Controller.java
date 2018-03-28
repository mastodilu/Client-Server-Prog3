//CONTROLLER

package prog3emailclientserver;

import mailserver.Mail;
import Model.Model;
import View.GUIClient;
import java.rmi.*;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import mailserver.Server;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class Controller implements Observer
{
    JButtonListener bl;//controller
    JTextAreaClickListener tl;//controller
    MyComponentListener cl;//controller
    GUIClient guiClient; //view
    Model model;//model
    UserAccount user;
    Server s;
    
    public Controller()
    {
        //action listeners
        tl = new JTextAreaClickListener();
        bl = new JButtonListener();
        cl = new MyComponentListener();//not used?? TODO try to remove it
        
        user = new UserAccount(
                "dilu.93@hotmail.it",
                "Dilu",
                "DILU",
                "DD"
        );
        
        bl.addObserver(this);
        tl.addObserver(this);
        
        model = new Model();
        guiClient = new GUIClient(user, bl, tl, cl); //view
        
        model.addObserver(guiClient);
        
        
        
        
        //RMI
        try{
            s = (Server)Naming.lookup("//localhost/MailServer");
            model.updateAtStart(user.getAddress(), s);
            model.receive(user.getAddress(), s, 5000);//listening for new emails
            model.updateSentList(s, user.getAddress(), 5000);//listening for new emails
            model.readMessagesFromServer(s, user.getAddress());//listening for new messages from server
        }catch(RemoteException e) {
                System.out.println("RemoteException:"); e.printStackTrace();
        }catch(Exception e) {
            System.out.println("RMI Exception in controller:"); e.printStackTrace();
        }
        
        //keep updating the message bar
        model.updateMessageBar();
    }
    
    @Override
    public void update(Observable ob, Object extra_arg)
    {
        if(ob != null)
        {
            if(ob instanceof JButtonListener)
            {
                JButtonListener temp = (JButtonListener)ob;
                String actionID = temp.getActionID();
                
                switch(actionID)
                {
                    case "reply from inbox"://ok
                    {
                        Mail oldEmail = guiClient.getWindow().getDisplayedMail();
                        Mail newEmail = new Mail();
                                newEmail.setSender(user.getAddress());
                                newEmail.setReceivers(model.extractAddresses(oldEmail.getSender()));
                                newEmail.setSubject("RE: " + oldEmail.getSubject());
                                newEmail.setMailContent("\n\n\n\n"
                                        + "--------------\n"
                                        + oldEmail.getMailContent());
                        guiClient.paintNewEmailFrame(newEmail);
                        break;
                    }
                        
                    case "reply all from inbox"://ok
                    {
                        Mail oldEmail = guiClient.getWindow().getDisplayedMail();
                        Mail newEmail = new Mail();
                                newEmail.setSender(user.getAddress());
                                LinkedList<String> receivers = model.extractAddresses(oldEmail.getSender());
                                for(String item : oldEmail.getReceivers()){
                                    if(!user.getAddress().equals(item)){
                                        receivers.add(item);
                                    }
                                }
                                newEmail.setReceivers(receivers);
                                newEmail.setSubject("RE: " + oldEmail.getSubject());
                                newEmail.setMailContent("\n\n\n\n"
                                        + "--------------\n"
                                        + oldEmail.getMailContent());
                        guiClient.paintNewEmailFrame(newEmail);
                        break;
                    }
                    
                    case "forward from inbox"://ok
                    {
                        String content = guiClient.getWindow().getMailArea().getMailContent();
                        String subject = guiClient.getWindow().getMailArea().getSubject();
                        Mail forwarding = new Mail();
                            forwarding.setSender(user.getAddress());
                            forwarding.setSubject("Forwarded: " + subject);
                            forwarding.setMailContent(content);
                        guiClient.paintNewEmailFrame(forwarding);
                        break;
                    }
                    
                    case "delete from inbox"://ok
                    {
                        boolean flag = false;
                        flag = model.deleteFromInbox(s, guiClient.getWindow().getDisplayedMail(), user.getAddress());
                        if(flag == true){
                            guiClient.showHideRightMailAreaInbox(null);
                        }
                        break;
                    }
                    
                    case "send new email"://ok
                    {
                        System.out.println("case " + actionID);
                        Mail mail = new Mail();//create mail by getting each field from view
                            mail.setSender(user.getAddress());
                                String to = guiClient.getNewEmailWindow().getTo().getText();//supposing there's ; and a white space after each email address
                                LinkedList<String> receivers = model.extractAddresses(to);
                            mail.setReceivers(receivers);
                            mail.setSubject(guiClient.getNewEmailWindow().getSubject().getText());
                            mail.setMailContent(guiClient.getNewEmailWindow().getMailContent().getText());
                            mail.setPriority(guiClient.getNewEmailWindow().getSelectedPriority());
                        model.send(s, mail);
                        break;
                    }
                        
                    case "discard new email"://not ok!!!
                    {
                        guiClient.getNewEmailWindow().discard();   
                        break;
                    }
                        
                    case "new email from menu"://ok
                    {
                        Mail newEmail = new Mail();
                        newEmail.setSender(user.getAddress());
                        guiClient.paintNewEmailFrame(newEmail);
                        break;
                    }
                        
                    case "sent from menu"://ok
                    {
                        guiClient.paintSentFrame();
                        break;
                    }
                    
                    case "order by date from menu":
                    {
                        System.out.println("case " + actionID);
                        model.setOrderByDate();
                        break;
                    }
                    
                    case "order by priority from menu":
                    {
                        System.out.println("case " + actionID);
                        model.setOrderByPriority();
                        break;
                    }
                        
                    case "forward from sent list"://ok
                    {
                        Mail forwarding = guiClient.getSentEmailWindow().getDisplayedMail();
                        Mail newEmail = new Mail();
                                newEmail.setSender(user.getAddress());
                                newEmail.setSubject("Forwarded: " + forwarding.getSubject());
                                newEmail.setMailContent(forwarding.getMailContent());
                        guiClient.paintNewEmailFrame(newEmail);
                        break;
                    }
                        
                    case "delete from sent list"://ok
                    {
                        boolean flag = false;
                        flag = model.deleteFromSentList(s, guiClient.getSentEmailWindow().getDisplayedMail(), user.getAddress());
                        if(flag == true){
                            guiClient.showHideRightMailAreaSent(null);
                        }
                        break;
                    }
                        
                    default:
                    {
                        System.out.println("something went wrong, sorry!");
                        break;
                    }
                }
            } else if(ob instanceof JTextAreaClickListener)
            {
                JTextAreaClickListener temp = (JTextAreaClickListener)ob;
                if(temp.getFromWhere().equals("inbox frame"))
                {
                    Mail email = temp.getClickedOn().getMail();
                    guiClient.showHideRightMailAreaInbox(email);
                } else //temp.getFromWhere().equals("sent frame")
                {
                    Mail email = temp.getClickedOn().getMail();
                    guiClient.showHideRightMailAreaSent(email);
                }
                
            }
        }
    }
    
    
}
