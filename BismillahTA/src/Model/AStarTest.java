package Model;

import java.io.IOException;
import java.io.StringReader;

import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.AStar.DistanceCosts;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceDGS;

public class AStarTest {

 	//     B-(1)-C
    //    /       \
    //  (1)       (10)
    //  /           \
    // A             F
    //  \           /
    //  (1)       (1)
    //    \       /
    //     D-(1)-E
    static String my_graph
            = "DGS004\n"
            + "my 0 0\n"
            + "an A xy: 0,2\n"
            + "an B xy: 4,2\n"
            + "an C xy: 4,2\n"
            + "an D xy: 0,0\n"
            + "an E xy: 0,2\n"
            + "an F xy: 0,4\n"
            + "ae AB A B weight:1 \n"
            + "ae AD A D weight:4 \n"
            + "ae BC B C weight:5 \n"
            + "ae BD B D weight:5 \n"
            + "ae BE B E weight:5 \n"
            + "ae BF B F weight:5 \n"
            + "ae CF C F weight:9 \n"
            + "ae DE D E weight:9 \n"
            + "ae EF E F weight:2 \n";

    public static void main(String[] args) throws IOException {
        Graph graph = new DefaultGraph("A Test");
        StringReader reader = new StringReader(my_graph);

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(graph);
        source.readAll(reader);

        AStar astar = new AStar(graph);
        astar.setCosts(new DistanceCosts());
        astar.compute("A", "F");

        System.out.println(astar.getShortestPath());
    }
}
