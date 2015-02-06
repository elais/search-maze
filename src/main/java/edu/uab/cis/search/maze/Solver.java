package edu.uab.cis.search.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solves a maze using A* search with an L1 heuristic.
 * 
 * Specifically, squares are explored with the following strategy:
 * <ul>
 * <li>Squares are ordered for exploration using the score f(x) = g(x) + h(x),
 * with the smallest f(x) squares being explored first</li>
 * <li>g(x) is the length of the path so far, from the start square to the
 * current square, including the steps necessary to avoid obstacles</li>
 * <li>h(x) is the L1 estimate of the path to the goal, that is, the Manhattan
 * distance to the goal, ignoring potential obstacles</li>
 * <li>Squares with the same f(x) score are ordered by the h(x) score, with
 * smaller h(x) scores first</li>
 * <li>Squares with the same f(x) and h(x) scores are ordered by row, with
 * smaller rows first</li>
 * <li>Squares with the same f(x), h(x) and row should be ordered by column,
 * with smaller columns first</li>
 * </ul>
 */
public class Solver {

  private Set<Square> explored;

  private List<Square> path;

  /**
   * Solves the given maze, determining the path to the goal.
   * 
   * @param maze
   *          The maze to be solved.
   */
  public Solver(Maze maze) {
    // TODO
    this.explored = new TreeSet<Square>();
    Queue<Node> open_list = new PriorityQueue<>(11, nodeComparator);
    this.path = new ArrayList<Square>();
    Node start = new Node(maze.getStart(), this.distance(maze.getStart(),
            maze.getGoal()));
    start.setG(0);
    open_list.add(start);
    
    
    while(!open_list.isEmpty()){
         Node current = open_list.poll();
         if(current.getNode() == maze.getGoal()){
             this.path.add(current.getNode());
             break;
         }
         
         open_list.remove(current);
         explored.add(current.getNode());
        ArrayList<Square> neighbors = this.getNeighbors(current);
         for(Square s : neighbors){
             if(maze.isBlocked(s) || explored.contains(s)){
                 continue;
             }
         }
    }
    
    //Comparator anonymous class implementation
  }

  /**
   * @return The squares along the path from the start to the goal, not
   *         including the start square and the goal square.
   */
  public List<Square> getPathFromStartToGoal() {
    return this.path;
  }

  /**
   * @return All squares that were explored during the search process. This is
   *         always a superset of the squares returned by
   *         {@link #getPathFromStartToGoal()}.
   */
  public Set<Square> getExploredSquares() {
    return this.explored;
  }
  
  public int distance(Square s1, Square s2){
      return Math.abs(s1.getColumn() - s2.getColumn()) + 
              Math.abs(s1.getRow() - s2.getRow());
  }
  
  public ArrayList<Square> getNeighbors(Node n){
      ArrayList<Square> neighbors = new ArrayList<>();
      Square s1 = new Square(n.getNode().getRow() + 
              0,n.getNode().getColumn() + 1);
      Square s2 = new Square(n.getNode().getRow() + 
              0,n.getNode().getColumn() - 1);
      Square s3 = new Square(n.getNode().getRow() + 
              1,n.getNode().getColumn() + 0);
      Square s4 = new Square(n.getNode().getRow() - 
              1,n.getNode().getColumn() + 0);
      Square s5 = new Square(n.getNode().getRow() + 
              1,n.getNode().getColumn() + 1);
      Square s6 = new Square(n.getNode().getRow() + 
              1,n.getNode().getColumn() - 1);
      Square s7 = new Square(n.getNode().getRow() - 
              1,n.getNode().getColumn() + 1);
      Square s8 = new Square(n.getNode().getRow() - 
              1,n.getNode().getColumn() - 1);
      
      neighbors.add(s1);
      neighbors.add(s2);
      neighbors.add(s3);
      neighbors.add(s4);
      neighbors.add(s5);
      neighbors.add(s6);
      neighbors.add(s7);
      neighbors.add(s8);
      return neighbors;
  }
  
  
  public static Comparator<Node> nodeComparator = new Comparator<Node>(){
      @Override
      public int compare (Node n1, Node n2){
          return n1.getF() - n2.getF();
      }
  };

}

final class Node{
    
    private final Square node;
    private int g;
    private int h;
    private int f;
    
    public Node(Square node, int distanceToGoal){
        this.node = node;
        this.g = Integer.MAX_VALUE;
        this.h = distanceToGoal;
    }


    
    public Square getNode(){
        return node;
    }
    
    public int getG(){
        return g;
    }
    
    public void setG(int g){
        this.g = g;
        this.f = g + h;
    }
    
    public int getH(){
        return h;
    }

    
    public int getF(){
        return f;
    }

}
