//VIEW
package View.components;

import mailserver.Mail;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
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
public class EnlargeSentMailArea {
    private JTextArea from, to, subject;
    private JTextArea mailContent;
    private JPanel area;
    private JButton forward, delete;
    private GridBagConstraints gbc, gbcFiller;
    private JButtonListener bl;
    
    public EnlargeSentMailArea(Mail email, JButtonListener bl)
    {
        this.bl = bl;
        area = new JPanel( new GridBagLayout());
        gbc = new GridBagConstraints();
        TitledBorder title; //titled border around each email component

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        
        forward = new JButton("Forward");
        forward.setName("forward from sent list");
        delete = new JButton("Delete");
        delete.setName("delete from sent list");
        
        forward.addActionListener(bl);
        delete.addActionListener(bl);
        
        buttons.add(forward);
        buttons.add(delete);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        
        area.add(buttons, gbc);
        
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
        subject.setEditable(false);
        subject.setLineWrap(true);
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
    
    public JPanel getArea()
    {
        return this.area;
    }
    
}
