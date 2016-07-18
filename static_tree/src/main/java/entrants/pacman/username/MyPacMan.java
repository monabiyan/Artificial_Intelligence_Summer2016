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

    public int [] getChildren(int[] nodes,int node)
    {
//        int node0=nodes[0];
//        int node1=nodes[1];
//        int node2=nodes[2];
//        int node3=nodes[3];
//        int node4=nodes[4];
//        int node11=nodes[5];
////        int node12=nodes[6];
//        int node13=nodes[6];
//        int node14=nodes[7];
////        int node21=nodes[9];
//        int node22=nodes[8];
//        int node23=nodes[9];
//        int node24=nodes[10];
////        int node31=nodes[13];
////        int node32=nodes[14];
//        int node33=nodes[11];
////        int node34=nodes[16];
////        int node41=nodes[17];
////        int node42=nodes[18];
////        int node43=nodes[19];
//        int node44=nodes[12];
        if (node==-1)
        {
            int []a={};
            return (a);
        }
        if (node==nodes[0])
        {
            int[] childeren={nodes[1],nodes[2],nodes[3],nodes[4]};
            return(childeren);
        }
        if (node==nodes[1])
        {
            int[] childeren={nodes[5],nodes[6],nodes[7]};
            return(childeren);
        }
        if (node==nodes[2])
        {
            int[] childeren={nodes[9],nodes[10],nodes[8]};
            return(childeren);
        }
        if (node==nodes[3])
        {
            int[] childeren={nodes[11]};
            return(childeren);
        }
        if (node==nodes[4])
        {
            int[] childeren={nodes[12]};
            return(childeren);
        }
        else
        {
            int []a={};
            return (a);
        }
    }



    public int find_i(int[] nodes,int index)
    {
        for (int i=0;i<nodes.length;i++)
        {
            if (nodes[i]==index)
            {
                return(i);
            }

        }
        return (-2);
    }




    public int bfs_find_goal(int[] nodes,boolean[] goals)
    {
        //DFS uses Stack data structure
//        boolean [] visited=new boolean[21];
//        for (int i=0;i<=nodes.length;i++)
//        {
//            visited[i]=false;
//        }

        Queue q = new LinkedList();
        q.add(nodes[0]);

        while(!q.isEmpty())
        {

            int new_node_to_expand= (int)(q.remove());
            System.out.println("new BFS node extracted from Que = "+new_node_to_expand);
            if (goals[find_i(nodes,new_node_to_expand)])
            {
                return (new_node_to_expand);
            }

            if((getChildren(nodes,new_node_to_expand)!=null))
            {
                int [] children=getChildren(nodes,new_node_to_expand);
                for (int i=0;i<children.length;i++)
                {
                    int child=children[i];
                    if (child!=-1){q.add(child);}

                }
            }
        }
        return(-99);

    }



    public MOVE choose_direction_given_goal_node(int[] nodes, int target, Game game)
    {
        if (target==nodes[1] ||target==nodes[5] ||target==nodes[7]||target==nodes[6])
        {
            return(MOVE.UP);
        }
        else if (target==nodes[2]||target==nodes[10] ||target==nodes[9])
        {
            return(MOVE.DOWN);
        }
        else if (target==nodes[3]||target==nodes[11])
        {
            return(MOVE.LEFT);
        }
        else if (target==nodes[4]||target==nodes[12])
        {
            return(MOVE.RIGHT);
        }



        else
            {
                int rand=1 + (int)(Math.random() * ((4 - 1) + 1));
                if (rand==1){return(MOVE.UP);}
                else if (rand==2){return(MOVE.DOWN);}
                else if (rand==3){return(MOVE.LEFT);}
                else {return(MOVE.RIGHT);}

            }

    }



    public boolean check_pill_available(int[] all_pills, int node_index)
    {


        for (int i = 0; i <all_pills.length;i++)
        {
            if (node_index==all_pills[i])
            {
                System.out.println("found a pill in index"+node_index);
                return(true);

            }
        }
        System.out.println("NOT found any pill in the current nodes");
        return(false);
    }

    public int[] update_unvisited_pills(int[] all_pills,int current_index)
    {
        all_pills[Arrays.asList(all_pills).indexOf(4)]=-1;
        return (all_pills);
    }





    public MOVE getMove(Game game, long timeDue) {
        //Place your game logic here to play the game as Ms Pac-Man

        // Should always be possible as we are PacMan

        /* my new code starts here*/


        int node0 = game.getPacmanCurrentNodeIndex();

        int node1=-1;
        int node2=-1;
        int node3=-1;
        int node4=-1;
        int node5=-1;
        int node6=-1;
        int node7=-1;
        int node8=-1;
        int node9=-1;
        int node10=-1;
        int node11=-1;
        int node12=-1;



        node1 = game.getNeighbour(node0,MOVE.UP);
        node2 = game.getNeighbour(node0,MOVE.DOWN);
        node3 = game.getNeighbour(node0,MOVE.LEFT);
        node4 = game.getNeighbour(node0,MOVE.RIGHT);

        if (node1>0)
        {
            node5 = game.getNeighbour(node1, MOVE.UP);
            node6 = game.getNeighbour(node1, MOVE.LEFT);
            node7 = game.getNeighbour(node1, MOVE.RIGHT);
        }
        if (node2>0)
        {
//
            node8 = game.getNeighbour(node2, MOVE.DOWN);
            node9 = game.getNeighbour(node2, MOVE.LEFT);
            node10 = game.getNeighbour(node2, MOVE.RIGHT);
        }
        if (node3>0)
        {

              node11 = game.getNeighbour(node3, MOVE.LEFT);

        }

        if (node4>0)
        {
            node12 = game.getNeighbour(node4, MOVE.RIGHT);
        }
        int[] nodes={node0,node1,node2,node3,node4,node5,node6,node7,node8,node9,node10,node11,node12};



        System.out.println("all 13 nodes at the time  =  "+ Arrays.toString(nodes));

        global_vars.VisitedNodes.add(node0);
        System.out.println();
        System.out.println("Updating visited"+ Arrays.toString(global_vars.VisitedNodes.toArray()));







        int [] all_pill_indices= new int[1292];
        for (int i=0;i<1292;i++)
        {
            all_pill_indices[i]=i;
        }



//        System.out.println("all pills1 = "+Arrays.toString(all_pill_indices));
//        int [] all_power_pill_indices=game.getCurrentMaze().pillIndices;
//
//        int[] array1and2 = new int[all_pill_indices.length + all_power_pill_indices.length];
//        System.arraycopy(all_pill_indices, 0, array1and2, 0, all_pill_indices.length);
//        System.arraycopy(all_power_pill_indices, 0, array1and2, all_pill_indices.length,all_power_pill_indices.length);
//
//        all_pill_indices=array1and2;
        System.out.println("all pills2 = "+Arrays.toString(all_pill_indices));





        boolean [] pill_exist_in_nodes=new boolean[13];
        for (int i=0;i<nodes.length;i++)
        {
            pill_exist_in_nodes[i]=false;
        }

        for (int i=0;i<nodes.length;i++)
        {
            System.out.println("checking pills iteration No "+i);
            if (global_vars.VisitedNodes.contains(nodes[i]))
            {
                continue;
            }
            else
            {
                pill_exist_in_nodes[i] = check_pill_available(all_pill_indices, nodes[i]);
            }


        }
        System.out.println("all set with checking pills");

        System.out.println("the goal nodes are : "+Arrays.toString(pill_exist_in_nodes));
        int target_node=bfs_find_goal(nodes,pill_exist_in_nodes);



        System.out.println("the next move after BFS = "+target_node);
        MOVE next_move = choose_direction_given_goal_node(nodes, target_node,game);














        /* my new code finishes here*/



//        System.out.println(next2);
        // Strategy 1: Adjusted for PO
        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            // If can't see these will be -1 so all fine there
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                int ghostLocation = game.getGhostCurrentNodeIndex(ghost);
                if (ghostLocation != -1) {
                    if (game.getShortestPathDistance(node0, ghostLocation) < MIN_DISTANCE) {
                        return game.getNextMoveAwayFromTarget(node0, ghostLocation, Constants.DM.PATH);
                    }
                }
            }
        }
        System.out.println(next_move);
        return(next_move);

    }
}
