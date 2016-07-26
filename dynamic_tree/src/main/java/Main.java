import entrants.pacman.username.*;
import examples.commGhosts.POCommGhosts;
import pacman.Executor;
import examples.poPacMan.POPacMan;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import pacman.controllers.examples.RandomGhosts;


/**
 * Created by pwillic on 06/05/2016.
 */


public class Main {






    public static void main(String[] args) {

        Executor executor = new Executor(false, true);

        System.out.println("MyPacman");
//        executor.runGameTimed(new POPacMan(), new POCommGhosts(50), true);
//        executor.runGameTimed(new MyPacMan(), new RandomGhosts(), true);
//        executor.runGameTimed(new MyPacMan_A_star(), new RandomGhosts(), true);
        executor.runGameTimed(new Alpha_Beta_proning(), new POCommGhosts(), true);
//        executor.runGame(new Alpha_Beta_proning(), new POCommGhosts(), true, 1000);
//        executor.runGameTimed(new Minmax(), new RandomGhosts(), true);


//
    }
}
