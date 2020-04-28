package FractalArt;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Random;

public class FractalTreePanel extends JPanel implements MainWindowOptions {
   private final Random             random                = new Random();
   private final SliderPanel        sliderPanel           = new SliderPanel();
   
   private boolean                  colorsEnabled         = false;
   
   private int                      initialSize           = 134;
   private int                      minBranchSize         = 15;
   private double                   nextBranchRatio       = 0.8;
   private double                   branchAngle           = 2 * Math.PI * 36/300;
   
   FractalTreePanel() {
      setLayout( new BorderLayout() );
      add( getSliderPanel(), BorderLayout.WEST );
   }
   
   @Override
   public void paintComponent( Graphics g ) {
      super.paintComponent( g );
      
      drawFractalTree( g, getWidth()/2, getHeight() - getHeight() / 10, initialSize, Math.PI / 2 );
      
      g.drawLine( getWidth()/2, getHeight() - getHeight() / 10, getWidth()/2, getHeight() );
   }
   
   //core drawing method
   private void drawFractalTree( Graphics g, int x, int y, int size, double angle ) {
      if( colorsEnabled ) {
         g.setColor( new Color( random.nextInt(256) , random.nextInt(256), random.nextInt(256) ) );
      }
   
      int mainBranchEndX = x + (int)( size * Math.cos( angle ) );
      int mainBranchEndY = y - (int)( size * Math.sin( angle ) );
      
      g.drawLine( x, y, mainBranchEndX, mainBranchEndY );
      
      if( size > minBranchSize ) {
         int nextBrachSize = (int)(size* nextBranchRatio );
         
         drawFractalTree( g, mainBranchEndX, mainBranchEndY, nextBrachSize, rightBranchAngle( angle ) );
         drawFractalTree( g, mainBranchEndX, mainBranchEndY, nextBrachSize, leftBranchAngle( angle ) );
      }
   }
   
   //helper functions
   private double rightBranchAngle( double previousBranchAngle ) {
      return ( 2*Math.PI - branchAngle ) / 2 - ( Math.PI - previousBranchAngle );
   }
   private double leftBranchAngle( double previousBranchAngle ) {
      return rightBranchAngle( previousBranchAngle ) + branchAngle;
   }
	//helper functions end
   
   @Override
   public void enableDarkTheme() {
      setBackground( UtilityClass.DARK_THEME_COLOR );
      setForeground( Color.WHITE );
      repaint();
   }
   
   @Override
   public void disableDarkTheme() {
      setBackground( Color.WHITE );
      setForeground( Color.BLACK );
      repaint();
   }
   
   @Override
   public void enableColors() {
      setColorsEnabled( true );
      repaint();
   }
   
   @Override
   public void disableColors() {
      setColorsEnabled( false );
      repaint();
   }
   
   private class SliderPanel extends JPanel {
      private final JSlider     initialSizeSlider        = new JSlider( SwingConstants.VERTICAL, 20, 200, 72 );
      private final JSlider     minBranchSizeSlider      = new JSlider( SwingConstants.VERTICAL, 9,  50,  15 );
      private final JSlider     nextBranchRatioSlider    = new JSlider( SwingConstants.VERTICAL, 0,  80,  80 );
      private final JSlider     branchAngleSlider        = new JSlider( SwingConstants.VERTICAL, 0,  300, 36 );
      
      private final JLabel      initialSizeLabel         = new JLabel( "Size" );
      private final JLabel      minBranchSizeLabel       = new JLabel( "Ends" );
      private final JLabel      nextBranchRatioLabel     = new JLabel( "Ratio" );
      private final JLabel      branchAngleLabel         = new JLabel( "Angle" );
      
      SliderPanel() {
         setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
         
         UtilityClass.setAntiClockwiseVerticalLabelUI( initialSizeLabel,
                                                       minBranchSizeLabel,
                                                       nextBranchRatioLabel,
                                                       branchAngleLabel );
   
         initialSizeSlider.addChangeListener( new InitialSizeSliderChangeListener() );
         minBranchSizeSlider.addChangeListener( new MinBranchSizeSliderChangeListener() );
         nextBranchRatioSlider.addChangeListener( new NextBranchRatioChangeListener() );
         branchAngleSlider.addChangeListener( new BranchAngleSliderChangeListener() );
   
         add( initialSizeSlider );
         add( initialSizeLabel );
         
         add( minBranchSizeSlider );
         add( minBranchSizeLabel );
         
         add( nextBranchRatioSlider );
         add( nextBranchRatioLabel );
         
         add( branchAngleSlider );
         add( branchAngleLabel );
      }
   
      private class InitialSizeSliderChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setInitialSize( 2 * ( (JSlider)e.getSource() ).getValue() );
            FractalTreePanel.this.repaint();
      
            System.out.println( initialSize );
         }
      }
   
      private class MinBranchSizeSliderChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setMinBranchSize( ( (JSlider)e.getSource() ).getValue() );
            FractalTreePanel.this.repaint();

            System.out.println( minBranchSize );
         }
      }
   
      private class NextBranchRatioChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setNextBranchRatio( (double) ( (JSlider)e.getSource() ).getValue() / 100 );
            FractalTreePanel.this.repaint();

            System.out.println( nextBranchRatio );
         }
      }
   
      private class BranchAngleSliderChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setBranchAngle( 2 * Math.PI * ( (JSlider)e.getSource() ).getValue() / 300 );
            FractalTreePanel.this.repaint();
      
            System.out.println( branchAngle );
            System.out.println( branchAngleSlider.getValue() );
         }
      }
   }
   
   //getters and setters start
   public void setMinBranchSize( int minBranchSize ) {
      this.minBranchSize = minBranchSize;
   }
   
   public void setBranchAngle( double branchAngle ) {
      this.branchAngle = branchAngle;
   }
   
   public SliderPanel getSliderPanel() {
      return sliderPanel;
   }
   
   public void setInitialSize( int initialSize ) {
      this.initialSize = initialSize;
   }
   
   public void setNextBranchRatio( double nextBranchRatio ) {
      this.nextBranchRatio = nextBranchRatio;
   }
   
   public FractalTreePanel setColorsEnabled( boolean colorsEnabled ) {
      this.colorsEnabled = colorsEnabled;
      return this;
   }
   
   //getters and setters end
}
