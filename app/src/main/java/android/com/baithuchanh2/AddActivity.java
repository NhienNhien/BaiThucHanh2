package android.com.baithuchanh2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.com.baithuchanh2.dal.SQLiteHelper;
import android.com.baithuchanh2.model.Item;
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

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner spTinhTrang, spCongTac;
    private EditText eTen, eNoiDung, eThoiHan;
    private Button btUpdate, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btnCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        eThoiHan.setOnClickListener(this);
    }

    private void initView() {
        spTinhTrang = findViewById(R.id.spTinhTrang);
        spCongTac = findViewById(R.id.spCongTac);
        eTen = findViewById(R.id.tvTen);
        eNoiDung = findViewById(R.id.tvNoiDung);
        eThoiHan = findViewById(R.id.tvThoiHan);
        btUpdate = findViewById(R.id.btUpdate);
        btnCancel = findViewById(R.id.btCancel);

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

        if (v == btUpdate){
            String ten = eTen.getText().toString().trim();
            String noiDung = eNoiDung.getText().toString().trim();
            String tinhTrang= spTinhTrang.getSelectedItem().toString();
            String thoiHan = eThoiHan.getText().toString();
            String congTac= spCongTac.getSelectedItem().toString();

            if(!ten.isEmpty() && !noiDung.isEmpty() && !thoiHan.isEmpty()) {
                Item i = new Item(ten, noiDung, tinhTrang, thoiHan, congTac);
                SQLiteHelper db = new SQLiteHelper(this);
                long tb = db.addItem(i);
                if(tb > 0) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Có lỗi sảy ra khi thêm vui lòng thêm lại", Toast.LENGTH_SHORT).show();
                }

            } else{
                Toast.makeText(this, "Vui lòng không để trống !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}