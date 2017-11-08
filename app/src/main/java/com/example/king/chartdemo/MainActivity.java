package com.example.king.chartdemo;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toolbar;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends ExpandableListActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.android:list);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> groupData1 = new HashMap<String, String>();
        groupData1.put("group", "外匯  ");
        Map<String, String> groupData2 = new HashMap<String, String>();
        groupData2.put("group", "商品");
        Map<String, String> groupData3 = new HashMap<String, String>();
        groupData3.put("group", "關於CFTC");
        list.add(groupData1);
        list.add(groupData2);
        list.add(groupData3);

        List<Map<String, String>> childList1 = new ArrayList<Map<String, String>>();//child
        Map<String, String> childData1 = new HashMap<String, String>();
        childData1.put("country", "美元");
        Map<String, String> childData2 = new HashMap<String, String>();
        childData2.put("country", "歐元");
        Map<String, String> childData3 = new HashMap<String, String>();
        childData3.put("country", "英鎊");
        Map<String, String> childData4 = new HashMap<String, String>();
        childData4.put("country", "日圓");
        Map<String, String> childData5 = new HashMap<String, String>();
        childData5.put("country", "澳元");
        Map<String, String> childData6 = new HashMap<String, String>();
        childData6.put("country", "紐元");
        Map<String, String> childData7 = new HashMap<String, String>();
        childData7.put("country", "加元");
        Map<String, String> childData8 = new HashMap<String, String>();
        childData8.put("country", "瑞朗");

        childList1.add(childData1);
        childList1.add(childData2);
        childList1.add(childData3);
        childList1.add(childData4);
        childList1.add(childData5);
        childList1.add(childData6);
        childList1.add(childData7);
        childList1.add(childData8);


        List<Map<String, String>> childList2 = new ArrayList<Map<String, String>>();//child
        Map<String, String> child2Data1 = new HashMap<String, String>();
        child2Data1.put("country", "黃金");
        Map<String, String> child2Data2 = new HashMap<String, String>();
        child2Data2.put("country", "銀");
        Map<String, String> child2Data3 = new HashMap<String, String>();
        child2Data3.put("country", "銅");
        Map<String, String> child2Data4 = new HashMap<String, String>();
        child2Data4.put("country", "玉米");
        Map<String, String> child2Data5 = new HashMap<String, String>();
        child2Data5.put("country", "黃豆");

        childList2.add(child2Data1);
        childList2.add(child2Data2);
        childList2.add(child2Data3);
        childList2.add(child2Data4);
        childList2.add(child2Data5);

        List<Map<String, String>> childList3 = new ArrayList<Map<String, String>>();//child
        Map<String, String> child3Data1 = new HashMap<String, String>();
        child3Data1.put("country", "CFTC官網");
        Map<String, String> child3Data2 = new HashMap<String, String>();
        child3Data2.put("country", "CFTC介紹");
        Map<String, String> child3Data3 = new HashMap<String, String>();
        child3Data3.put("country", "資料來源");

        childList3.add(child3Data1);
        childList3.add(child3Data2);
        childList3.add(child3Data3);


        List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
        childs.add(childList1);
        childs.add(childList2);
        childs.add(childList3);//三個list加入集合

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this, list, R.layout.group_layout, new String[]{"group"},
                new int[]{R.id.group_tv}, childs, R.layout.child_layout, new String[]{"country"}, new int[]{R.id.child_tv});
        setListAdapter(adapter);//設置adapter


    }

    //點擊跳轉activtiy
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        switch (groupPosition) {
            case 0:
                switch (childPosition) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, USDIndexFx.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, EuroFx.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, GbpFx.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, JpyFx.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MainActivity.this, AudFx.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MainActivity.this, NzdFx.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(MainActivity.this, CadFx.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(MainActivity.this, ChfFx.class);
                        startActivity(intent7);
                        break;


                }
                break;
            case 1:
                switch (childPosition) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, Gold.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, Silver.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, Copper.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, Corn.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MainActivity.this, Soybeans.class);
                        startActivity(intent4);
                        break;
                }
                break;
            case 2:
                switch (childPosition) {
                    case 0:
                        Intent intenta = new Intent(MainActivity.this, CFTC_OW.class);
                        startActivity(intenta);
                        break;
                    case 1:
                        Intent intentb = new Intent(MainActivity.this, CFTC_INFO.class);
                        startActivity(intentb);
                        break;
                    case 2:
                        Intent intentc = new Intent(MainActivity.this, CFTCquandl.class);
                        startActivity(intentc);
                        break;

                }
                break;




        }
        return super.onChildClick(parent, v, groupPosition, childPosition, id);
    }
}






