package com.example.pharmacist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ItemViewHolder> implements Filterable {
    private ArrayList<StockItem> mItemList;
    private ArrayList<StockItem> ListCopy;
    private onItemClickListener Listener;

    @Override
    public Filter getFilter() {
        return ItemFilter;
    }

    private Filter ItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StockItem> filteredList = new ArrayList<>();
            if (constraint==null || constraint.length() ==0){
                filteredList.addAll(ListCopy);
            }else{
                String filetrpattern = constraint.toString().toLowerCase().trim();

                for (StockItem item : ListCopy){
                  if (item.getsName().toLowerCase().contains(filetrpattern)){
                      filteredList.add(item);
                  }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mItemList.clear();
            mItemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public interface onItemClickListener{
        void onItemClick(int pos);

    }

    public void setOnItemClickListener(onItemClickListener mlistener){
        Listener = mlistener;

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView Tname, Tprice, Tqty,Tschedule;

        public ItemViewHolder(@NonNull View itemView, final onItemClickListener mlisten) {
            super(itemView);
            Tname = (TextView) itemView.findViewById(R.id.TxtName);
            Tprice = (TextView) itemView.findViewById(R.id.Txtprice);
            Tqty = (TextView) itemView.findViewById(R.id.Txtquantity);
            Tschedule = (TextView) itemView.findViewById(R.id.TXTShcedule);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlisten != null){
                        int position =  getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mlisten.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public itemAdapter(ArrayList<StockItem> List){
        mItemList = List;
        ListCopy = new ArrayList<>(mItemList);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_layout,viewGroup,false);
        ItemViewHolder  ivh = new ItemViewHolder(v,Listener);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        StockItem currItem  = mItemList.get(i);
        itemViewHolder.Tname.setText(currItem.getsName());
        itemViewHolder.Tprice.setText("R"+currItem.getsPrice());
        itemViewHolder.Tqty.setText(currItem.getsQty());
        itemViewHolder.Tschedule.setText(currItem.getsSchedule());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
