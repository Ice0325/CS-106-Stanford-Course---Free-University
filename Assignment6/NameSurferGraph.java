/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;
import sun.security.jca.GetInstance;

import java.awt.event.*;
import java.util.*;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	

    private ArrayList<NameSurferEntry> list = new ArrayList<NameSurferEntry>();
    private NameSurferEntry nameSurferEntry;
    private int y;
    private GLabel entries;
    private Color color;
    private static final int OFFSET = 2;

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		//	 You fill in the rest //
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		//	 You fill this in //
		list.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		list.add(entry);
	}
	
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		//	 You fill this in //
		removeAll();
		verticalLines();
		HorizontalLines();
		Labels();
		Graph();
	}
	
	
	
	//drawing graph
	private void Graph() {
		//initialize values
		int last_x = 0;
		int last_y = 0;
		//counting number of entries to change colours each 4 entries
		int n = 0;
		for(int j=0; j < list.size(); j++){
			NameSurferEntry entry = list.get(j);
			n++;
			if(n == 1){
				color = Color.BLACK;
			} else if(n == 2){
				color = Color.RED;
			} else if(n == 3){
				color = Color.BLUE;
			}else {
				color = Color.ORANGE;
				n = 0;
			}
			//initialize for new entry
			 last_x = 0;
			 last_y = 0;
			 //for each entry for each decade draw lines of graph
			for(int i=0; i < NDECADES; i++){
				//x is for each decade
				int x = getWidth() / NDECADES * i;
				
				int Rank = entry.getRank(i);
				
				//check if its first decade because u cant draw line on it, but must draw the label for entry and rank
				if(i == 0){
					//check if rank is 0 or not
					if(Rank != 0){
					    last_y = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) * Rank/ MAX_RANK ;
					    entries = new GLabel(entry.getName() + " " + Rank);
					    entries.setColor(color);
						entries.setFont("Default-14");
					    
					    add(entries, last_x + OFFSET, last_y - OFFSET);
					} else {
						last_y = getHeight() - GRAPH_MARGIN_SIZE;
						entries = new GLabel(entry.getName() + "*");
						entries.setColor(color);
						entries.setFont("Default-14");
						
						add(entries, last_x + OFFSET, last_y - OFFSET);
					}
				} else {
					//for other decades than first one check for ranks and set labels 
				if(Rank > 0 ){
			    	 y = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) * Rank/ MAX_RANK ;
			    	 entries = new GLabel(entry.getName() + " " + Rank);
				}else if(Rank == 0){
					 y = getHeight()- GRAPH_MARGIN_SIZE;
					 entries = new GLabel(entry.getName() + "*");
				}
				//set line, change label and line colour  and add them,
				GLine line = new GLine(last_x,last_y,x,y);
				line.setColor(color);
				entries.setColor(color);
				entries.setFont("Default-14");
				last_x = x;
				last_y = y;
				add(entries, last_x + OFFSET, last_y - OFFSET);
				add(line);
				}
				}
		
			}
	}

	private void verticalLines() {
		//for number of decades draw vertical lines
		for(int i=0; i < NDECADES; i++){
			double x = (getWidth() / NDECADES) * i;
			GLine line = new GLine(x,0,x,getHeight());
			add(line);
		}
	}

	//two horizontal lines for graph on top and bottom
	private void HorizontalLines(){
		GLine line1 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(),GRAPH_MARGIN_SIZE);
		GLine line2 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(line1);
		add(line2);
	}
	
	private void Labels(){
		//for number of decades draw each label of decade 
		for(int i=0; i < NDECADES; i++){
			//decade int itself
			int a = START_DECADE + i*10;
			GLabel label = new GLabel(Integer.toString(a));
			double x = 5 + (getWidth() / NDECADES) * i;
			double y = getHeight() - 5;
			add(label,x,y);
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
