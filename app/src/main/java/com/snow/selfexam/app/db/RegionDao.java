package com.snow.selfexam.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snow.selfexam.mvp.modle.RegionModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Laiyimin on 2016/9/2.
 */
public class RegionDao {

    private Context mContext;
    private SQLiteDatabase db;
    private String name;
    private int cityID1;

    public RegionDao(Context context) {
        this.mContext = context;
        this.db = RegionDBHelper.getInstance(context).getReadableDatabase();
    }

    /**
     * 加载省份
     *
     * @return
     */
    public List<RegionModel> loadProvinceList() {

        List<RegionModel> provinceList = new ArrayList<>();

        String sql = "SELECT ID,NAME FROM PROVINCE";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);

            RegionModel regionModel = new RegionModel();
            regionModel.setId(id);
            regionModel.setName(name);

            provinceList.add(regionModel);
        }

        return provinceList;
    }

    /**
     * 加载城市
     *
     * @param provinceId
     * @return
     */
    public List<RegionModel> loadCityList(Long provinceId) {
        List<RegionModel> provinceList = new ArrayList<>();

        String sql = "SELECT ID,NAME FROM CITY WHERE PID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(provinceId)});
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);

            RegionModel regionModel = new RegionModel();
            regionModel.setId(id);
            regionModel.setName(name);

            provinceList.add(regionModel);
        }

        return provinceList;
    }

    /**
     * 加载地区
     *
     * @param cityId
     * @return
     */
    public List<RegionModel> loadCountyList(Long cityId) {
        List<RegionModel> provinceList = new ArrayList<>();

        String sql = "SELECT ID,NAME FROM AREA WHERE PID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cityId)});
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);

            RegionModel regionModel = new RegionModel();
            regionModel.setId(id);
            regionModel.setName(name);

            provinceList.add(regionModel);
        }

        return provinceList;
    }

    /**
     * 加载数据
     *
     * @param dataName
     * @return
     */
    public String loadTBName(String dataName) {

        Cursor c_test = db.query("AREA", null, "name = ?", new String[]{dataName}, null, null, null);
        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数

        while (c_test.moveToNext()) {
            name = c_test.getString(3);
        }

        return name;
    }


    public int loadCiteId(String major) {

        Cursor c_test = db.query("city", null, "name = ?", new String[]{major}, null, null, null);
        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数

        while (c_test.moveToNext()) {
            cityID1 = c_test.getInt(c_test.getColumnIndex("id"));
        }


        return cityID1;


    }

    public String specId(String mCity) {

        Cursor c_test = db.query("city", null, "name = ?", new String[]{mCity}, null, null, null);
        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数

        while (c_test.moveToNext()) {
            name = c_test.getString(c_test.getColumnIndex("specid"));
        }


        return name;
    }
}
