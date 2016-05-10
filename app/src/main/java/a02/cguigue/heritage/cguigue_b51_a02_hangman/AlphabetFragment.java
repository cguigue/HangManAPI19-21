package a02.cguigue.heritage.cguigue_b51_a02_hangman;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlphabetFragment extends Fragment {

    HangmanController controller;
    private  String chosenLetters;

    public AlphabetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_alphabet, container, false);
        if(chosenLetters != null)
        {
            chosenLetters = savedInstanceState.getString("chosenLetters");
        }
        else
        {
            chosenLetters = "";
        }

        GridView alphaGrid = (GridView)vw.findViewById(R.id.gvAlphabet);
        alphaGrid.setAdapter(new ButtonAdapter(vw.getContext()));

        Button close = (Button)vw.findViewById(R.id.btnClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return vw;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("chosenLetters", chosenLetters);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null)
        {
            chosenLetters = savedInstanceState.getString("chosenLetters");
        }

    }


    public class ButtonAdapter extends BaseAdapter
    {
        private Context mContext;

        public ButtonAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return alphabet.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new Button for each item referenced by the Adapter
        public View getView(int position, View vw, ViewGroup parent) {
            Button btn;
            if (vw == null) {
                // if it's not recycled, initialize some attributes
                btn = new Button(mContext);
                btn.setTextSize(30);
                btn.setLayoutParams(new GridView.LayoutParams(180, 160));
                btn.setPadding(2, 2, 2, 2);
            } else {
                btn = (Button) vw;
            }
            if(chosenLetters.length() > 0)
            {
                for (char letter: chosenLetters.toCharArray())
                {
                    String current = letter+"";
                    if(current == btn.getText().toString())
                    {
                        btn.setVisibility(View.INVISIBLE);
                    }
                }
            }
            btn.setText(alphabet[position].toString());
            btn.setId(alphabet[position]);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnButtonClick(v);
                }
            });
            return btn;
        }

        public void OnButtonClick(View v)
        {
            Button btn = (Button)v;
            btn.setVisibility(View.INVISIBLE);
            LetterClicked(btn.getText().charAt(0));
            chosenLetters += btn.getText().toString();
        }

        // references to our images
        private Character[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }

    public interface HangmanController
    {
        void AlphabetButtonClicked(Character letter);
    }//HangmanController

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            controller = (HangmanController)activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString());
        }
    }//onAttach(Activity)

    public void onWin()
    {
        TextView message = (TextView)getActivity().findViewById(R.id.tvMessage);
        Button close = (Button)getActivity().findViewById(R.id.btnClose);
        GridView keys = (GridView)getActivity().findViewById(R.id.gvAlphabet);
        message.setText("Congratulations you solved the word!");
        keys.setVisibility(View.GONE);
        close.setVisibility(View.VISIBLE);
        message.setVisibility(View.VISIBLE);
    }

    public void onDie()
    {
        TextView message = (TextView)getActivity().findViewById(R.id.tvMessage);
        Button close = (Button)getActivity().findViewById(R.id.btnClose);
        GridView keys = (GridView)getActivity().findViewById(R.id.gvAlphabet);
        message.setText("Sorry! you ran out of lives.");
        keys.setVisibility(View.GONE);
        close.setVisibility(View.VISIBLE);
        message.setVisibility(View.VISIBLE);
    }

    public void LetterClicked(Character letter)
    {
        controller.AlphabetButtonClicked(letter);
    }

}//AlphabetFragment
