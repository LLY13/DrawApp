package lil115.drawapp;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import lil115.drawapp.RandomDraw;

import static lil115.drawapp.RandomDraw.points;
import static lil115.drawapp.RandomDraw.reDoStack;
import static lil115.drawapp.RandomDraw.unDoStack;


public class MainActivity extends AppCompatActivity {

    static int drawSize;
    static boolean isRandom = true;


    public static int getDrawSize() {
        if (drawSize == 0) {
            return 30;
        }else {
            return drawSize;
        }
    }


    public static void setDrawSize(int drawSize) {
        MainActivity.drawSize = drawSize;
    }

    public void setColorView(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view
        final View view = (View) findViewById(R.id.view);

        //redo and undo btn
        Button unDo = (Button) findViewById(R.id.unDo);
        Button reDo = (Button) findViewById(R.id.reDo);

        //choose from randomColor or a chosen color
        ToggleButton switchBtn = (ToggleButton) findViewById(R.id.switchBtn);
        switchBtn.setChecked(true);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isRandom = true;
                } else {
                    isRandom = false;
                }
            }
        });

        //undo and redo
        unDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unDoStack.size() != 0){
                    reDoStack.push(unDoStack.peek());
                    points.removeAll(unDoStack.pop());
                    view.invalidate();
                }
            }
        });

        reDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reDoStack.size() != 0){
                    unDoStack.push(reDoStack.pop());
                    points.addAll(unDoStack.peek());
                    view.invalidate();
                }
            }
        });

        //seekbar and drawSize
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(2);
        seekBar.incrementProgressBy(2);
        seekBar.setMax(80);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDrawSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
