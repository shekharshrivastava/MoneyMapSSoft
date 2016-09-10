package com.example.shasha.electrokart.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shasha.electrokart.R;

import java.util.ArrayList;

/**
 * Created by shasha on 11-02-2016.
 */
public class CustomCategoryPage extends BaseAdapter {
    Context context;
    ArrayList<IconClass> arrayList = new ArrayList<>();
    int LayoutId;
    private ImageView imageView;

    public CustomCategoryPage(Context context, ArrayList<IconClass> arrayList, int LayoutId) {
        this.context = context;
        this.arrayList = arrayList;
        this.LayoutId = LayoutId;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IconClass iconClass = arrayList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(LayoutId, null);
        imageView= (ImageView) itemView.findViewById(R.id.imageIcon);
        TextView textView = (TextView) itemView.findViewById(R.id.text1);

        imageView.setImageResource(iconClass.getImageId());
        textView.setText(iconClass.getIconName());


        return itemView;
    }
}