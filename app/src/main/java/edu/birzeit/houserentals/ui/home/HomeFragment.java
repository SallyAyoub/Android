package edu.birzeit.houserentals.ui.home;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.databinding.FragmentHomeBinding;
import edu.birzeit.houserentals.ui.viewTenant.Viewtenant;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static final int NOTIFICATION_ID = 123;
    private static final String NOTIFICATION_TITLE = " Rental Application Status";
    private static final String NOTIFICATION_BODY_APPROVE = "Your Rental Application of The Property Has Been Approved";
    private static final String NOTIFICATION_BODY_Reject = "Your Rental Application of The Property Has Been Rejected";
    private static final String MY_CHANNEL_ID = "my_chanel_1";
    private static final String MY_CHANNEL_NAME = "My channel";
    StatusBarNotification[] statusNotifs = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
        SessionManager sessionManager = new SessionManager(getActivity());
        String userEmail = sessionManager.getSession();
        Cursor user= db.getUser(userEmail);
        user.moveToNext();
        String agencyName=user.getString(12);
        String tenantFirstName=user.getString(1);
        Cursor applications=null;
        Cursor reservations=null;
        Cursor rejections=null;
        StatusBarNotification[] statusNotifs = null;
        try {
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            statusNotifs = notificationManager.getActiveNotifications();
        } catch (Throwable t) {
            // it can crash
        }
        if (statusNotifs != null)
            for (StatusBarNotification n : statusNotifs) {
                try {
                    if (n.getId()==NOTIFICATION_ID){
                        cancelNotification(NOTIFICATION_ID);
                    }

                    // do something
                } catch (Throwable t) {
                    // can crash
                }
            }

        LinearLayout main = (LinearLayout) root.findViewById(R.id.Home_fragment);


        if(agencyName!=null){
           applications = db.getApplicationsByAgency(agencyName);

        }

        if(tenantFirstName!=null){
            reservations=db.getReservationsOfTenant(userEmail);
            rejections=db.getRejectionsOfTenant(userEmail);
        }



        final LinearLayout vertical = root.findViewById(R.id.Home_Posts);
        Toolbar tb = (Toolbar) getActivity().findViewById(R.id.toolbar);
        Cursor recentPosts=db.getRecentPosts();
        if(recentPosts == null || recentPosts.getCount() == 0){
            TextView tv = new TextView(getActivity());
            tv.setText("No Rented Properties Yet");
            LinearLayout.LayoutParams paramsReserved = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(paramsReserved);
            tv.setTextSize(28);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            vertical.addView(tv);
            return root;
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //    params.weight = 1.0f;
        LinearLayout.LayoutParams paramsTwo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsTwo.weight = 1.0f;
        int i = 1;
        while(recentPosts.moveToNext()){

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView propertyData = new TextView(getActivity());
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(params);
            propertyData.setText("Property: "+i +"  "+"Posted By : "+" " +recentPosts.getString(3) +" "+"\nAvailable at"+"  "+(recentPosts.getString(6)+"\n It is a "+" "+(recentPosts.getString(7))));
            Glide.with(HomeFragment.this).load(recentPosts.getString(8).trim())
                    .error(R.drawable.home).into(imageView);
            propertyData.setLayoutParams(paramsTwo);
            propertyData.setGravity(Gravity.CENTER);

            propertyData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(imageView);
            layout.addView(propertyData);


            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(border);
            } else {
                layout.setBackground(border);
            }
            vertical.addView(layout);
            ++i;
        }


        Button checkApplications=new Button(getActivity());
        LinearLayout buttonsLayout = new LinearLayout(getActivity());
        buttonsLayout.setOrientation(LinearLayout.VERTICAL);
        buttonsLayout.setLayoutParams(params);
        checkApplications.setTransformationMethod(null);


        if(agencyName!=null) {
            checkApplications.setText("Check Tenant's Rental Applications");
            buttonsLayout.addView(checkApplications);
            vertical.addView(buttonsLayout);



            if (applications != null || applications.getCount() > 0) {
                while (  applications.moveToNext()){
                    String message = "The Tenant"+" " + applications.getString(3) + " " + applications.getString(4) +" "+"Wants to Rent a Property";
                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                    toast.show();

                }
            }
        }


        if(tenantFirstName!=null){

            if(reservations!=null || reservations.getCount()>0) {
                while (reservations.moveToNext()) {
                    String status = reservations.getString(6);
                    if (status.equals("false")) {
                        createNotification(NOTIFICATION_TITLE, NOTIFICATION_BODY_APPROVE);
                        db.updateReservations(userEmail);
                    }
                }
            }
             if (rejections!=null || rejections.getCount()>0){
                while (rejections.moveToNext()) {
                    String status = rejections.getString(6);
                    if (status.equals("false")) {
                        createNotification(NOTIFICATION_TITLE, NOTIFICATION_BODY_Reject);
                        db.updateRejections(userEmail);
                    }
                }

            }

        }



        checkApplications.setOnClickListener(getOnClickCheck(main, tb));
        return root;
    }

        private View.OnClickListener getOnClickCheck(LinearLayout main, androidx.appcompat.widget.Toolbar tb)  {
        return new View.OnClickListener() {
            public void onClick(View v) {

                   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                   Viewtenant Frag = new Viewtenant();
                   main.removeAllViews();
                   fragmentManager.beginTransaction().add(R.id.Home_fragment, Frag, Frag.getTag()).commit();


            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID,
                MY_CHANNEL_NAME, importance);
        NotificationManager notification = ( NotificationManager ) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
        if (notification != null) {
            notification.createNotificationChannel(channel);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotification(String title, String body) {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder( getActivity(), MY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from( getActivity());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void cancelNotification(int id){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from( getActivity());
        notificationManager.cancel(id);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}