package a02.cguigue.heritage.cguigue_b51_a02_hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class OptionsActivity extends AppCompatActivity {

    private int min = 3;
    private int max = 15;
    private String mode = "easy";
    private int difficulty = 0;
    private String userName = "Anonymous";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Bundle extras;
        extras = getIntent().getExtras();
        if(extras != null)
        {
            mode = extras.getString("mode");
            max = extras.getInt("max");
            min = extras.getInt("min");
            userName = extras.getString("user");
            if(mode == "easy")
            {
                difficulty = 0;
            }
            else
            {
                difficulty = 1;
            }
        }
        //Load the Listeners
        LoadListeners();
        //Load all spinner adapters
        LoadAllSpinners();

        //Set spinners to default or user values
        Spinner spnMin = (Spinner)findViewById(R.id.spnMin);
        spnMin.setSelection(min - 3);
        Spinner spnMax = (Spinner)findViewById(R.id.spnMax);
        spnMax.setSelection(max - 3);
        Spinner spnDifficulty = (Spinner)findViewById(R.id.spnDifficulty);
        spnDifficulty.setSelection(difficulty);

        //Hide widgets for adding users
        EditText user = (EditText)findViewById(R.id.etUser);
        user.setVisibility(View.GONE);
        Button addUser = (Button)findViewById(R.id.btnCommit);
        addUser.setVisibility(View.GONE);
    }//onCreate()

    public void LoadListeners()
    {
        //Save button Listener
        Button save = (Button)findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });

        //Commit User Button Listener
        Button addUser = (Button)findViewById(R.id.btnCommit);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommitUser();
            }
        });

        //Users Spinner Listener
        Spinner spnUsers = (Spinner)findViewById(R.id.spnUsers);
        spnUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                if (value.equals("Add New User")) {
                    EditText user = (EditText) findViewById(R.id.etUser);
                    user.setVisibility(View.VISIBLE);
                    Button addUser = (Button) findViewById(R.id.btnCommit);
                    addUser.setVisibility(View.VISIBLE);
                } else
                    if (value.equals("Select User"))
                    {
                    EditText user = (EditText) findViewById(R.id.etUser);
                    user.setVisibility(View.GONE);
                    Button addUser = (Button) findViewById(R.id.btnCommit);
                    addUser.setVisibility(View.GONE);
                    }
                    else {
                    userName = value;
                    SharedPreferences pref = getSharedPreferences("Hangman", Context.MODE_PRIVATE);
                    pref.edit().putString("user", userName).apply();
                    EditText user = (EditText) findViewById(R.id.etUser);
                    user.setVisibility(View.GONE);
                    Button addUser = (Button) findViewById(R.id.btnCommit);
                    addUser.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Min length Spinner Listener
        Spinner spnMin = (Spinner)findViewById(R.id.spnMin);
        spnMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                min = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Max length Spinner Listener
        Spinner spnMax = (Spinner)findViewById(R.id.spnMax);
        spnMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                max = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spnDifficulty = (Spinner)findViewById(R.id.spnDifficulty);
        spnDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mode = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }//LoadListeners

    public void LoadAllSpinners()
    {
        //Load Max spinner
        Spinner spnMax = (Spinner)findViewById(R.id.spnMax);
        ArrayAdapter maxAdapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_list_item_1);
        spnMax.setAdapter(maxAdapter);

        //Load Min spinner
        Spinner spnMin = (Spinner)findViewById(R.id.spnMin);
        ArrayAdapter minAdapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_list_item_1);
        spnMin.setAdapter(minAdapter);

        //Load Mode Spinner
        Spinner spnDifficulty = (Spinner)findViewById(R.id.spnDifficulty);
        ArrayAdapter modeAdapter = ArrayAdapter.createFromResource(this, R.array.difficulties, android.R.layout.simple_list_item_1);
        spnDifficulty.setAdapter(modeAdapter);

        //Load Users Spinner
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        Spinner spnUsers = (Spinner)findViewById(R.id.spnUsers);
        List<String> users = db.getUsers();
        users.add(0, "Select User");
        users.add("Add New User");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, users);
        spnUsers.setAdapter(adapter);
    }

    public void onCommitUser()
    {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        EditText user = (EditText)findViewById(R.id.etUser);
        if(user.getText().length() < 3)
        {
            return;
        }
        userName = user.getText().toString();
        db.InsertUser(user.getText().toString());
        LoadAllSpinners();
        user.setVisibility(View.GONE);
        Button addUser = (Button)findViewById(R.id.btnCommit);
        addUser.setVisibility(View.GONE);
    }

    public void onSave()
    {
        //Create and fill bundle
        Bundle extras = new Bundle();
        extras.putInt("min", min);
        extras.putInt("max", max);
        extras.putString("mode", mode);
        extras.putString("user", userName);

        //Add bundle to Intent
        Intent intent = new Intent();
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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

    @Override
    public void onBackPressed() {
        onSave();
        super.onBackPressed();

    }
}
