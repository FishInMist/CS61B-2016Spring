package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//In order to solve the backwash problem, just use two UFs here, one for isFull, one for percolation
public class Percolation {

    private class status {
        boolean open, full;
        public status(boolean open, boolean full) {
            this.open = open;
            this.full = full;
        }
    }
    private boolean inBound(int x, int y) {
        return 0 <= x && x<= N-1 && 0<= y && y <=N - 1;
    }
    private int matrixto1D(int x, int y) {
        return x * N + y + 1;
    }

    private int N;
    private status[][] system;
    private int numOfOpenSites;
    private WeightedQuickUnionUF connection;
    private WeightedQuickUnionUF fullConnection;
    private int[] xCor = {1, 0, 0, -1};
    private int[] yCor = {0, 1, -1, 0};

    public Percolation(int N) {
        this.N = N;
        system = new status[N][N];
        connection = new WeightedQuickUnionUF(N * N + 2);
        fullConnection = new WeightedQuickUnionUF(N * N + 2);
        numOfOpenSites = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
                system[i][j] = new status(false, false);
            }
            connection.union(0, i + 1);
            connection.union(N * N + 1, N * (N - 1) + i + 1);
            fullConnection.union(0, i + 1);
        }
    }
    public void open(int row, int col) {
        if (inBound(row, col) && !system[row][col].open) {
            numOfOpenSites++;
            system[row][col].open = true;
            for (int i = 0; i < 4; i++) {
                int x = row + xCor[i];
                int y = col + yCor[i];
                if (!inBound(x, y)) {
                    continue;
                }
                if (system[x][y].open) {
                    connection.union(matrixto1D(row, col), matrixto1D(x, y));
                    fullConnection.union(matrixto1D(row, col), matrixto1D(x, y));
                }
            }
        }
    }
    public boolean isOpen(int row, int col) {
        return system[row][col].open;
    }
    public boolean isFull(int row, int col) {
        if (system[row][col].open && fullConnection.connected(0, matrixto1D(row, col))) {
            system[row][col].full = true;
        }
        return system[row][col].full;
    }
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }
    public boolean percolates() {
        return connection.connected(0, N * N + 1);
    }
}                       
