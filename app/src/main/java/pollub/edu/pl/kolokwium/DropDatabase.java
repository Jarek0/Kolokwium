package pollub.edu.pl.kolokwium;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pollub.edu.pl.kolokwium.database.DatabaseContentProvider;

/**
 * Created by Dell on 2017-05-02.
 */

public class DropDatabase  extends AppCompatActivity{

    private DatabaseContentProvider provider=new DatabaseContentProvider();

    Button dropDatabaseButton;
    Button cancelDropDatabaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_database);

        dropDatabaseButton = (Button) findViewById(R.id.yesDropDatabaseButton);
        dropDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider.deleteAll();
                Intent intent = new Intent();
                setResult(0, intent);
                finish();
            }
        });

        cancelDropDatabaseButton = (Button) findViewById(R.id.cancelDropDatabaseButton);
        cancelDropDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
