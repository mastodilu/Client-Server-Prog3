// VIEW

package View.components;

import mailserver.Mail;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import prog3emailclientserver.JButtonListener;
import prog3emailclientserver.JTextAreaClickListener;
import prog3emailclientserver.MyComponentListener;
import prog3emailclientserver.UserAccount;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class SentFrame
{
    private JFrame mainWindow;
    private JPanel mailList;
    private JScrollPane scrollPane;
    private JTextAreaClickListener tl;
    private JButtonListener bl;
    private EnlargeSentMailArea enlarge;
    private boolean hidden;
    private LinkedList<Mail> sent;
    private JTextAreaMailList mail;
    private MyComponentListener cl;
    private UserAccount user;
    private Mail displayedMail;
    
    public SentFrame(UserAccount user, LinkedList<Mail> sent, JTextAreaClickListener tl, JButtonListener bl, MyComponentListener cl)
    {
        this.user = user;
        this.cl = cl;
        this.sent = sent;
        this.bl = bl;
        this.tl = tl;
        hidden = true;
        mainWindow = new JFrame("Sent-" + user.getAddress());
        mainWindow.setLayout(new BorderLayout());
        
        mainWindow.setPreferredSize(new Dimension(800, 500));
        mainWindow.setResizable(false);
        
        listAndShow();
        
        
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
    
    public void showHideRightMailAreaSent(Mail email)
    {
        if(hidden == true)//if hidden then paint rightMailArea
        {
            enlarge = new EnlargeSentMailArea(email, bl);
            mainWindow.add(enlarge.getArea(), BorderLayout.CENTER);
            mainWindow.pack();
            mainWindow.revalidate();
            mainWindow.repaint();
            
            displayedMail = email;//backup for easy reaching from controller
        } else//if not hidden then hide it
        {
            mainWindow.remove(enlarge.getArea());
            mainWindow.pack();
            mainWindow.revalidate();
            mainWindow.repaint();
            
            displayedMail = null;
        }
        hidden = !hidden;
    }
    
    public void setSent(LinkedList<Mail> sent)
    {
        this.sent = sent;
        listAndShow();
    }
    
    public void listAndShow()
    {
        boolean alreadyVisible = false;
        Dimension d;
        
        if(mailList != null)
        {
            scrollPane.remove(mailList);
            mainWindow.remove(scrollPane);
            alreadyVisible = true;
        }
        
        mailList = new JPanel();
        mailList.setBackground(Color.white);
        mailList.setOpaque(true);
        mailList.setLayout(new BoxLayout(mailList, Y_AXIS)); //give the mailList panel a BoxLayout to show the mail list correctly        
        
        d = new Dimension(250, 0);//minimum width of mail list
        mailList.add(new Box.Filler(d,d,d));
        
        int i = 0;
        for(Mail item : this.sent)
        {
            mail = new JTextAreaMailList(item, "sent frame");
            mail.addMouseListener(tl);
            if(i%2 == 0)
            {
                mail.setBackground(new Color(218, 255, 223));
            }
            i++;
            mailList.add(mail);
        }
        d = new Dimension(200, 420);//push mail to top
        mailList.add(new Box.Filler(d, d, d));
        
        scrollPane = new JScrollPane(mailList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainWindow.add(scrollPane, BorderLayout.LINE_START);
        if(alreadyVisible == true){
            mainWindow.validate();
            mainWindow.repaint();
        }
    }
    
    public JFrame getMainWindow()
    {
        return this.mainWindow;
    }

    public Mail getDisplayedMail() {
        return displayedMail;
    }
    
    
    
}
