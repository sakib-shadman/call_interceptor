package com.example.instorage_caller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instorage_caller.adapter.CustomerInfoAdapter;
import com.example.instorage_caller.dialog.LoadingDialog;
import com.example.instorage_caller.retrofit.ApiClient;
import com.example.instorage_caller.retrofit.apiinterface.ApiInterface;
import com.example.instorage_caller.retrofit.model.Booking;
import com.example.instorage_caller.retrofit.model.Customer;
import com.example.instorage_caller.retrofit.model.Options;
import com.example.instorage_caller.retrofit.model.SyncInfo;
import com.example.instorage_caller.retrofit.model.SyncRequest;
import com.example.instorage_caller.retrofit.model.SyncResponse;
import com.example.instorage_caller.retrofit.repository.ServiceRepository;
import com.example.instorage_caller.roomdb.AppDatabase;
import com.example.instorage_caller.roomdb.CustomerInfo;
import com.example.instorage_caller.util.SaveInformationUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 1;

    CompositeDisposable compositeDisposable;
    ServiceRepository serviceRepository;
    LoadingDialog mLoadingDialog;
    List<Customer> mCustomer = new ArrayList<>();
    List<Customer> mNewCustomer = new ArrayList<>();
    Integer mPageNumber = 1, mLastPageNumber = 0;
    List<CustomerInfo> customerInfos = new ArrayList<>();
    private AppDatabase appDatabase;
    CustomerInfoAdapter mAdapter;
    @BindView(R.id.customerRecyclerView)
    RecyclerView customerRecyclerView;
    @BindView(R.id.btnLoadAllData)
    Button btnLoadAllData;
    @BindView(R.id.smo_loding_dialog_progress_bar)
    SmoothProgressBar mProgressBar;
    List<CustomerInfo> customerInfosToShow = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!checkPermissionGranted()) {
            askForPermission();
        } else {

        }

        init();
        initializeRecyclerView();
        if (isDataLoaded()){

            new getCustomer().execute();
        }

    }

    private void init() {
        mLoadingDialog = new LoadingDialog(this, "Loading Data..");
        compositeDisposable = new CompositeDisposable();
        serviceRepository = new ServiceRepository(ApiClient.getClient().create(ApiInterface.class));
        appDatabase = AppDatabase.getDatabase(this);

    }


    private void initializeRecyclerView() {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        customerRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomerInfoAdapter(new ArrayList<CustomerInfo>(),this);
        customerRecyclerView.setAdapter(mAdapter);
        customerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    private boolean isDataLoaded(){

        SyncInfo syncInfo = SaveInformationUtil.getSyncInfo(this);
        if(syncInfo == null){
            btnLoadAllData.setText("Load All");
            return false;
        } else if(syncInfo != null && syncInfo.getTime() != null && !syncInfo.getTime().isEmpty()){
            btnLoadAllData.setText("Reload All");
            return true;
        }
        return false;
    }

    private void showPopUp() {

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.caller_dialog, null);
        Button button = myView.findViewById(R.id.close_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wm.removeView(myView);
            }
        });

        // Add layout to window manager
        wm.addView(myView, params);
    }

    private boolean checkPermissionGranted() {

      /*  if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }*/

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

      /*  if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }*/
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
      /*  if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }*/
        return true;
    }

    private void askForPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{
                        /* Manifest.permission.ACCESS_NETWORK_STATE,
                         Manifest.permission.INTERNET,*/
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        /* Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.WRITE_EXTERNAL_STORAGE,*/
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_CALL_LOG,
                        Manifest.permission.READ_PHONE_STATE,
                        /*Manifest.permission.SYSTEM_ALERT_WINDOW,*/
                },
                MY_PERMISSIONS_REQUEST);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED
                       /* && grantResults[5] == PackageManager.PERMISSION_GRANTED
                        && grantResults[6] == PackageManager.PERMISSION_GRANTED
                        && grantResults[7] == PackageManager.PERMISSION_GRANTED
                        && grantResults[8] == PackageManager.PERMISSION_GRANTED
                        && grantResults[9] == PackageManager.PERMISSION_GRANTED*/
                ) {

                    //checkForUpdate();

                } else {
                    askForPermission();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @OnClick(R.id.btnLoadAllData)
    public void onClickSync() {

        SyncInfo syncInfo = new SyncInfo();
        syncInfo.setTime(getCurrentDateTime());
        SaveInformationUtil.saveSyncInfo(this, syncInfo);
        if(btnLoadAllData.getText().toString().contains("Reload")){
            mProgressBar.setVisibility(View.VISIBLE);
            new deleteAllFromCustomerTable().execute();
        }  else {
            mProgressBar.setVisibility(View.VISIBLE);
            getAllData(1);
        }


    }

    @OnClick(R.id.btnGetData)
    public void onCLickGetData() {

        SyncInfo syncInfo = SaveInformationUtil.getSyncInfo(this);
        if (syncInfo != null && syncInfo.getTime() != null) {
            SyncRequest syncRequest = new SyncRequest();
            syncRequest.setTime(syncInfo.getTime());
            Options options = new Options();
            options.setOnlyNew(true);
            syncRequest.setOptions(options);

            getNewData(syncRequest);
        }


        //new getCustomer().execute();


    }

    private void getNewData(SyncRequest syncRequest) {
        mLoadingDialog.showDialogWithText("Syncing data...");
        compositeDisposable.add(serviceRepository.getNewData(syncRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SyncResponse>() {
                    @Override
                    public void onSuccess(SyncResponse syncResponse) {
                        mLoadingDialog.dismiss();
                        if(syncResponse != null){
                            if(syncResponse.getData() != null && !syncResponse.getData().isEmpty()){
                                if(!mNewCustomer.isEmpty()){
                                    mNewCustomer.clear();
                                }

                                mNewCustomer.addAll(syncResponse.getData());
                                processNewData();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        mLoadingDialog.dismiss();
                    }
                })
        );
    }

    private void getAllData(Integer pageNumber) {

       // mLoadingDialog.showDialogWithText("Loading All Data");
        compositeDisposable.add(serviceRepository.getAllData(pageNumber).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SyncResponse>() {

                    @Override
                    public void onSuccess(SyncResponse syncResponses) {

                        //mLoadingDialog.dismiss();
                        if (syncResponses != null) {
                            if (syncResponses.getData() != null) {

                                if (!mCustomer.isEmpty()) {
                                    mCustomer.clear();
                                }
                                mCustomer.addAll(syncResponses.getData());
                                processData();
                            }

                            if (mPageNumber == 1) {
                                if (syncResponses.getMeta() != null) {
                                    if (syncResponses.getMeta().getLastPage() != null) {
                                        mLastPageNumber = syncResponses.getMeta().getLastPage();
                                    }
                                }
                            }

                            mPageNumber = pageNumber;
                            if (mPageNumber < mLastPageNumber) {
                                mPageNumber = mPageNumber + 1;
                                getAllData(mPageNumber);
                            } else {
                                mProgressBar.setVisibility(View.GONE);
                                btnLoadAllData.setText("Reload All");
                                new getCustomer().execute();
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        //mLoadingDialog.dismiss();
                        mProgressBar.setVisibility(View.GONE);
                    }
                })
        );
    }

    private void showDataIntoView() {

        int first = 0;
        int last = 50;
        int size = customerInfosToShow.size();
        mAdapter.updateDataSet(customerInfosToShow);



    }


    private void processData() {


        for (int i = 0; i < mCustomer.size(); i++) {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setId(mCustomer.get(i).getId());
            customerInfo.setName(mCustomer.get(i).getName());
            customerInfo.setEmail(mCustomer.get(i).getEmail());
            customerInfo.setFormattedNumber(mCustomer.get(i).getFormattedNumber());
            customerInfo.setCallCount(mCustomer.get(i).getCallCount());
            customerInfo.setAddress(mCustomer.get(i).getAddress());
            customerInfo.setPhone(mCustomer.get(i).getPhone());
            customerInfo.setRating(mCustomer.get(i).getRating());
            customerInfo.setStatus(mCustomer.get(i).getStatus());
            customerInfo.setType(mCustomer.get(i).getType());

            if (mCustomer.get(i).getBooking() != null) {
                Booking booking = new Booking();
                booking.setActive(mCustomer.get(i).getBooking().getActive());
                booking.setSpaceSize(mCustomer.get(i).getBooking().getSpaceSize());
                booking.setFloorName(mCustomer.get(i).getBooking().getFloorName());
                booking.setCancelDate(mCustomer.get(i).getBooking().getCancelDate());
                booking.setStartFrom(mCustomer.get(i).getBooking().getStartFrom());
                booking.setStorage(mCustomer.get(i).getBooking().getStorage());
                booking.setUnitName(mCustomer.get(i).getBooking().getUnitName());
                customerInfo.setBooking(booking);
            }

            customerInfos.add(customerInfo);

        }
        new insertUser().execute();
    }

    private void processNewData(){

        List<CustomerInfo> updateList = new ArrayList<>();
        List<CustomerInfo> insertList = new ArrayList<>();

        mCustomer.addAll(mNewCustomer);
        processData();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                new getCustomer().execute();
            }
        }, 2000);

    }

   /* private void insertCustomer() {

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(1);
        customerInfo.setAddress("mirpur dohs");
        customerInfo.setCallCount(0);
        customerInfo.setEmail("abc@gmail.com");
        customerInfo.setFormattedNumber("+8801779647682");
        customerInfo.setName("Sakib");
        Booking booking = new Booking();
        booking.setActive(true);
        booking.setFloorName("Lindigo");
        booking.setSpaceSize("10 kvm");
        customerInfo.setBooking(booking);
        new insertUser().execute(customerInfo);
    }*/

    private String getCurrentDateTime() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            return currentDateandTime;
        } catch (Exception ex) {
            return "";

        }

    }

    private class insertUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                appDatabase.customerDao().addAllCustomer(customerInfos);
                return null;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            //Toast.makeText (LocationService.this,"saved to room db",Toast.LENGTH_SHORT).show ();
            customerInfos.clear();
            super.onPostExecute(result);
        }


    }

    private class getCustomer extends AsyncTask<Void, Void, Void> {

        LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this,"Preparing view...");
        @Override
        protected void onPreExecute() {
            loadingDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {

            try {
                loadingDialog.dismiss();
                showDataIntoView();
                super.onPostExecute(result);
            } catch (Exception ex) {

            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                customerInfosToShow = appDatabase.customerDao().getAllCustomer();
                 Log.println(Log.INFO, "Customer List->>>>>", customerInfosToShow.size()+"");
                return null;
            } catch (Exception ex) {
                return null;
            }
        }

    }


    private class deleteAllFromCustomerTable extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {

            try {
                getAllData(1);
                super.onPostExecute(result);
            } catch (Exception ex) {

            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                appDatabase.customerDao().deleteAllData();
                return null;
            } catch (Exception ex) {
                return null;
            }
        }

    }


}
