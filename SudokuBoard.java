import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class SudokuBoard { 
   private int [][] board;
   
   public SudokuBoard (String fileName) throws FileNotFoundException {
      board = new int [9][9];
      File f = new File(fileName);
      Scanner s = new Scanner (f);
      
      for (int r = 0; r < 9; r++) {
         if (s.hasNextLine()) {
            String line = s.nextLine();
            for (int c = 0; c < 9; c++){
               char num = line.charAt(c);
               
               if(num == '.') {
                  board[r][c] = 0;
               } else {
                  board[r][c] = num - '0';
               }
            }
         }
         
      }
   }
   
   private boolean isCorrect() {
      for (int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            int number = board[r][c];
            if(number < 0 || number > 9) {
               return false;
            }
         }
      }
      
      return true;
   }
  
   private boolean checkRows() {
      for (int r = 0; r < board.length; r++) {
      
         Map<Integer, Integer> numbers = new HashMap<>();
         
         for(int c = 0; c < board[r].length; c++) {
            int number = board[r][c];
                     
            if(numbers.containsKey(number) && number != 0) {
               return false;            
            } else {
               numbers.put(number, 1);
            }
         }
      }
      return true;
   } 
   
   private boolean checkColumns() {
      for (int c = 0; c < board.length; c++) {
      
         Map<Integer, Integer> numbers = new HashMap<>();
         
         for(int r = 0; r < board.length; r++) {
            int number = board[r][c];
                     
            if(numbers.containsKey(number) && number != 0) {
               return false;            
            } else {
               numbers.put(number, 1);
            }
         }
      }
      return true;
   }
   
   private int[][] miniSquare(int spot) {
      int[][] mini = new int[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            // whoa - wild! This took me a solid hour to figure out (at least)
            // This translates between the "spot" in the 9x9 Sudoku board
            // and a new mini square of 3x3
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   public boolean checkMiniSquares() {      
      for(int i = 1; i < 10; i++) {
         int[][] mini = miniSquare(i);

         Map<Integer, Integer> miniSquare = new HashMap<>();
         
         for (int r = 0; r < mini.length; r++) {
                           
                  for(int c = 0; c < mini[r].length; c++) {
                     int number = mini[r][c];
                              
                     if(miniSquare.containsKey(number) && number != 0) {
                        return false;            
               } else {
                  miniSquare.put(number, 1);
               }
            }
         }
      }
      return true;
   }
   
   public boolean isValid(){
      return isCorrect() && checkRows() && checkColumns() && checkMiniSquares();
   }
   
   public boolean isFull(){
      Map<Integer, Integer> numbers = new HashMap<>();
      
         
      for (int r = 0; r < board.length; r++) {
                        
               for(int c = 0; c < board[r].length; c++) {
                  int number = board[r][c];
                           
                  if(numbers.containsKey(number)) {
                     numbers.put(number,numbers.get(number) + 1);       
            } else {
               numbers.put(number, 1);
            }
         }
      }
      
      for (Integer key : numbers.keySet()) {
         if(numbers.get(key) != 9) { return false; }
      }
      return true;
   }
   
   public boolean isSolved() {
      return isValid() && isFull();
   }
   
   public String toString () {
      String result = "";
      for (int r = 0;r < 9; r++) {
         if (r % 3 == 0) {
            result += "| - - - + - - - + - - - |\n";
         }
         for (int c = 0; c < 9; c++) {
            if(c % 3 == 0) {
               result += "| ";
            }
            if (board[r][c]==0) {
               result += ". ";
            } else {
               result += board[r][c] + " ";
            }
         }
         result += "| \n";
         
      }
      result += "| - - - + - - - + - - - |\n";
      return result;
   }
}

/*
# PROGRAM OUTPUT
 Checking empty board...passed.
 Checking incomplete, valid board...passed.
 Checking complete, valid board...passed.
 Checking dirty data board...passed.
 Checking row violating board...passed.
 Checking col violating board...passed.
 Checking row&col violating board...passed.
 Checking mini-square violating board...passed.
 **** HORRAY: ALL TESTS PASSED ****
*/