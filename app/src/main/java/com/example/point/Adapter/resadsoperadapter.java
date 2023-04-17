package com.example.point.Adapter;


import static com.example.point.Adapter.resadsoperadapter.ContactViewHolder.brooot;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.point.DataModel.resadVis;
import com.example.point.R;
import com.example.point.finalcust;
import com.example.point.showbazargardi;

import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class resadsoperadapter extends RecyclerView.Adapter<resadsoperadapter.ContactViewHolder> {

    private List<resadVis> resadVisList;
    public static Activity context;
 static resadsoperadapter resadVisInterface;

    public resadsoperadapter(ArrayList<resadVis> resadVisList, resadsoperadapter.resadVisInterface resadVisInterface) {
        this.resadVisList = resadVisList;
        this.resadVisInterface = (resadsoperadapter) resadVisInterface;

    }

    public interface resadVisInterface {

        void onCustomListitemClick(int position);
    }
    public resadsoperadapter(List<resadVis> resadVisList , Activity activity) {
        this.resadVisList = resadVisList;
        context = activity;

    }


    @Override
    public int getItemCount() {
        return resadVisList.size();
    }




    @Override
    public void onBindViewHolder(resadsoperadapter.ContactViewHolder contactViewHolder, int i)
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

        brooot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(context, showbazargardi.class);
            context.startActivity(intent);

                PersianDate pdate = new PersianDate();
                PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
                String BUN =resadVisList.get(contactViewHolder.getAdapterPosition()).getName()+"";
                String Bdate =pdformater1.format(pdate);
               Intent bi = new Intent(context, showbazargardi.class);
                bi.putExtra("Bun",BUN);
                bi.putExtra("bdate",Bdate);
                context.startActivity(bi);








            }
        });





        }



    @Override
    public resadsoperadapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resadsopervisercard, viewGroup, false);
        return new resadsoperadapter.ContactViewHolder(itemView);

    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {




        static CardView brooot;
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
            brooot = v.findViewById(R.id.brooot);



        }

    }


}



