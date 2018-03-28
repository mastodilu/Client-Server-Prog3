//VIEW
package View.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import mailserver.Mail;
import prog3emailclientserver.JButtonListener;
import prog3emailclientserver.UserAccount;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class JFrameNewEmail extends JFrame
{
    private JFrame writeNewEmailWindow;
    private JPanel mainPanel, buttons;
    private JTextArea to, from, subject, mailContent;
    private TitledBorder title; //titled border around each email component
    private boolean forward, respond;
    private GridBagConstraints gbc;
    private JButtonListener bl;
    private UserAccount user;
    private JComboBox priority;
    
    
    public JFrameNewEmail(JButtonListener bl, UserAccount user, Mail mail)
    {
        this.user = user;
        this.bl = bl;
        writeNewEmailWindow = new JFrame("New email");
        mainPanel = new JPanel( new GridBagLayout());
        writeNewEmailWindow.setPreferredSize(new Dimension(440, 380));
        
        //buttons
        drawButtonsPanel();
        
        //from
        drawFrom(mail.getSender());
        
        //to
        String temp = "";
        for(String item : mail.getReceivers()){
            temp += item + ";";
        }
        drawTo(temp);
        
        //subject
        drawSubject(mail.getSubject());
        
        //mail content
        drawMailContent(mail.getMailContent());
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        writeNewEmailWindow.add(scrollPane);
        writeNewEmailWindow.pack();
        writeNewEmailWindow.setVisible(true);
    }
    
    
    private void drawButtonsPanel()
    {
        JButton send, discard;
        
        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        send = new JButton("Send");
        send.setName("send new email");
        discard = new JButton("Discard");
        discard.setName("discard new email");
        
        String[] priorityList = {
            "high priority",
            "normal priority",
            "low priority"};
        priority = new JComboBox(priorityList);
        priority.setSelectedIndex(1);
        
        
        send.addActionListener(bl);
        discard.addActionListener(bl);
        
        buttons.add(send);
        buttons.add(discard);
        buttons.add(priority);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(buttons, gbc);
        
        //filler in the first row
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;//fill the screen horizontally
        Dimension d = new Dimension(0, 0);
        mainPanel.add(new Box.Filler(d, d, d), gbc);
    }
    
    
    private void drawFrom(String sender)
    {
        title = BorderFactory.createTitledBorder("From");
        from = new JTextArea(sender);
        from.setEditable(false);
        gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1; //fill the screen horizontally
        mainPanel.add(from, gbc);
        from.setBorder(title);
    }
    
    
    private void drawTo(String temp)
    {
        title = BorderFactory.createTitledBorder("To");
        to = new JTextArea(temp);
        to.setLineWrap(true);
        gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1; //fill the screen horizontally
        mainPanel.add(to, gbc);
        to.setBorder(title);
    }
    
    
    private void drawSubject(String temp)
    {
        title = BorderFactory.createTitledBorder("Subject");
        subject = new JTextArea(temp);
        subject.setLineWrap(true);
        gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1; //fill the screen horizontally
        mainPanel.add(subject, gbc);
        subject.setBorder(title);
    }
    
    
    private void drawMailContent(String temp)
    {
        title = BorderFactory.createTitledBorder("Mail content");
        mailContent = new JTextArea(temp);
        mailContent.setLineWrap(true);
        gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1; //fill the screen horizontally
            gbc.weighty = 100;//fill the screen vertically
        mainPanel.add(mailContent, gbc);
        mailContent.setBorder(title);
    }
    
    
    public void discard()
    {
        to.setText("");
        subject.setText("");
        mailContent.setText("");
    }
    
    
    //SETTERS and GETTERS
    
    public JFrame getWriteNewEmailWindow()
    {
        return this.writeNewEmailWindow;
    }
    public void setForward(boolean flag)
    {
        this.forward = flag;
    }
    
    public void setRespond(boolean flag)
    {
        this.respond = flag;
    }
    
    public boolean getForward()
    {
        return this.forward;
    }
    
    public JTextArea getFrom()
    {
        return from;
    }
            
    public boolean getRespond()
    {
        return this.respond;
    }

    public JTextArea getTo() {
        return to;
    }

    public JTextArea getSubject() {
        return subject;
    }

    public JTextArea getMailContent() {
        return mailContent;
    }
    
    public String getSelectedPriority()
    {
        return (String)priority.getSelectedItem();
    }
    
    
    
    
}
