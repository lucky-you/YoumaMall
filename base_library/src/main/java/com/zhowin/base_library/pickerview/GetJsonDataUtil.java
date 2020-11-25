package com.zhowin.base_library.pickerview;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class GetJsonDataUtil {

    /**
     * 转成json
     */
    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * Gson 解析
     */
    public static ArrayList<ProvinceCityBean> parseData(String result) {
        ArrayList<ProvinceCityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceCityBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceCityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}

