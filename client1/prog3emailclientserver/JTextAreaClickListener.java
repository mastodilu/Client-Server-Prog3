//CONTROLLER

package prog3emailclientserver;

import View.components.JTextAreaMailList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

/**
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class JTextAreaClickListener extends Observable implements MouseListener
{
    JTextAreaMailList clickedOn;
    String fromWhere;
    
    public JTextAreaClickListener()
    {
        super();
        fromWhere = "";
    }
    
    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() instanceof JTextAreaMailList)
        {            
            clickedOn = (JTextAreaMailList)e.getSource();
            fromWhere = clickedOn.getFromWhere();
            
            setChanged();
            notifyObservers();
        }
    }
    
    public void mouseEntered(MouseEvent e)
    {
        //TODO use it to paint a border
    }
    
    public void mouseExited(MouseEvent e)
    {
        //TODO use it to delete the border if painted after mouseEntered()
    }
    
    public void mousePressed(MouseEvent e)
    {}
    
    public void mouseReleased(MouseEvent e)
    {}
    
    public JTextAreaMailList getClickedOn()
    {
        return this.clickedOn;
    }
    
    public String getFromWhere()
    {
        return this.fromWhere;
    }
}
