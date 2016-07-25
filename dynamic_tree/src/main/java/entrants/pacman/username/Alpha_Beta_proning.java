package entrants.pacman.username;

import pacman.controllers.PacmanController;
import pacman.entries.ghostMAS.Blinky;
import pacman.entries.ghostMAS.Inky;
import pacman.entries.ghostMAS.Pinky;
import pacman.entries.ghostMAS.Sue;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.*;

/**
 * Created by mohsennabian on 7/23/16.
 */




public class Alpha_Beta_proning extends PacmanController {
    private MOVE myMove = MOVE.NEUTRAL;
    private static final int MIN_DISTANCE = 30;
    private Random random = new Random();



    public MOVE eval_move(Game game,int current_id,int next_id)
    {

        System.out.println("in eval_move function,current id = "+current_id+"  next_id  =  "+next_id);
        MOVE[] moves=game.getPossibleMoves(current_id);
        for (MOVE move:moves)
        {
            if (game.getNeighbour(current_id,move)==next_id)
            {
                return(move);
            }

        }
        return(MOVE.LEFT);
    }





    public HashSet<int[]> ghost_all_posibilities(Hashtable<Constants.GHOST,int[]> ghosts_hashtable_ghost_key, Constants.GHOST [] ghosts_name_list)
    {
        Hashtable<Integer,int[]> ghosts_hashtable=new Hashtable<>();
        int kk=0;

//        for (Constants.GHOST gh:ghosts_hashtable_ghost_key.keySet())
        for (Constants.GHOST gh:ghosts_name_list)
        {
            ghosts_hashtable.put(kk,ghosts_hashtable_ghost_key.get(gh));
            kk=kk+1;
        }


        HashSet<int[]> possibilities_hashset=new HashSet();
        if (ghosts_hashtable.size()==4)
        {
            for (int i=0;i<ghosts_hashtable.get(0).length;i++)
            {
                for (int j=0;j<ghosts_hashtable.get(1).length;j++)
                {
                    for (int k=0;k<ghosts_hashtable.get(2).length;k++)
                    {
                        for (int l=0;l<ghosts_hashtable.get(3).length;l++)
                        {
                            int[] a = {ghosts_hashtable.get(0)[i],ghosts_hashtable.get(1)[j],ghosts_hashtable.get(2)[k]
                            ,ghosts_hashtable.get(3)[l]};
                            possibilities_hashset.add(a);
//                            System.out.println("there are 4 ghosts = " +Arrays.toString(a));

                        }
                    }
                }
            }
            return(possibilities_hashset);
        }
        if (ghosts_hashtable.size()==3)
        {
            for (int i=0;i<ghosts_hashtable.get(0).length;i++)
            {
                for (int j=0;j<ghosts_hashtable.get(1).length;j++)
                {
                    for (int k=0;k<ghosts_hashtable.get(2).length;k++)
                    {

                            int[] a = {ghosts_hashtable.get(0)[i],ghosts_hashtable.get(1)[j],ghosts_hashtable.get(2)[k]};
                            possibilities_hashset.add(a);
                            System.out.println("there are 3 ghosts = " +Arrays.toString(a));


                    }
                }
            }
            return(possibilities_hashset);
        }
        if (ghosts_hashtable.size()==2)
        {
            for (int i=0;i<ghosts_hashtable.get(0).length;i++)
            {
                for (int j=0;j<ghosts_hashtable.get(1).length;j++)
                {
                        int[] a = {ghosts_hashtable.get(0)[i],ghosts_hashtable.get(1)[j]};
                        possibilities_hashset.add(a);
                        System.out.println("there are 2 ghosts = " +Arrays.toString(a));

                }
            }
            return(possibilities_hashset);
        }
        if (ghosts_hashtable.size()==1)
        {

            for (int i=0;i<ghosts_hashtable.get(0).length;i++)
            {

                    int[] a = {ghosts_hashtable.get(0)[i]};
                    possibilities_hashset.add(a);
                    System.out.println("there are 1 ghosts = " +Arrays.toString(a));


            }
            return(possibilities_hashset);
        }

        return (possibilities_hashset);
    }



    public HashSet<int[]> GHOSTS_all_possible_next_moves(Constants.GHOST[] ghost_list,Game game)
    {
        Hashtable<Constants.GHOST, Integer> ghosts_current_location_hash = Ghosts_current_location_hash(ghost_list,game);



        Hashtable<Constants.GHOST, int[]> hash_gosts_nextmoves_id = new Hashtable<>(); //We want to see each Ghosts next move

        for (Constants.GHOST ghost:ghost_list)

        {
            int g_c_location=ghosts_current_location_hash.get(ghost);
            System.out.println(g_c_location);
//            if (g_c_location==-1){g_c_location=600;}
            int[] g_c_neighbors_ids=game.getNeighbouringNodes(g_c_location);
//            System.out.println("ghost neighbors =  "+Arrays.toString(g_c_neighbors_ids));

            hash_gosts_nextmoves_id.put(ghost,g_c_neighbors_ids);
        }



        HashSet<int[]> ghost_nextsids_hashset=ghost_all_posibilities(hash_gosts_nextmoves_id,ghost_list);

//        System.out.println("ghosts next move list:  "+ghost_nextsids_hashset.size());
        return(ghost_nextsids_hashset);
    }




    public  Hashtable<Constants.GHOST, Integer> Ghosts_current_location_hash(Constants.GHOST[] ghost_list,Game game)
    {
        Hashtable<Constants.GHOST, Integer> current_location_hash = new Hashtable<>();

        for (Constants.GHOST ghost:ghost_list)
        {
            current_location_hash.put(ghost,game.getGhostCurrentNodeIndex(ghost));  //So now we know the location of the ghosts.
        }
        return(current_location_hash);
    }


    public double score_eval(Game game)
    {
        int pills_no=game.getNumberOfActivePills();
        Boolean pacman_eaten=game.wasPacManEaten();
        Boolean game_over=game.gameOver();
        int ghost_eaten_no=game.getNumGhostsEaten();
        int current_node=game.getPacmanCurrentNodeIndex();
        Constants.GHOST[] ghost_names= Constants.GHOST.values();
        double distance_nearest_ghost=9999999;
        double sum_distance_pills;
        for (Constants.GHOST ghost_name:ghost_names)
        {
            int ghost_id=game.getGhostCurrentNodeIndex(ghost_name);
            double d=game.getEuclideanDistance(current_node,ghost_id);
            if (d<distance_nearest_ghost)
            {
                distance_nearest_ghost=d;
            }
        }
        double sum_distance_ghosts=0;
        for (Constants.GHOST ghost_name:ghost_names)
        {
            int ghost_id=game.getGhostCurrentNodeIndex(ghost_name);
            double d=game.getEuclideanDistance(current_node,ghost_id);
            sum_distance_ghosts=sum_distance_ghosts+d;
        }

        int[] all_available_pills=game.getActivePillsIndices();

        double distance_nearest_pills=9999999;

        for (int pill_id:all_available_pills)
        {
            double d=game.getDistance(current_node,pill_id,game.getPacmanLastMoveMade(), Constants.DM.PATH);

            if (d<distance_nearest_pills)
            {
                distance_nearest_pills=d;

            }
        }
        sum_distance_pills=0;
        System.out.println("googooolii  there are "+all_available_pills.length+"  pills left");
        for (int pill_id:all_available_pills)
        {
            double d=game.getEuclideanDistance(current_node,pill_id);
            sum_distance_pills=game.getDistance(current_node,pill_id,game.getPacmanLastMoveMade(), Constants.DM.PATH)+d;

        }
        if (game_over)
        {
            return(-9999999);
        }
        if (pacman_eaten)
        {
            return(-9999999);
        }


//        double final_score=distance_nearest_ghost*100000-distance_nearest_pills*100000-pills_no*10+
// ghost_eaten_no*10+sum_distance_pills*10+game.getScore()*100+sum_distance_ghosts*10000;
        double final_score=-distance_nearest_pills*100000+game.getScore()*1000000;
//        int final_score=-pills_no*10;
//        double final_score=-distance_nearest_pills-pills_no*10+sum_distance_pills;

        return (final_score);
    }


    public double alphabeta (int node0, int depth, double alpha, double beta, Boolean maximizingPlayer,Game game2)
    {

        Constants.GHOST[] ghost_list=Constants.GHOST.values();   //here is the ghost_list
        int[] ghost_locations;
        System.out.println("finding ghost locations");
        Hashtable<Constants.GHOST, Integer> ghosts_current_location_hash = Ghosts_current_location_hash(ghost_list,game2);
        HashSet<int[]>  ghost_nextsids_hashset=GHOSTS_all_possible_next_moves(ghost_list,game2);



        if (depth == 1)  //|| (node0 is a terminal node)
        {

            double score=score_eval(game2);
            System.out.println("reached depth 1 with score = "+ score);
            return (score);

        }

        if (maximizingPlayer == true)
        {
            double v=-9999999;

            int count=0;
            Iterator<int[]> ghosts_children = ghost_nextsids_hashset.iterator();

            while (ghosts_children.hasNext())
            {
                count=count+1;
                Game game3=game2.copy();
                int[]child_array=ghosts_children.next();
                System.out.println("nabian = "+Arrays.toString(child_array));
                EnumMap<Constants.GHOST, MOVE> gMap = new EnumMap<Constants.GHOST, MOVE>(Constants.GHOST.class);
                for (int i=0;i<ghost_list.length;i++)
                {
                    Constants.GHOST g_name=ghost_list[i];
                    MOVE g_move=eval_move(game3,ghosts_current_location_hash.get(g_name),child_array[i]);


                    gMap.put(g_name,g_move);

                }
//                game3.updateGhosts(gMap);
                game3.advanceGame(MOVE.NEUTRAL,gMap);
                v = Math.max(v, alphabeta(game3.getPacmanCurrentNodeIndex(), depth - 1, alpha, beta, false,game3));
                alpha = Math.max(alpha, v);
                if ((beta < v)||(beta==v))
                {
                    System.out.println("proning... with beta = "+beta + "   and alpha = " +alpha);
                    break;
                }
            }
            System.out.println("depth = "+depth+"  we have "+ count+ "   branches of ghosts");
            return v;
        }



//            for (int child:all_children_not_checked)
//            {
//                v = Math.max(v, alphabeta(child, depth - 1, alpha, beta, false,game2));
//                alpha = Math.max(alpha, v);
//                if ((beta < alpha)||(beta==alpha))
//                {
//                    break;
//                }
//            }
//            return v;

        else
        {
            double v= 9999999;
            int [] all_children_not_checked=game2.getNeighbouringNodes(node0);
            System.out.println("depth = "+depth+"  we have "+ all_children_not_checked.length+ "   branches of packman");
            for (int child:all_children_not_checked)
            {
                Game game3= game2.copy();
                EnumMap<Constants.GHOST, MOVE> gMap = new EnumMap<Constants.GHOST, MOVE>(Constants.GHOST.class);
                for (Constants.GHOST ghost:ghost_list)
                {
                    gMap.put(ghost,MOVE.NEUTRAL);
                }
                MOVE pack_man_next_move=eval_move(game3,game3.getPacmanCurrentNodeIndex(),child);


                game3.advanceGame(pack_man_next_move,gMap);
                v = Math.min(v, alphabeta(game3.getPacmanCurrentNodeIndex(), depth - 1, alpha, beta, true, game3));
                beta = Math.min(beta, v);
                if ((v < alpha) || (v== alpha))
                {
                    System.out.println("proning... with beta = "+beta + "   and alpha = " +alpha);
                    break;
                }
            }
            return v;
        }
    }



    public MOVE getMove(Game game, long timeDue)
    {

//        Constants.GHOST[] ghost_list=Constants.GHOST.values();
//        for (Constants.GHOST ghost:ghost_list)
//        {
//            System.out.println(game.getGhostCurrentNodeIndex(ghost));
//            if (game.getGhostCurrentNodeIndex(ghost)==-1)
//            {
//                System.out.println("we move left beacause of ghosts still there =  ");
//
//                return(MOVE.LEFT);
//
//            }
//        }
        int[] all_available_pills=game.getActivePillsIndices();
        System.out.println("shangooli  there are "+all_available_pills.length+"  pills left");

        double alpha = -9999999;
        double beta = 9999999;
        int depth=4;
        double bestScore = -Integer.MAX_VALUE;
        MOVE bestMove = null;
        int node0=game.getPacmanCurrentNodeIndex();
        for(MOVE child: game.getPossibleMoves(node0))
        {
            if(bestMove == null)
            {
                bestMove = child;
            }

            Game game2=game.copy();


            Constants.GHOST[] ghost_list=Constants.GHOST.values();   //here is the ghost_list
            EnumMap<Constants.GHOST, MOVE> gMap = new EnumMap<Constants.GHOST, MOVE>(Constants.GHOST.class);
            for (Constants.GHOST ghost:ghost_list)
            {
                gMap.put(ghost,MOVE.NEUTRAL);
            }

            game2.advanceGame(child,gMap);
            System.out.println("child = "+ child.toString());

            int pacman_next_node_id=game2.getPacmanCurrentNodeIndex();
            alpha = Math.max(alpha,alphabeta(pacman_next_node_id, depth - 1, alpha, beta,false,game2));
            if(alpha > bestScore)
            {
                bestMove = child;
                bestScore = alpha;
            }
        }

        System.out.println("this move is chosen " + bestMove.toString());
        return bestMove;



    }
}


