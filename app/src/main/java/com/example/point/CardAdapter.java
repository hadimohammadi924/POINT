package com.example.point;


import static com.example.point.CardAdapter.ContactViewHolder.finalcard;

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
import com.example.point.Adapter.shekayatadapter;
import com.example.point.DataModel.customer;
import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ContactViewHolder> {

    private List<customer> customerList;
    public static Activity context;


    public CardAdapter(List<customer> customerList , Activity activity) {
        this.customerList = customerList;
        context = activity;
         CardView finalcard;
    }


    @Override
    public int getItemCount() {
        return customerList.size();
    }




    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i)
    {


        contactViewHolder.text_id.setText("کد: "+customerList.get(i).getPgcode()+"     "+customerList.get(i).getType()+": "+customerList.get(i).getCust()+"      "+"مدیریت: "+customerList.get(i).getName());
        contactViewHolder.text_code.setText("تلفن: "+customerList.get(i).getTell1()+"      "+"موبایل: "+customerList.get(i).getMobile());
        contactViewHolder.text_title.setText("آدرس: "+customerList.get(i).getAddress());
        contactViewHolder.text_description.setText("ثبت شده توسط:"+customerList.get(i).getUn()+" "+"در مورخ: "+customerList.get(i).getDate()+"   "+customerList.get(i).getTime());
        contactViewHolder.text_codee.setText(customerList.get(i).getPgcode());
        Glide
                .with(context)
                .load(customerList.get(i).getPicURL())
                //   .centerCrop()
                //  .placeholder(R.drawable.ic_logo4)
                .into(contactViewHolder.cards_image);

        finalcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, finalcust.class);
                context.startActivity(intent);


                String custcodee =customerList.get(contactViewHolder.getAdapterPosition()).getPgcode()+"";
                Intent ii = new Intent(context, finalcust.class);
                ii.putExtra("pgcodee",custcodee);
                context.startActivity(ii);








            }
        });

    }

    @Override
    public  ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards, viewGroup, false);
        return new ContactViewHolder(itemView);

    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {



        ImageView cards_image;
        static CardView finalcard;
        TextView text_id, text_code, text_title, text_description,text_codee;

        public ContactViewHolder(final View v) {
            super(v);

            cards_image = v.findViewById(R.id.cards_image);
            text_id = v.findViewById(R.id.text_id);
            text_code = v.findViewById(R.id.text_code);
            text_title = v.findViewById(R.id.text_title);
            text_codee = v.findViewById(R.id.text_codee);
            text_description = v.findViewById(R.id.text_description);
            finalcard = v.findViewById(R.id.finalcard);

        }
    }
}
