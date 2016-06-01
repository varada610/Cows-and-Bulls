package puzzle.board.game.cowsandbulls;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sa90.materialarcmenu.ArcMenu;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import layout.DefaultFragment;
import layout.SolutionFragment;

public class GameBoard extends AppCompatActivity implements DefaultFragment.OnFragmentInteractionListener,SolutionFragment.OnFragmentInteractionListener{

    ListView trialsList =null;
    List<Trial> trials = null;
    ArrayAdapter<Trial> adapter = null;
    Mediator mediator=null;
    private String message="";
    //DBMediator manager = null;
   // private String menu="";
    //private DBHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mediator = Mediator.getInstance();
        this.mediator.setBoard(this);
       // this.manager = new DBMediator(this);

/*
        int number = this.manager.loadFromDb();
        if(number > 0)
        {
            trials.clear();
            Trial[] list = this.manager.getTrials();
            for(int i=0; i<list.length ; i++)
                updateListTrials(list[i]);
        }
*/

        //this.menu = "menu";

      /*  ArcMenu menu = (ArcMenu)findViewById(R.id.arcmenu);
        //try
        {
            *//*InputStream imgStream1 = getAssets().open("menu.png");
            InputStream imgStream2 = getAssets().open("cross.png");
            Drawable drawable1 = Drawable.createFromStream(imgStream1, null);
            Drawable drawable2 = Drawable.createFromStream(imgStream2, null);*//*
            //if(!menu.isMenuOpened())
               // menu.setBackground(drawable2);
            //menu.set

            if(menu.isMenuOpened())
            menu.setBackgroundResource(R.drawable.menu);

        } *//*catch (IOException e) {
            e.printStackTrace();
        }*/


        FloatingActionButton reset = (FloatingActionButton)findViewById(R.id.play_again);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArcMenu menu = (ArcMenu)findViewById(R.id.arcmenu);
                if(menu.isMenuOpened()) menu.toggleMenu();
                reset();
            }
        });

        FloatingActionButton hint = (FloatingActionButton)findViewById(R.id.giveUP);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArcMenu menu = (ArcMenu)findViewById(R.id.arcmenu);
                if(menu.isMenuOpened()) menu.toggleMenu();
                displaySolution();
            }
        });

        FloatingActionButton rules = (FloatingActionButton)findViewById(R.id.rules);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rules = new Intent(GameBoard.this, Rules.class);
                ArcMenu menu = (ArcMenu)findViewById(R.id.arcmenu);
                if(menu.isMenuOpened()) menu.toggleMenu();
                //Write to the db
               // manager.writeToDB(trials);
                startActivity(rules);

            }
        });

        DefaultFragment defaultFragment = new DefaultFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLayout, defaultFragment)
                .commit();

        trials = new LinkedList<>();
        trialsList = (ListView)findViewById(R.id.trials);
        adapter = new CustomTrialAdapter(this ,R.layout.activity_custom_trial, trials);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.activity_header, trialsList, false);
        trialsList.addHeaderView(header,null,false);

        trialsList.setAdapter(adapter);

    }



    private void displaySolution()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you give up?\nThe solution will be displayed.");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //Reset game.
                        gameWon(getSolution(), false);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_settings:
            {
                sendMessage();
                break;
            }

        }
        return true;
    }

    private void sendMessage()
    {
        SmsManager smsManager = SmsManager.getDefault();
        message = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(GameBoard.this);
        builder.setTitle("Send Feedback about application");


// Set up the input
        final EditText input = new EditText(GameBoard.this);
        input.setText("I want to provide feedback on....\n");
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Send Feedback", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                message = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

        if(!message.equals(""))
        {smsManager.sendTextMessage("4086737589", null, message, null, null);}
    }
public void updateListTrials(Trial trial)
{
    ArcMenu menu = (ArcMenu)findViewById(R.id.arcmenu);
    if(menu.isMenuOpened()) menu.toggleMenu();

    if(duplicateTrialCheck(trial)) {
        trials.add(trial);
        trialsList.invalidateViews();
        adapter.notifyDataSetChanged();
    }
    else
    {
        Toast.makeText(getApplicationContext(),"Duplicate entry, Try again",Toast.LENGTH_LONG).show();
    }
}

    private boolean duplicateTrialCheck(Trial trial)
    {
        Iterator<Trial> iterator = trials.iterator();
        String currentTrial = "";
        while (iterator.hasNext())
        {
            currentTrial = iterator.next().getTrial();
            if(currentTrial.equals(trial.getTrial())) return false;
        }
        return true;
    }

    private String getSolution()
    {
        return this.mediator.getSolution();
    }

    public void gameWon(String solution,boolean solved)
    {
        //fragment manager to display winning fragment
        SolutionFragment solutionFragment = new SolutionFragment();
        Bundle args = new Bundle();
        args.putBoolean("won", solved);
        args.putString("solution", solution);
        solutionFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, solutionFragment)
                .commit();

    }

    private void playAgain()
    {
        trials.clear();
        trialsList.invalidateViews();
        adapter.notifyDataSetChanged();

        //SQLiteDatabase db = new DBHelper(GameBoard.this).getWritableDatabase();
        //db.execSQL("delete from " + DBHelper.DATABASE_TABLE);
        //fragment manager to display play panel
        DefaultFragment defaultFragment = new DefaultFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, defaultFragment)
                .commit();
    }

    public void reset()
    {
        //Alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to reset the game?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //Reset game.
                        playAgain();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert1 = builder.create();
        alert1.show();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
