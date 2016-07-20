import entrants.pacman.username.MyPacMan_A_star;
import examples.commGhosts.POCommGhosts;
import pacman.Executor;
import examples.poPacMan.POPacMan;
import entrants.pacman.username.MyPacMan;
import entrants.pacman.username.global_vars;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.Arrays;
import pacman.controllers.examples.RandomGhosts;


/**
 * Created by pwillic on 06/05/2016.
 */


public class Main {






    public static void main(String[] args) {

        Executor executor = new Executor(true, true);










        System.out.println("MyPacman");
//        executor.runGameTimed(new POPacMan(), new POCommGhosts(50), true);
//        executor.runGameTimed(new MyPacMan(), new RandomGhosts(), true);
        executor.runGameTimed(new MyPacMan_A_star(), new RandomGhosts(), true);
//
    }
}
