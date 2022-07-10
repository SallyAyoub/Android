package edu.birzeit.houserentals.ui.viewTenant;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.databinding.TenantHistoryViewFragmentBinding;
import edu.birzeit.houserentals.databinding.ViewtenantFragmentBinding;
import edu.birzeit.houserentals.ui.edit.EditpropertiesViewModel;
import edu.birzeit.houserentals.ui.postDetails.PostDetailsFragment;
import edu.birzeit.houserentals.ui.tenantDetails.TenantDetails;
import edu.birzeit.houserentals.ui.tenantHistoryView.TenantHistoryView;

public class Viewtenant extends Fragment {

    private ViewtenantViewModel mViewModel;

    private ViewtenantFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ViewtenantViewModel.class);

        binding = ViewtenantFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataBaseHelper db = new DataBaseHelper(getActivity(), "HomeRent", null, 1);
        SessionManager sessionManager = new SessionManager(getActivity());
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        SessionManager session = new SessionManager(getActivity());
        String loggedUser = session.getSession();
        Cursor agency = db.getUser(loggedUser);
        agency.moveToNext();
        String agencyName = agency.getString(12);
        Cursor applications;
        LinearLayout main = (LinearLayout) root.findViewById(R.id.TenantmainLayout);
        LinearLayout top = (LinearLayout) root.findViewById(R.id.TenantLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        LinearLayout.LayoutParams paramsTwo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsTwo.weight = 1.0f;

        applications = db.getApplicationsByAgency(agencyName);

        while (applications.moveToNext()) {
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);
            TextView postData = new TextView(getActivity());
            postData.setText("Application ID: " + (applications.getInt(0)) + "\n" + "Tenant: " + applications.getString(3) + "" + applications.getString(4));
            postData.setLayoutParams(paramsTwo);
            postData.setGravity(Gravity.CENTER);
            postData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(postData);
            LinearLayout buttonsLayout = new LinearLayout(getActivity());
            buttonsLayout.setOrientation(LinearLayout.VERTICAL);
            buttonsLayout.setLayoutParams(params);
            Button viewButton = new Button(getActivity());
            Button historyButton = new Button(getActivity());
            Button accept = new Button(getActivity());
            Button reject = new Button(getActivity());
            reject.setText("Reject Application");
            accept.setText("Approve Application");
            accept.setTransformationMethod(null);
            reject.setTransformationMethod(null);
            viewButton.setText("View Tenant");
            historyButton.setText("View Tenant History");
            historyButton.setTransformationMethod(null);
            viewButton.setTransformationMethod(null);
            buttonsLayout.addView(viewButton);
            buttonsLayout.addView(historyButton);
            buttonsLayout.addView(accept);
            buttonsLayout.addView(reject);
            layout.addView(buttonsLayout);
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background
            border.setStroke(1, 0xFF000000); //black border with full opacity
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(border);
            } else {
                layout.setBackground(border);
            }


            viewButton.setOnClickListener(getOnClickView(applications.getString(1), top, tb));
            historyButton.setOnClickListener(getOnClickhistory(applications.getString(1), top, tb));
            accept.setOnClickListener(getOnClickAccept(applications.getString(1), applications.getInt(2), applications.getString(3), applications.getString(4), applications.getInt(0)));
            //reject.setOnClickListener(getOnClickReject(applications.getInt(0)));
            reject.setOnClickListener(getOnClickReject(applications.getString(1), applications.getInt(2), applications.getString(3), applications.getString(4), applications.getInt(0),agencyName));
            main.addView(layout);

        }


        tb.setTitle("Applications List");
        return root;
    }

    private View.OnClickListener getOnClickView(String Email, LinearLayout main, androidx.appcompat.widget.Toolbar tb) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tenant", Email);
                TenantDetails tenantDetails = new TenantDetails();
                tenantDetails.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.TenantLayout, tenantDetails, tenantDetails.getTag()).commit();

            }
        };
    }

    private View.OnClickListener getOnClickhistory(String Email, LinearLayout main, androidx.appcompat.widget.Toolbar tb) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Email", Email);
                TenantHistoryView history = new TenantHistoryView();
                history.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.TenantLayout, history, history.getTag()).commit();

            }
        };
    }

    private View.OnClickListener getOnClickAccept(String Email, int id, String firstname, String LastName, int appID) {
        return new View.OnClickListener() {

            DataBaseHelper db = new DataBaseHelper(getActivity(), "HomeRent", null, 1);

            public void onClick(View v) {
                db.insertReservation(id, Email, firstname, LastName);
                db.removeApplication(appID);

                Toast toast = Toast.makeText(getActivity(), " Rental Application Has been Approved", Toast.LENGTH_SHORT);
                toast.show();

            }
        };
    }

    private View.OnClickListener getOnClickReject(String Email, int id, String firstname, String LastName, int appID,String agnName) {
        {
            return new View.OnClickListener() {
                public void onClick(View v) {

                    DataBaseHelper db = new DataBaseHelper(getActivity(), "HomeRent", null, 1);
                    db.insertRejection(id, Email, firstname, LastName,agnName);
                    db.removeApplication(appID);
                    Toast toast = Toast.makeText(getActivity(), " Rental Application Has been Rejected", Toast.LENGTH_SHORT);
                    toast.show();

                }
            };
        }

    }
}