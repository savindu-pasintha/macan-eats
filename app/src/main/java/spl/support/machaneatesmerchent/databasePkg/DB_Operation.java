package spl.support.machaneatesmerchent.databasePkg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import spl.support.machaneatesmerchent.modelsPkg.ProfileModel;

public class DB_Operation extends SQLiteOpenHelper {
    public ArrayList<ProfileModel> productArrayListModel = new ArrayList<ProfileModel>();

    public DB_Operation(@Nullable Context context) {
        super(context, "market_database", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       String sql="CREATE TABLE Profile(shop_id INT PRIMARY KEY,device_key TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      String  sql ="DROP TABLE IF EXISTS Profile";
        sqLiteDatabase.execSQL(sql);
    }
    public long insert_profile(ProfileModel p){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("shop_id",p.getShopId());
        values.put("device_key",p.getDeviceKey());
        return db.insert("Profile",null,values);
    }
    public int delete_Profile(ProfileModel b){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Profile","shop_id="+b.getShopId(),null);
    }

    public long update_Profile(ProfileModel k){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("shop_id",k.getShopId());
        values.put("device_key",k.getDeviceKey());
        return db.update("Profile",values,"shop_id="+k.getShopId(),null);
    }

    public ArrayList<ProfileModel> viewAllProfiles(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Profile";
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount();
        ArrayList<ProfileModel> profileList = new ArrayList<ProfileModel>();
        if(count!=0){
            while (cursor.moveToNext()) {
                ProfileModel obj = new ProfileModel();
                obj.setShopId(cursor.getInt(0));
                obj.setDeviceKey(cursor.getString(1));
                profileList.add(obj);
            }
        }
        return profileList;

    }


}
