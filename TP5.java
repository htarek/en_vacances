import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TP5 {

    public static void main(String[] args) throws IOException {


        Graph graph = readFile("/home/abdellah/Desktop/TP5/src/clique3-ring4.txt", 16);

        System.out.println(graph.getQ());

        graph.move(0, 1);

        System.out.println(graph.getQ());

    }

    public static Graph readFile(String fileName, int estimEdges) throws IOException {
        String file = fileName;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        Graph graph = new Graph();
        int nodes[][] = new int[estimEdges * 2][0];
        int arcsLastIndex[] = new int[estimEdges * 2];
        Arrays.fill(arcsLastIndex, -1);
        int maxIdNumber = -1;

        final int incrementOfArcsTable = 20;
        while ((line = bufferedReader.readLine()) != null && estimEdges > 0) {
            if (line.startsWith("#")) {
                continue;
            }

            // split line
            String arc[] = line.trim().split("\t");
            int val1 = Integer.parseInt(arc[0]);
            int val2 = Integer.parseInt(arc[1]);
            if (arcsLastIndex[val1] >= nodes[val1].length - 2) {
                nodes[val1] = Arrays.copyOf(nodes[val1], nodes[val1].length + incrementOfArcsTable + 1);
            }
            arcsLastIndex[val1]++;

            nodes[val1][arcsLastIndex[val1]] = val2;

            if (arcsLastIndex[val2] >= nodes[val2].length - 2) {
                nodes[val2] = Arrays.copyOf(nodes[val2], nodes[val2].length + incrementOfArcsTable + 1);
            }
            arcsLastIndex[val2]++;
            nodes[val2][arcsLastIndex[val2]] = val1;

            nodes[val1][arcsLastIndex[val1] + 1] = -1;
            nodes[val2][arcsLastIndex[val2] + 1] = -1;

            graph.incrementArcNumber();
            if (val1 > maxIdNumber) {
                maxIdNumber = val1;
            }

            if (val2 > maxIdNumber) {
                maxIdNumber = val2;
            }

            estimEdges--;
        }
        graph.setDeg(arcsLastIndex);
        graph.setN(maxIdNumber + 1);
        graph.setNodes(nodes);

        return graph;
    }

}