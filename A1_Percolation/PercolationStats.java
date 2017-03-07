import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private double[]    ratio;
   private int       trials, numSites;
   
   public PercolationStats(int n, int trials) {
      
      if (n <= 0 || trials <= 0) {
         throw new IllegalArgumentException();         
      }
      
      this.trials = trials;      
      ratio = new double[trials];
      numSites = n*n;
      
      // perform trials independent experiments on an n-by-n grid
      for (int t = 0; t < trials; ++t) {
         Percolation p = new Percolation(n);
         int[] perm = StdRandom.permutation(numSites);
         int ptr = 0;
         while (!p.percolates()) {
            int index = perm[ptr];
            int row = index/n+1;
            int col = index % n+1;
            // Open a new site
            p.open(row, col);
            ++ptr;
         }
         ratio[t] = p.numberOfOpenSites() * 1.0 / numSites;  
      }
   }
   
   public double mean() {
      // sample mean of percolation threshold
      return StdStats.mean(ratio);
   }
   
   public double stddev() {
      return StdStats.stddev(ratio);
   }
   public double confidenceLo() {
      // low  endpoint of 95% confidence interval
      return mean()-1.96*stddev()/Math.sqrt(trials);
   }
   public double confidenceHi() {
      // high endpoint of 95% confidence interval
      return mean()+1.96*stddev()/Math.sqrt(trials);
   }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      int n = Integer.parseInt(args[0]);
      int testCase = Integer.parseInt(args[1]);
      PercolationStats tester = new PercolationStats(n, testCase);
      System.out.format("mean                    = %f\n", tester.mean());
      System.out.format("stddev                  = %f\n", tester.stddev());
      System.out.format("95%% confidence interval = [%f, %f]\n", tester.confidenceLo(), tester.confidenceHi());
   }

}
