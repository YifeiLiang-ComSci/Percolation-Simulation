

public class Percolation
{
    private int[] connect;
    private int[] open;
    private int[] sz;
    private int num;
    private int size;
    private int numOfOpenSite;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException();
        }
        connect = new int[n * n + 2];
        open = new int[n * n + 2];
        sz = new int[n * n + 2];
        for (int i = 0; i < connect.length; i++)
        {
            connect[i] = i;
            open [i] = 0;
            sz[i] = 1;
        }
        open[0] = 1;
        open[n*n+1] = 1;
        num = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {

        if(isOpen(row,col))
        {
            return;
        }
        if (row > num || col > num || row <= 0 || col <= 0)
        {
            throw new IllegalArgumentException();
        }
        numOfOpenSite++;
        int index = (row-1) * num + col;

        open[index] = 1;

        int up = index - num;
        if (up > 0)
        {

            union(index, up);
        }
        int down = index + num;
        if (down <= num*num)
        {
            union(index, down);
        }

        int right = index + 1;
        if (right <= num*num)
        {
            union(index, right);
        }
        int left = index -1;
        if (left > 0)
        {
            union(index, left);
        }
        if (index <= num)
        {

            union(index,0);
        }
        if (index > num*num-num)
        {

            union(index, num*num + 1);
        }



    }
    private void union(int index1, int index2)
    {
        if(open[index2] == 0)
        {
            return;
        }
        int i = root(index1);
        int j = root(index2);
        if (i == 0)
        {

            connect[j] = i;
            sz[i] += sz[j];
        } else if (j == 0)
        {

            connect[i] = j;
            sz[j] += sz[i];
        } else if (sz[i] < sz[j])
        {
            connect[i] = j;
            sz[j] += sz[i];
        } else
        {
            connect[j] = i;
            sz[i] += sz[j];
        }
    }
    public int root(int index)
    {
        while (index != connect[index])
        {
            index = connect[index];

        }
        return index;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row > num || col > num || row <= 0 || col <= 0)
        {
            throw new IllegalArgumentException();
        }
        int index = (row-1) * num + col;
        return open[index] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        int index = (row-1) * num + col;

        return root(index) == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numOfOpenSite;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return root(num * num + 1) == 0;
    }

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation p = new Percolation(1);
        p.open(1,1);
        // p.open(2,1);
        // p.open(2,2);
        // p.open(3,2);
        System.out.println(p.percolates());


    }
}