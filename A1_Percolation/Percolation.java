import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.FileReader;

public class Percolation {
   private int n, numOpenSites;
   private WeightedQuickUnionUF uf;
   private boolean[] blocked;
   private boolean[] full;
   
   public Percolation(int num) {
      // check whether the parameter is valid
       if (num <= 0) {
         throw new IllegalArgumentException();
       }
       
      // create n-by-n grid, with all sites blocked
      n = num;
      uf = new WeightedQuickUnionUF(n*n+2);
      blocked = new boolean[n*n+2]; // site 0 and n*n+1 is the virtual site
      full = new boolean[n*n+2];
      
      for (int i = 1; i <= n*n; ++i) blocked[i] = true;
      
      // set number of opensites as zero
      numOpenSites = 0;
   }
   
   private int index(int row, int col) {
      // return index of the site in the array
      if (row <= 0 || row > n || col <= 0 || col > n) {
         throw new IndexOutOfBoundsException();
      }
      return (row-1)*n + col;
   }
   
   public void open(int row, int col) {
      // open site (row, col) if it is not open already
      int id = index(row, col);
      if (!isOpen(row, col)) {
         numOpenSites++;
         blocked[id] = false;
         // Build connections
         if (row - 1 > 0 && isOpen(row-1, col)) {
            int q = index(row-1, col);
            uf.union(id, q); 
         }
         if (row + 1 <= n && isOpen(row+1, col)) {
            int q = index(row+1, col);
            uf.union(id, q); 
         }
         if (col - 1 > 0 && isOpen(row, col-1)) {
            int q = index(row, col-1);
            uf.union(id, q); 
         } 
         if (col + 1 <= n && isOpen(row, col+1)) {
            int q = index(row, col+1);
            uf.union(id, q); 
         }
         // build connections on virtual sites
         if (row == 1) {
             uf.union(0, id);
             full[id] = true;
         }
         if (row == n) {
             uf.union(id, n*n+1);
             if (full[id]) full[n*n+1] = true;
         }
      }
   }
   
   public boolean isOpen(int row, int col) {
      // is site (row, col) open?
      return !blocked[index(row, col)];
   }
   

   public boolean isFull(int row, int col) {
      // is site (row, col) full?
       if (!full[index(row, col)]) {
           full[index(row, col)] = uf.connected(0, index(row, col));
       }
       return full[index(row, col)];
   }
   
    public int numberOfOpenSites() {
       // number of open sites
       return numOpenSites;
    }
    
    public boolean percolates() {
       // does the system percolate?
        if (!full[n*n+1]) {
            full[n*n+1] = uf.connected(0, n*n+1);
        }
       return full[n*n+1];
    }
    
//    private void print(String s) {
//        System.out.println(s);
//    }
//    
////    public static void main(String[] args) {
////        String fileName="./test/input1.txt";
////        try {
////            FileReader reader = new FileReader(fileName);
////            int n = reader.read();
////            Percolation p = new Percolation(n);
////            System.out.println((p.isOpen(1, 1)));
////            System.out.println((p.isFull(1, 1)));
////            System.out.println((p.percolates()));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        
////    }
}
