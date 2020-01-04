import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class goBoard extends JPanel {

   private Tile[][] board = new Tile[19][19];
   private ArrayList<ArrayList<Tile>> groups = new ArrayList<ArrayList<Tile>>();
   private JPanel grid = new JPanel();
   private boolean turn = true; //true for P1, false for P2
   
   //pre:
   //post: calls all secondary methods to set up board
   public goBoard() {
      setLayout(new BorderLayout());
      grid.setLayout(new GridLayout(19,19));
      add(grid, BorderLayout.CENTER);
      fillBoard();
   }

   private class cellSelect implements ActionListener {
      
      private int row, col;
      
      public cellSelect(int r, int c) {
         row = r;
         col = c;
      }
      
      public void actionPerformed(ActionEvent e) {
         if(board[row][col].F() == -1) {
            if(turn)
               board[row][col].B();
            else
               board[row][col].W();
            turn = !turn;
            groupStone(row,col);
            for(int i = 0; i < groups.size(); i++) {
               if(!checkGroup(groups.get(i))) {
                  for(Tile tile:groups.get(i)) {
                     tile.I();
                  }
                  groups.remove(i);
               }
            }
         }
         repaint();
      }
   }  
   
   //pre: valid position and Tile object exists at position
   //post: stone is added to ArrayList of all touching tiles of same color, new group is created
   private void groupStone(int r, int c) {
      boolean added = false;
      ArrayList<Tile> addGroup = new ArrayList<Tile>(); //for adding all touching Tiles of same color to
      for(int i = 0; i < groups.size(); i++) { //checks if Tile is by any existing groups of same color
         if(r < 18 && board[r][c].F() == board[r+1][c].F() && groups.get(i).contains(board[r+1][c])) {
            if(!added) { //if this is the first group that the Tile is a part of, add it to the group
               groups.get(i).add(board[r][c]);
               addGroup = groups.get(i);
               added = true;
            }
            else { //else, combine this group with the Tile and the adjacent group it's been placed in
               addGroup.addAll(groups.remove(i));
            }
         }
         else if(r > 0 && board[r][c].F() == board[r-1][c].F() && groups.get(i).contains(board[r-1][c])) {
            if(!added) {
               groups.get(i).add(board[r][c]);
               addGroup = groups.get(i);
               added = true;
            }
            else {
               addGroup.addAll(groups.remove(i));
            }
         }
         else if(c < 18 && board[r][c].F() == board[r][c+1].F() && groups.get(i).contains(board[r][c+1])) {
            if(!added) {
               groups.get(i).add(board[r][c]);
               addGroup = groups.get(i);
               added = true;
            }
            else {
               addGroup.addAll(groups.remove(i));
            }
         }
         else if(c > 0 && board[r][c].F() == board[r][c-1].F() && groups.get(i).contains(board[r+1][c-1])) {
            if(!added) {
               groups.get(i).add(board[r][c]);
               addGroup = groups.get(i);
               added = true;
            }
            else {
               addGroup.addAll(groups.remove(i));
            }
         }
      }
      if(!added) {
         ArrayList<Tile> newGroup = new ArrayList<Tile>();
         newGroup.add(board[r][c]);
         groups.add(newGroup);
      }
   }
   
   //pre: valid position and Tile object at position
   //post: returns whether singular stone is alive or dependent on the group
   private boolean checkGroup(ArrayList<Tile> g) {
      boolean alive = false;
      int row, col;
      for(Tile t:g) {
         row = t.R();
         col = t.C();
         if(row < 18 && board[row+1][col].F() == -1) //checks validity and fill of orthogonal tiles
            alive = true;
         else if(row > 0 && board[row-1][col].F() == -1)
            alive = true;
         else if(col < 18 && board[row][col+1].F() == -1)
            alive = true;
         else if(col > 0 && board[row][t.C()-1].F() == -1)
            alive = true;
      }
      return alive;
   }
   
   //pre:
   //post: fills grid with instantiated Tile array
   private void fillBoard() {
      for(int r = 0; r < 19; r++) {
         for(int c = 0; c < 19; c++) {
            board[r][c] = new Tile(tileType(r,c), r, c);                //creates new tile based on tileType
            board[r][c].addActionListener(new cellSelect(r,c));         //adds an ActionListener for when tile is clicked
            board[r][c].setBorder(BorderFactory.createEmptyBorder());   //from StackOverflow; removes borders between tiles
            grid.add(board[r][c]);
         }
      }
   }
   
   //pre: two valid indices between [0,18]
   //post: returns an array representing the type of tile at specified indices
   //      first number represents row type, second number represents column type
   //      -1 = first row/col, 1 = last row/col, 0 = in-between
   private int[] tileType(int row, int col) {
      int[] array = new int[2];
      if(row % 6 == 3 && col % 6 == 3) { //checks if tile is a dot
         array[0] = 2;
         array[1] = 2;
         return array;
      }
      switch(row) { //determines first array number based on row #
         case 0: array[0] = -1;
         case 18: array[0]  = 1;
         default: array[0]  = 0;
      }
      switch(col) { //determines second array number based on column #
         case 0: array[1]  = -1;
         case 18: array[1]  = 1;
         default: array[1]  = 0;
      }
      return array;
   }
   
}