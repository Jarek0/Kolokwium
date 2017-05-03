package pollub.edu.pl.kolokwium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pollub.edu.pl.kolokwium.database.DatabaseContentProvider;

public class MainActivity extends AppCompatActivity {

    public final static String ADULT_ACCESS = "adult access";

    boolean accessToAdultGames = false;

    Button dropDatabaseButton;
    Button listOfGamesButton;
    Button accessToAdultGamesButton;

    TextView numberOfGames;

    private DatabaseContentProvider provider=new DatabaseContentProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberOfGames = (TextView) findViewById(R.id.numberOfGames);
        getCountGames();

        listOfGamesButton = (Button) findViewById(R.id.listOfGames);
        listOfGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listOfGamesActivityIntent = new Intent(MainActivity.this, ListOfGames.class);
                listOfGamesActivityIntent.putExtra(ADULT_ACCESS, accessToAdultGames);
                startActivity(listOfGamesActivityIntent);
            }
        });

        dropDatabaseButton = (Button) findViewById(R.id.dropDatabaseButton);
        dropDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dropDatabaseIntent = new Intent(MainActivity.this, DropDatabase.class);
                startActivityForResult(dropDatabaseIntent, 0);
            }
        });

        accessToAdultGamesButton = (Button) findViewById(R.id.accessToAdultGamesButton);
        accessToAdultGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessToAdultGames=(!accessToAdultGames);
                accessToAdultGamesButton.setText("WYSWIETLENIE GIER DLA DOROSLYCH: "+ (accessToAdultGames ? "WLACZONY":"WYLACZONY"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCountGames();
        if (resultCode == 0) {
            Toast toast = Toast.makeText(MainActivity.this, "Baza zostala wyczyszczona" , Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void getCountGames() {
        numberOfGames.setText("Super spis gier ("+provider.getProfilesCount()+" tytulow)");
    }

    @Override
    public void onResume(){
        super.onResume();
        getCountGames();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
