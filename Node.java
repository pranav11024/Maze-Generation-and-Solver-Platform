import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    private int Xpos;
    private int Ypos;
    private Color nodeColor = Color.LIGHT_GRAY;
    private final int WIDTH = 35;
    private final int HEIGHT = 35;
    public Node left, right, up, down;

    private int depth;
    private double cost = Double.POSITIVE_INFINITY;
    private double distance;
    private double fcost;
    private boolean searchedFromBothEnds = false;

    public Node(int x, int y) {
        Xpos = x;
        Ypos = y;
        depth = 0; 
    }

    public Node() {
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public double distanceTo(Node otherNode) {
        int dx = this.Xpos - otherNode.Xpos;
        int dy = this.Ypos - otherNode.Ypos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public int compareTo(Node otherNode) {
        return Double.compare(this.cost, otherNode.cost);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setSearchedFromBothEnds(boolean value) {
        searchedFromBothEnds = value;
    }

    public boolean isSearchedFromBothEnds() {
        return searchedFromBothEnds;
    }

    public static double distance(Node a, Node b) {
        double x = Math.pow(a.Xpos - b.Xpos, 2);
        double y = Math.pow(a.Ypos - b.Ypos, 2);
        return Math.sqrt(x + y);
    }

    public void render(Graphics2D g) {
       
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRoundRect(Xpos + 3, Ypos + 3, WIDTH, HEIGHT, 10, 10);

        
        g.setColor(Color.BLACK);
        g.drawRoundRect(Xpos, Ypos, WIDTH, HEIGHT, 10, 10);

        
        g.setColor(nodeColor);
        g.fillRoundRect(Xpos + 1, Ypos + 1, WIDTH - 2, HEIGHT - 2, 10, 10);
    }

    public void Clicked(int buttonCode) {
        System.out.println("called");
        if (buttonCode == 1) {
            
            nodeColor = Color.BLACK;
        }
        if (buttonCode == 2) {
            
            nodeColor = Color.GREEN;
        }
        if (buttonCode == 3) {
            
            nodeColor = Color.RED;
        }
        if (buttonCode == 4) {
            
            clearNode();
        }
    }

    public double getFCost() {
        return this.fcost;
    }

    public void setFCost(double fcost) {
        this.fcost = fcost;
    }

    public void setColor(Color c) {
        nodeColor = c;
    }

    public Color getColor() {
        return nodeColor;
    }

    public List<Node> getNeighbours() {
        List<Node> neighbours = new ArrayList<>();
        if (left != null && left.isPath())
            neighbours.add(left);
        if (down != null && down.isPath())
            neighbours.add(down);
        if (right != null && right.isPath())
            neighbours.add(right);
        if (up != null && up.isPath())
            neighbours.add(up);

        return neighbours;
    }

    public void setDirections(Node l, Node r, Node u, Node d) {
        left = l;
        right = r;
        up = u;
        down = d;
    }

    public void clearNode() {
        nodeColor = Color.LIGHT_GRAY;
    }

    public int getX() {
        return (Xpos - 15) / WIDTH;
    }

    public int getY() {
        return (Ypos - 15) / HEIGHT;
    }

    public Node setX(int x) {
        Xpos = x;
        return this;
    }

    public Node setY(int y) {
        Ypos = y;
        return this;
    }

    public boolean isWall() {
        return (nodeColor == Color.BLACK);
    }

    public boolean isStart() {
        return (nodeColor == Color.GREEN);
    }

    public boolean isEnd() {
        return (nodeColor == Color.RED);
    }

    public boolean isPath() {
        return (nodeColor == Color.LIGHT_GRAY || nodeColor == Color.RED);
    }

    public boolean isSearched() {
        return (nodeColor == Color.BLUE || nodeColor == Color.ORANGE);
    }
}
