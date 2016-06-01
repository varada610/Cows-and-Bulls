package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import puzzle.board.game.cowsandbulls.Calculation;
import puzzle.board.game.cowsandbulls.Mediator;
import puzzle.board.game.cowsandbulls.R;
import puzzle.board.game.cowsandbulls.Trial;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DefaultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Calculation calculation = new Calculation();



    Trial trial = null;
    Mediator mediator=null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DefaultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefaultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultFragment newInstance(String param1, String param2) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.mediator = Mediator.getInstance();
        this.mediator.setDefaultFragment(this);
       View view =  inflater.inflate(R.layout.fragment_default, container, false);

        //TEST CODE PLEASE REMOVE
       // EditText entry = (EditText)view.findViewById(R.id.entry);
       // entry.setText(this.calculation.getDigits());
         //TEST CODE PLEASE REMOVE

        // Inflate the layout for this fragment

        FloatingActionButton play = (FloatingActionButton)view.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("callbackcb", "reached");
                addTrial();
            }
        });
        return view;
    }

    public void addTrial()
    {
        EditText entry = (EditText)getView().findViewById(R.id.entry);
        String currentTrial = entry.getText().toString();
        Log.d("callbackadd", "reached");
        if(validateTrial(currentTrial))
        {
            this.trial = new Trial(currentTrial);
            entry.setText("");

            try
            {
                this.calculation.parseDigits(Integer.parseInt(currentTrial));
            }
            catch(NumberFormatException E){}

            this.calculation.calcScore();
           /* if(trial.getTrialNumber() > 0)*/
            trial.setBulls(this.calculation.getBulls());
            trial.setCows(this.calculation.getCows());
            //Toast.makeText(getApplicationContext(), this.calculation.getCows() + ": Cows " + trial.getTrial() + ": Trial", Toast.LENGTH_LONG).show();

            this.mediator.updateTrials(trial);
            if(this.calculation.hasWon())
            {
                String solution = getSolution();
                System.out.println("Has Won :"+solution);
                this.mediator.informOfWin(solution,true);
            }
        }

    }

    public String getSolution()
    {
        return this.calculation.getDigits();
    }

    public boolean validateTrial(String trial)
    {
        if(!(trial.equals("")) && (trial.matches("[0-9]+")) && !trial.contains(" ") && trial.length()==4) return true;

        else return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
