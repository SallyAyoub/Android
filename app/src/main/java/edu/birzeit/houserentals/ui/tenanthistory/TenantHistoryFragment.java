package edu.birzeit.houserentals.ui.tenanthistory;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.databinding.FragmentTenanthistoryBinding;
public class TenantHistoryFragment extends Fragment {

    private TenantHistoryViewModel tenantHistoryViewModel;
    private FragmentTenanthistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tenanthistory, container, false);
        final LinearLayout vertical = root.findViewById(R.id.reservations_tenant);
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        SessionManager session = new SessionManager(getActivity());
        String loggedUser = session.getSession();
        DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
        Cursor tenantReservations = db.getReservations(loggedUser);
        if(tenantReservations == null || tenantReservations.getCount() == 0){
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
        params.weight = 1.0f;
        LinearLayout.LayoutParams paramsTwo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsTwo.weight = 1.0f;

        while(tenantReservations.moveToNext()){
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);
            TextView propertyData1 = new TextView(getActivity());
            propertyData1.setText("City\n" + (tenantReservations.getString(1)));
            propertyData1.setLayoutParams(paramsTwo);
            propertyData1.setGravity(Gravity.CENTER);
            propertyData1.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            layout.addView(propertyData1);


            TextView propertyData2 = new TextView(getActivity());
            propertyData2.setText ("PostalAddress\n"+tenantReservations.getString(2));
            propertyData2.setLayoutParams(paramsTwo);
            propertyData2.setGravity(Gravity.CENTER);
            propertyData2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            layout.addView(propertyData2);

            TextView propertyData3 = new TextView(getActivity());
            propertyData3.setText ("  Posted by\n"+tenantReservations.getString(3));
            propertyData3.setLayoutParams(paramsTwo);
            propertyData3.setGravity(Gravity.CENTER);
            propertyData3.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            layout.addView(propertyData3);


            TextView dateAndTime = new TextView(getActivity());
            dateAndTime.setText("Reserved At :\n"+tenantReservations.getString(4));
            dateAndTime.setGravity(Gravity.CENTER);
            dateAndTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(dateAndTime);
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background
            border.setStroke(1, 0xFF000000); //black border with full opacity
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(border);
            } else {
                layout.setBackground(border);
            }
            vertical.addView(layout);
        }

        tb.setTitle("Tenant Reservations");
        return root;
    }

}