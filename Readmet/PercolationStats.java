import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Math;
public class PercolationStats {

private final double mean;
private final double std;
private final int numOfTrials;
private final double conFid = 1.96;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if(n < 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        numOfTrials = trials;
        double[] fraction = new double[trials];
        for(int i = 0 ; i < trials;i++){
            Percolation p = new Percolation(n);
            while(!p.percolates()) {
                int row = StdRandom.uniform(n )+ 1;
                int col = StdRandom.uniform(n )+1;

                    p.open(row,col);
            }
            fraction[i] = ((double)p.numberOfOpenSites())/(n*n);
        }

        mean =  StdStats.mean(fraction);
        std = StdStats.stddev(fraction);
    }

    // sample mean of percolation threshold
    public double mean(){
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean - (conFid * std/Math.sqrt(numOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean + (conFid*std/Math.sqrt(numOfTrials));
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats s = new PercolationStats(n,trials);
        System.out.println("mean                    = "+s.mean());
        System.out.println("stddev                  = " + s.stddev());
        System.out.println("95% confidence interval = ["+s.confidenceLo()+", "+s.confidenceHi()+"]");
    }

}