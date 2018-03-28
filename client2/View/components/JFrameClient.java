//VIEW

package View.components;

import mailserver.Mail;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import prog3emailclientserver.JButtonListener;
import prog3emailclientserver.JTextAreaClickListener;
import prog3emailclientserver.MyComponentListener;
import prog3emailclientserver.UserAccount;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */

public class JFrameClient
{
    private JFrame receivedEmailsWindow;
    private JMenuBar menuBar;
    private JMenu actions, account, orderBy;
    private JPanel mailList;
    private JMenuItem menuItem;
    private WriteMailArea mailArea;
    private JScrollPane scrollPane;
    private JButtonListener bl;
    private JTextAreaClickListener tl;
    private boolean hidden;//true when rightMailArea is not painted, false otherwise
    private LinkedList<Mail> inbox;
    private JTextAreaMailList mail;
    private MyComponentListener cl;
    private UserAccount user;
    private JTextArea messageArea;
    private Mail displayedMail;
    
    public JFrameClient(UserAccount user, JButtonListener bl, JTextAreaClickListener tl, MyComponentListener cl, LinkedList<Mail> inbox)
    {
        this.user = user;
        this.cl = cl;
        this.inbox = inbox;
        this.tl = tl;
        this.bl = bl;
        receivedEmailsWindow = new JFrame();
        receivedEmailsWindow.setTitle("Inbox-" + user.getAddress());
        receivedEmailsWindow.setLayout(new BorderLayout());
        hidden = true;
        
        
        receivedEmailsWindow.setPreferredSize(new Dimension(800, 500));
        receivedEmailsWindow.setResizable(false);
        
        //show white area on top to display important operation messages
        paintMessagePane();
        
        paintMenu(user);
        
        //show mail list on left side
        paintMailList();
        
        receivedEmailsWindow.pack();
        receivedEmailsWindow.setVisible(true);
        receivedEmailsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void paintMessagePane()
    {
        messageArea = new JTextArea("");
        messageArea.setEditable(false);
        receivedEmailsWindow.add(messageArea, BorderLayout.PAGE_START);
    }
    
    public void paintMenu(UserAccount user)
    {
        menuBar = new JMenuBar(); //create a menu with [File][Account] submenus
        actions = new JMenu("Actions");
        account = new JMenu("Account");
        orderBy = new JMenu("Order by");
        
        menuItem = new JMenuItem("New email");
        menuItem.setName("new email from menu");
        actions.add(menuItem);
        menuItem.addActionListener(bl);
        
        menuItem = new JMenuItem("Sent");
        menuItem.setName("sent from menu");
        actions.add(menuItem);
        menuItem.addActionListener(bl);
        
        menuItem = new JMenuItem("Email: " + user.getAddress());
        account.add(menuItem);
        menuItem = new JMenuItem("Name: " + user.getName());
        account.add(menuItem);
        menuItem = new JMenuItem("Surname: " + user.getSurname());
        account.add(menuItem);
        menuItem = new JMenuItem("Nickname: " + user.getNickname());
        account.add(menuItem);
        
        menuItem = new JMenuItem("Order by date");
        menuItem.setName("order by date from menu");
        orderBy.add(menuItem);
        menuItem.addActionListener(bl);
        
        menuItem = new JMenuItem("Order by priority");
        menuItem.setName("order by priority from menu");
        orderBy.add(menuItem);
        menuItem.addActionListener(bl);
        
        menuBar.add(actions);
        menuBar.add(account);
        menuBar.add(orderBy);
        
        receivedEmailsWindow.setJMenuBar(menuBar);
    }
    
    //draw the mail list on the left side of the main window and use BoxLayout
    public void paintMailList()
    {
//        boolean alreadyVisible = false;
        Dimension d;
        
        if(mailList != null)
        {
//            alreadyVisible = true;
            scrollPane.remove(mailList);
            receivedEmailsWindow.remove(scrollPane);
        }
        mailList = new JPanel();
        
        d = new Dimension(250, 0);//minimum width of mail list
        mailList.add(new Box.Filler(d,d,d));
        
        mailList.setBackground(Color.white);
        mailList.setOpaque(true);
        mailList.setLayout(new BoxLayout(mailList, Y_AXIS)); //give the mailList panel a BoxLayout to show the mail list correctly
        
        int i = 0;
        for(Mail item : this.inbox)
        {
            mail = new JTextAreaMailList(item, "inbox frame");
            if(i%2 == 0)
                mail.setBackground(new Color(228, 229, 224));
            i++;
            mail.addMouseListener(tl);
            mailList.add(mail);
        }
        d = new Dimension(200, 400);//push mail to top
        mailList.add(new Box.Filler(d, d, d));
        scrollPane = new JScrollPane(mailList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        receivedEmailsWindow.add(scrollPane, BorderLayout.LINE_START); //add the mail list panel on the left side of mainWindow
        receivedEmailsWindow.validate();
        receivedEmailsWindow.repaint();
    }
    
    //show mail preview on right side
    public void showHideRightMailAreaInbox(Mail email)
    {
        
        if(hidden == true)//if hidden then paint rightMailArea
        {
            mailArea = new WriteMailArea(bl, email);
            receivedEmailsWindow.add(mailArea.getArea(), BorderLayout.CENTER);
            receivedEmailsWindow.pack();
            receivedEmailsWindow.revalidate();
            receivedEmailsWindow.repaint();
            
            displayedMail = email;//backup for easy reaching from controller
        }
        else//if not hidden then update or hide it
        {
            receivedEmailsWindow.remove(mailArea.getArea());
            receivedEmailsWindow.pack();
            receivedEmailsWindow.revalidate();
            receivedEmailsWindow.repaint();

            displayedMail = null;
        }
        hidden = !hidden;
    }
    
    
    //SETTERS & GETTERS
    
    public void setReceivedEmailsWindow(JFrame jf)
    {
        this.receivedEmailsWindow = jf;
    }
    
    public JFrame getReceivedEmailsWindow()
    {
        return this.receivedEmailsWindow;
    }
    
    public void setInbox(LinkedList<Mail> inbox)
    {
        this.inbox = inbox;
        paintMailList();
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public WriteMailArea getMailArea() {
        return mailArea;
    }

    public Mail getDisplayedMail() {
        return displayedMail;
    }
    
    
    
    
}
