package pollub.edu.pl.kolokwium;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import pollub.edu.pl.kolokwium.database.DatabaseHelper;


public class CustomAdapter extends CursorAdapter {
    public CustomAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.game_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvBody = (TextView) view.findViewById(R.id.gameRow);

        String body = cursor.getPosition()+1+". "+
        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TITLE_COLUMN_NAME))+" ["+
        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.YEAR_COLUMN_NAME))+"]";

        tvBody.setText(body);
    }
}