package edu.uab.cis.search.maze;

import com.google.common.collect.Lists;
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
    Queue<Node> open_list = new PriorityQueue<>(10, nodeComparator);
    this.path = new ArrayList<Square>();
    Node start = new Node(maze.getStart(), distance(maze.getStart(), maze.getGoal()));
    start.setG(0);
    open_list.add(start);
    
    
    while(!open_list.isEmpty()){
         Node current = open_list.peek();
         

         if(current.getNode().equals(maze.getGoal())){
             explored.add(current.getNode());
             this.path.addAll(reconstructPath(current));
             break;
         }
        ArrayList<Square> neighbors = getNeighbors(current);
        open_list.remove(current);
        explored.add(current.getNode());
        for(Square s : neighbors){
            if(explored.contains(s) || maze.isBlocked(s)){
                continue;
            }
            Node n = new Node(s,this.distance(s, maze.getGoal()) );             
            int tentativeGScore = current.getG() + 
                    distance(current.getNode(),n.getNode());

            if((!open_list.contains(n)) || (tentativeGScore < n.getG())){
                n.setParent(current);
                n.setG(tentativeGScore);
                if(!open_list.contains(n)){
                    open_list.add(n);
                }
            }
        }
    }   
    //Comparator anonymous class implementation
  }
  
  public static List<Square> reconstructPath(Node n){
      List<Square> path = new ArrayList<>(); 
      Node current = n;
      
      while(current != null){
          path.add(current.getNode());
          current = current.getParent();
      }
      Collections.reverse(path);
      return path;
  }

  /**
   * @return The squares along the path from the start to the goal, not
   *         including the start square and the goal square.
   */
  public List<Square> getPathFromStartToGoal() {
    return this.path;
  }
  
  public static Comparator<Node> nodeComparator = new Comparator<Node>(){
      @Override
      public int compare (Node n1, Node n2){
          if(n1.getF() == n2.getF()){
              if(n1.getH() == n2.getH()){
                  if(n1.getNode().getRow() == n2.getNode().getRow()){
                      if(n1.getNode().getColumn() == n2.getNode().getColumn()){
                          return 0;
                      } else{
                          return n1.getNode().getColumn() - n2.getNode().getColumn();
                      }
                  } else{
                      return n1.getNode().getRow() - n2.getNode().getRow();
                  }
              } else{
                  return n1.getH() - n2.getH();
              }
          } else{
              return n1.getF() - n2.getF();
          }
      }
  };
  

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
      ArrayList<Square> neighbors = Lists.newArrayList(
              new Square(n.getNode().getRow(),n.getNode().getColumn() + 1),
              new Square(n.getNode().getRow(),n.getNode().getColumn() - 1),
              new Square(n.getNode().getRow() + 1,n.getNode().getColumn()),
              new Square(n.getNode().getRow() - 1,n.getNode().getColumn()));
      return neighbors;
  }

}

final class Node{
    
    private final Square node;
    private int g;
    private int h;
    private int f;
    private Node parent;
    
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
    
    public Node getParent(){
        return parent;
    }
    
    public void setParent(Node parent){
        this.parent = parent;
    }

}
