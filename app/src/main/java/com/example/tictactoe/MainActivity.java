package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tictactoe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    //0:red 1:yellow
    private int player = 0;

    //0:red 1:yellow 2:empty
    private int[] filled = {2,2,2,2,2,2,2,2,2};

    private int[][] winningPos = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private boolean winner = false;
    private GridLayout grd;

    private TextView message;
    private Button btn;

    public void dropIn(View view){

        ImageView pos = (ImageView) view;
        int tag = Integer.parseInt(view.getTag().toString());

        if(!winner&&filled[tag]==2){
            pos.setTranslationY(-2000);
            if(player==0){
                pos.setImageResource(R.drawable.red);
                player = 1;
                filled[tag] = 0;
            }else if(player == 1){
                pos.setImageResource(R.drawable.yellow);
                player = 0;
                filled[tag] = 1;
            }
            pos.animate().translationYBy(2000).setDuration(500);

            for(int[] winning: winningPos){
                if((filled[winning[0]] == filled[winning[1]])&&(filled[winning[1]] == filled[winning[2]])&&(filled[winning[0]] != 2)){
                    winner = true;
                    if(player == 0){
                        message.setText("Yellow has won!");
                    }else if(player == 1){
                        message.setText("Red has won!");
                    }
                }else if(!winner && draw()){
                    message.setText("Match is draw!");
                }
            }
            if(winner || draw()){
                btn.animate().translationYBy(-1000).setDuration(300);
            }
        }
    }

    public void onClick(View view){

        winner = false;
        for(int i=0;i<9;i++){
             filled [i] = 2;
        }
        message.setText("");
        player = 0;

        for(int i=0;i<grd.getChildCount();i++){
            ImageView img = (ImageView) grd.getChildAt(i);
            img.setImageDrawable(null);
        }
        grid();
        btn.setTranslationY(1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        message = binding.textView;
        btn = binding.button;
        btn.setTranslationY(1000);
        grid();

    }

    private boolean draw(){
        for(int i:filled){
            if(i==2)
                return false;
        }
        return true;
    }

    private void grid(){
        grd = binding.board;
        grd.setAlpha(0);
        grd.setScaleX(0);
        grd.setScaleY(0);
        grd.animate().alpha(1).rotationBy(360).scaleX(1).scaleY(1).setDuration(500);
    }
}