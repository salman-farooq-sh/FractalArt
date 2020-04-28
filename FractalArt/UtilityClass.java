package FractalArt;

import javax.swing.*;
import javax.swing.plaf.LabelUI;
import java.awt.*;

public class UtilityClass {
   public static final Color             DARK_THEME_COLOR                     = new Color( 30,30,30 );
   public static final LabelUI           CLOCKWISE_VERTICAL_LABEL_UI          = new VerticalLabelUI( true );
   public static final LabelUI           ANTI_CLOCKWISE_VERTICAL_LABEL_UI     = new VerticalLabelUI( false );
   
   public static String stringToHTML( String strToTransform ) {
      StringBuffer answer = new StringBuffer( "<html>" );
      String brTag = "<br>";
      String[] lettersArr = strToTransform.split("");
      
      for (String letter : lettersArr) {
         answer.append( letter + brTag ) ;
      }
      answer.append( "</html>" ) ;
      
      return answer.toString();
   }
   
   public static void setClockwiseVerticalLabelUI( JLabel ... labels ) {
      for( JLabel label : labels ) {
         label.setUI( CLOCKWISE_VERTICAL_LABEL_UI );
      }
   }
   
   public static void setAntiClockwiseVerticalLabelUI( JLabel ... labels ) {
      for( JLabel label : labels ) {
         label.setUI( ANTI_CLOCKWISE_VERTICAL_LABEL_UI );
      }
   }
}
