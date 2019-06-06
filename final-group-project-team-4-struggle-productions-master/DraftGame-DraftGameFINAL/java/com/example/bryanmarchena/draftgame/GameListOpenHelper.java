package com.example.bryanmarchena.draftgame;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;

public class GameListOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    //DB Name, Version, Table Name
    private static final String DATABASE_NAME = "gameList";
    private static final int DATABASE_VERSION = 1 ;//important!!!! especially first time around
    protected static final String TABLE_GAME_LIST = "gameEntries";
    protected Context context;
    protected ArrayList<GameItem> games = new ArrayList<>();
    protected GameItemAdapter adapter;
    protected MainActivity mainActivity;



    //column names
    protected static final String KEY_ID = "title";
    protected static final String KEY_DESCRIPT = "description";
    protected static final String KEY_DATE = "date";
    protected static final String KEY_PUBLISHER = "publisher";
    protected static final String KEY_CONSOLES = "consoles";
    protected static final String KEY_CATEGORIES = "categories";


    private static final String [] COLUMNS = {KEY_ID, KEY_DESCRIPT, KEY_DATE, KEY_PUBLISHER, KEY_CONSOLES, KEY_CATEGORIES };

    private static final String CREATE_TABLE_WORD_LIST =
            "CREATE TABLE " + TABLE_GAME_LIST + " (" +
                    KEY_ID + " STRING PRIMARY KEY, " + //if no id is specified it will be autoincremented
                    KEY_DESCRIPT + " TEXT, " + KEY_DATE + " TEXT, "
                    + KEY_PUBLISHER + " TEXT, " + KEY_CONSOLES + " TEXT, " + KEY_CATEGORIES + " TEXT); ";
    private android.database.sqlite.SQLiteDatabase myReadableDB, myWritableDB;

    public GameListOpenHelper(android.content.Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        mainActivity = new MainActivity();


        Log.d("WordListOpenHelper","Inside WordListOpenHelper constructor");
    }
    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {
        Log.d("WordListOpenHelper", "inside onCreate of WordListOpenHelper");

        sqLiteDatabase.execSQL(CREATE_TABLE_WORD_LIST);
        fillMyDBwithData(sqLiteDatabase);




    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("WordListOpenHelper", "inside onUpgrade of WordListOpenHelper");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_LIST);
        onCreate(sqLiteDatabase);
    }

    protected void fillMyDBwithData(android.database.sqlite.SQLiteDatabase db){
        String [] games =
                {"Super Smash Bros.", "God of War", "Fallout 76", "Red Dead Redemption 2",
                        "Kingdom Hearts 3", "Spider-Man", "Overwatch", "The Witcher 3", "Ni No Kuni 2", "Dark Souls", "Grand Theft Auto V",
                        "Final Fantasy XV"};
        String [] descriptions = {context.getResources().getString(R.string.ssbu_desc), context.getResources().getString(R.string.gow_desc), context.getResources().getString(R.string.fallout_desc), context.getResources().getString(R.string.rdr2_desc),
                context.getResources().getString(R.string.kh3_desc), context.getResources().getString(R.string.spider_desc), context.getResources().getString(R.string.over_desc), context.getResources().getString(R.string.witcher_desc), context.getResources().getString(R.string.kuni_desc), context.getResources().getString(R.string.souls_desc), context.getResources().getString(R.string.gta_desc),
                context.getResources().getString(R.string.ff_desc)};
        String [] publisher = {"Nintendo", "Santa Monica Studios", "Bethesda", "Rockstar Games",
                "Square Enix", "Insomniac Games", "Blizzard", "CD Projekt Red", "Level-5", "From Software", "Rockstar Games",
                "Square Enix" };
        String [] dates = {"12-06-2018", "04-20-2018", "11-14-2018", "10-26-2018",
                "01-29-2019", "09-07-2018", "05-24-2016", "05-19-2016", "03-23-2018", "09-22-2011", "09-17-2013",
                "11-29-2016"};
        String [] consoles = {"Switch", "PS4", "PS4, XBOX, PC", "PS4, XBOX",
                "PS4, XBOX", "PS4", "PS4, XBOX, PC", "PS4, XBOX, PC", "PS4", "PS4, XBOX, PC, Switch", "PS4, XBOX, PC",
                "PS4, XBOX, PC"};
        String [] categories = {"Fighting", "Action, Adventure", "Shooter, RPG", "Shooter, Adventure, RPG",
                "Action, RPG", "Action, Adventure", "Shooter", "Action, Adventure, RPG", "JRPG, Adventure", "RPG, Adventure", "Shooter, Adventure",
                "JRPG, Adventure"};


        ContentValues values = new ContentValues();
        for(int i=0; i < games.length; i++){
            values.put(KEY_ID, games[i]);
            values.put(KEY_DESCRIPT, descriptions[i]);
            values.put(KEY_PUBLISHER, publisher[i]);
            values.put(KEY_DATE, dates[i]);
            values.put(KEY_CONSOLES, consoles[i]);
            values.put(KEY_CATEGORIES, categories[i]);

            db.insert(TABLE_GAME_LIST, null, values);
        }
    }

    public GameItem query(String statement){ //(STRING statement)


        android.database.Cursor cursor = null;
        GameItem gameItem = new GameItem();
        ArrayList<GameItem> ga = new ArrayList<>();

        try{
            if(myReadableDB == null ){
                myReadableDB = getReadableDatabase();

            }
            cursor = myReadableDB.rawQuery(statement, null);
            cursor.moveToFirst();

            while(cursor.isAfterLast() == false) {
                GameItem gm = new GameItem();
                 gm.setTitle(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                 gm.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPT)));
                 gm.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                 gm.setPublisher(cursor.getString(cursor.getColumnIndex(KEY_PUBLISHER)));
                 gm.setCategories(cursor.getString(cursor.getColumnIndex(KEY_CATEGORIES)));
                 gm.setConsoles(cursor.getString(cursor.getColumnIndex(KEY_CONSOLES)));
                 Log.d("openhelper",""+ cursor.getPosition());
                 Log.d("openhelper", "" + cursor.isNull(cursor.getColumnIndex(KEY_ID)));



                 ga.add(gm);
                 cursor.moveToNext();
            }

            games = ga;

            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("games", games);
            Log.d("Intent","Failed intent.");
            Log.d("Intent",""+ games.size());


        }catch(Exception e){
            Log.wtf("WordListOpenHelper", "OMG What a terrible failure... could not query the DB");
            e.printStackTrace();
        }finally {
            cursor.close();
            return gameItem;
        }
    }
    public long count(){
        if(myReadableDB == null){
            myReadableDB = getReadableDatabase();
        }
        long theCount =  android.database.DatabaseUtils.queryNumEntries(myReadableDB, TABLE_GAME_LIST);
        return theCount;
    }

    public long insert(String title, String descript, String publisher, String date, String consoles, String categories){
        adapter = new GameItemAdapter(context, games);
        ContentValues values = new ContentValues();
        values.put(KEY_ID, title);
        values.put(KEY_DESCRIPT, descript);
        values.put(KEY_PUBLISHER, publisher);
        values.put(KEY_DATE, date);
        values.put(KEY_DESCRIPT, descript);
        values.put(KEY_CONSOLES, consoles);
        values.put(KEY_CATEGORIES, categories);
        long theID = 0;
        Log.d("InInsert", "Adapeter0: " + games.size() + " Items :" + count());
        //Log.d("MainActivity", " Yo " + mainActivity.adapter.getItemCount());
        try{
            if(myWritableDB == null){
                myWritableDB = getWritableDatabase();
                Log.d("InInsert", "Adapeter1: " + adapter.getItemCount());
            }
            Log.d("InInsert", "Adapeter2: " + adapter.getItemCount());
            theID = myWritableDB.insert(TABLE_GAME_LIST, null, values);



            Log.d("MainActivity", " Yo " + mainActivity.adapter.getItemCount());

            Log.d("InInsert", "Adapeter3: " + adapter.getItemCount());
        }
        catch(Exception e){
            Log.d("WordListOpenHelper", "inside insert OOPS couldnot insert");
        }
        finally {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("gameTitle", title);
            intent.putExtra("gamePub", publisher);
            intent.putExtra("gameDesc", descript);
            intent.putExtra("gameCon", consoles);
            intent.putExtra("gameDate", date);
            intent.putExtra("gameCate", categories);
            context.startActivity(intent);

            Log.d("InsideFinally", "Added: " + games.size());
            return theID;
        }

    }

    public long delete(String gameTitle){
        long deletedID = 0;
        try{
            if(myWritableDB == null){
                myWritableDB = getWritableDatabase();
            }
            deletedID = myWritableDB.delete(TABLE_GAME_LIST,
                    KEY_ID + " = ? ",
                    new String[]{gameTitle});
        }
        catch(Exception e){
            Log.d("WordListOpenHelper", "inside delete OOPS couldnot delete");
        }
        finally{
            return deletedID;
        }
    }

    public long update(long id, String title, String descript, String publisher, String date, String consoles, String categories) {
        int numOfRecordsUpdated = -1;
        try {
            if (myWritableDB == null) {
                myWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_ID, title);
            values.put(KEY_DESCRIPT, descript);
            values.put(KEY_PUBLISHER, publisher);
            values.put(KEY_DATE, date);
            values.put(KEY_CONSOLES, consoles);
            values.put(KEY_CATEGORIES, categories);
            numOfRecordsUpdated = myWritableDB.update(TABLE_GAME_LIST,
                    values,
                    KEY_ID + " = ?",
                    new String[]{String.valueOf(id)});


        } catch (Exception e) {
            Log.d("WordListOpenHelper", "inside delete OOPS couldnot delete");
        } finally {
            return numOfRecordsUpdated;

        }
    }


}
