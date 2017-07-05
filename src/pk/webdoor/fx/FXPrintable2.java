package pk.webdoor.fx;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class FXPrintable2 implements Printable {
    private Graphics comp;
    
    private BufferedImage bi;
    private int w, h;

	public FXPrintable2(BufferedImage comp){
        //this.comp = comp;
        this.bi = comp;
    }
	
	public BufferedImage createImage(Component panel) {

	    w = panel.getWidth();
	    h = panel.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.paint(g);
	    return bi;
	}
	
	/*
	@Override  
    public int print(Graphics g, PageFormat format, int page_index) 
            throws PrinterException {
    	
    	System.out.println("print called");
    	
        if (page_index > 0) {
            return Printable.NO_SUCH_PAGE;
        }


        Dimension dim = comp.getSize();
        double cHeight = dim.getHeight();
        double cWidth = dim.getWidth();


        double pHeight = format.getImageableHeight();
        double pWidth = format.getImageableWidth();

        double pXStart = format.getImageableX();
        double pYStart = format.getImageableY();
        

        System.out.println(" pXStart " + pXStart + " pYStart " + pYStart);

        double xRatio = pWidth / cWidth;
        double yRatio = pHeight / cHeight;


        System.out.println(" xRatio " + xRatio + " yRatio " + yRatio);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pXStart, pYStart);
        g2.scale(xRatio, yRatio);


        comp.paint(g2);

        
        return Printable.PAGE_EXISTS;
    }
    */

	@Override  
    public int print(Graphics g, PageFormat format, int page_index) 
            throws PrinterException {
    	
    	System.out.println("print called");
    	
        if (page_index > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D gg = (Graphics2D) g;
        
        

        double cHeight = bi.getHeight();
        double cWidth = bi.getWidth();
        
        double pXStart = format.getImageableX();
        double pYStart = format.getImageableY();
        
        double pHeight = format.getImageableHeight();
        double pWidth = format.getImageableWidth();
        
        double xRatio = pWidth / cWidth;
        double yRatio = pHeight / cHeight;
        
        gg.translate(pXStart, pYStart);
        gg.scale(xRatio, yRatio);
        
        //comp.drawImage(gg, 0, 0, null);
        
        gg.drawImage(bi, 0, 0, null);
        
        return Printable.PAGE_EXISTS;
    }
    
    

}