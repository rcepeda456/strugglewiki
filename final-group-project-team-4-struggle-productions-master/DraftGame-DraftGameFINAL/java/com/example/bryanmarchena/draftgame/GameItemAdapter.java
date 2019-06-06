package com.example.bryanmarchena.draftgame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.ViewHolder> {
    protected ArrayList<GameItem> gameItems;
    private Context context;

    public GameItemAdapter(Context context, ArrayList<GameItem> gameItems) {
        this.context = context;
        this.gameItems = gameItems;

    }

    @NonNull
    @Override
    public GameItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.game_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final GameItemAdapter.ViewHolder holder, final int position) {
        final GameItem currentGameItem = gameItems.get(position);

        holder.bindTo(currentGameItem);

        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent expandGame = new Intent(context, ExpandedActivity.class);
                expandGame.putExtra("GameTitle", currentGameItem.getTitle());
                expandGame.putExtra("GameDate", currentGameItem.getDate());
                expandGame.putExtra("GameDesc",currentGameItem.getDescription());
                expandGame.putExtra("GamePub", currentGameItem.getPublisher());
                expandGame.putExtra("GameCons", currentGameItem.getConsoles());
                expandGame.putExtra("GameCate", currentGameItem.getCategories());
                expandGame.putExtra("GameIndex", position);

                context.startActivity(expandGame);

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Game");
                builder.setMessage("Are you sure you want to delete this game?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    GameListOpenHelper gameListOpenHelper = new GameListOpenHelper(context);
                    gameItems.remove(holder.getAdapterPosition());
                    GameItemAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                    gameListOpenHelper.delete(currentGameItem.getTitle());

                    }
                });

                AlertDialog warning = builder.create();
                warning.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() { return gameItems.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView gameTitle;
        private TextView gameDate;
        private ImageView gameImage;
        private CardView cardView;

        ViewHolder(View itemView){
            super(itemView);
            gameTitle = (TextView) itemView.findViewById(R.id.game_title);
            gameDate = (TextView) itemView.findViewById(R.id.game_date);
            gameImage = (ImageView) itemView.findViewById(R.id.game_image);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }

        void bindTo(GameItem currentGame) {
            gameTitle.setText(currentGame.getTitle());
            gameDate.setText("Released: " + currentGame.getDate());
            Glide.with(context).load(R.drawable.kingdomhearts).into(gameImage);


        }

    }


}
