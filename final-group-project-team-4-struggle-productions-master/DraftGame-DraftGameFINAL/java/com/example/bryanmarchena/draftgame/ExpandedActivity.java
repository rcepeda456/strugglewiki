package com.example.bryanmarchena.draftgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ExpandedActivity extends AppCompatActivity {
    private TextView gameTitle_tv,gameDate_tv, gamePublisher_tv, gameCategory_tv, gameConsole_tv, gameDescription_tv;
    private ImageView gameImage_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded);

        gameTitle_tv = (TextView)findViewById(R.id.title);
        gameDate_tv = (TextView) findViewById(R.id.date);
        gamePublisher_tv = (TextView)findViewById(R.id.publisher);
        gameCategory_tv = (TextView)findViewById(R.id.category);
        gameConsole_tv = (TextView)findViewById(R.id.console);
        gameDescription_tv = (TextView)findViewById(R.id.description);

        gameImage_iv = (ImageView)findViewById(R.id.gameImg);
        //gameImage_iv.setImageBitmap();

        Intent intent = getIntent();
        String game_title = intent.getExtras().getString("GameTitle");
        String game_date = intent.getExtras().getString("GameDate");
        String game_desc = intent.getExtras().getString("GameDesc");
        String game_pub = intent.getExtras().getString("GamePub");
        String game_cons = intent.getExtras().getString("GameCons");
        String game_cate = intent.getExtras().getString("GameCate");
        int index = intent.getExtras().getInt("GameIndex");


        gameTitle_tv.setText(game_title);
        gameDate_tv.setText(game_date);
        gamePublisher_tv.setText(game_pub);
        gameCategory_tv.setText(game_cate);
        gameConsole_tv.setText(game_cons);
        gameDescription_tv.setText(game_desc);


    }
}
