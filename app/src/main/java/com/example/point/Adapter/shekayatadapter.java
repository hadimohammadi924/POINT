package com.example.point.Adapter;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.point.Adapter.shekayatadapter.ContactViewHolder.rooot;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.point.DataModel.customer;
import com.example.point.DataModel.shekayat;
import com.example.point.MainActivity;
import com.example.point.R;
import com.example.point.bazargardi;
import com.example.point.loogin;
import com.example.point.menu;
import com.example.point.sabte_response;
import com.example.point.tiketha;

import java.util.ArrayList;
import java.util.List;


public class shekayatadapter extends RecyclerView.Adapter<shekayatadapter.ContactViewHolder> {

    private List<shekayat> shekayatList;
    public static Activity context;
    static shekayatadapter shekayatAdapterInterface;

    public shekayatadapter(ArrayList<shekayat> shekayatList, shekayatadapter.shekayatAdapterInterface shekayatAdapterInterface) {
        this.shekayatList = shekayatList;
        this.shekayatAdapterInterface = (shekayatadapter) shekayatAdapterInterface;
    }

    public interface shekayatAdapterInterface {

        void onCustomListitemClick(int position);
    }
    public shekayatadapter(List<shekayat> shekayatList , Activity activity) {
        this.shekayatList = shekayatList;
        context = activity;
    }


    @Override
    public int getItemCount() {
        return shekayatList.size();
    }




    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i)
    {
        if(shekayatList.get(i).getTresponse() !="null") {



        contactViewHolder.ticketsID.setText("شناسه شکایت:"+shekayatList.get(i).getId_tiket());
        contactViewHolder.datetime.setText("زمان ثبت: "+shekayatList.get(i).getTdate()+"  "+ shekayatList.get(i).getTtime());
        contactViewHolder.titleTickets.setText("عنوان شکایت: "+shekayatList.get(i).getTtitle());
        contactViewHolder.categori.setText("حوزه شکایت: "+shekayatList.get(i).getTcategori()+"        نوع فوریت: "+shekayatList.get(i).getX2());
        contactViewHolder.CUser.setText("ثبت شده توسط:"+shekayatList.get(i).getX1());
        contactViewHolder.textDescription.setText("متن شکایت: "+shekayatList.get(i).getTdescription());
        contactViewHolder.ticketsReplyTitle.setText("پاسخ: "+shekayatList.get(i).getTresponse());
        contactViewHolder.responseuser.setText("پاسخ دهنده:  "+shekayatList.get(i).getTruser());
        contactViewHolder.rtime.setText("زمان پاسخ:  "+shekayatList.get(i).getTrdate()+" "+shekayatList.get(i).getTrtime());
        contactViewHolder.ticketsStatus.setText("وضعیت: "+shekayatList.get(i).getTstatus());
            rooot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "این تیکت پاسخ داده شده است", Toast.LENGTH_SHORT).show();

                }
            });

    }else  {
            contactViewHolder.ticketsID.setText("شناسه شکایت:"+shekayatList.get(i).getId_tiket());
            contactViewHolder.datetime.setText("زمان ثبت: "+shekayatList.get(i).getTdate()+"  "+ shekayatList.get(i).getTtime());
            contactViewHolder.titleTickets.setText("عنوان شکایت: "+shekayatList.get(i).getTtitle());
            contactViewHolder.categori.setText("حوزه شکایت: "+shekayatList.get(i).getTcategori()+"        نوع فوریت: "+shekayatList.get(i).getX2());
            contactViewHolder.CUser.setText("ثبت شده توسط:"+shekayatList.get(i).getX1());
            contactViewHolder.textDescription.setText("متن شکایت: "+shekayatList.get(i).getTdescription());
            contactViewHolder.ticketsReplyTitle.setText("پاسخ: "+shekayatList.get(i).getTresponse());
            contactViewHolder.responseuser.setText("پاسخ دهنده:  "+shekayatList.get(i).getTruser());
            contactViewHolder.rtime.setText("زمان پاسخ:  "+shekayatList.get(i).getTrdate()+" "+shekayatList.get(i).getTrtime());
            contactViewHolder.ticketsStatus.setText("وضعیت: "+shekayatList.get(i).getTstatus());
           contactViewHolder.replyLayout.setVisibility(View.GONE);
            rooot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, shekayatList.get(contactViewHolder.getAdapterPosition()).getId_tiket()+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, sabte_response.class);
                    context.startActivity(intent);

                    String tiketid =shekayatList.get(contactViewHolder.getAdapterPosition()).getId_tiket()+"";
                    Intent i = new Intent(shekayatadapter.context, sabte_response.class);
                    i.putExtra("tiketid",tiketid);
                    context.startActivity(i);

                }
            });

        }

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shekayatcard, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {




        static CardView replyLayout,rooot;
        TextView ticketsID, titleTickets, categori, datetime,CUser,textDescription,ticketsReplyTitle,responseuser,rtime,ticketsStatus;

        public ContactViewHolder(final View v) {
            super(v);


            ticketsID = v.findViewById(R.id.ticketsID);
            titleTickets = v.findViewById(R.id.titleTickets);
            categori = v.findViewById(R.id.categori);
            datetime = v.findViewById(R.id.datetime);
            CUser = v.findViewById(R.id.CUser);
            textDescription = v.findViewById(R.id.textDescription);
            ticketsReplyTitle = v.findViewById(R.id.ticketsReplyTitle);
            responseuser = v.findViewById(R.id.responseuser);
            rtime = v.findViewById(R.id.rtime);
            replyLayout = v.findViewById(R.id.replyLayout);
            ticketsStatus = v.findViewById(R.id.ticketsStatus);
            rooot = v.findViewById(R.id.rooot);


            }

        }


}








