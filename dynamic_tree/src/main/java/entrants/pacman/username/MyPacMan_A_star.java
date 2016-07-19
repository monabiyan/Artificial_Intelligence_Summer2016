package entrants.pacman.username;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
public class MyPacMan_A_star extends PacmanController {
    private MOVE myMove = MOVE.NEUTRAL;
    private static final int MIN_DISTANCE = 30;
    private Random random = new Random();


    public MOVE a_star_find_goal(Hashtable pills_hash,Game game,Hashtable move_hash,HashSet expanded_hash,Hashtable generation_hash,int target,Hashtable heuristic_hash,Hashtable f_cost_hash,PriorityQueue f_PQ)
    {
        //DFS uses Stack data structure
//        boolean [] visited=new boolean[21];
//        for (int i=0;i<=nodes.length;i++)
//        {
//            visited[i]=false;
//        }



        HashSet<Integer> detected_hash = new HashSet<Integer>();
        int depth_search_value=0;
        while(!f_PQ.isEmpty())
        {
//            System.out.println(depth_search_value);

            if (depth_search_value==40000)
            {
                System.out.println("Depth Exceeded");
                return(MOVE.LEFT);
            }
            int min_f_value= (int)(f_PQ.remove());

            System.out.println("min_f_value :  " + min_f_value);
//            System.out.println("new BFS node extracted from Que = "+new_node_to_expand);

            Set<Integer> key_set=f_cost_hash.keySet();
            Integer[] array_id = key_set.toArray(new Integer[key_set.size()]);
            int new_node_to_expand=100000;
            for (int id:array_id)
            {
//                System.out.println(f_cost_hash.get(id)+"  VS.  "+min_f_value);

                if (((int)f_cost_hash.get(id)==(int)min_f_value)&& detected_hash.contains(id)==false)
                {
                    new_node_to_expand=id;
                    detected_hash.add(id);
//                    System.out.println("new_node id with minimum f_value for expansion = " + min_f_value);
                    break;
                }
            }
            System.out.println("new_node id to expand with minimum f_value for expansion = " + new_node_to_expand);





            if ((Boolean)pills_hash.get(new_node_to_expand)==true)
            {
                System.out.println("found pill!!! = "+new_node_to_expand);
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
                        heuristic_hash.put(id,(int) game.getEuclideanDistance(id,target));
                        int g=(int) generation_hash.get(id);
                        int h=(int) heuristic_hash.get(id);
                        int f=g+h;
                        f_cost_hash.put(id,f);
                        f_PQ.offer(f);

                        System.out.println(f+ " is pushed to f_PQ in the generation of  " +(int)generation_hash.get(id));
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
        Hashtable heuristic_hash = new Hashtable();




        for (int i=0;i<2000;i++)
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



        HashSet<Integer> expanded_hash = new HashSet<Integer>();
        Hashtable move_hash = new Hashtable();
        Hashtable f_cost_hash = new Hashtable();
        PriorityQueue<Integer> f_PQ = new PriorityQueue<Integer>();
        PriorityQueue<Integer> max_pill = new PriorityQueue<Integer>(20000,Collections.reverseOrder());











        Set<Integer> key_set=pills_hash.keySet();
        Integer[] array_pills = key_set.toArray(new Integer[key_set.size()]);
        for (int i=0;i<array_pills.length;i++)
        {
            int pill_location=array_pills[i];
            if ((Boolean)pills_hash.get(pill_location)==true)
            {
                max_pill.offer((int) pill_location);
            }
        }
        int target=max_pill.remove();
        System.out.println("the farthest pill_id for f value distance is =  "+target);





        expanded_hash.add(node0);

        MOVE []  all_children_move=game.getPossibleMoves(node0);
        for (int i=0;i<all_children_move.length;i++)
        {
            int neighbor_id=game.getNeighbour(node0,all_children_move[i]);
            if ((expanded_hash.contains(neighbor_id)==false))
//
            {
                move_hash.put(neighbor_id,all_children_move[i]);
                expanded_hash.add(neighbor_id);
                generation_hash.put(neighbor_id,1);
                int distance=(int) game.getEuclideanDistance(neighbor_id,target);
                heuristic_hash.put(neighbor_id,distance);
                System.out.println("h value for " +neighbor_id+"  is = " +distance);

                int g=(int) generation_hash.get(neighbor_id);

                int h=(int) heuristic_hash.get(neighbor_id);






                System.out.println("g value for " +neighbor_id+"  is = " +g);
                int f = g + h;
                System.out.println("f value for " +neighbor_id+"  is = " +f);

                f_cost_hash.put(neighbor_id,f);

                f_PQ.offer(f);

                System.out.println("node0 children added =  "+neighbor_id);
            }

        }


        MOVE target_node=a_star_find_goal(pills_hash,game,move_hash,expanded_hash,generation_hash,target,heuristic_hash,f_cost_hash,f_PQ);
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
