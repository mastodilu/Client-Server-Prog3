/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailserver;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class Log {
    
    private JFrame mainWindow;
    private JScrollPane scrollpane;
    private JTextArea text;
    
    public Log()
    {
        text = new JTextArea();
        mainWindow = new JFrame("Log");
        scrollpane = new JScrollPane(text);
        
        mainWindow.add(scrollpane);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, java.awt.Color.WHITE));

        Dimension pref = new Dimension(400, 400);
        Dimension min = new Dimension(400, 400);
        
        mainWindow.setPreferredSize(pref);
        mainWindow.setMinimumSize(min);

        
        mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
        
        
        public void writeLog(String msg)
        {
            text.setText("> " + msg + "\n" + text.getText());
            
        }
}
