package path.adjacency_list;

import map.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjancecyList {
    private HashMap<Position, List<ListNode>> map;

    public AdjancecyList(HashMap<Position, List<ListNode>> map) {
        this.map = map;
    }

    public List<ListNode> getNodeList(Position position){
        return map.get(position);
    }

    public List<Position> getKeySet(){
        return (List<Position>) map.keySet();
    }


    @Override
    public String toString() {
        String string = "";
        for(Map.Entry<Position, List<ListNode>> e : map.entrySet()){
            string += "at pos " + e.getKey().toString();
            List<ListNode> list = e.getValue();
            for(ListNode ln : list)
                string += ln.toString() + " ";
            string += '\n';
        }
        return string;
    }
}
