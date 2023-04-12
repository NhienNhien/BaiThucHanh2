package android.com.baithuchanh2.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String ten, noiDung,  tinhTrang, thoiHan, congTac;

    public Item(int id, String ten, String noiDung, String tinhTrang, String thoiHan, String congTac) {
        this.id = id;
        this.ten = ten;
        this.noiDung = noiDung;
        this.tinhTrang = tinhTrang;
        this.thoiHan = thoiHan;
        this.congTac = congTac;
    }

    public Item(String ten, String noiDung, String tinhTrang, String thoiHan, String congTac) {
        this.ten = ten;
        this.noiDung = noiDung;
        this.tinhTrang = tinhTrang;
        this.thoiHan = thoiHan;
        this.congTac = congTac;
    }

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", tinhTrang='" + tinhTrang + '\'' +
                ", thoiHan='" + thoiHan + '\'' +
                ", congTac='" + congTac + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(String thoiHan) {
        this.thoiHan = thoiHan;
    }

    public String getCongTac() {
        return congTac;
    }

    public void setCongTac(String congTac) {
        this.congTac = congTac;
    }
}
