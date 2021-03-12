import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Community {
    LinkedHashSet<Integer> nodes;
    long num;
    long sc = 0;

    public Community(long num) {
        this.num = num;
        nodes = new LinkedHashSet<>();
    }


    public void addNode(int node, int deg){
        nodes.add(node);
        sc += deg;
    }

    public void removeNode(int node, int deg){
        nodes.remove(node);
        sc -= deg;

    }

    public long getSc() {
        return sc;
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }
}
