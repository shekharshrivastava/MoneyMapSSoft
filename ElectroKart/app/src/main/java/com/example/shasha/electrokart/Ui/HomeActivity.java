package com.example.shasha.electrokart.Ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.shasha.electrokart.Utils.DBConstants.*;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.shasha.electrokart.Database.BackupDatabase;
import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.LoginModel;
import com.example.shasha.electrokart.R;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;
import net.frederico.showtipsview.ShowTipsViewInterface;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    String NAME = "Shekhar Shrivastava";
    String EMAIL = "ershrivastavashekhar@gmail.com";
    int PROFILE = R.drawable.electro;

    private Toolbar toolbar;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;

    ActionBarDrawerToggle mDrawerToggle;
    //    private CardView spendCardView;
    private TextView mSpendAmountDisplay;
    private TextView spendAmountDisplay;
    private TextView spendingMonth;
    private TextView budgetMoney;
    private TextView budgetMonth;
    private TextView remainingCashMoney;
    //    private TextView remainingCashMonth;
    private int yy;
    private int mm;
    private int mBudget = 0;
    //    private TextView budgetExceed;
    private ArrayList<Spend> spend;
    private SwipeMenuListView latestTransactionlist;
    private SpendAdapter adapter;
    private int Budget = 0;
    private int mSpendBudget;

    private static final String menuDeleteNotes = "Delete";
    private static final String menuEditNotes = "Edit";
    private static final String menuDetailsNotes = "Details";
    private InterstitialAd interstitial;

    private GoogleApiClient client;
    private RecyclerView mTransactionList;
    private ArrayList<TotalSpendArea> displayTransaction;
    private DisplayTransactionAdapter mTransactionAdapter;
    //    private TextView showBudgetLayout;
    private int cashLeft = 0;
    private SensorManager sensorManager;
    private SensorEvent event;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CircleImageView circleView;
    //    private ImageView showSpentData;
    private FloatingActionButton fab;
    private ImageView budgetAddBtn;
    private TextView latestTransactionMoney;
    private TextView totalSpendArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Money Map");
        toolbar.setTitleTextColor(Color.BLACK);
       /* final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstDayOfMonth.set(Calendar.MINUTE, 0);
        firstDayOfMonth.set(Calendar.SECOND, 0);
        firstDayOfMonth.set(Calendar.MILLISECOND, 0);
        String firstDayOfCurMonth = String.valueOf(firstDayOfMonth.getTime());
        System.out.println("calendar ---->" + firstDayOfCurMonth);

        Calendar currentDayOfMonth = Calendar.getInstance();
        currentDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        currentDayOfMonth.set(Calendar.MINUTE, 0);
        currentDayOfMonth.set(Calendar.SECOND, 0);
        currentDayOfMonth.set(Calendar.MILLISECOND, 0);
        String curDayOfMonth = String.valueOf(currentDayOfMonth.getTime());
        System.out.println("calendar ---->" + curDayOfMonth);

        if (firstDayOfCurMonth.equals(curDayOfMonth) || currentDayOfMonth.getTime().after(firstDayOfMonth.getTime())) {
            try {
                BackupDatabase.backupDatabase();
                if (Budget != 0) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Budget != 0 || mBudget != 0) {
                    Intent intent = new Intent(HomeActivity.this, SpendActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                    alertDialogBuilder.setMessage("Please set your budget do you want to continue ?");

                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            createBudgetDialog();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }

        });
        mSpendAmountDisplay = (TextView) findViewById(R.id.spendAmountDisplay);

        String TITLES[] = {"Notification", "Settings", "Help", "Info"};
        int ICONS[] = {R.drawable.notifications_drawer, R.drawable.settings_drawer, R.drawable.help_drawer, R.drawable.info_drawer};
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new MyAdapter(TITLES, ICONS);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);
        // Setting the layout Manager

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
//        getUserThumbnail();

        initialize();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        final SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        /*if (pref.getBoolean("ACTIVITY_LAUNCH", false) == false) {
            showTips(this, showBudgetLayout, "Add Button", "This button is used to add  budget");
        }*/
    }

    public void initialize() {

        spendAmountDisplay = (TextView) findViewById(R.id.spendAmountDisplay);
        spendingMonth = (TextView) findViewById(R.id.spendingMonth);
        budgetMoney = (TextView) findViewById(R.id.budgetMoney);
        budgetMonth = (TextView) findViewById(R.id.budgetMonth);
        remainingCashMoney = (TextView) findViewById(R.id.remainingCashMoney);
//        remainingCashMonth = (TextView) findViewById(R.id.remainingCashMonth);
//        budgetExceed = (TextView) findViewById(R.id.budgetExceed);
        //  circleView = (CircleImageView) findViewById(R.id.circleView);
        budgetAddBtn = (ImageView) findViewById(R.id.budgetAddBtn);
        latestTransactionMoney = (TextView) findViewById(R.id.latestTransactionMoney);
        latestTransactionMoney.setText(getLatestTransactionValue());

        totalSpendArea = (TextView)findViewById(R.id.totalSpendArea);

//        showSpentData = (ImageView) findViewById(R.id.showSpentData);
//        showSpentData.setOnClickListener(this);
       /* TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(LoginModel.getInstance().getUserName());
        TextView userEmail = (TextView) findViewById(R.id.userEmail);
        userEmail.setText(LoginModel.getInstance().getEmailId());*/


        mTransactionList = (RecyclerView) findViewById(R.id.transactionList);
        mTransactionList.setHasFixedSize(true);
        interstitial = new InterstitialAd(HomeActivity.this);
        // Insert the Ad Unit ID
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        interstitial.setAdUnitId("ca-app-pub-123456789/123456789");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(android_id)
                .addTestDevice("63E97F66896C40EB5C4B26A739C8ADFB")
                .build();

        mAdView.loadAd(adRequest);
        interstitial.loadAd(adRequest);

        ImageView budgetAddBtn = (ImageView) findViewById(R.id.budgetAddBtn);
        budgetAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBudgetDialog();
            }
        });
        getCurrentDate();
        //Latest Transactions
        spend = new ArrayList<Spend>();
        displayTransaction = new ArrayList<TotalSpendArea>();

        latestTransactionlist = (SwipeMenuListView) findViewById(R.id.listLatestTransaction);
        latestTransactionlist.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mTransactionAdapter = new DisplayTransactionAdapter(displayTransaction);

        adapter = new SpendAdapter(this, android.R.layout.simple_list_item_1, spend);
        if (!(adapter == null))

        {
            latestTransactionlist.setVisibility(View.GONE);
            latestTransactionlist.setVisibility(View.GONE);
        } else {
            latestTransactionlist.setVisibility(View.GONE);
            latestTransactionlist.setVisibility(View.GONE);
        }
        latestTransactionlist.setAdapter(adapter);
        mTransactionList.setAdapter(mTransactionAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTransactionList.setLayoutManager(layoutManager);

//        registerForContextMenu(latestTransactionlist);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
              /*  SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setWidth(dp2px(60));
                deleteItem.setIcon(R.drawable.delete);
                menu.addMenuItem(deleteItem);*/


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background

                // set item width
                deleteItem.setWidth(dp2px(100));

                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        latestTransactionlist.setMenuCreator(creator);


        latestTransactionlist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override        // step 2. listener item click even            t

            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        delete(position);
                        break;

                }
                return false;
            }
        });

        // set SwipeListener
        latestTransactionlist.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        latestTransactionlist.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        latestTransactionlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, EditTransactionActivity.class);
                intent.putExtra("positionId", position);
                startActivity(intent);
            }


        });
        mTransactionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(mTransactionAdapter.getItemCount()>0) {
            totalSpendArea.setText("Total Spend Areas " + "(" + mTransactionAdapter.getItemCount() + ")");
        }else{
            totalSpendArea.setText("Total Spend Areas ");
        }
    }

    private void getUserThumbnail() {

        SharedPreferences userProfilePicturePref = this.getSharedPreferences("AppUserProfilePic", MODE_PRIVATE);
        String userProfilePicString = userProfilePicturePref.getString("userProfilePicture", null);
        if (!TextUtils.isEmpty(userProfilePicString)) {
            Bitmap userThumb = setThumbnail(userProfilePicString);
            circleView.setImageBitmap(userThumb);
        } else {
            Intent intent = getIntent();
            String userProfilePictureThroughFb = intent.getStringExtra("user_profile_picture");
            if (!TextUtils.isEmpty(userProfilePictureThroughFb)) {
                Bitmap userThumb = setThumbnail(userProfilePicString);
                circleView.setImageBitmap(userThumb);
            } else {
//                circleView.setImageResource(R.drawable.header_profile);
            }
        }
     /*   circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ImageFullScreenActivity.class);
                startActivity(intent);
            }
        });*/
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void getDataFromPrefrence() {
        SharedPreferences spendAmount = this.getSharedPreferences("Spend", MODE_PRIVATE);
        mSpendBudget = spendAmount.getInt("spend", 0);
        if (mSpendBudget != 0) {
            spendAmountDisplay.setText(mSpendBudget + "/-");
        } else {
            spendAmountDisplay.setText("NIL");
        }

        SharedPreferences budgetAmount = this.getSharedPreferences("budget", MODE_PRIVATE);
        Budget = budgetAmount.getInt("inputBudget", 0);
        if (Budget != 0) {
            budgetMoney.setText(Budget + "/-");
//            showBudgetLayout.setText("Add more");
        } else {
            budgetMoney.setText("Set Your Budget");
//            showBudgetLayout.setText("Add");
        }
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (!pref.getBoolean("SHOW BUDGET SWITCH", true)) {
         /*   showBudgetLayout.setSystemUiVisibility(View.GONE);
            showBudgetLayout.setVisibility(View.GONE);*/
        } else {
           /* showBudgetLayout.setSystemUiVisibility(View.VISIBLE);
            showBudgetLayout.setVisibility(View.VISIBLE);*/
        }


    }

    public void calculateRemainingCash(int budget, int spendAmount) {
        cashLeft = budget - spendAmount;
        if (!(spendAmount >= budget)) {
            remainingCashMoney.setText((cashLeft + "/-"));
//            budgetExceed.setVisibility(View.INVISIBLE);
//            budgetExceed.setVisibility(View.GONE);
        } else {
//            budgetExceed.setVisibility(View.VISIBLE);
//            budgetExceed.setVisibility(View.VISIBLE);
            remainingCashMoney.setText("0");
//            budgetExceed.setText("Exceed By  " + (mSpendBudget - Budget));
        }

    }

    public void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        mm = calendar.get(Calendar.MONTH) + 1;
        yy = calendar.get(Calendar.YEAR);
        String tempDate = "" + mm + "/" + yy;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        try {
            Date currentMonth = simpleDateFormat.parse(tempDate);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
            String getCurrentMonthAndYear = sdf.format(currentMonth);
            spendingMonth.setText(getCurrentMonthAndYear);
            budgetMonth.setText(getCurrentMonthAndYear);
//            remainingCashMonth.setText(getCurrentMonthAndYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void createBudgetDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Monthly Budget ");


        final EditText inputBudget = new EditText(HomeActivity.this);
        inputBudget.isFocusable();
        inputBudget.setHint("Rs.");
        inputBudget.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(inputBudget);
        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                mBudget = Integer.parseInt(inputBudget.getText().toString());
                if (Budget != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to add on existing budget ? ");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int addedBudget = mBudget + Budget;
                            budgetMoney = (TextView) findViewById(R.id.budgetMoney);
                            budgetMoney.setText("₹ " + addedBudget);
                            SharedPreferences preferences = getSharedPreferences("budget", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("inputBudget", addedBudget);
                            editor.commit();
                            Budget = addedBudget;
                            calculateRemainingCash(addedBudget, mSpendBudget);

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            budgetMoney.setText(inputBudget.getText().toString());
                            SharedPreferences preferences = getSharedPreferences("budget", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("inputBudget", mBudget);
                            editor.commit();
                            Budget = mBudget;
                            calculateRemainingCash(mBudget, mSpendBudget);


                        }
                    });
                    builder.show();

                } else {
                    budgetMoney.setText(inputBudget.getText().toString());
                    SharedPreferences preferences = getSharedPreferences("budget", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("inputBudget", mBudget);
                    editor.commit();
                    calculateRemainingCash(mBudget, mSpendBudget);
                }


            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
   /*     if (item.getItemId() == (R.id.menu_setting)) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);


        }
        return true;

*/
        switch (item.getItemId()) {


            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.account:
                Intent AccountSummaryIntent = new Intent(HomeActivity.this, AccountSummaryActivity.class);
                startActivity(AccountSummaryIntent);
                return true;

            // For rest of the options we just show a toast on click

            case R.id.bills:
                BackupDatabase backupDatabase = new BackupDatabase();
                try {
                    backupDatabase.backupDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.spends:
                Intent spendSummaryIntent = new Intent(HomeActivity.this, SpendSummaryActivity.class);
                startActivity(spendSummaryIntent);
                return true;

            case R.id.about:
                Intent aboutIntent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            case R.id.sms:
                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.READ_SMS)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.


                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(HomeActivity.this,
                                new String[]{Manifest.permission.READ_SMS},
                                MY_PERMISSIONS_REQUEST_READ_SMS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    Intent TransactionSmsIntent = new Intent(HomeActivity.this, TransactionBillsActivity.class);
                    startActivity(TransactionSmsIntent);
                }

                return true;

            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to Logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences
                                = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("KEY_LOGIN_STATUS", false);
                        editor.commit();

                        Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.create().show();

                LoginManager.getInstance().logOut();

                return true;

            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;

        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.spendCardView:
                createSpendTransactionDialog();
                break;*/

            case R.id.btnCancel:
                break;
           /* case R.id.showSpentData:
                Intent intent = new Intent(this, SpendSummaryActivity.class);
                startActivity(intent);
                break;
*/

        }
    }

    private void createSpendTransactionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.spend_dialog);
        dialog.setCancelable(false);
        dialog.setTitle("Transaction");
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        final EditText et_spendAmount = (EditText) dialog.findViewById(R.id.spendAmount);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button btnSet = (Button) dialog.findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spendAmount = et_spendAmount.getText().toString();
                getSpendAmout(spendAmount);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void getSpendAmout(String spendAmount) {
        mSpendAmountDisplay.setText("Rs " + spendAmount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromPrefrence();
        calculateRemainingCash(Budget, mSpendBudget);
        getWriteFromDB();
        getSpendArea();
        latestTransactionMoney.setText(getLatestTransactionValue()+"/- ");
        if(mTransactionAdapter.getItemCount()>0) {
            totalSpendArea.setText("Total Spend Areas " + "(" + mTransactionAdapter.getItemCount() + ")");
        }else{
            totalSpendArea.setText("Total Spend Areas ");
        }
//        getUserThumbnail();
    }

    public void getWriteFromDB() {
        spend.clear();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = new String[]
                {
                        COL_SPEND_ID,
                        COL_PAID_TO,
                        COL_DATE,
                        COL_NOTE,
                        COL_CATEGORY,
                        COL_AMOUNT


                };


        Cursor cursor = db.query(TABLE_SPEND, columns, null, null, null, null, null);

        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Spend w = new Spend();

                w.spend_id = cursor.getInt(cursor.getColumnIndex(COL_SPEND_ID));
                w.AmountPaid = cursor.getString(cursor.getColumnIndex(COL_AMOUNT));
                w.Date = cursor.getString(cursor.getColumnIndex(COL_DATE));
                w.Paid_To = cursor.getString(cursor.getColumnIndex(COL_PAID_TO));


                spend.add(w);

                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();

    }

    public void getSpendArea() {
        displayTransaction.clear();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

//        String whereClause = "SELECT * FROM " + TABLE_CATEGORY + " LIMIT 3";
        String[] columns = new String[]
                {
                        COL_CATEGORY_ID,
                        COL_CATEGORY_TYPE,
                        COL_TOTAL_SPEND,
                        COL_CATEGORY_IMAGE


                };


//        Cursor cursor = db.query(TABLE_CATEGORY, columns, null, null, null, null, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " ORDER BY " + COL_TOTAL_SPEND + " DESC ", null);

        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                TotalSpendArea tsa = new TotalSpendArea();

                tsa.spendAreaId = cursor.getInt(cursor.getColumnIndex(COL_CATEGORY_ID));
                tsa.totalAmount = cursor.getString(cursor.getColumnIndex(COL_TOTAL_SPEND));
                tsa.categoryArea = cursor.getString(cursor.getColumnIndex(COL_CATEGORY_TYPE));
                String getImageId = cursor.getString(cursor.getColumnIndex(COL_CATEGORY_IMAGE));
                int id = Integer.parseInt(getImageId);
                //byte[] encodeByte = Base64.decode(getImageId, Base64.DEFAULT);
                //tsa.categoryImage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                tsa.categoryImage = id;
                displayTransaction.add(tsa);

                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        mTransactionAdapter.notifyDataSetChanged();

    }


    public boolean delete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to Delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Spend w = spend.get(position);
                DBHelper helper = new DBHelper(HomeActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                SQLiteDatabase dbSpendAmount = helper.getReadableDatabase();

                String whereClause = String.format("%s = %d", COL_SPEND_ID, w.spend_id);
                db.delete(TABLE_SPEND, whereClause, null);

                Toast.makeText(HomeActivity.this, "Transaction Deleted", Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();
               /* String[] columns = new String[]
                        {
                                COL_SPEND_ID,
                                COL_AMOUNT


                        };
                Cursor cursor = dbSpendAmount.query(TABLE_SPEND, columns, whereClause, null, null, null, null);

                if (!cursor.isAfterLast()) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        Spend spend = new Spend();

                        spend.spend_id = cursor.getInt(cursor.getColumnIndex(COL_SPEND_ID));
                        spend.AmountPaid = cursor.getString(cursor.getColumnIndex(COL_AMOUNT));
                        cursor.moveToNext();
                    }
                }

                db.close();
                getWriteFromDB();
            }*/
            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();

        return true;
    }

    public Bitmap setThumbnail(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void createBudgetDialogForNewMonth() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Monthly Budget ");
        final EditText inputBudget = new EditText(HomeActivity.this);
        inputBudget.isFocusable();
        inputBudget.setHint("Rs.");
        inputBudget.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(inputBudget);
        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public void showTips(final Activity context, View v, String title, String description) {
        ShowTipsView showtips = new ShowTipsBuilder(context)
                .setTarget(v)
                .setTitle(title)
                .setDescription(description)
                .setDelay(500)
                .setBackgroundAlpha(128)
                .setCloseButtonColor(R.color.colorPrimary)
                .setCallback(new ShowTipsViewInterface() {
                    @Override
                    public void gotItClicked() {
                        ShowTipsView showTipsView = new ShowTipsBuilder(context)
//                                .setTarget(showSpentData)
                                .setTitle(getString(R.string.spend_details_btn_title))
                                .setDescription(getString(R.string.spend_details_desc))
                                .setDelay(500)
                                .setBackgroundAlpha(128)
                                .setCloseButtonColor(R.color.colorPrimary)
                                .setCallback(new ShowTipsViewInterface() {
                                    @Override
                                    public void gotItClicked() {
                                        ShowTipsView showTipsView = new ShowTipsBuilder(context)
                                                .setTarget(fab)
                                                .setTitle(getString(R.string.add_spend_button_titile))
                                                .setDescription(getString(R.string.button_spend_desc))
                                                .setDelay(500)
                                                .setBackgroundAlpha(128)
                                                .setCloseButtonColor(R.color.colorPrimary)
                                                .setCallback(new ShowTipsViewInterface() {
                                                    @Override
                                                    public void gotItClicked() {
                                                        SharedPreferences preferences
                                                                = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putBoolean("ACTIVITY_LAUNCH", true);
                                                        editor.commit();
                                                    }
                                                })
                                                .build();
                                        showTipsView.show(context);
                                    }
                                })
                                .build();
                        showTipsView.show(context);
                    }
                })

                .build();

        showtips.show(context);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent TransactionSmsIntent = new Intent(HomeActivity.this, TransactionBillsActivity.class);
                    startActivity(TransactionSmsIntent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public String getLatestTransactionValue() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String lastSpendAmount = "NA";
//        String whereClause = "SELECT * FROM " + TABLE_CATEGORY + " LIMIT 3";


//        Cursor cursor = db.query(TABLE_CATEGORY, columns, null, null, null, null, null);
        Cursor cursor = db.rawQuery("SELECT " + COL_AMOUNT + " FROM " + TABLE_SPEND + " ORDER BY " + COL_SPEND_ID + " DESC LIMIT 1", null);

        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {


                lastSpendAmount = cursor.getString(cursor.getColumnIndex(COL_AMOUNT));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return lastSpendAmount;
    }
}


