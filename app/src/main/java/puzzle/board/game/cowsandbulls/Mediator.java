package puzzle.board.game.cowsandbulls;

import layout.DefaultFragment;
import layout.SolutionFragment;

/**
 * Created by Varada on 3/31/2016.
 */
public class Mediator {

    private GameBoard board=null;
    private DefaultFragment defaultFragment=null;
    private SolutionFragment solutionFragment=null;
    private static final Mediator instance = new Mediator();

    // Private constructor prevents instantiation from other classes
    private Mediator() { }

    public static Mediator getInstance() {
        return instance;
    }

    public String getSolution(){return this.defaultFragment.getSolution();}

    public void setBoard(GameBoard board){ this.board = board;}

    public void setDefaultFragment(DefaultFragment fragment){this.defaultFragment = fragment;}

    public void setSolutionFragment(SolutionFragment fragment){this.solutionFragment = fragment;}

    public void updateTrials(Trial trial){this.board.updateListTrials(trial);}

    public void informOfWin(String solution,boolean solved){this.board.gameWon(solution,solved);};

    public void reset(){this.board.reset();}
}
