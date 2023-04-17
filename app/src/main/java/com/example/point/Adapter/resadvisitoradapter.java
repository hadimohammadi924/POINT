package com.example.point.Adapter;




import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.point.DataModel.resadVis;
import com.example.point.R;

import java.util.ArrayList;
import java.util.List;


public class resadvisitoradapter extends RecyclerView.Adapter<resadvisitoradapter.ContactViewHolder> {

    private List<resadVis> resadVisList;
    public static Activity context;
 static resadvisitoradapter resadVisInterface;

    public resadvisitoradapter(ArrayList<resadVis> resadVisList, resadvisitoradapter.resadVisInterface resadVisInterface) {
        this.resadVisList = resadVisList;
        this.resadVisInterface = (resadvisitoradapter) resadVisInterface;
    }

    public interface resadVisInterface {

        void onCustomListitemClick(int position);
    }
    public resadvisitoradapter(List<resadVis> resadVisList , Activity activity) {
        this.resadVisList = resadVisList;
        context = activity;
    }


    @Override
    public int getItemCount() {
        return resadVisList.size();
    }




    @Override
    public void onBindViewHolder(resadvisitoradapter.ContactViewHolder contactViewHolder, int i)
    {




            contactViewHolder.resadvname.setText("نام همکار: "+resadVisList.get(i).getName());
            contactViewHolder.vcount.setText("فروشگاه های بازدید شده امروز: "+resadVisList.get(i).getCountToday());
            contactViewHolder.vtotalcount.setText("فروشگاه های بازدید شده کل: "+resadVisList.get(i).getCount());
            contactViewHolder.vftime.setText("زمان اولین فعالیت ثبت شده امروز: "+resadVisList.get(i).getFtime());
            contactViewHolder.vltime.setText("زمان آخرین فعالیت ثبت شده امروز: "+resadVisList.get(i).getLtime());
        Glide
                .with(context)
                .load(resadVisList.get(i).getUrl())
                //   .centerCrop()
                //  .placeholder(R.drawable.ic_logo4)
                .into(contactViewHolder.user_image);







        }



    @Override
    public resadvisitoradapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resadvisitorcard, viewGroup, false);
        return new resadvisitoradapter.ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {





        TextView resadvname, vcount, vtotalcount, vftime ,vltime;
        ImageView user_image;
        public ContactViewHolder(final View v) {
            super(v);


            resadvname = v.findViewById(R.id.resadvname);
            vcount = v.findViewById(R.id.vcount);
            vtotalcount = v.findViewById(R.id.vtotalcount);
            vftime = v.findViewById(R.id.vftime);
            vltime = v.findViewById(R.id.vltime);
            user_image = v.findViewById(R.id.user_image);



        }

    }


}



