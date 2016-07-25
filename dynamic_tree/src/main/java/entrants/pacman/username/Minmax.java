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




public class Minmax extends PacmanController {
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










    public MOVE getMove(Game game, long timeDue)
    {

        int node0 =game.getPacmanCurrentNodeIndex();
        int neighbors[]=game.getNeighbouringNodes(node0);
        for (int neighbor:neighbors)
        {
            System.out.println(neighbor);
            MOVE checkmove=eval_move(game,node0,neighbor);
            System.out.println("the neighbor id is =  "+ neighbor+ " with the move = " +checkmove.toString());
        }
        return(MOVE.LEFT);

    }
}


