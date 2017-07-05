package pk.webdoor.fx;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javax.swing.JFrame;

import pk.webdoor.model.Customer;
import pk.webdoor.model.InvoiceToCust;
import pk.webdoor.model.WDPackage;

public class Printer extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Customer cus;
	public static InvoiceToCust selectedInvoice;
	public static WDPackage selectedPack; 
	
	private Logger log = Logger.getLogger(Printer.class.getName());

	public Printer(Customer cus, InvoiceToCust selectedInvoice, WDPackage selectedPack){

		this.cus = cus;
		this.selectedInvoice = selectedInvoice;
		this.selectedPack = selectedPack;
		initComponents();
	}

	
	private void initComponents() {
		fxPanel = new JFXPanel();
		cancelButton = new javax.swing.JButton();
		okButton = new javax.swing.JButton();

	        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setTitle("Invoice Print Preview");

	        /**
	         * The border creates a border in printout too.
	         */
	        //fxPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
	        

	        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(fxPanel);
	        fxPanel.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	            .add(0, 672, Short.MAX_VALUE)
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	            .add(0, 313, Short.MAX_VALUE)
	        );

	        cancelButton.setText("Cancel");
	        
	        cancelButton.addActionListener(new ActionListener() {
	        	 
	            public void actionPerformed(ActionEvent e)
	            {
	            	dispose();
	            }
	        });

	        okButton.setText("OK");
	        okButton.addActionListener(new ActionListener() {
	        	 
	            public void actionPerformed(ActionEvent e)
	            {
	        	    PrinterJob pjob = PrinterJob.getPrinterJob();
	        	    PageFormat preformat = pjob.defaultPage();
	        	    preformat.setOrientation(PageFormat.PORTRAIT);
	        	    PageFormat postformat = pjob.pageDialog(preformat);
	        	    //If user does not hit cancel then print.
	        	    if (preformat != postformat) {
	        	        //Set print component
	        	    	BufferedImage image = createImage(fxPanel);
	        	    	//Graphics g = image.getGraphics();
	        	        pjob.setPrintable(new FXPrintable2(image), postformat);
	        	        if (pjob.printDialog()) {
	        	            try {
	        					pjob.print();
	        					dispose();
	        				} catch (PrinterException e1) {
	        					// TODO Auto-generated catch block
	        					e1.printStackTrace();
	        				}
	        	        }
	        	    }
	            }
	        });     

	        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	            .add(layout.createSequentialGroup()
	                .addContainerGap()
	                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                    .add(fxPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
	                        .add(0, 0, Short.MAX_VALUE)
	                        .add(okButton)
	                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                        .add(cancelButton)))
	                .addContainerGap())
	        );

	        layout.linkSize(new java.awt.Component[] {cancelButton, okButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

	        layout.setVerticalGroup(
	            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	            .add(layout.createSequentialGroup()
	                .addContainerGap()
	                .add(fxPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
	                    .add(cancelButton)
	                    .add(okButton))
	                .addContainerGap())
	        );
	        
	        

	        pack();
	        //setSize(715, 890);
	        setSize(720, 890);
	        
	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	            initFX(fxPanel);
	            }
	       });
	       
		
	}
	
    private BufferedImage bi;
    
	public BufferedImage createImage(Component panel) {
		//System.out.println("createImage called");
		log.log(Level.INFO, "createImage called");
		
	    int w = panel.getWidth();
	    int h = panel.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.paint(g);
	    return bi;
	}
	
    protected void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
		
	}

	private Scene createScene() {
		Group root = new Group();
        AnchorPane page = null;
		try {
			page = (AnchorPane) FXMLLoader.load(Printer.class.getResource("print.fxml"));
			//page.setStyle("-fx-border-color: rgb(49, 89, 23)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root, Color.WHITE);
		//page.setPrefWidth(scene.getWidth());
		//page.setPrefHeight(scene.getHeight());
		root.getChildren().add(page);
        
        
        return (scene);
	}

	private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private JFXPanel fxPanel;

}
