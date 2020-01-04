import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {

   private ImageIcon black = new ImageIcon("black.png"), white = new ImageIcon("white.png");
   
   private int fill, row, col;
   private final int BLANK = -1, BLACK = 0, WHITE = 1; //fill values
   
   private int[] tileType;

   public Tile(int[] tt, int r, int c) {
      super(new ImageIcon("regular.png"));
      tileType = tt;
      row = r;
      col = c;
      I();
   }
   
   public void B() {
      setIcon(black);
      fill = BLACK;
   }
   
   public void W() {
      setIcon(white);
      fill = WHITE;
   }
   
   public void I() {
      setIcon(new ImageIcon(tileType[0] + "," + tileType[1] + ".png"));
      fill = BLANK;
   }
   
   public int F() {
      return fill;
   }

   public int R() {
      return row;
   }
   
   public int C() {
      return col;
   }

}