import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] threshold;
    private int trials;

    public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
        if(n<=0 || trials<=0){
            throw new IllegalArgumentException("Arguments out of range: must be n > 0 and trials > 0");
        }
        threshold = new double[trials];
        this.trials = trials;
        int openSites;
        int row;
        int col;

        for (int i = 0; i < trials; i++ ){
            openSites = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()){
                col = StdRandom.uniform(1, n+1);
                row = StdRandom.uniform(1, n+1);
                if(percolation.isOpen(row, col)){
                    continue;
                }
                percolation.open(row, col);
                openSites++;
            }
            threshold[i] = (double)openSites/(n*n);
        }

    }

    public double mean(){                          // sample mean of percolation threshold
        return StdStats.mean(threshold);
    }
    public double stddev(){                        // sample standard deviation of percolation threshold
        return StdStats.stddev(threshold);
    }
    public double confidenceLo(){                  // low  endpoint of 95% confidence interval
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }
    public double confidenceHi(){                  // high endpoint of 95% confidence interval
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }

    public static void main(String[] args){    // test client (described below)
        if (args.length < 2){
            System.out.println("Should be at least 2 paramets");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean: "+ stats.mean());
        System.out.println("stddev: "+ stats.stddev());
        System.out.println("95% confidence interval: "+ stats.confidenceLo()+", "+ stats.confidenceHi());


    }
}
