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
    this.explored = new HashSet<Square>();
    Queue<Node> open_list = new PriorityQueue<>(11, nodeComparator);
    this.path = new ArrayList<Square>();
    Node start = new Node(maze.getStart(), 0)
    
    
    while(!open_list.isEmpty()){
         Square current = f_score.entrySet().stream().min(
                 Map.Entry.comparingByValue(Integer::compareTo)).get().getKey();
         if(current == maze.getGoal()){
             
         }
         
         open_list.remove(current);
         explored.add(current);
         for(int i = 1; i < 5; i++){
             if( i == 1){
          
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
  
  public static Comparator<Node> nodeComparator = new Comparator<Node>(){
      @Override
      public int compare (Node n1, Node n2){
          return (int);
      }
  };

}

final class Node<Square>{
    
    private final Square node;
    private int g;
    private int h;
    private int f;
    
    public Node (Square node, Map<Square, Integer> heuristic){
        this.node = node;
        this.g = Integer.MAX_VALUE;
        this.heuristic = heuristic;
    }
    
    public Square getNode(){
        return node;
    }
    
    public int getG(){
        return g;
    }
    
    public void setG(int g){
        this.g = g;
    }
    
    
    public int getH(){
        return h;
    }
    
    public int getF(){
        return f;
    }

}
