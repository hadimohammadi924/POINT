package com.example.point.Adapter;


import static com.example.point.Adapter.bazargardiadapter.ContactViewHolder.brooot;

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
import com.example.point.DataModel.bbazargardi;
import com.example.point.DataModel.shekayat;
import com.example.point.R;
import com.example.point.finalcust;
import com.example.point.finalcustforsoper;
import com.example.point.menu;
import com.example.point.sabte_response;

import java.util.ArrayList;
import java.util.List;



    public class bazargardiadapter extends RecyclerView.Adapter<com.example.point.Adapter.bazargardiadapter.ContactViewHolder> {

        private List<bbazargardi> bbazargardiList;
        public static Activity context;
     static com.example.point.Adapter.bazargardiadapter bbazargardiAdapterInterface;

        public bazargardiadapter(ArrayList<bbazargardi> bbazargardiList, com.example.point.Adapter.bazargardiadapter.bbazargardiAdapterInterface bbazargardiAdapterInterface) {
            this.bbazargardiList = bbazargardiList;
            this.bbazargardiAdapterInterface = (com.example.point.Adapter.bazargardiadapter) bbazargardiAdapterInterface;
        }

        public interface bbazargardiAdapterInterface {

            void onCustomListitemClick(int position);
        }
        public bazargardiadapter(List<bbazargardi> bbazargardiList , Activity activity) {
            this.bbazargardiList = bbazargardiList;
            context = activity;
        }


        @Override
        public int getItemCount() {
            return bbazargardiList.size();
        }




        @Override
        public void onBindViewHolder(com.example.point.Adapter.bazargardiadapter.ContactViewHolder contactViewHolder, int i)
        {




                contactViewHolder.ticketsID.setText("کد مشتری و نام:  "+bbazargardiList.get(i).getBcode()+"  "+bbazargardiList.get(i).getBname());
                contactViewHolder.datetime.setText("درخواست یا پیام مشتری: "+bbazargardiList.get(i).getBdarkhast());
                contactViewHolder.titleTickets.setText("توصیه کارشناس به ویزیتور:  "+bbazargardiList.get(i).getBtoosiye());
                contactViewHolder.categori.setText("نظر کارشناس در مورد فروشگاه: "+bbazargardiList.get(i).getBnazar());
                contactViewHolder.CUser.setText("زمان مراجعه:  "+bbazargardiList.get(i).getBdate()+" "+bbazargardiList.get(i).getBtime());
            Glide
                    .with(context)
                    .load(bbazargardiList.get(i).getByakhurl())
                    //   .centerCrop()
                    //  .placeholder(R.drawable.ic_logo4)
                    .into(contactViewHolder.bcards_image);





               brooot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, finalcustforsoper.class);
                        context.startActivity(intent);


                        String pgcode =bbazargardiList.get(contactViewHolder.getAdapterPosition()).getBcode()+"";
                        Intent ii = new Intent(context, finalcustforsoper.class);
                        ii.putExtra("pgcodeee",pgcode);
                        context.startActivity(ii);
                    }
                });

            }



        @Override
        public com.example.point.Adapter.bazargardiadapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bazargardicard, viewGroup, false);
            return new com.example.point.Adapter.bazargardiadapter.ContactViewHolder(itemView);
        }

        public static class ContactViewHolder extends RecyclerView.ViewHolder {




            static CardView brooot;
            TextView ticketsID, titleTickets, categori, datetime,CUser,textDescription,ticketsReplyTitle,responseuser,rtime,ticketsStatus;
            ImageView bcards_image;
            public ContactViewHolder(final View v) {
                super(v);


                ticketsID = v.findViewById(R.id.bticketsID);
                titleTickets = v.findViewById(R.id.btitleTickets);
                categori = v.findViewById(R.id.bcategori);
                datetime = v.findViewById(R.id.bdatetime);
                CUser = v.findViewById(R.id.bCUser);
                textDescription = v.findViewById(R.id.btextDescription);
                bcards_image = v.findViewById(R.id.bcards_image);

                ticketsStatus = v.findViewById(R.id.bticketsStatus);
                brooot = v.findViewById(R.id.brooot);


            }

        }


    }



