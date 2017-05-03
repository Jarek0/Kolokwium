package pollub.edu.pl.kolokwium;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pollub.edu.pl.kolokwium.database.DatabaseContentProvider;
import pollub.edu.pl.kolokwium.database.DatabaseHelper;

/**
 * Created by Dell on 2017-05-02.
 */

public class EditGame extends AppCompatActivity {
    private long rowId;
    private EditText titleInput;
    private EditText kindInput;
    private EditText yearInput;
    private EditText ageInput;
    private Button addGameButton;
    private final int MODE_ADD=1;
    private final int MODE_EDIT=0;
    private int MODE=MODE_ADD;
    private long editingItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        titleInput= (EditText) findViewById(R.id.titleOfGameET);
        kindInput= (EditText) findViewById(R.id.kindOfGameET);
        yearInput= (EditText) findViewById(R.id.yearOfGameET);
        ageInput= (EditText) findViewById(R.id.ageOfGameET);

        Bundle b=getIntent().getExtras();
        if(b!=null){
            editingItemId=b.getLong("itemId");
            MODE=MODE_EDIT;
        }else
            MODE=MODE_ADD;

        addGameButton= (Button) findViewById(R.id.addGameButton);

        if(MODE==MODE_EDIT)
            addGameButton.setText("EDYTUJ GRE W BAZIE");
        else
            addGameButton.setText("DODAJ GRE DO BAZY");

        addGameButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String error=validateElements();
                if(error.equals("")){
                    ContentValues gameContentValues=new ContentValues();
                    gameContentValues.put(DatabaseHelper.TITLE_COLUMN_NAME,titleInput.getText().toString());
                    gameContentValues.put(DatabaseHelper.KIND_COLUMN_NAME,kindInput.getText().toString());
                    gameContentValues.put(DatabaseHelper.YEAR_COLUMN_NAME,yearInput.getText().toString());
                    gameContentValues.put(DatabaseHelper.AGE_COLUMN_NAME,ageInput.getText().toString());
                    if(MODE==MODE_ADD) getContentResolver().insert(DatabaseContentProvider.CONTENT_URI,gameContentValues);
                    else getContentResolver().update(ContentUris.withAppendedId(DatabaseContentProvider.CONTENT_URI,editingItemId),gameContentValues,null,null);

                    Intent intent = new Intent();
                    setResult(MODE, intent);
                    finish();
                }else{
                    Toast toast=Toast.makeText(EditGame.this,error,Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        if(MODE==MODE_EDIT){
            String[] columnNames=new String[]{DatabaseHelper.ID,DatabaseHelper.TITLE_COLUMN_NAME,DatabaseHelper.YEAR_COLUMN_NAME,DatabaseHelper.AGE_COLUMN_NAME
                    ,DatabaseHelper.KIND_COLUMN_NAME};
            Cursor c=getContentResolver().query(ContentUris.withAppendedId(DatabaseContentProvider.CONTENT_URI,editingItemId),columnNames,null,null,null);
            c.moveToFirst();
            titleInput.setText(c.getString(c.getColumnIndex(DatabaseHelper.TITLE_COLUMN_NAME)));
            yearInput.setText(c.getString(c.getColumnIndex(DatabaseHelper.YEAR_COLUMN_NAME)));
            kindInput.setText(c.getString(c.getColumnIndex(DatabaseHelper.KIND_COLUMN_NAME)));
            ageInput.setText(c.getString(c.getColumnIndex(DatabaseHelper.AGE_COLUMN_NAME)));
        }
    }

    private String validateElements(){
        String message="";
        if(!(titleInput.getText().toString().length()>0 && titleInput.getText().toString().length()<20)){
            message+="bledny tytul\n";
        }
        if(!(kindInput.getText().toString().length()>0 && titleInput.getText().toString().length()<20)){
            message+="bledny gatunek\n";
        }
        if(!(yearInput.getText().toString().length()>0 && yearInput.getText().toString().matches("^(19|20)[0-9]{2}$"))){
            message+="bledny rok\n";
        }
        if(!(ageInput.getText().toString().length()>0 && ageInput.getText().toString().matches("^([1-9]|1[0-8])$"))){
            message+="bledny wiek\n";
        }
        message.trim();
        return message;
    }
}
