package net.sqindia.movhaulagent.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.hbb20.CountryCodePicker;
import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.Fragment.LoginFragment;
import net.sqindia.movhaulagent.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 23-05-2017.
 */

public class RegisterFragment extends android.support.v4.app.Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 234;
    private static final int REQUEST_CODE =10 ;
    private static final int REQUEST_CODE_PHOTO = 20;
    TextView tv_activity_header,tv_login;
    TextInputLayout til_name,til_address,til_state,til_city,til_phone,til_email,til_bank;
    EditText et_name,et_address,et_state,et_city,et_phone,et_email,et_bank,et_coverage;
    LinearLayout lt_state,lt_city,lt_bank,lt_coverage,lt_id_card,lt_photo;
    public String[] ar_banks;
    public String[] ar_state;
    public String[] ar_city;
    public String[] ar_banks_copy;
    Typeface tf;
    CountryCodePicker ccp_register;

    ArrayList<Uri> image_uris;
    String str_id_card_photo,str_photograph;
    ImageView iv_id_card,iv_photograph;

    LinearLayout lt_action_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View get_RegisterView = inflater.inflate(R.layout.register_fragment, container, false);

        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_RegisterView);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lato.ttf");

        ar_banks = new String[]{" HSBC ", " Hongkong&Sangai Bank ", " SAFC ", " Bank Of Africa ", "Federal Bank of Nigeria", " LEKIA Bank ", " Nigeria Bank ",};

        ar_state = new String[]{"Abia","Akwa Ibom","Benue","Borno","Delta","Enugu","Edo","Jigawa","Kebbi","Lagos","Ogun","Oyo","Rivers","Yobe"};

        ar_city = new String[]{"Asaba","Bauchi","Dutse","Jimeta","Kanduna","Lafia","Lekki","Oron","Port Harcourt","Sokoto","Warri","Zaria"};

        tv_login = (TextView) get_RegisterView.findViewById(R.id.textview_login);
        tv_activity_header = (TextView)getActivity().findViewById(R.id.textview_header);
        tv_activity_header.setText(getString(R.string.register));
        Log.e("tag","register");

        lt_action_back  = (LinearLayout) getActivity().findViewById(R.id.action_back);
        lt_action_back.setVisibility(View.VISIBLE);

        til_name = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_username);
        til_address = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_address);
        til_state = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_state);
        til_city = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_city);
        til_phone = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_phone);
        til_email = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_email);
        til_bank = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_bank);
        lt_bank = (LinearLayout) get_RegisterView.findViewById(R.id.layout_choose_bank);
        lt_state = (LinearLayout) get_RegisterView.findViewById(R.id.layout_choose_state);
        lt_city = (LinearLayout) get_RegisterView.findViewById(R.id.layout_choose_city);
        lt_coverage = (LinearLayout) get_RegisterView.findViewById(R.id.layout_coverage);
        lt_coverage = (LinearLayout) get_RegisterView.findViewById(R.id.layout_coverage);
        lt_id_card = (LinearLayout) get_RegisterView.findViewById(R.id.layout_id_card);
        lt_photo = (LinearLayout) get_RegisterView.findViewById(R.id.layout_photograph);

        iv_id_card = (ImageView)  get_RegisterView.findViewById(R.id.imageview_idcard);
        iv_photograph = (ImageView)  get_RegisterView.findViewById(R.id.imageview_photograph);

        et_bank = (EditText) get_RegisterView.findViewById(R.id.edittext_bank);
        et_state = (EditText) get_RegisterView.findViewById(R.id.edittext_state);
        et_city = (EditText) get_RegisterView.findViewById(R.id.edittext_city);
        et_coverage = (EditText) get_RegisterView.findViewById(R.id.edittext_coverage);


        ccp_register = (CountryCodePicker) get_RegisterView.findViewById(R.id.ccp_register);

        til_name.setTypeface(tf);
        til_address.setTypeface(tf);
        til_state.setTypeface(tf);
        til_city.setTypeface(tf);
        til_phone.setTypeface(tf);
        til_email.setTypeface(tf);
        til_bank.setTypeface(tf);
        ccp_register.setTypeFace(tf);






        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new LoginFragment());
                ft.commit();
                tv_activity_header.setText(getString(R.string.login));
                lt_action_back.setVisibility(View.GONE);
            }
        });

        lt_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(ar_banks,et_bank);
            }
        });

        lt_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(ar_state,et_state);
            }
        });

        lt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(ar_city,et_city);
            }
        });


        et_coverage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    lt_coverage.setVisibility(View.VISIBLE);

                    String io = et_coverage.getText().toString();


                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lparams.setMargins(5, 5, 5,  5);
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(lparams);
                    tv.setGravity(Gravity.LEFT);

                    Drawable img = getResources().getDrawable(R.drawable.del_icon);
                    img.setBounds(0, 0, 20, 20);
                    tv.setCompoundDrawables(null, null, img, null);

                    //tv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.close, 0);
                    //  tv.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.close),null);
                    tv.setCompoundDrawablePadding(5);
                    tv.setBackground(getResources().getDrawable(R.drawable.chips_edittext_gb));
                    tv.setTextColor(getResources().getColor(R.color.textColor));
                    tv.setPadding(15, 10, 10, 5);
                    tv.setGravity(Gravity.CENTER);

                    tv.setText(io);
                    lt_coverage.addView(tv);

                    et_coverage.setText("");

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lt_coverage.removeView(v);
                            if (lt_coverage.getChildCount() == 0) {
                                lt_coverage.setVisibility(View.GONE);
                            }
                        }
                    });

                    Log.e("tag", "count: " + lt_coverage.getChildCount());


                }


                return false;

            }
        });



        lt_id_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("tag", "permission Not granted");


                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else {
                    com.gun0912.tedpicker.Config config = new com.gun0912.tedpicker.Config();
                    config.setSelectionMin(1);
                    config.setSelectionLimit(1);
                    config.setCameraHeight(R.dimen.app_camera_height);
                    config.setCameraBtnBackground(R.drawable.round_rd);
                    config.setToolbarTitleRes(R.string.img_vec_lic);
                    config.setSelectedBottomHeight(R.dimen.bottom_height);
                    ImagePickerActivity.setConfig(config);
                    Intent intent = new Intent(getActivity(), com.gun0912.tedpicker.ImagePickerActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }

            }
        });


        lt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("tag", "permission Not granted");


                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else {
                    com.gun0912.tedpicker.Config config = new com.gun0912.tedpicker.Config();
                    config.setSelectionMin(1);
                    config.setSelectionLimit(1);
                    config.setCameraHeight(R.dimen.app_camera_height);
                    config.setCameraBtnBackground(R.drawable.round_rd);
                    config.setToolbarTitleRes(R.string.img_vec_lic_photo);
                    config.setSelectedBottomHeight(R.dimen.bottom_height);
                    ImagePickerActivity.setConfig(config);
                    Intent intent = new Intent(getActivity(), com.gun0912.tedpicker.ImagePickerActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PHOTO);
                }


            }
        });




        return get_RegisterView;
    }


    public void popup(final String[] ar_bank, final EditText et_data){

        ar_banks_copy = ar_bank;


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_choose_bank, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        LinearLayout myRoot = (LinearLayout) dialogView.findViewById(R.id.layout_top);
        LinearLayout a = null;

        for (int i = 0; i < ar_banks_copy.length; i++) {

            a = new LinearLayout(getActivity());
            LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            para.setMargins(5, 5, 5, 5);
            a.setOrientation(LinearLayout.HORIZONTAL);
            a.setLayoutParams(para);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
            params.gravity = Gravity.CENTER;
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.button_change);
            imageView.setLayoutParams(params);
            TextView tss = new TextView(getActivity());
            tss.setText(ar_banks_copy[i]);
            LinearLayout.LayoutParams paramsQ = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsQ.gravity = Gravity.CENTER;
            tss.setLayoutParams(paramsQ);
            tss.setTextSize(18);
            tss.setTextColor(getResources().getColor(R.color.textColor));
            FontsManager.changeFonts(tss);

            View vres = new View(getActivity());
            LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            vres.setLayoutParams(paramss);
            vres.setBackgroundColor(getResources().getColor(R.color.viewColor));

            a.addView(imageView);
            a.addView(tss);
            myRoot.addView(a);
            if(i!=ar_banks_copy.length-1)
                myRoot.addView(vres);

            final int k = i;

            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("tag", "a:" + ar_banks_copy[k]);
                    b.dismiss();
                    et_data.setText(ar_banks_copy[k]);
                }
            });
        }
        b.show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {


            image_uris = data.getParcelableArrayListExtra(com.gun0912.tedpicker.ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e("tag", "12345" + image_uris);
            if (image_uris != null) {
                str_id_card_photo = image_uris.get(0).toString();
                Glide.with(getActivity()).load(new File(str_id_card_photo)).centerCrop().into(iv_id_card);
            }

        }

        else if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {


            image_uris = data.getParcelableArrayListExtra(com.gun0912.tedpicker.ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e("tag", "12345" + image_uris);
            if (image_uris != null) {
                str_photograph = image_uris.get(0).toString();
                Glide.with(getActivity()).load(new File(str_photograph)).centerCrop().into(iv_photograph);
            }

        }

    }

}