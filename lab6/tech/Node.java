package lab6.tech;

import java.util.List;

public class Node {
    Class<?> cls;
    List<Attribute> attributeList;

    public Node(Class<?> cls, List<Attribute> attributeList) {
        this.cls = cls;
        this.attributeList = attributeList;
    }

    @Override
    public String toString() {
        return "Node{" +
                "cls=" + cls +
                ", attributeList=" + attributeList +
                '}';
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}
