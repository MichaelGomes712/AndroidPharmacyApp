package com.example.pharmacist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.DrawableWrapper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pharmacist.Database.Database.ModelDB.Cart;
import com.example.pharmacist.Utils.Common;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartViewHolder> {

    Context context;
    List<Cart> cartList;

    public void removeItem(int pos){
        cartList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void restoreItem(Cart item, int pos){
        cartList.add(pos,item);
        notifyItemInserted(pos);
    }

    private onItemClickListener Listener;

    public interface onItemClickListener{
        void onDeleteclick(int pos);

    }

    public void setOnItemClickListener(onItemClickListener mlistener){
        Listener = mlistener;

    }

    public cartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout,viewGroup,false);
        return new cartViewHolder(itemView,Listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final cartViewHolder cartViewHolder, final int i) {
        cartViewHolder.txtname.setText(cartList.get(i).name);
        cartViewHolder.txtPrice.setText("R"+cartList.get(i).total);
        cartViewHolder.btnQty.setRange(1,cartList.get(i).quantityAvailable);
        cartViewHolder.btnQty.setNumber(String.valueOf(cartList.get(i).quantity));
        cartViewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cartList.get(cartViewHolder.getAdapterPosition()).name;

                final Cart deleteditem = cartList.get(cartViewHolder.getAdapterPosition());
                final int deletedIndex = cartViewHolder.getAdapterPosition();

                final AlertDialog.Builder  message = new AlertDialog.Builder(context);
                message.setTitle("Confirm");
                message.setMessage("Are you sure you want to remove "+name);
                message.setCancelable(true);
                message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(deletedIndex);
                        Common.cartRepository.deleteCartItem(deleteditem);
                        dialog.cancel();
                    }
                });
                message.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                message.show();
            }
        });
        cartViewHolder.btnQty.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = cartList.get(i);
                cart.quantity = newValue;
                cart.total = Math.round(cart.price * newValue * 100.00)/100.00;
                cartViewHolder.txtPrice.setText("R"+cart.total);
                cartViewHolder.btnQty.setNumber(String.valueOf(cartList.get(i).quantity));
                Common.cartRepository.updateCart(cart.total,cart.quantity,cart.id);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class cartViewHolder extends RecyclerView.ViewHolder {

        TextView txtPrice, txtname;
        ElegantNumberButton btnQty;
        ImageView imgDelete;

        public cartViewHolder(@NonNull View itemView, final onItemClickListener mlisten) {

            super(itemView);
            txtPrice = (TextView) itemView.findViewById(R.id.order_price);
            txtname = (TextView) itemView.findViewById(R.id.order_name);
            btnQty = (ElegantNumberButton) itemView.findViewById(R.id.order_count);
            imgDelete = (ImageView)itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlisten != null){
                        int position =  getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mlisten.onDeleteclick(position);
                        }
                    }
                }
            });
        }
    }

    public String getNames(){
        String names ="";
        int numItems = Common.cartRepository.countCartItems();
        for (int i =0;i < numItems-1; i++){
            names += String.valueOf(cartList.get(i).quantity) + '*' + cartList.get(i).name+",";
        }
        names += String.valueOf(cartList.get(numItems-1).quantity) + '*' + cartList.get(numItems-1).name;
        return names;
    }

    public Double getTotal(){
        Double total = 0.00;
        int numItems = Common.cartRepository.countCartItems();
        for(int i =0; i < numItems;i ++){
            total += cartList.get(i).total;
        }
        return total;
    }

    public String getName(int i){
        return cartList.get(i).name;
    }

    public int getQuantity(int i){
        return cartList.get(i).quantity;
    }

    public int getAvailQuantity(int i){
        return cartList.get(i).quantityAvailable;
    }
}
