
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation
{
    private final WeightedQuickUnionUF connect;
    private final boolean[] open;
    private final WeightedQuickUnionUF uf;
    private final int num;
    private int numOfOpenSite;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        uf = new WeightedQuickUnionUF(n*n + 1);
        if (n <= 0)
        {
            throw new IllegalArgumentException();
        }
        connect = new WeightedQuickUnionUF(n*n+2);
        open = new boolean[n * n + 2];

        for (int i = 0; i < n*n + 2; i++)
        {
            //connect[i] = i;
            open [i] = false;
            //sz[i] = 1;
        }
        for(int i = 1 ; i <= n;i++){
            connect.union(0,i);
            uf.union(i,0);
            connect.union(n*n+1,n*n+1-i);
        }
        open[0] = true;
        open[n*n+1] = true;
        num = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        int index = (row-1) * num + col;
        if (row > num || col > num || row <= 0 || col <= 0)
        {
            throw new IllegalArgumentException();
        }

        if(open[index])return;
        numOfOpenSite++;

        open[index] = true;

        int up = index - num;
        if (up > 0 && open[up] )
        {
            uf.union(index,up);
            connect.union(index,up);
        }
        int down = index + num;
        if (down <= num*num && open[down])
        {
            uf.union(index,down);
            connect.union(index, down);
        }

        int right = index + 1;
        if (right <= num*num && right% num != 1 && open[right])
        {
            uf.union(index,right);
            connect.union(index, right);
        }
        int left = index -1;
        if (left > 0 && left % num != 0 && open[left])
        {
            uf.union(index,left);
            connect.union(index, left);
        }
//        if (index <= num)
//        {
//            //uf.union(index,0);
//            //connect.union(index,0);
//        }




    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row > num || col > num || row <= 0 || col <= 0)
        {
            //System.out.println(row <= 0);
            //System.out.println(row +" " +col);
            throw new IllegalArgumentException();
        }
        int index = (row-1) * num + col;
        return open[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row > num || col > num || row <= 0 || col <= 0)
        {
            throw new IllegalArgumentException();
        }

        int index = (row-1) * num + col;
        //System.out.println("root: " + uf.find(0));
        return open[index] &&uf.find(index) == uf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numOfOpenSite;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return numOfOpenSite>0 &&connect.find(0) == connect.find(num*num + 1);
    }

    // test client (optional)
    public static void main(String[] args)
    {


    }
}