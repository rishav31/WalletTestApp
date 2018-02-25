package com.rst.rishav.wallettestapp;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout payment_layout,recharge_layout,dth_layout,electricity_layout,addmoney_layout,credit_layout,passbook_layout;
    ImageView paybtn,addmoneybtn,passbookbtn;
    Button paymentbtn,rechargebtn,elctricity_paybtn,dth_pay,credit_pay;

    //Payment Layout
    EditText pay_id,pay_amt,pay_remarks;

    //AddMoney Layout
    EditText add_crdNum,add_crdName,add_amt;
    Spinner bankName,cardType;
    Button addMoney;
    private String bank_name,card_type;

    //RechargeLayout
    EditText rech_mobile,rech_amt;
    Spinner operator;
    String getoperatorName;

    //electricity layout
    EditText elec_id,elec_amt;
    Spinner boards;
    String getBoardsName;

    //dth layout
    EditText dth_id,dth_amt;
    Spinner dthoperators;
    String getDthOperator;

    //Credit Card layout
    EditText credit_num, credit_amt,credit_nameOnCard;
    DatabaseHandler db;
    private ListView listView;

    int olderBalance,newBalance;
    TextView balance;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,android.Manifest.permission.CAMERA,android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);


        payment_layout = (LinearLayout) findViewById(R.id.payment_layout);
        recharge_layout = (LinearLayout) findViewById(R.id.recharge_layout);
        dth_layout = (LinearLayout) findViewById(R.id.dth_layout);
        electricity_layout = (LinearLayout) findViewById(R.id.electricity_layout);
        addmoney_layout = (LinearLayout) findViewById(R.id.addmoney_layout);
        credit_layout = (LinearLayout) findViewById(R.id.credit_layout);
        passbook_layout = (LinearLayout) findViewById(R.id.passbook_layout);

        listView = (ListView) findViewById(R.id.pasbook_transaction);
        //Payment layout
        pay_id = (EditText) findViewById(R.id.pay_mobile);
        pay_amt = (EditText) findViewById(R.id.pay_amount);
        pay_remarks = (EditText) findViewById(R.id.pay_remark);
        paymentbtn = (Button) findViewById(R.id.pay_pay);
        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = pay_id.getText().toString();
                String amount = pay_amt.getText().toString();
                String remarks = pay_remarks.getText().toString();
                showDialog(mobileNo+"\n"+amount+"\n"+remarks);
            }
        });
        //AddMoney layout
        add_crdNum = (EditText) findViewById(R.id.add_cardnumber);
        add_crdName = (EditText) findViewById(R.id.add_name);
        add_amt = (EditText) findViewById(R.id.add_amount);
        addMoney = (Button) findViewById(R.id.add_money);
        bankName = (Spinner) findViewById(R.id.bankName);
        cardType = (Spinner) findViewById(R.id.cardType);
        addMoney = (Button) findViewById(R.id.add_money);
        bankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                bank_name = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, ""+bank_name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                card_type = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, ""+card_type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNum = add_crdNum.getText().toString();
                String cardAmt = add_amt.getText().toString();
                String cardname = add_crdName.getText().toString();
                showDialog(cardNum+"\n"+cardAmt);
            }
        });

        //RechargeLayout
        rech_mobile = (EditText) findViewById(R.id.rech_mobile);
        rech_amt = (EditText) findViewById(R.id.rech_amt);
        operator = (Spinner) findViewById(R.id.network_operator);
        rechargebtn = (Button) findViewById(R.id.rech_pay);
        operator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 getoperatorName = adapterView.getItemAtPosition(position).toString();
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, ""+ getoperatorName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rechargebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double mobileNo = Double.parseDouble(rech_mobile.getText().toString());
                int rechAmount = Integer.parseInt(rech_amt.getText().toString());
                showDialog(mobileNo+"\n"+rechAmount);
                Toast.makeText(MainActivity.this, mobileNo+" "+rechAmount+" "+getoperatorName, Toast.LENGTH_SHORT).show();
            }
        });

        //Electricity Layout
        elec_id = (EditText) findViewById(R.id.elec_id);
        elec_amt = (EditText) findViewById(R.id.elec_amt);
        boards = (Spinner) findViewById(R.id.board);
        elctricity_paybtn = (Button) findViewById(R.id.elec_pay);
        boards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                getBoardsName = adapterView.getItemAtPosition(position).toString();
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, ""+ getBoardsName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        elctricity_paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = elec_id.getText().toString();
                String rechAmount = elec_amt.getText().toString();
                showDialog(mobileNo+"\n"+rechAmount);
                Toast.makeText(MainActivity.this, mobileNo+" "+rechAmount+" "+getBoardsName, Toast.LENGTH_SHORT).show();
            }
        });
        //Dth Layout
        dth_id = (EditText) findViewById(R.id.dth_id);
        dth_amt = (EditText) findViewById(R.id.dth_amount);
        dthoperators = (Spinner) findViewById(R.id.dthoperator);
        dth_pay = (Button) findViewById(R.id.dth_pay);
        dthoperators.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                getDthOperator = adapterView.getItemAtPosition(position).toString();
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, ""+ getDthOperator, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dth_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = dth_id.getText().toString();
                String rechAmount = dth_amt.getText().toString();
                showDialog(mobileNo+"\n"+rechAmount);
                Toast.makeText(MainActivity.this, mobileNo+" "+rechAmount+" "+getDthOperator, Toast.LENGTH_SHORT).show();
            }
        });

        //CreditCard layout
        credit_num = (EditText) findViewById(R.id.credit_number);
        credit_nameOnCard = (EditText) findViewById(R.id.credit_name);
        credit_amt = (EditText) findViewById(R.id.credit_payamt);
        credit_pay = (Button) findViewById(R.id.credit_pay);
        credit_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = credit_num.getText().toString();
                String name = credit_nameOnCard.getText().toString();
                String amt = credit_amt.getText().toString();
                showDialog(number+"\n"+amt);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        balance = (TextView) findViewById(R.id.balance);
        int oldbalance = Integer.parseInt(balance.getText().toString());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View view = findViewById(id);
        if (id == R.id.nav_pay) {
            payToAnother(view);
            // Handle the camera action
        } else if (id == R.id.nav_addmoney) {
            addMoney(view);
        } else if (id == R.id.nav_passbook) {
            passBook(view);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //wallet layout methods
    public void payToAnother(View view){
        crossfade(payment_layout);
        passbook_layout.setVisibility(View.GONE);
        payment_layout.setVisibility(View.VISIBLE);
        addmoney_layout.setVisibility(View.GONE);
        recharge_layout.setVisibility(View.GONE);
        electricity_layout.setVisibility(View.GONE);
        dth_layout.setVisibility(View.GONE);
        credit_layout.setVisibility(View.GONE);
    }
    public void addMoney(View view){
        crossfade(addmoney_layout);
        passbook_layout.setVisibility(View.GONE);
        payment_layout.setVisibility(View.GONE);
        addmoney_layout.setVisibility(View.VISIBLE);
        recharge_layout.setVisibility(View.GONE);
        electricity_layout.setVisibility(View.GONE);
        dth_layout.setVisibility(View.GONE);
        credit_layout.setVisibility(View.GONE);
    }
    public void passBook(View view){
        crossfade(passbook_layout);
        List<String> items = db.getAllTransaction();
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        passbook_layout.setVisibility(View.VISIBLE);
        payment_layout.setVisibility(View.GONE);
        addmoney_layout.setVisibility(View.GONE);
        electricity_layout.setVisibility(View.GONE);
        dth_layout.setVisibility(View.GONE);
        credit_layout.setVisibility(View.GONE);
        recharge_layout.setVisibility(View.GONE);
    }
    //payment layout methods
    public void mobileRecharge(View view) {
        crossfade(recharge_layout);
        passbook_layout.setVisibility(View.GONE);
        payment_layout.setVisibility(View.GONE);
        addmoney_layout.setVisibility(View.GONE);
        electricity_layout.setVisibility(View.GONE);
        dth_layout.setVisibility(View.GONE);
        credit_layout.setVisibility(View.GONE);
        recharge_layout.setVisibility(View.VISIBLE);
    }

    public void electricityRecharge(View view) {
        crossfade(electricity_layout);
        passbook_layout.setVisibility(View.GONE);
        payment_layout.setVisibility(View.GONE);
        addmoney_layout.setVisibility(View.GONE);
        recharge_layout.setVisibility(View.GONE);
        dth_layout.setVisibility(View.GONE);
        credit_layout.setVisibility(View.GONE);
        electricity_layout.setVisibility(View.VISIBLE);
    }

    public void dthRecharge(View view) {
        crossfade(dth_layout);
        passbook_layout.setVisibility(View.GONE);
        payment_layout.setVisibility(View.GONE);
        addmoney_layout.setVisibility(View.GONE);
        electricity_layout.setVisibility(View.GONE);
        recharge_layout.setVisibility(View.GONE);
        credit_layout.setVisibility(View.GONE);
        dth_layout.setVisibility(View.VISIBLE);
    }

    public void creditCardPayment(View view) {
        crossfade(credit_layout);
        passbook_layout.setVisibility(View.GONE);
        payment_layout.setVisibility(View.GONE);
        addmoney_layout.setVisibility(View.GONE);
        electricity_layout.setVisibility(View.GONE);
        dth_layout.setVisibility(View.GONE);
        recharge_layout.setVisibility(View.GONE);
        credit_layout.setVisibility(View.VISIBLE);
    }

    public void animateView(final View view){
        view.animate()
                .translationY(view.getHeight())
                .alpha(1.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void crossfade(View mContentView) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mContentView.animate()
                .alpha(1f)
                .setDuration(600)
                .setListener(null);
    }

    public void showDialog(String s) {
        db.addTransaction(s);
        ConfirmDailog confirmDailog = new ConfirmDailog(this,s);
        confirmDailog.setCancelable(false);
        confirmDailog.show();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
