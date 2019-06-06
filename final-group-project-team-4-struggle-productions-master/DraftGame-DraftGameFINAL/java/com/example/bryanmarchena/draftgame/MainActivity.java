package com.example.bryanmarchena.draftgame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected GameItemAdapter adapter;
    private ArrayList<GameItem> gameItems;
    private TypedArray gameImageResources;
    public GameListOpenHelper sqLiteHelper;
    protected Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameItems = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        sqLiteHelper = new GameListOpenHelper(this);

        String query = "SELECT * FROM " + sqLiteHelper.TABLE_GAME_LIST + " ORDER BY " +
                sqLiteHelper.KEY_ID + " ASC";


        sqLiteHelper.query(query);

        gameItems = sqLiteHelper.games;
        adapter = new GameItemAdapter(this, gameItems);
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();


    }


    public void update(ArrayList<GameItem> gg){

       // adapter = new GameItemAdapter(this, gg);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent();
        GameItem gm = new GameItem();
        gm.setTitle(intent.getStringExtra("gameTitle"));
        gm.setDescription(intent.getStringExtra("gameDesc"));
        gm.setConsoles(intent.getStringExtra("gameCon"));
        gm.setCategories(intent.getStringExtra("gameCate"));
        gm.setDate(intent.getStringExtra("gameDate"));
        gm.setPublisher(intent.getStringExtra("gamePub"));
        if(gm.getTitle() == ""){
            gameItems.add(gm);
            adapter.notifyDataSetChanged();
        }

        //gameItems = sqLiteHelper.games;


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //adapter.notifyDataSetChanged();
    }


    public void addGame(View view) {
        Intent intent = new Intent (this, AddGame.class);
        startActivityForResult(intent, 99);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == RESULT_OK){
            //gameItems = sqLiteHelper.games;
            //adapter.notifyDataSetChanged();
        }
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.Settings) {
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivityForResult(intent,99);
//
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // launch settings activity
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_settings_headers) {
            // launch settings activity
            startActivity(new Intent(MainActivity.this, SettingsHeadersActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SortActivity extends AppCompatActivity{
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sort);
        }
    }



    public void Onclick(MenuItem item){

        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(this);
        final android.view.View dialogView = inflater.inflate(R.layout.sort, null);
        dialogBuilder.setView(dialogView);
        android.widget.TextView sort = dialogView.findViewById(R.id.textView);
        final CheckBox ps4 = (android.widget.CheckBox) dialogView.findViewById(R.id.sort_PS4);
        final CheckBox xbox = (android.widget.CheckBox) dialogView.findViewById(R.id.sort_XBOX);
        final CheckBox swich = (android.widget.CheckBox) dialogView.findViewById(R.id.sort_Switch);
        final CheckBox pc = (android.widget.CheckBox) dialogView.findViewById(R.id.sort_PC);

        dialogBuilder.setPositiveButton("DONE", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String query1 = "SELECT * FROM " + sqLiteHelper.TABLE_GAME_LIST +
                        " WHERE " + sqLiteHelper.KEY_CONSOLES;

                int numChecked = 0;


                String ps4Q = " LIKE " + "'%PS4%'";
                boolean ps4Checked = false;

                String xboxQ = " LIKE " + "'%XBOX%'";
                boolean xboxChecked = false;

                String swichQ = " LIKE " + "'%Switch%'";
                boolean swichChecked = false;

                String pcQ = " LIKE " + "'%PC%'";
                boolean pcChecked = false;

                android.util.Log.d("addContent", "DONE");


                if (ps4.isChecked() && numChecked < 1) {
                    query1 = query1 + ps4Q;
                    numChecked += 1;
                    ps4Checked = true;
                    Log.d("InSorter1", ""+query1);
                }

                if (xbox.isChecked() && numChecked < 1) {
                    query1 = query1 + xboxQ;
                    numChecked += 1;
                    xboxChecked = true;
                    Log.d("InSorter2", ""+query1);
                }

                if (pc.isChecked() && numChecked < 1) {
                    query1 = query1 + pcQ;
                    numChecked += 1;
                    xboxChecked = true;
                    Log.d("InSorter3", ""+query1);
                }

                if (swich.isChecked() && numChecked < 1) {
                    query1 = query1 + swichQ;
                    numChecked += 1;
                    swichChecked = true;
                    Log.d("InSorter4", ""+query1);
                }
                if (ps4.isChecked() && numChecked >= 1 && ps4Checked == false) {
                    query1 = query1 + " AND " + sqLiteHelper.KEY_CONSOLES + ps4Q;
                    numChecked += 1;
                    Log.d("InSorter5", ""+query1);
                }

                if (xbox.isChecked() && numChecked >= 1 && xboxChecked == false) {
                    query1 = query1 + " AND " + sqLiteHelper.KEY_CONSOLES + xboxQ;
                    numChecked += 1;
                    Log.d("InSorter6", ""+query1);
                }


                if (pc.isChecked() && numChecked >= 1 && pcChecked == false) {
                    query1 = query1 + " AND " + sqLiteHelper.KEY_CONSOLES + pcQ;
                    numChecked += 1;
                    Log.d("InSorter7", ""+query1);
                }

                if (swich.isChecked() && numChecked >= 1 && swichChecked == false) {
                    query1 = query1 + " AND " + sqLiteHelper.KEY_CONSOLES + swichQ;
                    numChecked += 1;
                    Log.d("InSorter8", ""+query1);
                }

                if(ps4Checked == false && xboxChecked == false && swichChecked == false && pcChecked == false){
                    sqLiteHelper.query("SELECT * FROM " + sqLiteHelper.TABLE_GAME_LIST + " ORDER BY " +
                            sqLiteHelper.KEY_ID + " ASC");
                    Log.d("InSorter9", ""+query1);
                } else {
                    Log.d("InSorterElse", ""+query1);
                    sqLiteHelper.query(query1);
                }

                Log.d("InSorter", ""+query1);

                //adapter.notifyDataSetChanged();//update the RecyclerView by notifying the Adapter
            }

        });

        dialogBuilder.create().show();


    }



}
