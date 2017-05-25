package net.sqindia.movhaulagent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Salman on 19-05-2017.
 */

class Dialog_Region extends Dialog {

    public Activity activity;
    TextView tv_header;
    ImageView img_back;
    Typeface tf;
    ArrayList<String> state_lists = new ArrayList<>();
    ArrayList<String> district_lists = new ArrayList<>();
    ListAdapter adapter1, adapter2, adapter3;
    LoginActivity regi;
    String str_states, str_districts;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                tv_header.setText("State");
                img_back.setVisibility(View.GONE);
                //adapter1.notifyDataSetChanged();
            }  else {
                tv_header.setText("District");
               /// adapter3.notifyDataSetChanged();
            }
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
    private ViewPager viewPager;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;


    public Dialog_Region(Activity activity) {
        super(activity);
        this.activity = activity;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_region);


        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/lato.ttf");
        tv_header = (TextView) findViewById(R.id.textview_header);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        img_back = (ImageView) findViewById(R.id.img_back);

        tv_header.setTypeface(tf);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = sharedPreferences.edit();
        editor.putString("state", "");
        editor.putString("district", "");
        editor.commit();

        Log.e("tag","dialog_worked");

        regi = new LoginActivity();

        layouts = new int[]{
                R.layout.register_state,
                R.layout.register_state     };

        state_lists.add("Abia");
        state_lists.add("Borno");
        state_lists.add("Imo");
        state_lists.add("Kogi");
        state_lists.add("Osun");
        state_lists.add("Sokoto");


        district_lists.add("Rijau");
        district_lists.add("Owan");
        district_lists.add("Sakaba");
        district_lists.add("Wurno");
        district_lists.add("Yauri");
        district_lists.add("Zaria");

        img_back.setVisibility(View.GONE);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        // viewPager.beginFakeDrag();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = 0;
                current = viewPager.getCurrentItem() - 1;
                // Do something after 5s = 5000ms
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);

                } else {
                    // launchHomeScreen();
                }

            }
        });


    }


    public class MyViewPagerAdapter extends PagerAdapter {

        ListView lview_state, lview_district, lview_zip;
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            if (position == 0) {

                lview_state = (ListView) view.findViewById(R.id.lview);
                Log.e("tag","stsiz: "+state_lists.size());
                adapter1 = new ListAdapter(activity.getApplicationContext(), R.layout.dialog_region_txts, state_lists);
                lview_state.setAdapter(adapter1);


            } else  {


                lview_district = (ListView) view.findViewById(R.id.lview);
                Log.e("tag","destsz: "+district_lists.size());
                adapter2 = new ListAdapter(activity.getApplicationContext(), R.layout.dialog_region_txts, district_lists);
                lview_district.setAdapter(adapter2);


            }


            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public class ListAdapter extends ArrayAdapter<String> {

        Context cc;
        ArrayList<String> data_lists;
        int resourceid;

        public ListAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.cc = context;
            this.data_lists = objects;
            this.resourceid = textViewResourceId;
        }

        @Override
        public View getDropDownView(int posi, View convertView, ViewGroup parent) {
            return getCustomView(posi, convertView, parent);
        }

        @Override
        public View getView(int posi, View convertView, ViewGroup parent) {
            return getCustomView(posi, convertView, parent);
        }


        public View getCustomView(final int posi, View row, ViewGroup parent) {

            Typeface tf = Typeface.createFromAsset(cc.getAssets(), "fonts/lato.ttf");

            LayoutInflater inflater = (LayoutInflater) cc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View arow = inflater.inflate(resourceid, parent, false);

            TextView label = (TextView) arow.findViewById(R.id.textview_header);

            label.setTypeface(tf);

            label.setText(data_lists.get(posi));

            arow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (viewPager.getCurrentItem() == 0) {

                        regi.str_state = data_lists.get(posi);
                        str_states = data_lists.get(posi);


                        editor.putString("state", str_states);
                        editor.commit();

                        Log.e("tag", "cc " + str_states);
                        int current = 0;
                        current = viewPager.getCurrentItem() + 1;
                        if (current < layouts.length) {
                            viewPager.setCurrentItem(current);
                        } else {
                        }
                    } else {
                        str_districts = data_lists.get(posi);
                        regi.str_district = data_lists.get(posi);

                        editor.putString("district", str_districts);
                        editor.commit();

                        Log.e("tag", "zz " + regi.str_state +regi.str_district);
                        dismiss();
                    }
                }


            });


            return arow;
        }
    }


}