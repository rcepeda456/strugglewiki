package com.example.bryanmarchena.draftgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddGame extends AppCompatActivity {

    protected EditText gameTitle, gameDescript, gamePblsher;
    protected CalendarView releaseDate;
    protected CheckBox ps4, xbox1, nswitch, pc;
    protected Button submit;
    private String addGameDate;
    private String addGameCategory = new String();


    public static final int GET_FROM_GALLERY = 1;

    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_add_game);



        //textviews
        gameTitle = findViewById(R.id.add_title);
        gameDescript = findViewById(R.id.add_description);
        gamePblsher = findViewById(R.id.add_publisher);
        //calendarview
        releaseDate = findViewById(R.id.add_release_date);
        //checkboxes
        ps4 = findViewById(R.id.PS4);
        xbox1 = findViewById(R.id.XBOX1);
        nswitch = findViewById(R.id.SWITCH);
        pc = findViewById(R.id.PC);
        //submit button
        submit = findViewById(R.id.add_submit);


        releaseDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                addGameDate = ""+month+"-"+dayOfMonth+"-"+year;

            }
        });

    }

    public void submitGame(View view) {

        String addGameTitle, addGameDescript, addGamePblsher, addGamereleaseDate, addGameConsoles;

        addGameTitle = gameTitle.getText().toString();
        addGameDescript = gameDescript.getText().toString();
        addGamePblsher = gamePblsher.getText().toString();
        addGamereleaseDate = addGameDate;
        addGameConsoles = new String();


        if(ps4.isChecked()){
            addGameConsoles += "PS4";
        }
        if(xbox1.isChecked()){
            if(!addGameConsoles.isEmpty()){
                addGameConsoles += ", XBOX";
            }
            else{
                addGameConsoles += "XBOX";
            }
        }
        if(nswitch.isChecked()){
            if(!addGameConsoles.isEmpty()){
                addGameConsoles += ", Switch";
            }
            else{
                addGameConsoles += "Switch";
            }
        }
        if(pc.isChecked()){
            if(!addGameConsoles.isEmpty()){
                addGameConsoles += ", PC";
            }
            else{
                addGameConsoles += "PC";
            }
        }



        GameListOpenHelper gameListOpenHelper = new GameListOpenHelper(this);
        gameListOpenHelper.insert(addGameTitle,addGameDescript,addGamePblsher,addGamereleaseDate, addGameConsoles, addGameCategory);



        Log.d("Added New Game", "Checking Category: "+addGameCategory);


        finish();



    }


    public void categorySelector(View view) {

        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(this);
        final android.view.View dialogView = inflater.inflate(R.layout.categories, null);
        dialogBuilder.setView(dialogView);
        final CheckBox action = (android.widget.CheckBox) dialogView.findViewById(R.id.cate_action);
        final CheckBox adventure = (android.widget.CheckBox) dialogView.findViewById(R.id.cate_adventure);
        final CheckBox fighting = (android.widget.CheckBox) dialogView.findViewById(R.id.cate_fighting);
        final CheckBox jrpg = (android.widget.CheckBox) dialogView.findViewById(R.id.cate_jrpg);
        final CheckBox rpg = (android.widget.CheckBox) dialogView.findViewById(R.id.cate_rpg);
        final CheckBox shooter = (android.widget.CheckBox) dialogView.findViewById(R.id.cate_shooter);
        dialogBuilder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(action.isChecked()){
                    addGameCategory +=  "Action";
                }
                if(adventure.isChecked()){
                    if(!addGameCategory.isEmpty()){
                        addGameCategory += ", Adventure";
                    }
                    else{
                        addGameCategory += "Adventure";
                    }
                }
                if(fighting.isChecked()){
                    if(!addGameCategory.isEmpty()){
                        addGameCategory += ", Fighting";
                    }
                    else{
                        addGameCategory += "Fighting";
                    }
                }
                if(jrpg.isChecked()){
                    if(!addGameCategory.isEmpty()){
                        addGameCategory += ", JRPG";
                    }
                    else{
                        addGameCategory += "JRPG";
                    }
                }
                if(rpg.isChecked()){
                    if(!addGameCategory.isEmpty()){
                        addGameCategory += ", RPG";
                    }
                    else{
                        addGameCategory += "RPG";
                    }
                }
                if(shooter.isChecked()){
                    if(!addGameCategory.isEmpty()){
                        addGameCategory += ", Shooter";
                    }
                    else{
                        addGameCategory += "Shooter";
                    }
                }

            }
        });
        dialogBuilder.create().show();

    }

//  image selector using bitmap
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap bitmap = null;
//
//        //Detects request codes
//        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
//            Uri selectedImage = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte imageInByte[] = stream.toByteArray();
//                imageByteArray = imageInByte;
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//    }

}
