package puzzle.board.game.cowsandbulls;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Rules extends AppCompatActivity {

    private String message="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String rules = ("\t How to play : \n\n\t Enter a number with 4 different digits \n\t and the computer has a secret"+
                "\n\t code of 4 digits selected for you already.\n\n\t #For every digit in the right place you\n" +
                "\t get a Bull." +
                "\n\t #For every right digit you get a Cow. \n\t #There are no repeated digits.\n\t #The trials are unlimited." +
                "\n\n\t The ultimate objective is to get 4 bulls. "+
                "\n" +
                "\t Click the menu at the Bottom-Right \n\tto get help or quit anytime"+
                "\n\n\t\t\t\t\t\t\t\t Have fun!! ");

        TextView rulesText = (TextView)findViewById(R.id.rules);
        rulesText.setText(rules);

        FloatingActionButton play = (FloatingActionButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        FloatingActionButton feedback = (FloatingActionButton) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                message = "";

                AlertDialog.Builder builder = new AlertDialog.Builder(Rules.this);
                builder.setTitle("Send Feedback about application");


// Set up the input
                final EditText input = new EditText(Rules.this);
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
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }

}
