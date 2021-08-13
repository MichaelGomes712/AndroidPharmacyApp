package com.example.pharmacist;

public class StockItem {
    private String sName,sPrice, sQty,sSchedule, sID;


    public StockItem(String name, String price, String qty, String Schedule, String id){
    sName  = name;
    sPrice  = price;
    sQty = qty;
    sSchedule = Schedule;
    sID = id;

    }

    public String getsName() {
        return sName;
    }

    public String getsPrice() {
        return sPrice;
    }

    public String getsQty() {
        return sQty;
    }

    public String getsSchedule() {
        return sSchedule;
    }

    public String getsID() {
        return sID;
    }
}
