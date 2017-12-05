package com.example.nilot.calculator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nilot on 28-06-2017.
 */

public class CalculatorArrayAdapter<T> extends ArrayAdapter<T> {
    List<String> list;
    MainActivity mainActivity;

    public  CalculatorArrayAdapter(Context context, int resource, List<T> objects, MainActivity fullscreenActivity){
        super(context,resource,objects);
        list = (List<String>)objects;
        this.mainActivity = fullscreenActivity;
    }

    public int getPixelsFromDPs(int dps){
        Resources r = mainActivity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = super.getView(position, convertView, parent);

        TextView text = new TextView(this.getContext());
        text.setText(list.get(position));
        text.setGravity(Gravity.CENTER);
        if(list.get(position).equals("1") || list.get(position).equals("2") || list.get(position).equals("3") || list.get(position).equals("4") || list.get(position).equals("5") || list.get(position).equals("6") || list.get(position).equals("7") || list.get(position).equals("8") || list.get(position).equals("9") || list.get(position).equals("0")){
            text.setBackgroundColor(Color.CYAN);
        }
        else if(list.get(position).equals("C")){
            text.setBackgroundColor(Color.RED);

        }
        else if(list.get(position).equals("=")) {
            text.setBackgroundColor(Color.GREEN);
            view.setBackgroundColor(Color.GREEN);
        }
        else {
            text.setBackgroundColor(Color.MAGENTA);
        }
        text.setBackgroundResource(R.drawable.boarder_grid_cells);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(120,100);
        params.width = getPixelsFromDPs(50);
        params.height = getPixelsFromDPs(50);

        // Set the TextView layout parameters
        text.setLayoutParams(params);
        return text;
    }
}