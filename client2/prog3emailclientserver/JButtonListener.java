//CONTROLLER

package prog3emailclientserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JMenuItem;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class JButtonListener extends Observable implements ActionListener{
    String actionID;
    
    public JButtonListener()
    {
        super();
        actionID = "";
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof JButton)
        {
            JButton btn = (JButton)e.getSource();
            actionID = btn.getName().toLowerCase();
            System.out.println("Called actionPerformed " + actionID);
            setChanged();
            notifyObservers();
        } else if(e.getSource() instanceof JMenuItem)
        {
            JMenuItem item = (JMenuItem)e.getSource();
            actionID = item.getName();
//            actionID = item.getActionCommand().toLowerCase();
            System.out.println("Called actionPerformed from JMenuItem " + actionID);
            setChanged();
            notifyObservers();
        }
    }

    public String getActionID()
    {
        return actionID;
    }

    public void setActionID(String actionID)
    {
        this.actionID = actionID;
    } 
}
