//VIEW
package View.components;

import mailserver.Mail;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import prog3emailclientserver.JButtonListener;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
//area where you can fill each section of the email, it's on the right side
public class WriteMailArea {
    private JTextArea from, to, subject, mailContent;
    private JPanel area;
    private JButton reply, replyAll, forward, delete;
    private GridBagConstraints gbc, gbcFiller;
    private JButtonListener bl;
    
    public WriteMailArea(JButtonListener bl, Mail email)
    {
        this.bl = bl;
        area = new JPanel( new GridBagLayout());
        gbc = new GridBagConstraints();
        TitledBorder title; //titled border around each email component

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        
        reply = new JButton("Reply");
        reply.setName("reply from inbox");
        replyAll = new JButton("Reply all");
        replyAll.setName("reply all from inbox");
        forward = new JButton("Forward");
        forward.setName("forward from inbox");
        
        delete = new JButton("Delete");
        delete.setName("delete from inbox");
        
        reply.addActionListener(bl);
        replyAll.addActionListener(bl);
        forward.addActionListener(bl);
        delete.addActionListener(bl);
        
        buttons.add(reply);
        buttons.add(replyAll);
        buttons.add(forward);
        buttons.add(delete);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        
        area.add(buttons, gbc);
        
        //filler in the first row
        gbcFiller = new GridBagConstraints();
            gbcFiller.gridx = 1;
            gbcFiller.gridy = 0;
            gbcFiller.weightx = 1;//fill the screen horizontally
        Dimension d = new Dimension(0, 0);
        area.add(new Box.Filler(d, d, d), gbcFiller);

        //from
        title = BorderFactory.createTitledBorder("From");
        from = new JTextArea(email.getSender());
        from.setEditable(false);
        from.setLineWrap(true);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1; //fill the screen horizontally
        area.add(from, gbc);
        from.setBorder(title);
        
        //to
        title = BorderFactory.createTitledBorder("To");
        to = new JTextArea(email.getEachReceiver());
        to.setEditable(false);
        to.setLineWrap(true);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1; //fill the screen horizontally
        area.add(to, gbc);
        to.setBorder(title);
        
        //subject
        title = BorderFactory.createTitledBorder("Subject");
        subject = new JTextArea(email.getSubject());
        subject.setLineWrap(true);
        subject.setEditable(false);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1; //fill the screen horizontally
        area.add(subject, gbc);
        subject.setBorder(title);
        
        //mail content
        title = BorderFactory.createTitledBorder("Email Content");
        mailContent = new JTextArea(email.getMailContent());
        mailContent.setEditable(false);
        mailContent.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(mailContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;//fill the available space vertically and horizontally
            gbc.weighty = 100;//fill the screen vertically
            gbc.weightx = 1;//fill the screen horizontally
        area.add(scrollPane, gbc);
        mailContent.setBorder(title);
                
        area.setVisible(true);
    }
    
    
    
    
    
    
    public void updateMailArea(Mail email)
    {
        setFrom(email.getSender());
        setTo(email.getEachReceiver());
        setSubject(email.getSubject());
        setMailContent(email.getMailContent());
        System.out.println("view updated");
    }
    
    
    
    //SETTERS and GETTERS
    public void setTo(String to)
    {
        this.to.setText(to);
    }
    
    
    public void setFrom(String from)
    {
        this.from.setText(from);
    }
    
    
    public void setSubject(String subject)
    {
        this.from.setText(subject);
    }
    
    
    public void setMailContent(String mailContent)
    {
        this.mailContent.setText(mailContent);
    }
    
        
    public String getFrom()
    {
        return this.from.getText();
    }
    
    
    public String getSubject()
    {
        return this.subject.getText();
    }
    
    
    public String getMailContent()
    {
        return this.mailContent.getText();
    }
    
    
    public JPanel getArea()
    {
        return this.area;
    }
    

    public JTextArea getTo() {
        return to;
    }
    
    
}
