package a02.cguigue.heritage.cguigue_b51_a02_hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class HangmanActivity extends AppCompatActivity
                             implements AlphabetFragment.HangmanController, HangmanFragment.HangmanController

{
    private String mode;
    private String word;
    private String user;
    private int max;
    private int min;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            mode = extras.getString("mode");
            max = extras.getInt("max");
            min = extras.getInt("min");
        }
        else
        {
            mode = "easy";
            min = 3;
            max = 15;

        }
        LoadWords(mode);
        GenerateWord();

        SharedPreferences pref = getSharedPreferences("Hangman", Context.MODE_PRIVATE);
        user = pref.getString("user", "Anonymous");

    }

    @Override
    protected void onResume()
    {
        SharedPreferences pref = getSharedPreferences("Hangman", Context.MODE_PRIVATE);

        if(!user.equals(pref.getString("user", "Anonymous")))
        {
            recreate();
        }
        super.onResume();
    }

    public void LoadWords(String mode)
    {
        try
        {
            if(mode.equals("easy"))
            {
                InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.hangwords_easy));
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.reCreateWords();
                BufferedReader buffer = new BufferedReader(reader);
                String word;

                while((word = buffer.readLine()) != null)
                {

                    db.InsertWord(word);
                }
            }
            else
            if(mode.equals("hard"))
            {
                InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.hangwords_hard));
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.reCreateWords();
                BufferedReader buffer = new BufferedReader(reader);
                String word;

                while((word = buffer.readLine()) != null)
                {

                    db.InsertWord(word);
                }
            }
        }
        catch(IOException e)
        {
            //TODO failed to load Words to database
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hangman, menu);
        return true;
    }

    public void GenerateWord()
    {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> words = db.getWords();
        int location = new Random().nextInt(words.size());
        word = words.get(location);

        if(max >= word.length() && word.length() >= min)
        {
            SetWord(word);
            InitializeWord();
        }
        else
            GenerateWord();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


            Intent intent = new Intent(this, OptionsActivity.class);
            startActivity(intent);


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
       Interface methods, passes data to the HangmanFragment so it may use it.
     */
    @Override
    public void AlphabetButtonClicked(Character letter) {
        HangmanFragment hangman = (HangmanFragment)getFragmentManager().findFragmentById(R.id.fragHangman);
        hangman.AlphabetButtonClicked(letter);
    }

    public void SetWord(String word)
    {
        HangmanFragment hangmanFragment = (HangmanFragment)getFragmentManager().findFragmentById(R.id.fragHangman);
        hangmanFragment.SetWord(word);
        hangmanFragment.SetUser();
    }

    public void InitializeWord()
    {
        HangmanFragment hangmanFragment = (HangmanFragment)getFragmentManager().findFragmentById(R.id.fragHangman);
        hangmanFragment.InitializeWord();
    }
    /*
        Interface methods, passes data to the AlphabetFragment so it may use it.
     */
    @Override
    public void onWin()
    {
        AlphabetFragment alphabet = (AlphabetFragment)getFragmentManager().findFragmentById(R.id.fragAlphabet);
        alphabet.onWin();
    }

    public void onDie()
    {
        AlphabetFragment alphabet = (AlphabetFragment)getFragmentManager().findFragmentById(R.id.fragAlphabet);
        alphabet.onDie();
    }


}
