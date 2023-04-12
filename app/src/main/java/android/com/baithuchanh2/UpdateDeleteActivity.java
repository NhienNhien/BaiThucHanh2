package android.com.baithuchanh2;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.com.baithuchanh2.dal.SQLiteHelper;
import android.com.baithuchanh2.model.Item;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner spTinhTrang, spCongTac;
    private EditText eTen, eNoiDung, eThoiHan;
    private Button btUpdate, btnCancel, btnDelete;
    private Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedelete);
        initView();

        item = (Item) getIntent().getSerializableExtra("item");
        eTen.setText(item.getTen());
        eNoiDung.setText(item.getNoiDung());
        eThoiHan.setText(item.getThoiHan());

        int p = 0;
        for (int i=0; i<spTinhTrang.getCount(); i++){
            if (spTinhTrang.getItemAtPosition(i).toString().equals(item.getTinhTrang())){
                p=i;
                break;
            }
        }
        spTinhTrang.setSelection(p);

        int k = 0;
        for (int i=0; i<spCongTac.getCount(); i++){
            if (spCongTac.getItemAtPosition(i).toString().equals(item.getCongTac())){
                k=i;
                break;
            }
        }
        spCongTac.setSelection(k);

        btnCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        eThoiHan.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


    }

    private void initView() {
        spTinhTrang = findViewById(R.id.spTinhTrang);
        spCongTac = findViewById(R.id.spCongTac);
        eTen = findViewById(R.id.tvTen);
        eNoiDung = findViewById(R.id.tvNoiDung);
        eThoiHan = findViewById(R.id.tvThoiHan);
        btUpdate = findViewById(R.id.btUpdate);
        btnCancel = findViewById(R.id.btCancel);
        btnDelete = findViewById(R.id.btDelete1);

        ArrayAdapter adapterTinhTrang = new ArrayAdapter<>(this,
                R.layout.item_sprinner,
                getResources().getStringArray(R.array.tinhTrang));
        spTinhTrang.setAdapter(adapterTinhTrang);

        ArrayAdapter adapterCongTac = new ArrayAdapter<>(this,
                R.layout.item_sprinner,
                getResources().getStringArray(R.array.congTac));
        spCongTac.setAdapter(adapterCongTac);


    }

    @Override
    public void onClick(View v) {
        if(v == eThoiHan){
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date ="";
                    if(m>8){
                        date = d+"/"+(m+1)+"/"+y;
                    } else {
                        date = d+"/0"+(m+1)+"/"+y;
                    }
                    if (d < 10) {
                        date = "0"+date;
                    }
                    eThoiHan.setText(date);

                }
            }, y, m, d);
            dialog.show();
        }

        if (v == btnCancel) {
            finish();
        }
        SQLiteHelper db = new SQLiteHelper(this);
        if (v == btUpdate){
            String ten = eTen.getText().toString().trim();
            String noiDung = eNoiDung.getText().toString().trim();
            String tinhTrang= spTinhTrang.getSelectedItem().toString();
            String thoiHan = eThoiHan.getText().toString();
            String congTac= spCongTac.getSelectedItem().toString();

            if(!ten.isEmpty() && !noiDung.isEmpty() && !thoiHan.isEmpty()) {
                Item i = new Item(item.getId(), ten, noiDung, tinhTrang, thoiHan, congTac);
                long tb = db.upDate(i);
                if(tb > 0) {
                    Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Có lỗi sảy ra khi update", Toast.LENGTH_SHORT).show();
                }

            } else{
                Toast.makeText(this, "Vui lòng không để trống và đơn vị giá là số!", Toast.LENGTH_SHORT).show();
            }
        }

        if(v == btnDelete) {
            Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateDeleteActivity.this);
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn chắc muốn xóa công việc" +item.getTen() +" không?");
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long tb = db.delete(item.getId());
                    if(tb > 0) {
                        Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Có lỗi sảy ra khi xóa", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();
        }
    }
}