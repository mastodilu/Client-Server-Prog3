//CONTROLLER
package prog3emailclientserver;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class MyComponentListener implements ComponentListener
{
    Dimension   screenSize;
    Double      screenWidth;
    Double      screenHeight;
    Double      height;
    Double      width;
    
    public MyComponentListener()
    {
        super();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
    }
    
    public void componentResized(ComponentEvent e)
    {
        e.getComponent().setMaximumSize(screenSize);
        if(e != null)
        {
            if(e.getComponent() instanceof JFrame)
            {
                JFrame temp = (JFrame)e.getSource();
                if(temp.getWidth() > screenWidth && temp.getHeight() > screenHeight)
                {
//                    e.getComponent().setPreferredSize(screenSize);
                    e.getComponent().setSize(screenWidth.intValue()-100, screenHeight.intValue()-100);
                } else
                {
                    if(temp.getWidth() > screenWidth)
                    {
                        e.getComponent().setSize(screenWidth.intValue()-100, height.intValue());
//                        e.getComponent().setPreferredSize( new Dimension(screenWidth.intValue()-100, height.intValue()));
                    }
                    if(temp.getHeight() > screenHeight-200)
                    {
                        e.getComponent().setSize(width.intValue(), screenHeight.intValue()-100);
//                        e.getComponent().setPreferredSize(new Dimension(width.intValue(), screenHeight.intValue()-100));
                    }
                }
                
            }
        }
    }
    
    public void componentMoved(ComponentEvent e){}
    public void componentShown(ComponentEvent e){}
    public void componentHidden(ComponentEvent e){}
}
