package puzzle.board.game.cowsandbulls;

import java.util.Random;

/**
 * Created by Varada on 3/26/2016.
 */
public class Calculation
{
    private int[] userGuess=null;
    private int[] key=null;

    private int bulls;
    private int cows;

    public Calculation()
    {
        this.userGuess = new int[4];
        this.key = new int[4];
        this.bulls = 0;

        this.randNumbGen();
	/*	for(int index=0;index<4;index++)
			System.out.print(this.key[index]);*/
    }

    public void parseDigits(int fourDigNumber)
    {
        if(fourDigNumber > 10)
        {
            int index = 0;
            int number = fourDigNumber;
            while (number > 0)
            {
                this.userGuess[(Math.abs(index-4))-1]=( number % 10);
                number = number / 10;
                index++;
            }

        }//end if
    }

    public String getDigits()
    {
        String digits="";
        for(int index=0;index<4;index++)
            digits = digits.concat(String.valueOf(this.key[index]));

        return digits;
    }

    public boolean hasWon()
    {
        boolean bool = false;

        if(bulls==4)
            bool = true;

        return bool;
    }

    private void randNumbGen()
    {
        boolean positionHit = false;
        Random ranNumber = new Random();
        for(int index=0; index < 4;index++)
        {
            this.key[index] = ranNumber.nextInt(10);
        }

        while(positionHit == false)
        {
            positionHit = this.areDuplicates(ranNumber);
        }


    }

    private boolean areDuplicates(Random rand)
    {
        boolean bool = true;

        for(int i=0;i < 3;i++)
            for(int j=i+1; j < 4; j++)
            {
                if((this.key[i]==this.key[j]) && (i!=j))
                {
                    this.key[i] = rand.nextInt(10);
                    bool = false;
                }
            }
        return bool;
    }

    public void calcScore()
    {
        this.bulls=0;
        this.cows=0;

        //BULLS
        for(int index=0;index<4;index++)
            if(this.key[index]==this.userGuess[index])
                this.bulls++;

        //COWS
        for(int i=0;i < 4;i++)
            for(int j=0; j < 4; j++)
            {
                if(this.key[i]==this.userGuess[j])
                    this.cows++;
            }

        if(this.bulls>0)
        {
            this.cows = this.cows-this.bulls;
        }
    }

    public int getBulls()
    {
        return this.bulls;
    }

    public int getCows()
    {
        return this.cows;
    }

}
