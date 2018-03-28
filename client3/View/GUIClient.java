//VIEW

package View;

import mailserver.Mail;
import Model.Model;
import View.components.*;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import prog3emailclientserver.JButtonListener;
import prog3emailclientserver.JTextAreaClickListener;
import prog3emailclientserver.MyComponentListener;
import prog3emailclientserver.UserAccount;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */

public class GUIClient implements Observer
{
    private UserAccount user;
    private LinkedList<Mail> inbox, sent;
    private JFrameClient window;
    private JFrameNewEmail newEmailWindow;
    private SentFrame sentEmailWindow;
    
    private JTextAreaClickListener tl;
    private JButtonListener bl;
    private MyComponentListener cl;
    
    public GUIClient(UserAccount user, JButtonListener bl, JTextAreaClickListener tl, MyComponentListener cl)
    {
        this.user = user;
        this.inbox = new LinkedList();
        this.sent = new LinkedList();
        this.bl = bl;
        this.tl = tl;
        this.cl = cl;
        paintInboxFrame();
    }
    
    //show a frame in which can be written a new email
    public void paintNewEmailFrame(Mail mail)
    {
        newEmailWindow = new JFrameNewEmail(bl, user, mail);
    }
    
    public void paintInboxFrame()
    {
        if(window == null){//the first time, when gui starts
            window = new JFrameClient(user, bl, tl, cl, this.inbox);
        }else{//every other time when 'window' is not null
            window.setInbox(this.inbox);
        }
    }
    
    public void paintSentFrame()
    {
        if(sentEmailWindow == null){
            sentEmailWindow = new SentFrame(user, sent, tl, bl, cl);
        }else{
            sentEmailWindow.setSent(sent);
            if(!sentEmailWindow.getMainWindow().isVisible()){
                sentEmailWindow.getMainWindow().setVisible(true);
                sentEmailWindow.getMainWindow().repaint();
            }
        }
    }
    
    public void showHideRightMailAreaInbox(Mail email)
    {
        window.showHideRightMailAreaInbox(email);
    }
    
    public void showHideRightMailAreaSent(Mail email)
    {
        sentEmailWindow.showHideRightMailAreaSent(email);
    }
    
    public void update(Observable ob, Object extra_arg)
    {
        System.out.println("called GUIClient update");
        if(ob != null)
        {
            if(ob instanceof Model)
            {
                Model model = (Model)ob;
                
                if(model.getNewInbox() == true)//if inbox in model has been modified
                {
                    this.inbox = model.getInbox();
                    paintInboxFrame();//change inbox list in view
                    System.out.println("GUIClient update() inbox size " + inbox.size());
                }
                if(model.getNewSent() == true)
                {
                    this.sent = model.getSent();
                    if(sentEmailWindow != null && sentEmailWindow.getMainWindow().isVisible()){
                        paintSentFrame();
                    }
                    System.out.println("GUIClient update() sent list size " + sent.size());
                }
                //update message
                window.getMessageArea().setText(model.getMessageToDisplay());
                
            } else System.out.println("ob is not instanceof Model");
        }
    }

    public JFrameNewEmail getNewEmailWindow() {
        return newEmailWindow;
    }

    public JFrameClient getWindow() {
        return window;
    }

    public SentFrame getSentEmailWindow() {
        return sentEmailWindow;
    }
    
    
    
    

    
    
    
}
