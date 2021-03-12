public class Graph {

    int n;
    int m;
    int deg[];
    int nodes[][] = null;
    Community community[];
    int nodeCommunity[];
    long Q = 0;

    Graph() {

    }

    public void setDeg(int[] deg) {
        this.deg = deg;
    }

    public int getM() {
        return m;
    }

    public int getN() {

        return n;
    }

    public void setN(int maxIdNumber) {
        this.n = maxIdNumber;
        community = new Community[n];
        nodeCommunity = new int[n];
        for (int i = 0; i < n; i++) {
            community[i] = new Community(i);
            community[i].addNode(i, getDegOf(i));
            nodeCommunity[i] = i;
        }
    }

    public void incrementArcNumber() {
        m++;
    }

    public void setNodes(int[][] nodes) {
        this.nodes = nodes;
    }

    private int getDegOf(int u) {
        return deg[u] + 1;
    }

    public long getQ() {
        if (Q == 0) {
            Q = getQInitial();
        }
        return Q;
    }

    private long getQInitial() {
        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += (long) -Math.pow((getDegOf(i)), 2);
        }

        return sum;
        //sum_C (2m\sum_u d_u^C - S_C^2)
        /*
        int sumQ = 0;
        int mx2 = 2 * m;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int delta = (community[i] == community[j]) ? 1 : 0;
                sumQ += (getA(i, j) - ((getDegOf(i) * getDegOf(j)) / mx2)) * delta;
            }

        }
        return sumQ / mx2;
        */
    }

    private long getA(int u, int v) {
        int nodeToSearch = u;
        int secondNodeToSearch = v;

        if (getDegOf(u) > getDegOf(v)) {
            nodeToSearch = v;
            secondNodeToSearch = u;
        }

        for (int i = 0; i < getDegOf(nodeToSearch); i++) {
            if (nodes[nodeToSearch][i] == secondNodeToSearch) {
                return 1;
            }
        }

        return 0;
    }

    public void move(int u, int v) {
        long dub = 0;
        long sb = 0;

        for (int i = 0; i < getDegOf(u); i++) {
            if (nodeCommunity[u] == nodeCommunity[nodes[u][i]]) {
                dub++;
            }
        }
        int oldUCommunity = nodeCommunity[u];

        sb = community[oldUCommunity].getSc();


        nodeCommunity[u] = nodeCommunity[v];
        community[oldUCommunity].removeNode(u, getDegOf(u));
        community[nodeCommunity[v]].addNode(u, getDegOf(u));
        if (community[oldUCommunity].isEmpty()) {
            community[oldUCommunity] = null;
        }

        long duc = 0;
        long sc = 0;

        for (int i = 0; i < getDegOf(u); i++) {
            if (nodeCommunity[u] == nodeCommunity[nodes[u][i]]) {
                duc++;
            }
        }
        sc = community[nodeCommunity[v]].getSc();

        Q += ((4 * m * (duc - dub)) - (2 * getDegOf(u) * (sc - sb + getDegOf(u)))) / Math.pow((2 * m), 2);
    }

    public static class Clustering {
        public double clusteringGlobal;
        public double clusteringLocalMoy;

        public void print() {
            System.out.format("%.5f\n", clusteringLocalMoy);
            System.out.format("%.5f\n", clusteringGlobal);
        }
    }
}
