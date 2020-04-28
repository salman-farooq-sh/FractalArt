package FractalArt;

import javax.swing.*;
import java.awt.*;

public class HilbertCurvePanel extends JPanel {
   private final int      NORTH     = 0;
   private final int      SOUTH     = 1;
   private final int      EAST      = 2;
   private final int      WEST      = 3;
   
   HilbertCurvePanel() {
   
   }
   @Override
   protected void paintComponent( Graphics g ) {
      super.paintComponent( g );
      
      drawHilbert( (Graphics2D)g, 10,10, 300, NORTH, 1 );
   }
   
   private void drawHilbert( Graphics2D g2d, int x, int y, int sideLength, int orientation, int order ) {
      JPanel panel = new JPanel();
      
      panel.getGraphics().drawLine( x,            y, x+sideLength, y            );
      panel.getGraphics().drawLine( x,            y, x           , y+sideLength );
      panel.getGraphics().drawLine( x+sideLength, y, x+sideLength, y+sideLength );
   }
   
   private void drawPartialSquare( Graphics g, int x, int y ) {
   
   }
   
   
}

class Ma {
   public static void main( String[] args ) {
      JFrame frame = new JFrame();
      
      frame.add( new HilbertCurvePanel() );
      
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      frame.setSize( 600,600 );
      frame.setVisible( true );
   }
}
