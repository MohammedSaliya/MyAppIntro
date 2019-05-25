package com.example.myappintro;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.Button;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myappintro.APIClasss.APIClient;
import com.example.myappintro.Bean.ViewpagerBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends AppCompatActivity {

    PreferenceManager preferenceManager;
    LinearLayout Layout_bars;
    TextView[] bottomBars;
    TextView txt_login;
    int[] screens;
    Button signup;
    ViewPager vp;
    MyViewPagerAdapter myvpAdapter;
    String TAG = "MainScreen";
    List<ViewpagerBean> viewpagerBean = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        LoadViewData();

        vp = findViewById(R.id.view_pager);
        Layout_bars = findViewById(R.id.layoutBars);
        txt_login = findViewById(R.id.txt_login);
        signup = findViewById(R.id.signup);

        screens = new int[]{
                R.layout.intro_screen1,
                R.layout.intro_screen2,
                R.layout.intro_screen3,
        };


    }

    private void LoadViewData() {
        Call<ViewpagerBean> beanCall = APIClient
                .getapiClient()
                .getapiInterface()
                .getViewPagerResponce();

        beanCall.enqueue(new Callback<ViewpagerBean>() {


            @Override
            public void onResponse(Call<ViewpagerBean> call, Response<ViewpagerBean> response) {

                viewpagerBean.add(response.body());

                Log.e(TAG, "onResponse: " + viewpagerBean.get(0).getData().size());

                Log.e(TAG, "onResponse: " + viewpagerBean.get(0).getData().toString());

//                Toast.makeText(MainScreen.this, viewpagerBean.get(0).getData().get(0).getHeading(), Toast.LENGTH_SHORT).show();

                myvpAdapter = new MyViewPagerAdapter();
                vp.setAdapter(myvpAdapter);
                vp.addOnPageChangeListener(viewPagerPageChangeListener);
                preferenceManager = new PreferenceManager(getApplicationContext());
                vp.addOnPageChangeListener(viewPagerPageChangeListener);

                if (!preferenceManager.FirstLaunch()) {
                    launchMain();
                    finish();
                }
                ColoredBars(0);
            }

            @Override
            public void onFailure(Call<ViewpagerBean> call, Throwable t) {
                Log.e(TAG, "Error" + t.getMessage());
                Toast.makeText(MainScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void next(View v) {
        int i = getItem(+1);
        if (i < screens.length) {
            vp.setCurrentItem(i);
        } else {
            launchMain();
        }
    }

    public void skip(View view) {
        launchMain();
    }

    private void ColoredBars(int thisScreen) {
        int[] colorsInactive = getResources().getIntArray(R.array.dot_on_page_not_active);
        int[] colorsActive = getResources().getIntArray(R.array.dot_on_page_active);
        bottomBars = new TextView[screens.length];

        Layout_bars.removeAllViews();
        for (int i = 0; i < bottomBars.length; i++) {
            bottomBars[i] = new TextView(this);
            bottomBars[i].setTextSize(100);
            bottomBars[i].setText(Html.fromHtml("&#175"));
            Layout_bars.addView(bottomBars[i]);
            bottomBars[i].setTextColor(colorsInactive[thisScreen]);
            signup.setTextColor(colorsInactive[thisScreen]);

        }
        if (bottomBars.length > 0)
            bottomBars[thisScreen].setTextColor(colorsActive[thisScreen]);
        signup.setTextColor(colorsInactive[thisScreen]);

    }

    private int getItem(int i) {
        return vp.getCurrentItem() + i;
    }

    private void launchMain() {
        preferenceManager.setFirstTimeLaunch(false);
        startActivity(new Intent(MainScreen.this, MainActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            ColoredBars(position);
            if (position == screens.length - 1) {

            } else {

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(screens[position], container, false);
            TextView textView, dec;
            ImageView imageViewfirst;


            textView = view.findViewById(R.id.headin);
            dec = view.findViewById(R.id.dec);
            imageViewfirst = view.findViewById(R.id.imageViewfirst);
            Glide.with(getBaseContext()).load(viewpagerBean.get(0).getData().get(position).getImage()).into(imageViewfirst);
            dec.setText(viewpagerBean.get(0).getData().get(position).getDescription());

            Log.e(TAG, "" + viewpagerBean.get(0).getData().get(position).getHeading());
            textView.setText("" + viewpagerBean.get(0).getData().get(position).getHeading());


            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return screens.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);

        }

        @Override
        public boolean isViewFromObject(View v, Object object) {
            return v == object;
        }
    }
}