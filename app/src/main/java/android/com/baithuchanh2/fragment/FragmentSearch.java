package android.com.baithuchanh2.fragment;

import android.app.DatePickerDialog;
import android.com.baithuchanh2.R;
import android.com.baithuchanh2.adapter.RecycleViewAdapter;
import android.com.baithuchanh2.dal.SQLiteHelper;
import android.com.baithuchanh2.model.Item;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener{

    private RecyclerView rcView;
    private SearchView seacrch;
    private Button btnSearch;
    private Spinner sp;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;
    private TextView tvThongke;
    List<Item> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        list = db.getAll();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        rcView.setLayoutManager(manager);
        rcView.setAdapter(adapter);


        seacrch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                list = db.searchByNoiDung(s);
                adapter.setList(list);
                return true;
            }
        });


        btnSearch.setOnClickListener(this);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String c = sp.getItemAtPosition(position).toString();
                if(!c.equalsIgnoreCase("all")) {
                    list = db.searchByTinhTrang(c);
                } else {
                    list = db.getAll();
                }
                adapter.setList(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initView(View v) {
        tvThongke = v.findViewById(R.id.tvThongKe);
        rcView = v.findViewById(R.id.rcViewSearch);
        seacrch =  v.findViewById(R.id.search);
        btnSearch =  v.findViewById(R.id.btSearch);
        sp =  v.findViewById(R.id.spCategory);
        String[] arr = getResources().getStringArray(R.array.tinhTrang);
        String[] arr1 = new String[arr.length+1];
        arr1[0] ="All";
        for (int i = 0; i < arr.length; i++) {
            arr1[i+1] = arr[i];
        }
        sp.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_sprinner, arr1));
    }

    @Override
    public void onClick(View v) {

        if(v == btnSearch) {
            int a = db.searchByTinhTrang("Chưa thực hiện").size();
            int b = db.searchByTinhTrang("Đang thực hiện").size();
            int c = db.searchByTinhTrang("Hoàn thành").size();
            tvThongke.setText("Chưa thực hiện: " + a + " công việc."
                                +"\nĐang thực hiện: " + b + " công việc."
                                    +"\nHoàn thành: " + c + " công việc.");
        }
    }


}
