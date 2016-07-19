package entrants.pacman.username;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.*;


/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getMove() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., entrants.pacman.username).
 */
public class MyPacMan extends PacmanController {
    private MOVE myMove = MOVE.NEUTRAL;
    private static final int MIN_DISTANCE = 30;
    private Random random = new Random();


    public MOVE bfs_find_goal(Queue q,Hashtable pills_hash,Game game,Hashtable move_hash,HashSet expanded_hash,Hashtable generation_hash)
    {
        //DFS uses Stack data structure
//        boolean [] visited=new boolean[21];
//        for (int i=0;i<=nodes.length;i++)
//        {
//            visited[i]=false;
//        }




        int depth_search_value=0;
        while(!q.isEmpty())
        {
//            System.out.println(depth_search_value);

            if (depth_search_value==999999)      //big enough to observe all the game structure
            {
                System.out.println("Depth Exceeded");
                return(MOVE.LEFT);
            }
            int new_node_to_expand= (int)(q.remove());
            System.out.println("removed one node from Q with id :  " + new_node_to_expand);
//            System.out.println("new BFS node extracted from Que = "+new_node_to_expand);
            if ((Boolean)pills_hash.get(new_node_to_expand)==true)
            {
                System.out.println("found it using BFS");
                return ((MOVE) move_hash.get(new_node_to_expand));
            }

            else if(game.getNeighbouringNodes(new_node_to_expand).length!=0)
            {
                int [] all_children_not_checked=game.getNeighbouringNodes(new_node_to_expand);
                for (int id:all_children_not_checked)
                {
                    if ((expanded_hash.contains(id)==false))
                    {
                        move_hash.put(id,(MOVE) move_hash.get(new_node_to_expand));
                        expanded_hash.add(id);
                        generation_hash.put(id,((int)generation_hash.get(new_node_to_expand)+1));
                        System.out.println((int)generation_hash.get(id));
                        q.add(id);
                    }
                }
                depth_search_value=depth_search_value+1;
            }
        }
        return(MOVE.LEFT);

    }







    public MOVE getMove(Game game, long timeDue) {



        int node0 = game.getPacmanCurrentNodeIndex();
        System.out.println("node0  =  "+node0);
        Hashtable pills_hash = new Hashtable();
        Hashtable generation_hash = new Hashtable();




        for (int i=0;i<1292;i++)
        {
            pills_hash.put(i,false);
        }
        int [] only_pills=game.getCurrentMaze().pillIndices;
        for (int i=0;i <only_pills.length;i++)
        {
            if (global_vars.visited_hash.contains(only_pills[i])==false)
            {
                pills_hash.put(only_pills[i],true);
            }


        }

        pills_hash.put(node0,false);
        global_vars.visited_hash.add(node0);

        Queue q = new LinkedList();

        HashSet<Integer> expanded_hash = new HashSet<Integer>();
        Hashtable move_hash = new Hashtable();
//        int [] all_children_not_checked=game.getNeighbouringNodes(node0);
        MOVE []  all_children_move=game.getPossibleMoves(node0);
        expanded_hash.add(node0);
        for (int i=0;i<all_children_move.length;i++)
        {
                int neighbor_id=game.getNeighbour(node0,all_children_move[i]);
                if ((expanded_hash.contains(neighbor_id)==false))
//
                {
                    move_hash.put(neighbor_id,all_children_move[i]);
                    expanded_hash.add(neighbor_id);
                    generation_hash.put(neighbor_id,1);
                    q.add(neighbor_id);
                    System.out.println("node0 children added =  "+neighbor_id);
                }


        }


        MOVE target_node=bfs_find_goal(q,pills_hash,game,move_hash,expanded_hash,generation_hash);
        int next_id=game.getNeighbour(node0,target_node);
        System.out.println("next id = "+ next_id);
        pills_hash.put(next_id,false);
        global_vars.visited_hash.add(next_id);
        return(target_node);
//        System.out.println("the next move after BFS = "+target_node);
//        MOVE next_move = choose_direction_given_goal_node(target_node,move_hash);
//

















//        System.out.println(next2);
//         Strategy 1: Adjusted for PO
//        for (Constants.GHOST ghost : Constants.GHOST.values()) {
//            // If can't see these will be -1 so all fine there
//            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
//                int ghostLocation = game.getGhostCurrentNodeIndex(ghost);
//                if (ghostLocation != -1) {
//                    if (game.getShortestPathDistance(node0, ghostLocation) < MIN_DISTANCE) {
//                        return game.getNextMoveAwayFromTarget(node0, ghostLocation, Constants.DM.PATH);
//                    }
//                }
//            }
//        }
//        System.out.println(next_move);
//        return(next_move);

    }
}
