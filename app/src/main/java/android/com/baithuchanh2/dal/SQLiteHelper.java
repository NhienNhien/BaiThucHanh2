package android.com.baithuchanh2.dal;

import android.com.baithuchanh2.model.Item;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CongViec.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT, noiDung TEXT, tinhTrang TEXT, thoiHan TEXT, congTac TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//     lấy tất cả item sắp xếp theo thời gian giảm dần.
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "id DESC";
        Cursor rs = st.query("items",null,
                null, null, null, null, null);

        while (rs != null  && rs.moveToNext()){
            list.add(new Item(rs.getInt(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)));
        }
        return list;
    }
    public long addItem(Item i){
        ContentValues values = new ContentValues();
        values.put("ten", i.getTen());
        values.put("noiDung", i.getNoiDung());
        values.put("tinhTrang", i.getTinhTrang());
        values.put("thoiHan", i.getThoiHan());
        values.put("congTac", i.getCongTac());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("items", null, values);
    }

    public long upDate(Item i){
        ContentValues values = new ContentValues();
        values.put("ten", i.getTen());
        values.put("noiDung", i.getNoiDung());
        values.put("tinhTrang", i.getTinhTrang());
        values.put("thoiHan", i.getThoiHan());
        values.put("congTac", i.getCongTac());
        SQLiteDatabase st = getWritableDatabase();
        String whereClause = "id = ?";
        String[]whenArgs = {Integer.toString(i.getId())};
        return st.update("items",  values, whereClause, whenArgs);
    }

    public long delete(int id){
        SQLiteDatabase st = getWritableDatabase();
        String whereClause = "id = ?";
        String[]whenArgs = {Integer.toString(id)};
        return st.delete("items", whereClause, whenArgs);
    }


    public List<Item> searchByNoiDung(String key){
        List<Item> list = new ArrayList<>();
        String whereClause = "title like ?";
        String order = "thoiHan ASC";
        String[]whenArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause,
                whenArgs, null, null, order);
        while (rs != null && rs.moveToNext()){
            list.add(new Item(rs.getInt(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)));
        }
        return list;
    }


    public List<Item> searchByTinhTrang(String tinhtrang){
        List<Item> list = new ArrayList<>();
        String whereClause = "tinhTrang like ?";
        String[]whenArgs = {tinhtrang};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause,
                whenArgs, null, null, null);
        while (rs != null && rs.moveToNext()){
            list.add(new Item(rs.getInt(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)));
        }
        return list;
    }

}
