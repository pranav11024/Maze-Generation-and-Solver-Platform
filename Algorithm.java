import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.awt.Point;

public class Algorithm {
    public int searchtime = 100;
    public boolean solutionFound = false;

    public boolean isSolutionFound() {
        return solutionFound;
    }

    public int getSearchTime() {
        return searchtime;
    }

    public void setSearchTime(int searchtime) {
        this.searchtime = searchtime;
    }

    public void dfs(Node start) {
        Stack<Node> nodes = new Stack<>();
        nodes.push(start);

        while (!nodes.empty()) {
            Node curNode = nodes.pop();
            if (!curNode.isEnd()) {
                curNode.setColor(Color.ORANGE);
                try {
                    Thread.sleep(searchtime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                curNode.setColor(Color.BLUE);
                for (Node adjacent : curNode.getNeighbours()) {
                    nodes.push(adjacent);
                }
            } else {
                curNode.setColor(Color.MAGENTA);
                solutionFound = true;
                break;
            }
        }

        if (!solutionFound) {
            for (int i = 0; i < Main.NODES_WIDTH; i++) {
                for (int j = 0; j < Main.NODES_HEIGHT; j++) {
                    if (!Main.nodeList[i][j].isWall()) {
                        Main.nodeList[i][j].setColor(Color.BLACK);
                    }
                }
            }
        }
    }

    public void iddfs(Node start) {
        int maxDepth = 1000000000;//1000 
        //boolean solutionFound = false;
    
        while (!solutionFound ) {
            solutionFound = dls(start, maxDepth, 0); 
            maxDepth++; 
        }
    }
    
    private boolean dls(Node node, int depthLimit, int currentDepth) {
        node.setDepth(currentDepth); 
    
        if (currentDepth > depthLimit) {
            return false; 
        }
        
        if (node.isEnd()) {
            node.setColor(Color.MAGENTA);
            return true; 
        }
    
        node.setColor(Color.ORANGE);
        try {
            Thread.sleep(searchtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        node.setColor(Color.BLUE);
    
        
        for (Node adjacent : node.getNeighbours()) {
            if (!adjacent.isSearched()) {
                if (dls(adjacent, depthLimit, currentDepth + 1)) {
                    return true; 
                }
            }
        }
        return false; 
    }
    


    public void bfs(Node start, Node end, int graphWidth, int graphHeight) {
        Queue<Node> queue = new LinkedList<>();
        Node[][] prev = new Node[graphWidth][graphHeight];

        queue.add(start);
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            if (curNode.isEnd()) {
                curNode.setColor(Color.MAGENTA);
                solutionFound = true;
                break;
            }

            if (!curNode.isSearched()) {
                curNode.setColor(Color.ORANGE);
                try {
                    Thread.sleep(searchtime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                curNode.setColor(Color.BLUE);
                for (Node adjacent : curNode.getNeighbours()) {
                    queue.add(adjacent);
                    prev[adjacent.getX()][adjacent.getY()] = curNode;
                }
            }
        }

        if (!solutionFound) {
            for (int i = 0; i < Main.NODES_WIDTH; i++) {
                for (int j = 0; j < Main.NODES_HEIGHT; j++) {
                    if (!Main.nodeList[i][j].isWall()) {
                        Main.nodeList[i][j].setColor(Color.BLACK);
                    }
                }
            }
        }

        shortpath(prev, end);
    }

    public void Astar(Node start, Node targetNode, int graphWidth, int graphHeight) {
        List<Node> openList = new ArrayList<>();
        Node[][] prev = new Node[graphWidth][graphHeight];
        openList.add(start);

        while (!openList.isEmpty()) {
            Node curNode = getLeastHeuristic(openList, targetNode, start);
            openList.remove(curNode);

            if (curNode.isEnd()) {
                curNode.setColor(Color.MAGENTA);
                solutionFound = true;
                break;
            }
            curNode.setColor(Color.ORANGE);
            try {
                Thread.sleep(searchtime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            curNode.setColor(Color.BLUE);
            for (Node adjacent : curNode.getNeighbours()) {
                if (adjacent.isSearched()) {
                    continue;
                }
                double f1 = Node.distance(adjacent, targetNode);
                double h1 = Node.distance(curNode, start);

                double f2 = Node.distance(adjacent, targetNode);
                double h2 = Node.distance(curNode, start);

                if (!openList.contains(adjacent) || (f1 + h1 < f2 + h2)) {
                    prev[adjacent.getX()][adjacent.getY()] = curNode;
                    if (!openList.contains(adjacent)) {
                        openList.add(adjacent);
                    }
                }
            }
        }

        if (!solutionFound) {
            for (int i = 0; i < Main.NODES_WIDTH; i++) {
                for (int j = 0; j < Main.NODES_HEIGHT; j++) {
                    if (!Main.nodeList[i][j].isWall()) {
                        Main.nodeList[i][j].setColor(Color.BLACK);
                    }
                }
            }
        }

        shortpath(prev, targetNode);
    }

    public void dijkstra(Node start, Node end, int graphWidth, int graphHeight) {
        
        double[][] distances = new double[graphWidth][graphHeight];
        for (int i = 0; i < graphWidth; i++) {
            for (int j = 0; j < graphHeight; j++) {
                distances[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        distances[start.getX()][start.getY()] = 0; 

        
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(distances[a.getX()][a.getY()], distances[b.getX()][b.getY()]));
        pq.add(start);

        
        Node[][] prev = new Node[graphWidth][graphHeight];

        while (!pq.isEmpty()) {
            Node curNode = pq.poll();

            
            if (curNode == end) {
                solutionFound = true;
                break;
            }

            curNode.setColor(Color.ORANGE); 

            try {
                Thread.sleep(searchtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            curNode.setColor(Color.BLUE); 

            // Explore neighbors
            for (Node adjacent : curNode.getNeighbours()) {
                double newDistance = distances[curNode.getX()][curNode.getY()] + curNode.distanceTo(adjacent);

                
                if (newDistance < distances[adjacent.getX()][adjacent.getY()]) {
                    distances[adjacent.getX()][adjacent.getY()] = newDistance;
                    prev[adjacent.getX()][adjacent.getY()] = curNode;
                    pq.add(adjacent); 
                }
            }
        }

        if (solutionFound) {
            shortpath(prev, end);
        } else {
            clearNonWallNodes(); 
        }
    }

    private void clearNonWallNodes() {
        for (int i = 0; i < Main.NODES_WIDTH; i++) {
            for (int j = 0; j < Main.NODES_HEIGHT; j++) {
                if (!Main.nodeList[i][j].isWall()) {
                    Main.nodeList[i][j].setColor(Color.BLACK);
                }
            }
        }
    }

    private Node getLeastHeuristic(List<Node> nodes, Node end, Node start) {
        if (!nodes.isEmpty()) {
            Node leastH = nodes.get(0);
            for (int i = 1; i < nodes.size(); i++) {
                double f1 = Node.distance(nodes.get(i), end);
                double h1 = Node.distance(nodes.get(i), start);

                double f2 = Node.distance(leastH, end);
                double h2 = Node.distance(leastH, start);
                if (f1 + h1 < f2 + h2) {
                    leastH = nodes.get(i);
                }
            }
            return leastH;
        }
        return null;
    }

    private void shortpath(Node[][] prev, Node end) {
        Node pathConstructor = end;
        while (pathConstructor != null) {
            pathConstructor = prev[pathConstructor.getX()][pathConstructor.getY()];

            if (pathConstructor != null) {
                pathConstructor.setColor(Color.ORANGE);
            }
            try {
                Thread.sleep(searchtime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   /*  public void wallFollowing(Node start, Node end, int graphWidth, int graphHeight) {
    Stack<Node> path = new Stack<>();
    Set<Node> visited = new HashSet<>(); 
    Node current = start;
    Node previous = null;
    Direction currentDirection = Direction.RIGHT; 
    Direction hand = Direction.RIGHT; 

    int moveCounter = 0; 
    int maxMoveCount = 3; 

    while (true) {
        if (current == end) {
            solutionFound = true;
            break;
        }
        current.setColor(Color.ORANGE);
        try {
            Thread.sleep(searchtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        current.setColor(Color.BLUE);

        Node next = getNextNode(current, currentDirection, graphWidth, graphHeight);
        if (next != null && !next.isWall() && next != previous && !visited.contains(next)) {
            path.push(current);
            visited.add(current); 
            previous = current;
            current = next;
            moveCounter++;
            continue;
        }

        if (moveCounter >= maxMoveCount) {
            // Switch hands if stuck in a loop
            hand = (hand == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
            currentDirection = getNextDirection(currentDirection, hand);
            moveCounter = 0;
            continue;
        }

        currentDirection = getNextDirection(currentDirection, hand);
        next = getNextNode(current, currentDirection, graphWidth, graphHeight);
        if (next != null && !next.isWall() && next != previous && !visited.contains(next)) {
            path.push(current);
            visited.add(current); 
            previous = current;
            current = next;
            moveCounter++;
            continue;
        }

        if (path.isEmpty()) {
            break;
        }

        current = path.pop();
        previous = null;
        moveCounter = 0; 
    }

    if (!solutionFound) {
        clearNonWallNodes();
    } else {
        
        while (!path.isEmpty()) {
            Node node = path.pop();
            node.setColor(Color.ORANGE);
            try {
                Thread.sleep(searchtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
    
    private Direction getNextDirection(Direction currentDirection, Direction hand) {
        
        switch (currentDirection) {
            case UP:
                return (hand == Direction.RIGHT) ? Direction.RIGHT : Direction.LEFT;
            case DOWN:
                return (hand == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
            case LEFT:
                return (hand == Direction.RIGHT) ? Direction.DOWN : Direction.UP;
            case RIGHT:
                return (hand == Direction.RIGHT) ? Direction.UP : Direction.DOWN;
            default:
                return null;
        }
    }
    
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    
    private Node getNextNode(Node current, Direction direction, int graphWidth, int graphHeight) {
        int nextX = current.getX();
        int nextY = current.getY();
        
        switch (direction) {
            case UP:
                nextY--;
                break;
            case DOWN:
                nextY++;
                break;
            case LEFT:
                nextX--;
                break;
            case RIGHT:
                nextX++;
                break;
            default:
                return null;
        }
        

        if (nextX >= 0 && nextX < graphWidth && nextY >= 0 && nextY < graphHeight) {
            return Main.nodeList[nextX][nextY];
        } else {
            return null; 
        }
    }*/
    public void bellmanFord(Node start, Node end, int graphWidth, int graphHeight) {
        
        double[][] distances = new double[graphWidth][graphHeight];
        for (int i = 0; i < graphWidth; i++) {
            for (int j = 0; j < graphHeight; j++) {
                distances[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        distances[start.getX()][start.getY()] = 0; // Distance from start to start is 0
    
        
        Node[][] prev = new Node[graphWidth][graphHeight];
    
        
        Set<Node> visited = new HashSet<>();
    
        
        boolean relaxationDone = false;
        for (int i = 0; i < graphWidth * graphHeight - 1; i++) {
            for (int j = 0; j < graphWidth; j++) {
                for (int k = 0; k < graphHeight; k++) {
                    Node curNode = Main.nodeList[j][k];
                    if (curNode.isWall()) {
                        continue; // Skip walls
                    }
                    if (!curNode.equals(start) && !curNode.equals(end)) {
                        visited.add(curNode); 
                    }
                    for (Node adjacent : curNode.getNeighbours()) {
                        double newDistance = distances[j][k] + curNode.distanceTo(adjacent);
                        if (newDistance < distances[adjacent.getX()][adjacent.getY()]) {
                            distances[adjacent.getX()][adjacent.getY()] = newDistance;
                            prev[adjacent.getX()][adjacent.getY()] = curNode;
                            relaxationDone = true;
                        }
                    }
                }
            }
            if (!relaxationDone) {
                break; 
            }
            relaxationDone = false; 
        }
    
        // Check for negative cycles
        for (int j = 0; j < graphWidth; j++) {
            for (int k = 0; k < graphHeight; k++) {
                Node curNode = Main.nodeList[j][k];
                if (curNode.isWall()) {
                    continue; // Skip walls
                }
                for (Node adjacent : curNode.getNeighbours()) {
                    double newDistance = distances[j][k] + curNode.distanceTo(adjacent);
                    if (newDistance < distances[adjacent.getX()][adjacent.getY()]) {
                        // Negative cycle detected
                        clearNonWallNodes();
                        return;
                    }
                }
            }
        }
    
        
        for (Node node : visited) {
            node.setColor(Color.ORANGE);
            try {
                Thread.sleep(searchtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            node.setColor(Color.BLUE);
        }
    
        
        if (distances[end.getX()][end.getY()] < Double.POSITIVE_INFINITY) {
            solutionFound = true;
            markShortestPath(prev, end);
        } else {
            clearNonWallNodes(); 
        }
    }
    
private void markShortestPath(Node[][] prev, Node end) {
    Node current = end;
    while (current != null) {
        current.setColor(Color.ORANGE);
        try {
            Thread.sleep(searchtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        current = prev[current.getX()][current.getY()];
    }
}
}
