package lab6;

import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.GraphComponent;
import lab6.annotation.Column;
import lab6.annotation.Entity;
import lab6.tech.Attribute;
import lab6.tech.Edge;
import lab6.tech.Node;
import lab6.tech.RelationType;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    public List<Class<?>> searchAll(String path){
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(path);
        if (scannedUrl == null) {
            throw new IllegalArgumentException("Bad package " + path);
        }
        File scannedDir = new File(scannedUrl.getFile());
        java.util.List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(searchAll(file, path));
        }
        return classes;
    }

    public List<Class<?>> searchAll(File file, String path){
        List<Class<?>> classes = new ArrayList<>();
        String resource = path + "." + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(searchAll(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            String className = resource.substring(0, resource.length() - 6);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    public List<Class<?>> getClasses(String path){
        List<Class<?>> list = searchAll(path);
        List<Class<?>> resultList = new ArrayList<>();
        if (list != null) {
            for (Class cls : list) {
                Annotation[] annotations = cls.getAnnotations();
                if (annotations != null) {
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType().equals(Entity.class)){
                            resultList.add(cls);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    public List<Field> getAttributes(Class<?> cls){
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation: annotations){
                    if (annotation.annotationType().equals(Column.class)){
                        fieldList.add(field);
                    }
                }
            }
        }
        return fieldList;
    }

    public List<Edge> getEdges(List<Node> nodes){
        List<Edge> edges = new ArrayList<>();
        for (Node node: nodes){
            Field[] fields = node.getCls().getDeclaredFields();
            for (Field field: fields){
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation: annotations){
                    String annotationName = annotation.annotationType().getSimpleName();
                    if (annotationName.equals(RelationType.ManyToOne.name()) ||
                            annotationName.equals(RelationType.OneToMany) ||
                            annotationName.equals(RelationType.OneToOne) ||
                            annotationName.equals(RelationType.OneToMany)){
                        edges.add(new Edge(node, getNodeByName(field.getType().getName(), nodes), RelationType.valueOf(annotationName)));
                    }
                }
            }
        }
        return edges;
    }

    private Node getNodeByName(String name, List<Node> nodeList){
        for (int i = 0; i < nodeList.size(); i++ ){
            Node node = nodeList.get(i);
            if (node.getCls().getName().equals(name)){
                return node;
            }
        }
        return null;
    }

    public HashMap<String, List> init(){
        HashMap<String, List> map = new HashMap<>();
        List<Node> nodeList = new ArrayList<>();
        List<Edge> edges;
        String path = "lab6";
        List<Class<?>> classes = getClasses(path);
        if (classes != null){
            for (Class<?> cls: classes){
                List<Field> fieldList = getAttributes(cls);
                List<Attribute> attributeList = new ArrayList<>();
                for (Field field: fieldList){
                    attributeList.add(new Attribute(field.getName(), field.getType().getName()));
                }
                nodeList.add(new Node(cls, attributeList));
            }
        }
        edges = getEdges(nodeList);
        map.put("Node", nodeList);
        map.put("Edge", edges);
        return map;
    }

    public List<INode> createINodes(IGraph iGraph, List<Node> nodeList){
        List<INode> nodes = new ArrayList<>();
        int x = 0;
        int y = 0;
        int width = 150;
        int length = 300;
        for (Node node: nodeList){
            INode iNode = iGraph.createNode(new RectD(x, y, width, length));
            nodes.add(iNode);
            ILabel iLabel = iGraph.addLabel(iNode, node.getCls().getSimpleName());
            String text = node.getCls().getSimpleName() + "\n";
            for (int i = 0; i < node.getAttributeList().size(); i++ ){
                text += "\t- " + node.getAttributeList().get(i).getName() + "\n";
            }
            iGraph.setLabelText(iLabel, text);
            if (x + 200 > 1200){
                x = 0;
                y += 400;
            } else {
                x += 300;
            }
        }
        return nodes;
    }

    public List<IEdge> createIEdges(IGraph iGraph, List<Edge> edgeList, List<INode> nodes, List<Node> nodeList){
        List<IEdge> edges = new ArrayList<>();
        for (Edge edge: edgeList){
            int startNode = nodeList.indexOf(edge.getSourse());
            int endNode = nodeList.indexOf(edge.getTarget());
            IEdge iEdge = iGraph.createEdge(nodes.get(startNode), nodes.get(endNode));
            edges.add(iEdge);
            iGraph.addLabel(iEdge, edge.getRelationType().toString());
            iGraph.addBend(iEdge, new PointD(75, 350));
        }
        return edges;
    }

    public Graph(){
        HashMap<String, List> map = init();
        GraphComponent graphComponent = new GraphComponent();
        IGraph iGraph = graphComponent.getGraph();
        List<INode> nodes = createINodes(iGraph, map.get("Node"));
        List<IEdge> edges = createIEdges(iGraph, map.get("Edge"), nodes, map.get("Node"));
        JFrame frame = new JFrame("Order");
        graphComponent.fitGraphBounds();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(graphComponent, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Graph::new);
    }
}
