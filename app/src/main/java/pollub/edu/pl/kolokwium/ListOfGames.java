package pollub.edu.pl.kolokwium;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pollub.edu.pl.kolokwium.database.DatabaseContentProvider;
import pollub.edu.pl.kolokwium.database.DatabaseHelper;

/**
 * Created by Dell on 2017-05-02.
 */

public class ListOfGames extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CustomAdapter listAdapter;
    private ListView listView;

    int sortPointer = 0;
    ArrayList<String> kindSort = new ArrayList<String>(){{
        add(DatabaseHelper.ID);
        add(DatabaseHelper.TITLE_COLUMN_NAME);
        add(DatabaseHelper.KIND_COLUMN_NAME);
        add(DatabaseHelper.YEAR_COLUMN_NAME);
        add(DatabaseHelper.AGE_COLUMN_NAME);
    }};

    boolean accessToAdultGames = false;

    Button sortByTitleButton;
    TextView addGameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            accessToAdultGames = bundle.getBoolean(MainActivity.ADULT_ACCESS);
        } else {
            accessToAdultGames = savedInstanceState.getBoolean(MainActivity.ADULT_ACCESS);
        }

        setContentView(R.layout.activity_game_list);

        listView= (ListView) findViewById(R.id.listView);
        runLoader();

        addGameTextView= (TextView) findViewById(R.id.addGameTV);
        addGameTextView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Intent editGameIntent = new Intent(ListOfGames.this, EditGame.class);
                                                   startActivityForResult(editGameIntent,0);
                                               }
                                           });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent=new Intent(ListOfGames.this,EditGame.class);
                Bundle b=new Bundle();
                b.putLong("itemId",id);
                editIntent.putExtras(b);
                startActivityForResult(editIntent,1);
            }
        });

        sortByTitleButton= (Button) findViewById(R.id.sortByTitleButton);
        sortByTitleButton.setText("SORTOWANIE PO: "+kindSort.get(sortPointer));
        sortByTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sortPointer+1>=kindSort.size())
                    sortPointer=0;
                else
                    sortPointer+=1;

                sortByTitleButton.setText("SORTOWANIE PO: "+kindSort.get(sortPointer));
                runLoader();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast toast;
        if (resultCode == 1)
            toast= Toast.makeText(ListOfGames.this, "Dodano nowy rekord" , Toast.LENGTH_LONG);
        else
            toast= Toast.makeText(ListOfGames.this, "Edytowano rekord", Toast.LENGTH_LONG);

        runLoader();
        toast.show();
    }

    private void runLoader(){
        getLoaderManager().initLoader(0,null,this);
        String[] columnNames={DatabaseHelper.ID,DatabaseHelper.TITLE_COLUMN_NAME,DatabaseHelper.YEAR_COLUMN_NAME};
        Cursor cursor=getContentResolver().query(DatabaseContentProvider.CONTENT_URI,columnNames,null,null,kindSort.get(sortPointer));
        listAdapter=new CustomAdapter(this,cursor);
        listView.setAdapter(listAdapter);
    }


    @Override
    protected void onResume() {
        listAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection={DatabaseHelper.ID,DatabaseHelper.TITLE_COLUMN_NAME,DatabaseHelper.YEAR_COLUMN_NAME};
        CursorLoader cursorLoader=null;
        String sort = DatabaseHelper.TITLE_COLUMN_NAME;
        if(!accessToAdultGames) {
            String selection = DatabaseHelper.AGE_COLUMN_NAME + "<?";
            String[] selectionArgs = {"18"};
            cursorLoader = new CursorLoader(this, DatabaseContentProvider.CONTENT_URI, projection, selection, selectionArgs, kindSort.get(sortPointer));
        }
        else
        {
            cursorLoader = new CursorLoader(this, DatabaseContentProvider.CONTENT_URI, projection, null, null, kindSort.get(sortPointer));
        }
            return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listAdapter.swapCursor(null);

    }
}
