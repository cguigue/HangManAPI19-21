package a02.cguigue.heritage.cguigue_b51_a02_hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Button play = (Button)findViewById(R.id.btnPlay);
               play.setOnClickListener(new View.OnClickListener()
               {
                   @Override
                   public void onClick(View view)
                   {
                        Intent intent = new Intent(view.getContext(), HangmanActivity.class);
                       if(extras != null)
                       {
                           intent.putExtras(extras);
                       }
                        startActivity(intent);
                   }
               });
        Button options = (Button)findViewById(R.id.btnOptions);
               options.setOnClickListener(new View.OnClickListener()
               {
                   @Override
                   public void onClick(View view)
                   {
                        Intent intent = new Intent(view.getContext(), OptionsActivity.class);
                       if(extras != null)
                       {
                           intent.putExtras(extras);
                       }
                        startActivityForResult(intent, 200);
                   }
               });
        Button about = (Button)findViewById(R.id.btnAbout);
               about.setOnClickListener(new View.OnClickListener()
               {
                   @Override
                   public void onClick(View view)
                   {
                        Intent intent = new Intent(view.getContext(), AboutActivity.class);
                        startActivity(intent);
                   }
               });
        Button scores = (Button)findViewById(R.id.btnScores);
                scores.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
                        startActivity(intent);
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getExtras() != null)
        {
            extras = data.getExtras();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
