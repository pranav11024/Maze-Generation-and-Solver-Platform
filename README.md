#  Maze Generation & Pathfinding Visualizer

A Java-based interactive tool to **generate mazes** and **visualize pathfinding algorithms** in real time. This project is designed for learning and demonstrating the behavior of classical AI algorithms through an intuitive GUI built with Java Swing.

---

##  Features

-  **Maze Generation**
  - Randomized Kruskal's Algorithm

-  **Pathfinding Algorithms**
  - Iterative Deepening Depth-First Search (IDDFS)
  - Depth-First Search (DFS)
  - Breadth-First Search (BFS)
  - A* Search
  - Dijkstra's Algorithm
  - Bellman-Ford Algorithm

-  **Interactive Grid-Based GUI**
  - Place walls, start, and finish nodes via mouse
  - Visualize each algorithm step-by-step
  - Adjust visualization speed (Slow, Medium, Fast)
  - Maze generation and solving in one integrated interface

---

##  File Structure

| File | Description |
|------|-------------|
| `Main.java` | Main application GUI and controller logic |
| `Algorithm.java` | All implemented maze generation and pathfinding algorithms |
| `Node.java` | Represents each cell in the grid, with all necessary metadata for search |

---

##  How to Run

###  Requirements
- Java JDK 8 or higher
- IDE (e.g., IntelliJ IDEA, Eclipse) or terminal with `javac`

###  Running the Application

1. **Compile all files**:
    ```bash
    javac Main.java Node.java Algorithm.java
    ```

2. **Run the application**:
    ```bash
    java Main
    ```

---

##  Controls

- **Left Click**: Set wall or remove wall
- **Right Click**: Set start and finish nodes
- **"Generate Maze" Button**: Create a randomized maze using Kruskal’s Algorithm
- **"Solve" Button**: Run selected pathfinding algorithm
- **Dropdowns**: Choose algorithm and visualization speed
- **Reset/Clear Buttons**: Reset the board or clear paths

---

##  Algorithms Explained

### Maze Generation
- **Randomized Kruskal’s Algorithm**:
  - Generates perfect mazes by treating the grid as a disjoint-set forest and removing walls while avoiding cycles.

### Pathfinding Algorithms
- **IDDFS**: Combines depth-first search's space-efficiency and breadth-first search's completeness.
- **DFS**: Explores as far as possible along each branch before backtracking.
- **BFS**: Explores all neighbors at the current depth before moving deeper (guarantees shortest path in unweighted graphs).
- **A***: Uses heuristics + path cost for optimal pathfinding.
- **Dijkstra**: Finds shortest paths from a single source using edge weights (like A* but no heuristic).
- **Bellman-Ford**: Handles graphs with negative edge weights (not needed for grids but included for completeness).

---


