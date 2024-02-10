import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

import com.sun.xml.internal.ws.assembler.jaxws.AddressingTubeFactory;

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

    private JTextField field;
    private JButton graph;
    private JButton clear;
    private NameSurferGraph nameSurferGraph;
    private NameSurferDataBase nameSurferDataBase;
    

    public void init() {
      

        //adding buttons and textfield
        field = new JTextField(10);
        add(field, SOUTH);
        
        graph = new JButton("Graph");
        add(graph, SOUTH);
        
        clear = new JButton("Clear");
        add(clear, SOUTH);
        
        addActionListeners();
      
        nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        
        nameSurferGraph = new NameSurferGraph();
        add(nameSurferGraph);
       
     
    }

    public void actionPerformed(ActionEvent e) {
        String name = field.getText();

        //check if graph button is clicked 
        if (e.getSource() == graph) {
        	//check if textfield is empty
            if (name.isEmpty()) {
                return;
            }
           

            NameSurferEntry entry = nameSurferDataBase.findEntry(name);
            
            //check if name is found 
            if (entry != null) {
            	//update graph and add the entry to the list
            	nameSurferGraph.addEntry(entry);
            	nameSurferGraph.update();
            } 
            //check if clear button is clicked
        } else if (e.getSource() == clear) {
            nameSurferGraph.clear();
            nameSurferGraph.update();
      }
    }
    
}
