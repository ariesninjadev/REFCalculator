public class MatrixSpace {

    private int[][] mat;
    private final int mod;

    public MatrixSpace(int[][] mat, int mod) {
        this.mat = mat;
        this.mod = mod;
        if (mod < 1) {
            throw new IllegalArgumentException("The field size must be at least 1");
        }
        int ms = mat[0].length;
        for (int i=1;i<mat.length;i++) {
            if (mat[i].length != ms) {
                throw new IllegalArgumentException("Invalid Matrix");
            }
        }
    }

    private int[][] fit() {
        int[][] m = mat;
        for (int i=0;i<m.length;i++) {
            for (int j=0;j<m[i].length;j++) {
                m[i][j] %= mod;
            }
        }
        return m;
    }

    private int[][] dotAddend(int r, int c, int t, int ref) {
        int[][] m = mat;
        int rc = (r+ref)%mat.length;
        int f = m[r][c];
        int carry = m[rc][c];
        int R2Mult = 0;
        while ((carry*R2Mult+f)%mod!=t) {
            System.out.println((carry*R2Mult+f)%mod);
            R2Mult++;
        }
        for (int i=0;i<m[0].length;i++) {
            m[r][i] = (m[r][i] + (m[rc][i] * R2Mult)) % mod;
        }
        return m;
    }

    private int[][] inplace(int r, int c, int t) {
        int[][] m = mat;
        int f = m[r][c];
        int mult = 0;
        while ((f*mult)%mod!=t) {
            mult++;
        }
        for (int i=0;i<m[0].length;i++) {
            m[r][i] = (m[r][i] * mult) % mod;
        }
        return m;
    }

    public int[][] REFM() {
        // Calculates the Reduced Echelon-Form Matrix
        // on a finite field "mod" given a Matrix "mat"
        // mat[row][col]
        // mod

        int[][] workingMat = mat;

        if (mat[0][0]!=1) {
            workingMat = dotAddend(0, 0, 1,1);
        }

        for (int i=1;i<mat.length;i++) {
            for (int j=0;j<i;j++) {
                System.out.println(i + " " + j);
                workingMat = dotAddend(i, j, 0, -1);
            }
        }

        return workingMat;
    }

}
