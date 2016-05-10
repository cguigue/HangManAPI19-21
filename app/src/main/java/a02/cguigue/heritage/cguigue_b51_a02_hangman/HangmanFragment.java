package a02.cguigue.heritage.cguigue_b51_a02_hangman;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HangmanFragment extends Fragment {

    HangmanController controller;
    private int livesLost = 0;
    private String word;
    private String user;
    private int[] images = {R.drawable.hangman0, R.drawable.hangman1, R.drawable.hangman2, R.drawable.hangman3, R.drawable.hangman4, R.drawable.hangman5, R.drawable.hangman6};
    public HangmanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_hangman, container, false);

        ImageView image = (ImageView)vw.findViewById(R.id.ivHangman);
        image.setImageResource(images[livesLost]);

        return vw;
    }

    public void InitializeWord()
    {
        TextView tvWord = (TextView)getActivity().findViewById(R.id.tvWord);
        for (char letter: word.toCharArray())
        {
            tvWord.append("-");
        }
    }

    public void UpdateImage()
    {

        ImageView image = (ImageView)getActivity().findViewById(R.id.ivHangman);
        image.setImageResource(images[livesLost]);
    }

    public void AlphabetButtonClicked(Character letter)
    {
        CheckLetter(letter);
    }

    public void CheckLetter(Character letter)
    {
        boolean isFound = false;
        TextView tv = (TextView)getActivity().findViewById(R.id.tvWord);
        CharSequence text = tv.getText();
        String temp = "";
        for (int i = 0; i < text.length(); ++i)
        {
            if(Character.toLowerCase(letter.charValue()) == word.charAt(i))
            {
                temp += word.charAt(i);
                isFound = true;
            }
            else
            {
                temp += text.charAt(i);
            }
        }
        tv.setText(temp);

        if(!isFound)
        {
            ++livesLost;
        }

        UpdateImage();
        CheckForWin();
        CheckForDeath();

    }

    public void CheckForWin()
    {
        TextView temp = (TextView)getActivity().findViewById(R.id.tvWord);
        if(!temp.getText().toString().contains("-"))
        {
            onWin();
        }
    }

    public void CheckForDeath()
    {
        if(livesLost == 6)
        {
            controller.onDie();
            TextView temp = (TextView)getActivity().findViewById(R.id.tvWord);
            temp.setText(word);
        }
    }


    public void SetWord(String word)
    {
        this.word = word;
    }

    public void SetUser()
    {
        SharedPreferences pref = getActivity().getSharedPreferences("Hangman", Context.MODE_PRIVATE);
        user = pref.getString("user", "Anonymous");
        TextView tvUser = (TextView)getActivity().findViewById(R.id.tvUserName);
        if(!user.equals("Anonymous"))
        {
            tvUser.setText("Good Luck " + user +"!");
        }

    }

    public void onWin()
    {
        controller.onWin();
        int score = (word.length()/ livesLost) * 1000;
        DatabaseHandler db = new DatabaseHandler(getActivity());
        db.InsertScore(user, score + "");
    }

    public interface HangmanController
    {
        void onWin();

        void onDie();
    }

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


}
