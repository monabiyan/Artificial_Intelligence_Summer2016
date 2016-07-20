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



   // This functions returns the next move using the A_star search algorithm.
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

            if (depth_search_value==999999)     //big enough to observe all the game structure
            {
                System.out.println("Depth Exceeded");
                return(MOVE.LEFT);  // for undefined condition moves to left.
            }
            int min_f_value= (int)(f_PQ.remove());  // extract the minimum f-value in P-Q.

            System.out.println("min_f_value :  " + min_f_value);
//            System.out.println("new BFS node extracted from Que = "+new_node_to_expand);

            Set<Integer> key_set=f_cost_hash.keySet();
            Integer[] array_id = key_set.toArray(new Integer[key_set.size()]);
            int new_node_to_expand=100000;
            for (int id:array_id)    //now we extract actual id based on the f-value
            {
//                System.out.println(f_cost_hash.get(id)+"  VS.  "+min_f_value);

                if (((int)f_cost_hash.get(id)==(int)min_f_value)&& detected_hash.contains(id)==false)
                {
                    new_node_to_expand=id;
                    detected_hash.add(id);  //we avoid selecting same node for a repeatetive f-value.
//                    System.out.println("new_node id with minimum f_value for expansion = " + min_f_value);
                    break;
                }
            }
            System.out.println("new_node id to expand with minimum f_value for expansion = " + new_node_to_expand);





            if ((Boolean)pills_hash.get(new_node_to_expand)==true)     //if the node contains pills  means we found what we wanted.
            {
                System.out.println("found pill!!! = "+new_node_to_expand);
                return ((MOVE) move_hash.get(new_node_to_expand));
            }

            else if(game.getNeighbouringNodes(new_node_to_expand).length!=0) //else, push its children f-value into PQ.
            {
                int [] all_children_not_checked=game.getNeighbouringNodes(new_node_to_expand);
                for (int id:all_children_not_checked)
                {
                    if ((expanded_hash.contains(id)==false))
                    {
                        move_hash.put(id,(MOVE) move_hash.get(new_node_to_expand));
                        expanded_hash.add(id);
                        generation_hash.put(id,((int)generation_hash.get(new_node_to_expand)+1)); //generation one added w/r to parent
//                      int distance=(int) game.getEuclideanDistance(id,target);
                        int distance=nearest_pill_distance(id, pills_hash,game); //eucluidian distance to the nearest available pill calculated
                        heuristic_hash.put(id,distance);  //h-value

                        int g=(int) generation_hash.get(id); //g-value
                        int h=(int) heuristic_hash.get(id);
                        int f=g+h;  //f value: A-star formulation
                        f_cost_hash.put(id,f);
                        f_PQ.offer(f);  //pushing f-value in PQ

                        System.out.println(f+ " is pushed to f_PQ in the generation of  " +(int)generation_hash.get(id));
                    }
                }
                depth_search_value=depth_search_value+1;  //just to keep track of this loop
            }
        }
        return(MOVE.LEFT);

    }


    //This function returns the euclidian distance between the given node and the nearest available pill to it.
    public int nearest_pill_distance(int node, Hashtable pills_id,Game game)
    {

        PriorityQueue<Integer> k = new PriorityQueue<Integer>();

        Set<Integer> id_set=pills_id.keySet();
        int[] id_array = new int[id_set.size()];

        int index = 0;

        for( Integer i : id_set)
        {
            id_array[index++] = i; //note the autounboxing here
        }
        for (int id:id_array)
        {
            if (pills_id.get(id)==true)

            {
                k.offer((int)game.getEuclideanDistance(node,id));
            }
        }

        return(k.remove());
    }

    public MOVE getMove(Game game, long timeDue) {



        int node0 = game.getPacmanCurrentNodeIndex();   //node 0 is the current node.
        System.out.println("node0  =  "+node0);
        Hashtable pills_hash = new Hashtable();
        Hashtable generation_hash = new Hashtable();
        Hashtable heuristic_hash = new Hashtable();




        for (int i=0;i<1292;i++)  //initialize the pills_hash for all nodes as false
        {
            pills_hash.put(i,false);
        }
        int [] only_pills=game.getCurrentMaze().pillIndices;  // get all initial pills

        for (int i=0;i <only_pills.length;i++)  // here we update the pills_hash
        {
            if (global_vars.visited_hash.contains(only_pills[i])==false)  //check if the packman has visited that node
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

        MOVE []  all_children_move=game.getPossibleMoves(node0);  //get the possible moves of the current node
        for (int i=0;i<all_children_move.length;i++)  //here we put the f-value of the children of the initial node in the  p-queue.
        {
            int neighbor_id=game.getNeighbour(node0,all_children_move[i]);
            if ((expanded_hash.contains(neighbor_id)==false))
//
            {
                move_hash.put(neighbor_id,all_children_move[i]); //save the move
                expanded_hash.add(neighbor_id);
                generation_hash.put(neighbor_id,1);  //generations will be 1
//                int distance=(int) game.getEuclideanDistance(neighbor_id,target);
                int distance=nearest_pill_distance(neighbor_id, pills_hash,game); // find the min distance to the pills
                heuristic_hash.put(neighbor_id,distance); //save the min distance
                System.out.println("h value for " +neighbor_id+"  is = " +distance);

                int g=(int) generation_hash.get(neighbor_id); //g is the generation

                int h=(int) heuristic_hash.get(neighbor_id);  //h , the heuristic value






                System.out.println("g value for " +neighbor_id+"  is = " +g);
                int f = g + h;  // f value  (A_star formula)
                System.out.println("f value for " +neighbor_id+"  is = " +f);

                f_cost_hash.put(neighbor_id,f);   //save f in the f_cost_hash

                f_PQ.offer(f);  // push f in PQ

                System.out.println("node0 children added =  "+neighbor_id);
            }

        }

        // a_star_find_goal finds the next move based on the A-Star algorithm
        MOVE target_node=a_star_find_goal(pills_hash,game,move_hash,expanded_hash,generation_hash,target,heuristic_hash,f_cost_hash,f_PQ);
        int next_id=game.getNeighbour(node0,target_node); // find id using the move
        System.out.println("next id = "+ next_id);
        pills_hash.put(next_id,false); // no longer any pill will be there since it is eaten
        global_vars.visited_hash.add(next_id); // it will be visited by packman
        return(target_node);  //return the next move
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
