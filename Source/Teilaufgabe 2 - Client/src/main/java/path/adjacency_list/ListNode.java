package path.adjacency_list;

import map.Position;

public class ListNode {
    private Position position;
    private int weight;

    public ListNode(Position position, int weight) {
        this.position = position;
        this.weight = weight;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "position=" + position +
                ", weight=" + weight +
                '}';
    }
}
