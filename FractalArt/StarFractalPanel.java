package FractalArt;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Random;

public class StarFractalPanel extends JPanel implements MainWindowOptions  {
   private final Random          random                 = new Random();
   private final SliderPanel     sliderPanel            = new SliderPanel();
   
   private boolean               colorsEnabled          = false;
   private int                   initialArmLength       = 210;
   private int                   minArmSize             = 82;
   private int                   numOfArms              = 20;
   private double                nextArmRatio           = 0.4;
   
   StarFractalPanel() {
      setLayout( new BorderLayout() );
      add( getSliderPanel(), BorderLayout.WEST );
   }
   
   @Override
   public void paintComponent( Graphics g ) {
      super.paintComponent( g );
      
      drawStarFractal( g, getWidth()/2, getHeight()/2, initialArmLength );
   }
	
	//core drawing method
   private void drawStarFractal( Graphics g, int centreX, int centreY, int armLength ) {
      if( colorsEnabled ) {
         g.setColor( new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256) ) );
      }
      
      int endPointX;
      int endPointY;
      
      for( int i = 0; i < numOfArms; i++ ) {
         endPointX = centreX + (int)( armLength * Math.cos( i * 2 * Math.PI / numOfArms + Math.PI/2 ) );
         endPointY = centreY - (int)( armLength * Math.sin( i * 2 * Math.PI / numOfArms + Math.PI/2 ) );
         
         g.drawLine( centreX, centreY, endPointX, endPointY );
         
         if( armLength > minArmSize ) {
            drawStarFractal( g, endPointX, endPointY, (int)(armLength * nextArmRatio) );
         }
      }
   }
   
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
      private final JSlider         initialArmLengthSlider        = new JSlider( SwingConstants.VERTICAL, 10, 150, 105 );
      private final JSlider         minArmSizeSlider              = new JSlider( SwingConstants.VERTICAL, 20, 100, 82 );
      private final JSlider         numOfArmsSlider               = new JSlider( SwingConstants.VERTICAL, 3, 20, 20 );
      private final JSlider         nextArmRatioSlider            = new JSlider( SwingConstants.VERTICAL, 10, 40, 40 );
   
      private final JLabel          initialArmLengthLabel         = new JLabel( "Size",  SwingConstants.CENTER );
      private final JLabel          minArmSizeLabel               = new JLabel( "Ends",  SwingConstants.CENTER );
      private final JLabel          numOfArmsLabel                = new JLabel( "Arms",  SwingConstants.CENTER );
      private final JLabel          nextArmRatioLabel             = new JLabel( "Ratio", SwingConstants.CENTER );
      
      SliderPanel() {
         setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
   
         UtilityClass.setAntiClockwiseVerticalLabelUI( initialArmLengthLabel,
                                                       minArmSizeLabel,
                                                       numOfArmsLabel,
                                                       nextArmRatioLabel );
         
         initialArmLengthSlider.addChangeListener( new InitialArmLengthChangeListener() );
         minArmSizeSlider.addChangeListener( new MinArmSizeSliderChangeListener() );
         numOfArmsSlider.addChangeListener( new NumOfArmsChangeListener() );
         nextArmRatioSlider.addChangeListener( new NextArmRatioChangeListener() );
         
         add( initialArmLengthSlider );
         add( initialArmLengthLabel );
         
         add( minArmSizeSlider );
         add( minArmSizeLabel );
         
         add( numOfArmsSlider );
         add( numOfArmsLabel );
         
         add( nextArmRatioSlider );
         add( nextArmRatioLabel );
      }
   
      private class InitialArmLengthChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setInitialArmLength( 2 * ( (JSlider)e.getSource() ).getValue() );
            StarFractalPanel.this.repaint();
            
            System.out.println( initialArmLength );
         }
      }
      
      private class MinArmSizeSliderChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setMinArmSize( ( (JSlider)e.getSource() ).getValue() );
            StarFractalPanel.this.repaint();
   
            System.out.println( minArmSize );
         }
      }
   
      private class NumOfArmsChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setNumOfArms( ( (JSlider)e.getSource() ).getValue() );
            StarFractalPanel.this.repaint();
            
            System.out.println( numOfArms );
         }
      }
   
      private class NextArmRatioChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            setNextArmRatio( (double)( (JSlider)e.getSource() ).getValue()/100 );
            StarFractalPanel.this.repaint();
            
            System.out.println( nextArmRatio );
         }
      }
   }
   
   //getters and setters start
   public StarFractalPanel setNextArmRatio( double nextArmRatio ) {
      this.nextArmRatio = nextArmRatio;
      return this;
   }
   
   public StarFractalPanel setNumOfArms( int numOfArms ) {
      this.numOfArms = numOfArms;
      return this;
   }
   
   public StarFractalPanel setInitialArmLength( int initialArmLength ) {
      this.initialArmLength = initialArmLength;
      return this;
   }
   
   public StarFractalPanel setMinArmSize( int minArmSize ) {
      this.minArmSize = minArmSize;
      return this;
   }
   
   public SliderPanel getSliderPanel() {
      return sliderPanel;
   }
   
   public StarFractalPanel setColorsEnabled( boolean colorsEnabled ) {
      this.colorsEnabled = colorsEnabled;
      return this;
   }
   //getters and setters end
}













