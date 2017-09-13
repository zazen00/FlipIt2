package com.globalappdev.flipit;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.globalappdev.flipit.models.Expense;
import com.globalappdev.flipit.models.Expenses;
import com.globalappdev.flipit.models.Investment;
import com.globalappdev.flipit.models.Investments;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 8/16/2017.
 */

public class DemoParallaxFragment extends Fragment  {

    private DemoParallaxAdapter mCatsAdapter;
    private MainActivity parent;
    View inflated;
    ImageView ivBasicImage;
    String bgUrl;
    boolean bAdd;
    ListView lvmain;
    Expenses expenses;
    private GoogleMap googleMap;

    public void setExpenses(Expenses expenses) {

        this.expenses = expenses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.demo_fragment_parallax, container, false);
        inflated = v;
        ivBasicImage = (ImageView) inflated.findViewById(R.id.parallax_image);
        bgUrl = getArguments().getString("image");
        Bundle arguments = getArguments();
        bAdd = arguments.containsKey("bg_image");
        if(bAdd){
            showAddViews();
//            final ImageView image = (ImageView) v.findViewById(R.id.image);

            ivBasicImage.setImageResource(getArguments().getInt("bg_image"));
            ivBasicImage.post(new Runnable() {
                @Override
                public void run() {
                    Matrix matrix = new Matrix();
                    matrix.reset();

                    float wv = ivBasicImage.getWidth();
                    float hv = ivBasicImage.getHeight();

                    float wi = ivBasicImage.getDrawable().getIntrinsicWidth();
                    float hi = ivBasicImage.getDrawable().getIntrinsicHeight();

                    float width = wv;
                    float height = hv;

                    if (wi / wv > hi / hv) {
                        matrix.setScale(hv / hi, hv / hi);
                        width = wi * hv / hi;
                    } else {
                        matrix.setScale(wv / wi, wv / wi);
                        height= hi * wv / wi;
                    }

                    matrix.preTranslate((wv - width) / 2, (hv - height) / 2);
                    ivBasicImage.setScaleType(ImageView.ScaleType.MATRIX);
                    ivBasicImage.setImageMatrix(matrix);
                }
            });
        }
        downloadImages();

        TextView total = (TextView)v.findViewById(R.id.inv_total);

        ArrayList<String> lvList = new ArrayList<>();
        lvmain = (ListView)v.findViewById(R.id.lvmain) ;
        if(expenses!=null) {


            ArrayList<Expense> arrExp = expenses.getExpenses();
            float ftotal = 0;
            for (Expense e : arrExp) {
                float me = e.cost * e.qty;
                ftotal += me;
                String s = e.item + " price: " + e.cost + " qty: " + e.qty;
                lvList.add(s);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    parent,
                    android.R.layout.simple_list_item_1,
                    lvList);

            lvmain.setAdapter(arrayAdapter);
            total.setText("$" + String.valueOf(ftotal));
        }
//        image.setImageResource(getArguments().getInt("image"));
//        image.se


        LinearLayout llNew = (LinearLayout)  v.findViewById(R.id.ll_add_inv);
        llNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewInvestment();
            }
        });
        ImageButton addButton = (ImageButton)v.findViewById(R.id.add_ic);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewInvestment();
            }
        });

        TextView text = (TextView)v.findViewById(R.id.tv_address);
        text.setText(getArguments().getString("name"));


        return v;
    }
    public void showAddViews(){
        CardView cv_topcard = (CardView)inflated.findViewById(R.id.cv_topcard);
        cv_topcard.setVisibility(View.GONE);
        CardView cv_total = (CardView)inflated.findViewById(R.id.cv_total);
        cv_total.setVisibility(View.GONE);
        TextView mText = (TextView)inflated.findViewById(R.id.tv_address);
        mText.setVisibility(View.GONE);
        EditText add = (EditText)inflated.findViewById(R.id.et_address);
        add.setVisibility(View.VISIBLE);
        Button save = (Button)inflated.findViewById(R.id.btn_create);

        save.setVisibility(View.VISIBLE);
        Fab mFab = (Fab)inflated.findViewById(R.id.fab);
        mFab.setVisibility(View.GONE);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeInvestment();
            }
        });

    }
    public void createNewInvestment(){


        parent.scrollToAdd();
        Log.d("called","you ht it");
    }
    public void adjustImage(){

        ivBasicImage.post(new Runnable() {
                @Override
                public void run() {
                    Matrix matrix = new Matrix();
                    matrix.reset();

                    float wv = ivBasicImage.getWidth();
                    float hv = ivBasicImage.getHeight();

                    float wi = ivBasicImage.getDrawable().getIntrinsicWidth();
                    float hi = ivBasicImage.getDrawable().getIntrinsicHeight();

                    float width = wv;
                    float height = hv;

                    if (wi / wv > hi / hv) {
                        matrix.setScale(hv / hi, hv / hi);
                        width = wi * hv / hi;
                    } else {
                        matrix.setScale(wv / wi, wv / wi);
                        height= hi * wv / wi;
                    }

                    matrix.preTranslate((wv - width) / 2, (hv - height) / 2);
                    ivBasicImage.setScaleType(ImageView.ScaleType.MATRIX);
                    ivBasicImage.setImageMatrix(matrix);
                }
            });
    }
    public void downloadImages(){
        Picasso.with(parent.mContext).load(bgUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder_bg)
                .into(
                ivBasicImage,
                new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {

            }
        });


        Fab fab = (Fab) inflated.findViewById(R.id.fab);
        View sheetView = inflated.findViewById(R.id.fab_sheet);
        View overlay = inflated.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.colorPrimaryDark);
        int fabColor = getResources().getColor(R.color.cardview_light_background);

        // Initialize material sheet FAB
        MaterialSheetFab materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);
    }

    public void setAdapter(DemoParallaxAdapter catsAdapter) {
        mCatsAdapter = catsAdapter;
    }
    public void setParent(MainActivity m){
        this.parent = m;
    }

    public void makeInvestment(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dev").child(getResources().getString(R.string.dev_id_1));

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("data",dataSnapshot.getValue().toString());
                Investments mInvestments = dataSnapshot.getValue(Investments.class);

                List<Investment> investments = mInvestments.getInvestments();

                ArrayList<Expense> expenses = new ArrayList<>();
                Expense mfirst = new Expense("add",0,0.f,"url");
                Expense mfirstq = new Expense("add",0,0.f,"url");
                expenses.add(mfirst);
                expenses.add(mfirstq);
                Expenses mdt = new Expenses(expenses);

                EditText add = (EditText)inflated.findViewById(R.id.et_address);

                Investment mInv = new Investment("inv1",add.getText().toString(),mdt,"https://firebasestorage.googleapis.com/v0/b/chapp-5c477.appspot.com/o/inve1.jpg?alt=media&token=2bf7c91f-9611-4ebb-a39d-79fbb1fc895f");

                investments.add(mInv);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("dev").child(getResources().getString(R.string.dev_id_1));
                myRef.setValue(investments);
                parent.refreshList();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

    }


}
