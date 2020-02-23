package com.codebrewers.loc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class weatheradapter extends RecyclerView.Adapter<weatheradapter.viewholder> {

    private String[] w;
    private String[] d;
    private double[] tempmin;
    private double[] tempmax;
    private double[] humidity;
    private String[] img;
    public weatheradapter(String[] w,String [] d,double[] tempmin,double[] tempmax,double[] humidity,String[] img)
    {
        this.w=w;
        this.d=d;
        this.tempmin=tempmin;
        this.tempmax=tempmax;
        this.humidity=humidity;
        this.img=img;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.recycler_layout,viewGroup,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        String url="http://openweathermap.org/img/w/";
        url=url+img[i]+".png";
        Glide.with(viewholder.img.getContext()).load(url).into(viewholder.img);
        String w1=w[i];
        String d1=d[i];
        double tempmin1=tempmin[i];
        double tempmax1=tempmax[i];
        double humidity1=humidity[i];
        viewholder.date.setText(d1.substring(8,10)+d1.substring(4,5)+d1.substring(5,7)+d1.substring(4,5)+d1.substring(0,4)+" "+d1.substring(11,16));
        viewholder.tempmax.setText("Max. Temp.="+String.valueOf(tempmax1-273.15).substring(0,4)+"°C");
        viewholder.weather.setText(w1.substring(0,1).toUpperCase()+w1.substring(1));
        viewholder.tempmin.setText("Min. Temp.="+String.valueOf(tempmin1-273.15).substring(0,4)+"°C");
        viewholder.humidity.setText("Humidity="+String.valueOf(humidity1)+"%");

    }

    @Override
    public int getItemCount() {
        return w.length;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView date;
        TextView weather;
        TextView tempmax;
        TextView tempmin;
        TextView humidity;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.date);
            img=(ImageView)itemView.findViewById(R.id.img);
            weather=(TextView)itemView.findViewById(R.id.weather);
            tempmax=(TextView)itemView.findViewById(R.id.tempmax);
            tempmin=(TextView)itemView.findViewById(R.id.tempmin);
            humidity=(TextView)itemView.findViewById(R.id.humidity);
        }
    }
}
