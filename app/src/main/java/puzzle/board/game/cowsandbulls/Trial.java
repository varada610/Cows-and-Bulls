package puzzle.board.game.cowsandbulls;

/**
 * Created by Varada on 3/27/2016.
 */
public class Trial {

    private String trial="";
    private int trialNumber =0,bulls = 0, cows =0;

    public Trial() {  }

    public Trial(String trial) { this.trial = trial; }

    public Trial(int index,String trial, int cows, int bulls)
    {
        this.trialNumber = index;
        this.trial = trial;
        this.cows = cows;
        this.bulls = bulls;
    }

    public String getTrial() { return this.trial;}

    public void setTrialNumber(int index) { this.trialNumber = index;  }
    public int getTrialNumber() {   return this.trialNumber;  }

    public void setBulls(int bulls){ this.bulls = bulls; }
    public int getBulls(){ return  this.bulls;}

    public void setCows(int cows){ this.cows = cows; }
    public int getCows(){ return  this.cows;}
}
