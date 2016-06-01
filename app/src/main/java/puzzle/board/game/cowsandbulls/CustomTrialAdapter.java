package puzzle.board.game.cowsandbulls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Varada on 3/27/2016.
 */
public class CustomTrialAdapter extends ArrayAdapter<Trial> {

    private Context context;
    private List<Trial> trialList = null;

    static class ViewHolder {
        TextView trial;
        TextView index;
        RatingBar cows;
        RatingBar bulls;
    }

    public CustomTrialAdapter(Context context, int resource,List<Trial> trials) {
        super(context, resource, trials);
        this.trialList = trials;
        this.context= context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trial trial = trialList.get(position);
        trial.setTrialNumber(position);
        ViewHolder holder;
        View row;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_custom_trial, null);

            holder = new ViewHolder();

            // Set the text
            RelativeLayout layout = (RelativeLayout) row.findViewById(R.id.row);
            /*if(trial.getTrialNumber()==0)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)layout.getLayoutParams();
                params.setMargins(0, 10, 0, 0);
                layout.setLayoutParams(params);
            }*/

            holder.index = (TextView) row.findViewById(R.id.index);
            holder.trial = (TextView) row.findViewById(R.id.trial);
            holder.cows = (RatingBar) row.findViewById(R.id.cowRating);
            holder.bulls = (RatingBar) row.findViewById(R.id.bullRating);

            holder.cows.setFocusable(false);
            holder.bulls.setFocusable(false);
            holder.cows.setIsIndicator(true);
            holder.bulls.setIsIndicator(true);

            row.setTag(holder);
        }
        else
        { row = convertView;
            holder = (ViewHolder) row.getTag();}


        int index = trial.getTrialNumber()+1;

        holder.index.setText(""+index+"]");
        holder.trial.setText(trial.getTrial() + "");

        holder.cows.setStepSize(1);
        holder.bulls.setStepSize(1);

        if(trial.getCows() > 0)
        {
            holder.cows.setRating(trial.getCows());
            holder.cows.setMax(trial.getCows());
            holder.cows.setNumStars(trial.getCows());
        }
        else {
            holder.cows.setRating(0);
            holder.cows.setMax(1);
            holder.cows.setNumStars(1);
        }

        if(trial.getBulls() > 0)
        {
            holder.bulls.setRating(trial.getBulls());
            holder.bulls.setMax(trial.getBulls());
            holder.bulls.setNumStars(trial.getBulls());
        }
        else
        {
            holder.bulls.setRating(0);
            holder.bulls.setMax(1);
            holder.bulls.setNumStars(1);
        }

        return row;
    }
    }
