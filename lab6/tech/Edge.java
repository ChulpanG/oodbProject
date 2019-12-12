package lab6.tech;

public class Edge {
    Node sourse;
    Node target;
    RelationType relationType;

    public Edge(Node sourse, Node target, RelationType relationType) {
        this.sourse = sourse;
        this.target = target;
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "sourse=" + sourse +
                ", target=" + target +
                ", relationType=" + relationType +
                '}';
    }

    public Node getSourse() {
        return sourse;
    }

    public void setSourse(Node sourse) {
        this.sourse = sourse;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }
}
