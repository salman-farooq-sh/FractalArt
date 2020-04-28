package FractalArt;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainWindow extends JFrame {
   private final String                STAR_FRACTAL               = "Star";
   private final String                FRACTAL_TREE               = "Tree";
   private final String                SIERPINSKI                 = "Sierpinski";
   
   private final JPanel                fractalTreePanel           = new FractalTreePanel();
   private final JPanel                starFractalPanel           = new StarFractalPanel();
   private final JPanel                sierpinskiPanel            = new SierpinskiFractalPanel();
   private final JPanel                cards                      = new JPanel( new CardLayout() );
   
   public MainWindow() {
      setLayout( new BorderLayout() );
      
      cards.add( fractalTreePanel, FRACTAL_TREE );
      cards.add( starFractalPanel, STAR_FRACTAL );
      cards.add( sierpinskiPanel,  SIERPINSKI );
      
      add( cards );
      add( new OptionsPanel(), BorderLayout.EAST );
      
      setMinimumSize( new Dimension( 800, 500 ) );
      setSize( 1200, 700 );
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setLocationRelativeTo( null );
      setVisible( true );
   }
   
   private class OptionsPanel extends JPanel {
      private final JCheckBox				colorsEnabledCheckBox       = new JCheckBox( "Colors" );
      private final JCheckBox				darkThemeEnabledCheckBox    = new JCheckBox( "Dark Theme" );
      
      private final String[] 				fractalChoicesStrings       = { FRACTAL_TREE, STAR_FRACTAL, SIERPINSKI };
      private final JComboBox				fractalChoicesComboBox      = new JComboBox( fractalChoicesStrings );
      
      OptionsPanel() {
         setLayout( new GridLayout( 20, 1 ) );
         
         colorsEnabledCheckBox.addChangeListener(      new ColorsEnabledCheckBoxActionListener()      );
         darkThemeEnabledCheckBox.addChangeListener(   new DarkThemeEnabledCheckBoxActionListener()   );
         fractalChoicesComboBox.addActionListener(     new FractalChoicesComboxBoxActionListener()    );
         
         add( colorsEnabledCheckBox );
         add( darkThemeEnabledCheckBox );
         add( fractalChoicesComboBox );
         
         colorsEnabledCheckBox.doClick();
         darkThemeEnabledCheckBox.doClick();
      }
      
      private class ColorsEnabledCheckBoxActionListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            if( ( (JCheckBox) e.getSource() ).isSelected() ) {
               ( (MainWindowOptions) starFractalPanel ).enableColors();
               ( (MainWindowOptions) fractalTreePanel ).enableColors();
               ( (MainWindowOptions) sierpinskiPanel ).enableColors();
            }
            else {
               ( (MainWindowOptions) starFractalPanel ).disableColors();
               ( (MainWindowOptions) fractalTreePanel ).disableColors();
               ( (MainWindowOptions) sierpinskiPanel ).disableColors();
            }
         }
      }
      
      private class DarkThemeEnabledCheckBoxActionListener implements ChangeListener {
         @Override
         public void stateChanged( ChangeEvent e ) {
            if( ( (JCheckBox) e.getSource() ).isSelected() ) {
               ( (MainWindowOptions) starFractalPanel ).enableDarkTheme();
               ( (MainWindowOptions) fractalTreePanel ).enableDarkTheme();
               ( (MainWindowOptions) sierpinskiPanel ).enableDarkTheme();
            }
            else {
               ( (MainWindowOptions) starFractalPanel ).disableDarkTheme();
               ( (MainWindowOptions) fractalTreePanel ).disableDarkTheme();
               ( (MainWindowOptions) sierpinskiPanel ).disableDarkTheme();
            }
         }
      }
      
      private class FractalChoicesComboxBoxActionListener implements ActionListener {
         @Override
         public void actionPerformed( ActionEvent e ) {
            switch( (String) ( (JComboBox) e.getSource() ).getSelectedItem() ) {
               case STAR_FRACTAL:
                  ( (CardLayout) cards.getLayout() ).show( cards, STAR_FRACTAL );
                  break;
                  
               case FRACTAL_TREE:
                  ( (CardLayout) cards.getLayout() ).show( cards, FRACTAL_TREE );
                  break;
                  
               case SIERPINSKI:
                  ( (CardLayout) cards.getLayout() ).show( cards, SIERPINSKI );
                  break;
   
            }
         }
      }
   }
}
