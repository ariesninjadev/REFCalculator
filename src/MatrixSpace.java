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

    public static void printMat(int[][] mat) {
        for (int[] k : mat) {
            for (int l : k) {
                System.out.print(l + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
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

    private int[][] dotAddend(int[][] wm, int r, int c, int t, int ref) {
        int rc = (r+ref)%wm.length;
        int f = wm[r][c];
        int carry = wm[rc][c];
        //System.out.println(rc + " " + c);
        int R2Mult = 0;
        while ((carry*R2Mult+f)%mod!=t) {

            R2Mult++;
        }
        for (int i=0;i<wm[0].length;i++) {
            wm[r][i] = (wm[r][i] + (wm[rc][i] * R2Mult)) % mod;
        }
        return wm;
    }

    private int[][] inPlace(int[][] wm, int r, int c, int t) {
        int f = wm[r][c];
        int mult = 1;
        while ((f*mult)%mod!=t) {
            mult++;
        }
        System.out.println(mult);
        for (int i=0;i<wm[0].length;i++) {
            wm[r][i] = (wm[r][i] * mult) % mod;
        }
        return wm;
    }

    private int[][] greedyUnit(int[][] wm, int r, int c, int t) {
        if (wm[r][c]==t) {
            return wm;
        }
        if (wm[r][c]!=0 && t!=0) {
            return inPlace(wm, r, c, t);
        }
        int rel = -1;
        while (wm[(r+rel)%wm.length][c]==0) {
            rel--;
            if (rel==wm.length) {
                throw new RuntimeException("Logical Collapse");
            }
        }
        return dotAddend(wm, r, c, t, rel);
    }

    public int[][] REFM() {
        // Calculates the Reduced Row-Echelon form matrix
        // on a finite field "mod" given a matrix "mat".

        int[][] workingMat = mat;

        workingMat = greedyUnit(workingMat, 0, 0, 1);

        for (int i=1;i<mat.length;i++) {
            for (int j=0;j<i;j++) {
                printMat(workingMat);
                //System.out.println(i + " " + j);
                workingMat = greedyUnit(workingMat, i, j, 0);
            }
            workingMat = greedyUnit(workingMat, i, i, 1);
        }

        printMat(workingMat);

        return workingMat;
    }

}
