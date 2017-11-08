package com.example.king.chartdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;

public class Gold extends AppCompatActivity {


    JSONObject j;

    LineChart linechart;
    LineData data;

    List<String> date = new ArrayList<String>();
    Button button1;
    Button button2;
    Button button3;
    XAxis xAxis;
    private String descriptionText;
    private String nonCommercialNumber;
    private String CommercialNumber;
    private String nonReportableNumber;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cftc_chart_layout);
        linechart = (LineChart) findViewById(R.id.linechart);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawerLayout);


        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        //設置toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //設置drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.action_home)
                {
                    Intent intent=new Intent(Gold.this,MainActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.action_help)
                {
                    Intent intent=new Intent(Gold.this,chart_info.class);
                    startActivity(intent);
                }
                else if(id==R.id.action_about)
                {
                    Intent intent=new Intent(Gold.this,about.class);
                    startActivity(intent);
                }

                return false;
            }
        });


        nonCommercialNumber="2";
        CommercialNumber="5";
        nonReportableNumber="9";
        descriptionText="CFTC Gold ";



        loadDataFromQuandl loadDataFromQuandl = new loadDataFromQuandl();
        loadDataFromQuandl.execute(nonCommercialNumber,CommercialNumber,nonReportableNumber);//初始化載入圖表，導入json

        xAxis = linechart.getXAxis();


        xAxis.setTextSize(6f);

        linechart.getDescription().setEnabled(true);

        // scaling can now only be done on x- and y-axis separately
        linechart.setPinchZoom(false);

        linechart.setDrawGridBackground(false);
        linechart.setAutoScaleMinMaxEnabled(true);
        Description description = new Description();
        description.setText(descriptionText);
        linechart.setDescription(description);



        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextColor(GRAY);
        YAxis left = linechart.getAxisLeft();
        left.setDrawLabels(true);
        left.setDrawAxisLine(true);
        left.setDrawGridLines(true);
        left.setDrawZeroLine(true); // draw a zero line
        left.setZeroLineColor(GRAY);
        left.setZeroLineWidth(1f);


        linechart.getAxisRight().setEnabled(false);
        linechart.getLegend().setEnabled(true);

    }


    private class loadDataFromQuandl extends AsyncTask<String, Void, LineData> {

        List<Entry> yValueNC = new ArrayList<Entry>();
        List<Entry> yValueC=new ArrayList<Entry>();
        List<Entry> yValueNR=new ArrayList<Entry>();


        @Override
        protected LineData doInBackground(String... strings) {


            int jsPos1= Integer.valueOf(strings[0]);
            int jsPos2= Integer.valueOf(strings[1]);
            int jsPos3= Integer.valueOf(strings[2]);
            LineDataSet nonCommercialSet;
            LineDataSet CommercialSet;
            LineDataSet nonReportable;

            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder() //設置連線
                        .url("https://www.quandl.com/api/v3/datasets/CFTC/ND_F_L_ALL.json?api_key=Tg54Z7Bexy5stBTrTdTi&start_date=2013-1-1")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                j = new JSONObject(responseData);
                int count = j.getJSONObject("dataset").getJSONArray("data").length();
                date.clear();

                //讀取json數據
                for (int i = (count - 1); i >= 0; i--) {
                    float k = (count - 1) - i;
                    float nonCommercialLong = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(jsPos1).toString());
                    float nonCommercialShort = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(jsPos1 + 1).toString());
                    float nonCommercialNet = (nonCommercialLong - nonCommercialShort);
//                    Log.d("IMX", String.valueOf(nonCommercialNet));

                    float CommercialLong = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(jsPos2).toString());
                    float CommercialShort = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(jsPos2 + 1).toString());
                    float CommercialNet = (CommercialLong - CommercialShort);

                    float nonReportableLong = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(jsPos3).toString());
                    float nonReportableShort = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(jsPos3 + 1).toString());
                    float nonReportableNet = (nonReportableLong - nonReportableShort);


                    //取得日期x值

                    date.add(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(0).toString());

                    //圖表元素
                    yValueNC.add(new Entry(k, nonCommercialNet));
                    yValueC.add(new Entry(k, CommercialNet));
                    yValueNR.add(new Entry(k, nonReportableNet));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            nonCommercialSet = new LineDataSet(yValueNC, "nonCommercial");
            CommercialSet= new LineDataSet(yValueC, "Commercial");
            nonReportable=new LineDataSet(yValueNR,"nonReportable");


            nonCommercialSet.setColor(Color.RED);
            nonCommercialSet.setDrawFilled(false);
            nonCommercialSet.setFillColor(Color.RED);
            nonCommercialSet.setDrawCircles(false);

            CommercialSet.setColor(Color.BLUE);
            CommercialSet.setDrawFilled(false);
            CommercialSet.setFillColor(Color.BLUE);
            CommercialSet.setDrawCircles(false);

            nonReportable.setColor(Color.GREEN);
            nonReportable.setDrawFilled(false);
            nonReportable.setFillColor(Color.GREEN);
            nonReportable.setDrawCircles(false);

            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();//線集合
            dataSets.add(nonCommercialSet);
            dataSets.add(CommercialSet);
            dataSets.add(nonReportable);

            data = new LineData(dataSets);

            return data;
        }

        @Override
        protected void onPostExecute(LineData LineData) {
            super.onPostExecute(LineData);

            xAxis = linechart.getXAxis();
            linechart.setBackgroundColor(WHITE);

            xAxis.setValueFormatter(new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    return date.get((int) value);
                }


            });


            linechart.setData(data);
            linechart.notifyDataSetChanged();//刷新數據
            linechart.invalidate();
            button1 =findViewById(R.id.button1);
            button2 =findViewById(R.id.button2);
            button3 =findViewById(R.id.button3);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(data.getDataSetByLabel("nonCommercial",true)==null)
                    {

                        loadNonCommercial loadNonCommercial = new loadNonCommercial();
                        loadNonCommercial.execute(nonCommercialNumber);
                    }
                    else
                    {
                        data.removeDataSet(data.getDataSetByLabel("nonCommercial",true));


                        linechart.notifyDataSetChanged();//刷新數據
                        linechart.invalidate();
                    }

                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.getDataSetByLabel("Commercial",true)==null)
                    {

                        loadCommercial loadCommercial = new loadCommercial();
                        loadCommercial.execute(CommercialNumber);
                    }
                    else
                    {
                        data.removeDataSet(data.getDataSetByLabel("Commercial",true));


                        linechart.notifyDataSetChanged();//刷新數據
                        linechart.invalidate();
                    }

                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.getDataSetByLabel("nonReportable",true)==null)
                    {

                        loadNonReportable loadNonReportable = new loadNonReportable();
                        loadNonReportable.execute(nonReportableNumber);
                    }
                    else
                    {
                        data.removeDataSet(data.getDataSetByLabel("nonReportable",true));


                        linechart.notifyDataSetChanged();//刷新數據
                        linechart.invalidate();
                    }
                }
            });
        }




    }
    private class loadNonCommercial extends AsyncTask<String, Void, LineDataSet> {
        List<Entry> yValue = new ArrayList<Entry>();
        @Override
        protected LineDataSet doInBackground(String... strings) {
            int s = Integer.valueOf(strings[0]);
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.quandl.com/api/v3/datasets/CFTC/EC_F_L_ALL.json?api_key=Tg54Z7Bexy5stBTrTdTi&start_date=2013-1-1")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                j = new JSONObject(responseData);
                int count = j.getJSONObject("dataset").getJSONArray("data").length();
                Log.d("IMX", String.valueOf(count));
                date.clear();
                for (int i = (count - 1); i >= 0; i--) {
                    float positionLong = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(s).toString());
                    float positionShort = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(s + 1).toString());
                    float netPosition = (positionLong - positionShort);
                    Log.d("IMX", String.valueOf(netPosition));
                    float k = (count - 1) - i;


                    date.add(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(0).toString());


                    yValue.add(new Entry(k, netPosition));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            LineDataSet nonCommercialSet = new LineDataSet(yValue, "nonCommercial");
            nonCommercialSet.setColor(Color.RED);
            nonCommercialSet.setDrawFilled(false);
            nonCommercialSet.setFillColor(Color.RED);
            nonCommercialSet.setDrawCircles(false);

            return nonCommercialSet;
        }


        @Override
        protected void onPostExecute(LineDataSet nonCommercialSet) {
            super.onPostExecute(nonCommercialSet);
//            xAxis = linechart.getXAxis();
//
//            xAxis.setValueFormatter(new IAxisValueFormatter() {
//
//                @Override
//                public String getFormattedValue(float value, AxisBase axis) {
//
//                    return date.get((int) value);
//                }
//
//
//            });

            data.addDataSet(nonCommercialSet);

            linechart.notifyDataSetChanged();//刷新數據
            linechart.invalidate();

        }


    }
    private class loadCommercial extends AsyncTask<String, Void, LineDataSet> {
        List<Entry> yValue = new ArrayList<Entry>();
        @Override
        protected LineDataSet doInBackground(String... strings) {
            int s = Integer.valueOf(strings[0]);
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.quandl.com/api/v3/datasets/CFTC/GC_F_ALL.json?api_key=Tg54Z7Bexy5stBTrTdTi&start_date=2013-01-01")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                j = new JSONObject(responseData);
                int count = j.getJSONObject("dataset").getJSONArray("data").length();
                Log.d("IMX", String.valueOf(count));
                date.clear();
                for (int i = (count - 1); i >= 0; i--) {
                    float positionLong = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(s).toString());
                    float positionShort = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(s + 1).toString());
                    float netPosition = (positionLong - positionShort);
                    Log.d("IMX", String.valueOf(netPosition));
                    float k = (count - 1) - i;


                    date.add(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(0).toString());


                    yValue.add(new Entry(k, netPosition));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            LineDataSet commercialSet = new LineDataSet(yValue, "Commercial");
            commercialSet.setColor(Color.BLUE);
            commercialSet.setDrawFilled(false);
            commercialSet.setFillColor(Color.BLUE);
            commercialSet.setDrawCircles(false);




            return commercialSet;
        }


        @Override
        protected void onPostExecute(LineDataSet commercialSet) {
            super.onPostExecute(commercialSet);


            data.addDataSet(commercialSet);

            linechart.notifyDataSetChanged();
            linechart.invalidate();

        }


    }
    private class loadNonReportable extends AsyncTask<String, Void, LineDataSet> {
        List<Entry> yValue = new ArrayList<Entry>();
        @Override
        protected LineDataSet doInBackground(String... strings) {
            int s = Integer.valueOf(strings[0]);
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.quandl.com/api/v3/datasets/CFTC/EC_F_L_ALL.json?api_key=Tg54Z7Bexy5stBTrTdTi&start_date=2012-10-24")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                j = new JSONObject(responseData);
                int count = j.getJSONObject("dataset").getJSONArray("data").length();
                Log.d("IMX", String.valueOf(count));
                date.clear();
                for (int i = (count - 1); i >= 0; i--) {
                    float positionLong = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(s).toString());
                    float positionShort = Float.valueOf(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(s + 1).toString());
                    float netPosition = (positionLong - positionShort);
                    Log.d("IMX", String.valueOf(netPosition));
                    float k = (count - 1) - i;


                    date.add(j.getJSONObject("dataset")
                            .getJSONArray("data").getJSONArray(i).get(0).toString());

                    yValue.add(new Entry(k, netPosition));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            LineDataSet nonReportable = new LineDataSet(yValue, "nonReportable");
            nonReportable.setColor(Color.GREEN);
            nonReportable.setDrawFilled(false);
            nonReportable.setFillColor(Color.GREEN);
            nonReportable.setDrawCircles(false);




            return nonReportable;
        }


        @Override
        protected void onPostExecute(LineDataSet nonReportable) {
            super.onPostExecute(nonReportable);


            data.addDataSet(nonReportable);

            linechart.notifyDataSetChanged();
            linechart.invalidate();

        }


    }

}