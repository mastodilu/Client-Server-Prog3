//VIEW

package View.components;

import mailserver.Mail;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class JTextAreaMailList extends JTextArea {
    private Mail mail;
    private String fromWhere;
    
    public JTextAreaMailList()
    {
        super();
        super.setLineWrap(true);
        super.setEditable(false);
        super.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        super.setHighlighter(null);
        
        this.fromWhere = "";
        this.mail = new Mail();
//        setWhiteBackground();
    }
    
    public JTextAreaMailList(Mail email, String fromWhere)
    {
        super(email.toShortString());
        
        super.setLineWrap(true);
        super.setEditable(false);
        super.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        super.setHighlighter(null);
        
        this.mail = email;
        this.fromWhere = fromWhere;
//        setWhiteBackground();
    }
    
    public void setMail(Mail mail)
    {
        this.mail = mail; 
    }
    
    public Mail getMail()
    {
        return this.mail;
    }
    
    public String getFromWhere()
    {
        return this.fromWhere;
    }
    
//    private void setWhiteBackground()
//    {
//        this.setOpaque(true);
//        this.setBackground(Color.white);
//    }
}
