package com.example.reservationrestaurant.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.reservationrestaurant.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="eatitDB.db";
    private static final int DB_VER=1;
    public Database(Context context){
        super(context, DB_NAME,null, DB_VER);
    }

    public List<Order> getCarts()
    {
        SQLiteDatabase db= getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"ProductName", "ProductId","Quantity","Price","Discount"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        int indexProductId = c.getColumnIndex("ProductId");
        int indexProductName = c.getColumnIndex("ProductName");
        int indexQuantity = c.getColumnIndex("Quantity");
        int indexPrice = c.getColumnIndex("Price");
        int indexDiscount = c.getColumnIndex("Discount");

        final List<Order> result = new ArrayList<>();
        while (c.moveToNext()) {
            String productId = (indexProductId != -1) ? c.getString(indexProductId) : "";
            String productName = (indexProductName != -1) ? c.getString(indexProductName) : "";
            String quantity = (indexQuantity != -1) ? c.getString(indexQuantity) : "";
            String price = (indexPrice != -1) ? c.getString(indexPrice) : "";
            String discount = (indexDiscount != -1) ? c.getString(indexDiscount) : "";

            result.add(new Order(productId, productName, quantity, price, discount));
        }

        return result;
    }

    public void addToCart(Order order){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId, ProductName, Quantity, Price, Discount) VALUES ('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        db.execSQL(query);
    }


    public void cleanToCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

}
