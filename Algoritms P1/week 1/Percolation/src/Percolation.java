import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Random;

public class Percolation {

    private int gridSize;
    private boolean[][] grid;
    private WeightedQuickUnionUF tgrid;
    private WeightedQuickUnionUF bgrid;
    private int top;
    private int bottom;

    public Percolation(int n){
        if(n <= 0){
            throw new IllegalArgumentException("Invalid argument, mast be > 0");
        }
        grid = new boolean[n][n];
        tgrid = new WeightedQuickUnionUF(n*n + 1);
        bgrid = new WeightedQuickUnionUF(n*n + 2);
        top = n*n;;
        bottom = n*n+1;
        gridSize = n;

    }
    public void open(int row, int col){// open site (row, col) if it is not open already

        boundValidate(row, col);
        if(isOpen(row, col)){
            return;
        }

        int index = convertToIndex(row, col);
        grid[row-1][col-1] = true;

        if(row != 1 && isOpen(row-1, col)){
            tgrid.union(index, convertToIndex(row-1, col));
            bgrid.union(index, convertToIndex(row-1, col));
        }

        if(row != gridSize && isOpen(row+1, col)){
            tgrid.union(index, convertToIndex(row+1, col));
            bgrid.union(index, convertToIndex(row+1, col));
        }

        if(col != 1 && isOpen(row, col-1)){
            tgrid.union(index, convertToIndex(row, col-1));
            bgrid.union(index, convertToIndex(row, col-1));
        }

        if(col != gridSize && isOpen(row, col+1)){
            tgrid.union(index, convertToIndex(row, col+1));
            bgrid.union(index, convertToIndex(row, col+1));
        }
        if(row == 1){
            tgrid.union(index, top);
            bgrid.union(index, top);
        }
        if(row == gridSize){
            bgrid.union(index, bottom);
        }


    }
    public boolean isOpen(int row, int col){  // is site (row, col) open?
        boundValidate(row, col);
        return grid[row-1][col-1];
    }
    public boolean isFull(int row, int col){  // is site (row, col) full?
        boundValidate(row, col);
        return tgrid.connected(convertToIndex(row, col), top);
    }
    public boolean percolates(){
        return  bgrid.connected(top, bottom);
    }

    private void boundValidate (int row, int col){
        if(row > gridSize || col > gridSize || row <= 0 || col <= 0){
            throw new java.lang.IndexOutOfBoundsException("Values out of range: row:"+row+" col:"+col);
        }
    }
    private int convertToIndex(int row, int col){
        return (col-1) + (row-1)*gridSize;
    }

    public static void main(String... args ){
        Percolation p = new Percolation(10);
        while (true){
            p.open(new Random().nextInt(10)+1,new Random().nextInt(10)+1);
            if(p.percolates()){
                break;
            }
        }

        System.out.println(p.percolates());
    }
}
