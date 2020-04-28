package FractalArt;

import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SierpinskiFractalPanel extends JPanel implements MainWindowOptions {
   private int                   								startRadius            	= 230;
	private int                   								endsRadius             	= 50;
	private double                								nextRadiusRatio        	= 0.5;
	private double                								angle1                 	= 2 * Math.PI * 48 / 100;
	private double                								angle2                 	= 2 * Math.PI * 16 / 100;
	private double                								angle3						= 2 * Math.PI * 85 / 100;
	private boolean               								colorsEnabled          	= false;
 
	private final Random          								random                 	= new Random();
   private final SliderPanel     								sliderPanel            	= new SliderPanel();
   private final SliderPanel.CircularSlidersPanel			circularSlidersPanel		= sliderPanel.getCircularSlidersPanel();
   private final JInternalFrame 									circularSlidersIFrame 	= new JInternalFrame( "", true, false, false );
   
   SierpinskiFractalPanel() {
   	circularSlidersIFrame.setLayout( new BorderLayout() );
   	circularSlidersIFrame.add( circularSlidersPanel );
		circularSlidersIFrame.setSize( 300,300 );
		circularSlidersIFrame.setDefaultCloseOperation( JInternalFrame.HIDE_ON_CLOSE );
		setLayout( null );
		add( circularSlidersIFrame );
   	
      setLayout( new BorderLayout() );
      add( getSliderPanel(), BorderLayout.WEST );
   }
   
   @Override
   protected void paintComponent( Graphics g ) {
      super.paintComponent( g );
      
      drawSierpinski( g, getWidth()/2, getHeight()/2, startRadius, angle1, angle2, angle3 );
   }
	
	//core drawing method
   private void drawSierpinski( Graphics g, int centreX, int centreY, double radius, double angle1, double angle2, double angle3 ) {
      if( colorsEnabled ) {
         g.setColor( new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256) ) );
      }
   
      int corner1X = (int)( centreX + radius * Math.cos( angle1 ) );
      int corner1Y = (int)( centreY - radius * Math.sin( angle1 ) );
   
      int corner2X = (int)( centreX + radius * Math.cos( angle2 ) );
      int corner2Y = (int)( centreY - radius * Math.sin( angle2 ) );
   
      int corner3X = (int)( centreX + radius * Math.cos( angle3 ) );
      int corner3Y = (int)( centreY - radius * Math.sin( angle3 ) );
      
      int centroidX = ( corner1X + corner2X + corner3X ) / 3;
      int centroidY = ( corner1Y + corner2Y + corner3Y ) / 3;
      
      int toTranslateX = centreX - centroidX;
      int toTranslateY = centreY - centroidY;
      
      corner1X += toTranslateX; corner1Y += toTranslateY;
      corner2X += toTranslateX; corner2Y += toTranslateY;
      corner3X += toTranslateX; corner3Y += toTranslateY;
      
      g.drawLine( corner1X, corner1Y, corner2X, corner2Y );
      g.drawLine( corner2X, corner2Y, corner3X, corner3Y );
      g.drawLine( corner3X, corner3Y, corner1X, corner1Y );
      
      if( radius > endsRadius ) {
         drawSierpinski( g, corner1X, corner1Y, radius * nextRadiusRatio, angle1, angle2, angle3 );
         drawSierpinski( g, corner2X, corner2Y, radius * nextRadiusRatio, angle1, angle2, angle3 );
         drawSierpinski( g, corner3X, corner3Y, radius * nextRadiusRatio, angle1, angle2, angle3 );
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
      private final JSlider					startRadiusSlider    	    = new JSlider( SwingConstants.VERTICAL, 0, 500, 230 );
      private final JSlider					endsRadiusSlider     	    = new JSlider( SwingConstants.VERTICAL, 5, 50,  50 );
      private final JSlider					nextRadiusRatioSlider	    = new JSlider( SwingConstants.VERTICAL, 1, 6,   5  );
      private final JSlider					angle1Slider         	    = new JSlider( SwingConstants.VERTICAL, 0, 100, 48 );
      private final JSlider					angle2Slider	        	    = new JSlider( SwingConstants.VERTICAL, 0, 100, 16 );
      private final JSlider					angle3Slider	        	    = new JSlider( SwingConstants.VERTICAL, 0, 100, 85 );
      
      private final JLabel			         startRadiusLabel           = new JLabel( "Size" );
      private final JLabel			         endsRadiusLabel            = new JLabel( "Ends" );
      private final JLabel			         nextRadiusRatioLabel       = new JLabel( "Ratio" );
      private final JLabel			         angle1Label                = new JLabel( "Angle1" );
      private final JLabel			         angle2Label                = new JLabel( "Angle2" );
      private final JLabel			         angle3Label						= new JLabel( "Angle3" );
      
      private final CircularSlidersPanel 	circularSlidersPanel 		= new CircularSlidersPanel();
      
      private final JCheckBox					showCircularSliders			= new JCheckBox();
      
      private SliderPanel() {
			setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
         
         UtilityClass.setAntiClockwiseVerticalLabelUI( startRadiusLabel,
                                                       endsRadiusLabel,
                                                       nextRadiusRatioLabel,
                                                       angle1Label,
                                                       angle2Label,
                                                       angle3Label );
         
         ChangeListener changeListener = new SliderChangeListener();
         
         startRadiusSlider.addChangeListener( 		changeListener 			);
         endsRadiusSlider.addChangeListener( 		changeListener 			);
         nextRadiusRatioSlider.addChangeListener( 	changeListener 			);
         angle1Slider.addChangeListener( 				changeListener 			);
         angle2Slider.addChangeListener( 				changeListener 			);
         angle3Slider.addChangeListener( 				changeListener 			);
			circularSlidersPanel.addKnob1Handler( 		new KnobHandler() {
				@Override
				public void knobMoved( double knobAngle ) {
					setAngle1( knobAngle );
					SierpinskiFractalPanel.this.repaint();
			
					System.out.println( getAngle1() );
				}
			} );
			circularSlidersPanel.addKnob2Handler( 		new KnobHandler() {
				@Override
				public void knobMoved( double knobAngle ) {
					setAngle2( knobAngle );
					SierpinskiFractalPanel.this.repaint();
			
					System.out.println( getAngle2() );
				}
			} );
			circularSlidersPanel.addKnob3Handler( 		new KnobHandler() {
				@Override
				public void knobMoved( double knobAngle ) {
					setAngle3( knobAngle );
					SierpinskiFractalPanel.this.repaint();
			
					System.out.println( getAngle3() );
				}
			} );
         
         add( startRadiusSlider );
         add( startRadiusLabel );
         
         add( endsRadiusSlider );
         add( endsRadiusLabel );
         
         add( nextRadiusRatioSlider );
         add( nextRadiusRatioLabel );
   
         add( showCircularSliders );
         
         showCircularSliders.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed( ActionEvent e ) {
					if( showCircularSliders.isSelected() ) {
						circularSlidersIFrame.show();
					}
					else {
						circularSlidersIFrame.hide();
					}
				}
			} );
      }
      
      private class SliderChangeListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            if( e.getSource() == startRadiusSlider ) {
               setStartRadius( startRadiusSlider.getValue() );
               SierpinskiFractalPanel.this.repaint();
   
               System.out.println( getStartRadius() );
            }
            else if( e.getSource() == endsRadiusSlider ) {
               setEndsRadius( endsRadiusSlider.getValue() );
               SierpinskiFractalPanel.this.repaint();
   
               System.out.println( getEndsRadius() );
            }
            else if( e.getSource() == nextRadiusRatioSlider ) {
               setNextRadiusRatio( (double) nextRadiusRatioSlider.getValue() / 10 );
               SierpinskiFractalPanel.this.repaint();
   
               System.out.println( getNextRadiusRatio() );
            }
            else if( e.getSource() == angle1Slider ) {
               setAngle1( 2 * Math.PI * angle1Slider.getValue() / 100 );
               SierpinskiFractalPanel.this.repaint();
	
					System.out.println( angle1Slider.getValue() );
					System.out.println( getAngle1() );
            }
            else if( e.getSource() == angle2Slider ) {
               setAngle2( 2 * Math.PI * angle2Slider.getValue() / 100 );
               SierpinskiFractalPanel.this.repaint();
	
					System.out.println( angle2Slider.getValue() );
               System.out.println( getAngle2() );
            }
            else if( e.getSource() == angle3Slider ) {
               setAngle3( 2 * Math.PI * angle3Slider.getValue() / 100 );
               SierpinskiFractalPanel.this.repaint();
	
					System.out.println( angle3Slider.getValue() );
               System.out.println( getAngle3() );
            }
            else {
               JOptionPane.showMessageDialog( null, "Error in " + getClass().getName() );
            }
         }
      }
	
		private class CircularSlidersPanel extends JPanel {
			private final int 								KNOB_RADIUS 			= 10;
			private final int 								PADDING		 			= 10;
		
			private final ArrayList<KnobHandler> 		knob1Handlers 			= new ArrayList<>();
			private final ArrayList<KnobHandler> 		knob2Handlers 			= new ArrayList<>();
			private final ArrayList<KnobHandler>		knob3Handlers 			= new ArrayList<>();
			
			private double 									knob1Angle 				= angle1;
			private double 									knob2Angle 				= angle2;
			private double 									knob3Angle 				= angle3;
			
			private final Point 								knob1Centre 			= new Point();
			private final Point 								knob2Centre 			= new Point();
			private final Point 								knob3Centre 			= new Point();
		
			CircularSlidersPanel() {
				MouseAdapter m = new KnobSlidingHandler();
				
				addMouseMotionListener( m );
				addMouseListener( m );
			
				setPreferredSize( new Dimension( 200,200 ) );
			}
		
			@Override
			protected void paintComponent( Graphics g ) {
				super.paintComponent( g );
			
				int width = getWidth();
				int height = getHeight();
			
				int radius = ( width < height ? width/2 : height/2 ) - PADDING;
			
				drawCircle( g, width/2, height/2, radius, false );
			
				knob1Centre.x = width/2  + (int)( radius * Math.cos( knob1Angle ) );
				knob1Centre.y = height/2 - (int)( radius * Math.sin( knob1Angle ) );
			
				knob2Centre.x = width/2  + (int)( radius * Math.cos( knob2Angle ) );
				knob2Centre.y = height/2 - (int)( radius * Math.sin( knob2Angle ) );
			
				knob3Centre.x = width/2  + (int)( radius * Math.cos( knob3Angle ) );
				knob3Centre.y = height/2 - (int)( radius * Math.sin( knob3Angle ) );
			
				drawTriangle( g, knob1Centre, knob2Centre, knob3Centre );
			
				drawCircle( g, knob1Centre.x, knob1Centre.y, KNOB_RADIUS, true );
				drawCircle( g, knob2Centre.x, knob2Centre.y, KNOB_RADIUS, true );
				drawCircle( g, knob3Centre.x, knob3Centre.y, KNOB_RADIUS, true );
			}
		
			private void drawTriangle( Graphics g, Point corner1, Point corner2, Point corner3 ) {
				Color lastColor = g.getColor();
				
				g.setColor( Color.RED );
			
				g.drawPolygon( new int[] { corner1.x, corner2.x, corner3.x },
									new int[] { corner1.y, corner2.y, corner3.y },
									3 );
			
				g.setColor( lastColor );
			}
		
			private void drawCircle( Graphics g, int centreX, int centreY, int radius, boolean filled ) {
				if( filled ) {
					g.fillOval( centreX - radius, centreY - radius, 2 * radius, 2 * radius );
				}
				else {
					g.drawOval( centreX - radius, centreY - radius, 2 * radius, 2 * radius );
				}
			}
		
			public void addKnob1Handler( KnobHandler knobHandler ) {
				knob1Handlers.add( knobHandler );
			}
		
			public void addKnob2Handler( KnobHandler knobHandler ) {
				knob2Handlers.add( knobHandler );
			}
		
			public void addKnob3Handler( KnobHandler knobHandler ) {
				knob3Handlers.add( knobHandler );
			}
			
			private class KnobSlidingHandler extends MouseAdapter {
				private boolean knob1Pressed;
				private boolean knob2Pressed;
				private boolean knob3Pressed;
				
				@Override
				public void mousePressed( MouseEvent e ) {
					if( distance( e.getX(), e.getY(), knob1Centre.x, knob1Centre.y ) <= KNOB_RADIUS ) {
						knob1Pressed = true;
					}
					else if( distance( e.getX(), e.getY(), knob2Centre.x, knob2Centre.y ) <= KNOB_RADIUS ) {
						knob2Pressed = true;
					}
					else if( distance( e.getX(), e.getY(), knob3Centre.x, knob3Centre.y ) <= KNOB_RADIUS ) {
						knob3Pressed = true;
					}
				}
			
				@Override
				public void mouseReleased( MouseEvent e ) {
					knob1Pressed = false;
					knob2Pressed = false;
					knob3Pressed = false;
				}
			
				@Override
				public void mouseDragged( MouseEvent e ) {
					if( knob1Pressed ) {
						int width = CircularSlidersPanel.this.getWidth();
						int height = CircularSlidersPanel.this.getHeight();
					
						knob1Angle = Math.atan2( ( height / 2 - e.getY() ), ( e.getX() - width / 2 ) );
						
						CircularSlidersPanel.this.repaint();
						
						for( KnobHandler x : knob1Handlers ) {
							x.knobMoved( knob1Angle );
						}
					}
					else if( knob2Pressed ) {
						int width = CircularSlidersPanel.this.getWidth();
						int height = CircularSlidersPanel.this.getHeight();
						
						knob2Angle = Math.atan2( ( height/2 - e.getY() ), ( e.getX() - width/2 ) );
						
						CircularSlidersPanel.this.repaint();
						
						for( KnobHandler x: knob2Handlers ) {
							x.knobMoved( knob2Angle );
						}
					}
					else if( knob3Pressed ) {
						int width = CircularSlidersPanel.this.getWidth();
						int height = CircularSlidersPanel.this.getHeight();
						
						knob3Angle = Math.atan2( ( height / 2 - e.getY() ), ( e.getX() - width / 2 ) );
						
						CircularSlidersPanel.this.repaint();
						
						for( KnobHandler x: knob3Handlers ) {
							x.knobMoved( knob3Angle );
						}
					}
				}
			
				private int distance( int x1, int y1, int x2, int y2 ) {
					return (int)( Math.sqrt( Math.pow( x1 - x2, 2 ) + Math.pow( y1 - y2, 2 ) ) );
				}
			}
		}
		
		public CircularSlidersPanel getCircularSlidersPanel() {
      	return circularSlidersPanel;
		}
   }
   
   //getters and setters start
   public SliderPanel getSliderPanel() {
      return sliderPanel;
   }
   
   public boolean isColorsEnabled() {
      return colorsEnabled;
   }
   
   public SierpinskiFractalPanel setColorsEnabled( boolean colorsEnabled ) {
      this.colorsEnabled = colorsEnabled;
      return this;
   }
   
   public int getStartRadius() {
      return startRadius;
   }
   
   public SierpinskiFractalPanel setStartRadius( int startRadius ) {
      this.startRadius = startRadius;
      return this;
   }
   
   public int getEndsRadius() {
      return endsRadius;
   }
   
   public SierpinskiFractalPanel setEndsRadius( int endsRadius ) {
      this.endsRadius = endsRadius;
      return this;
   }
   
   public double getNextRadiusRatio() {
      return nextRadiusRatio;
   }
   
   public SierpinskiFractalPanel setNextRadiusRatio( double nextRadiusRatio ) {
      this.nextRadiusRatio = nextRadiusRatio;
      return this;
   }
   
   public double getAngle1() {
      return angle1;
   }
   
   public SierpinskiFractalPanel setAngle1( double angle1 ) {
      this.angle1 = angle1;
      return this;
   }
   
   public double getAngle2() {
      return angle2;
   }
   
   public SierpinskiFractalPanel setAngle2( double angle2 ) {
      this.angle2 = angle2;
      return this;
   }
   
   public double getAngle3() {
      return angle3;
   }
   
   public SierpinskiFractalPanel setAngle3( double angle3 ) {
      this.angle3 = angle3;
      return this;
   }
   //getters and setters end
}
