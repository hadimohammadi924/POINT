package com.example.point.Adapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.point.DataModel.customer;
import com.example.point.R;

import java.util.ArrayList;

public class customAdapter extends RecyclerView.Adapter<customAdapter.MyViewholder> {

    private ArrayList<customer> customerlist;
    customAdapterInterface customAdapterInterface;

    public customAdapter(ArrayList<customer> customerlist, customAdapter.customAdapterInterface customAdapterInterface) {
        this.customerlist = customerlist;
        this.customAdapterInterface = customAdapterInterface;
    }

    public interface customAdapterInterface {

        void onCustomListitemClick(int position);
    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder myViewholder, int position) {

      customer customer = customerlist.get(position);
      myViewholder.mid.setText(String.valueOf(customer.getId()));
     //  myViewholder.mid.setText(customer.getId());
        myViewholder.mname.setText(customer.getName());
        myViewholder.mtypeRel.setText(customer.getTypeRel());
        myViewholder.mcust.setText(customer.getCust());
        myViewholder.mtime.setText(customer.getTime());
        myViewholder.mdate.setText(customer.getDate());

    }

    @Override
    public int getItemCount() {
        return customerlist.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {


        private TextView mid, mname, mtypeRel, mcust,mtime,mdate;
        private CardView cardView;


        public MyViewholder(@NonNull View itemView) {


            super(itemView);
            mid = (TextView) itemView.findViewById(R.id.idd);
            mname = (TextView) itemView.findViewById(R.id.name);
            mtypeRel = (TextView) itemView.findViewById(R.id.typeRel);
            mcust = (TextView) itemView.findViewById(R.id.cust);
            mtime = (TextView) itemView.findViewById(R.id.timee);
            mdate = (TextView) itemView.findViewById(R.id.dateee);
            cardView = (CardView) itemView.findViewById(R.id.root);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    customAdapterInterface.onCustomListitemClick(pos);

                }
            });

        }
    }


}
