package android.com.baithuchanh2.adapter;

import android.com.baithuchanh2.R;
import android.com.baithuchanh2.model.Item;
import android.com.baithuchanh2.model.ItemListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewholder>{

    private List<Item> list;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Item getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewholder holder, int position) {
        Log.d("hoangdev", ""+position);
        Item item = list.get(position);
        holder.ten.setText(item.getTen());
        holder.noiDung.setText(item.getNoiDung());
        holder.tinhTrang.setText(item.getTinhTrang());
        holder.thoiHan.setText(item.getThoiHan());
        holder.congTac.setText(item.getCongTac());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView ten, noiDung, tinhTrang, thoiHan, congTac;

        public HomeViewholder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.tvTen);
            noiDung = itemView.findViewById(R.id.tvNoiDung);
            tinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            thoiHan = itemView.findViewById(R.id.tvThoiHan);
            congTac = itemView.findViewById(R.id.tvCongTac);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClick(itemView, getAdapterPosition());
            }
        }
    }
}
