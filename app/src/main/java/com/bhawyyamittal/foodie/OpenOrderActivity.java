package com.bhawyyamittal.foodie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class OpenOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    //TextView txt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_order);
        recyclerView = findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // txt = findViewById(R.id.test);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.open_order_row,
                OrderViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {
                viewHolder.setItemName(model.getItemname());
                viewHolder.setUserName(model.getUsername());

            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        View view;

        public OrderViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

        }
        public void setUserName(String user){
            TextView txt1 = view.findViewById(R.id.orderUserName);
            txt1.setText(user);
        }
        public void setItemName(String item){
            TextView txt2 = view.findViewById(R.id.orderItemName);
            txt2.setText(item);
        }
    }


}
