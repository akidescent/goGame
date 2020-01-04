import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class GoDriver {

   public static void main(String[] arg) {
      JFrame frame = new JFrame("Go");
      goBoard myPanel = new goBoard();
      frame.setContentPane(myPanel);
      frame.setSize(1060, 1083);	
      frame.setLocation(500,100);		
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

}