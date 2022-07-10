package edu.birzeit.houserentals.ui.agencyhistory;

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
import edu.birzeit.houserentals.databinding.FragmentAgencyhistoryBinding;
public class AgencyHistoryFragment extends Fragment {

    private AgencyHistoryFragment agencyHistoryViewmodel;
    private FragmentAgencyhistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_agencyhistory, container, false);
        final LinearLayout vertical = root.findViewById(R.id.reservations_agency);
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        SessionManager session = new SessionManager(getActivity());
        String loggedUser = session.getSession();
        DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
        Cursor agency= db.getUser(loggedUser);
        agency.moveToNext();
        String agencyName=agency.getString(12);
        //Cursor agencyReservations = db.getApplicationsByAgency(agencyName);
        Cursor agencyReservations = db.getPostsby(loggedUser);
        if(agencyReservations == null || agencyReservations.getCount() == 0){
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

        while(agencyReservations.moveToNext()){
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);
            TextView propertyData = new TextView(getActivity());

            //propertyData.setText("Posted at : " +agencyReservations.getString(4) +" "+"constructed at"+"  "+(agencyReservations.getString(6)));//+"available at"+(agencyReservations.getString(6)));
            propertyData.setText("City\n " + (agencyReservations.getString(1)));
            propertyData.setLayoutParams(paramsTwo);
            propertyData.setGravity(Gravity.CENTER);
            propertyData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(propertyData);

            TextView propertyData2 = new TextView(getActivity());
            propertyData2.setText ("Address\n"+agencyReservations.getString(2));
            propertyData2.setLayoutParams(paramsTwo);
            propertyData2.setGravity(Gravity.CENTER);
            propertyData2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            layout.addView(propertyData2);

            TextView propertyData3 = new TextView(getActivity());
            propertyData3.setText ("    Rented by\n"+agencyReservations.getString(3)+""+agencyReservations.getString(4));
            propertyData3.setLayoutParams(paramsTwo);
            propertyData3.setGravity(Gravity.CENTER);
            propertyData3.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            layout.addView(propertyData3);


             TextView dateAndTime = new TextView(getActivity());
             dateAndTime.setText("Reserved At :\n"+agencyReservations.getString(5));
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

        tb.setTitle("Agency Reservations");
        return root;
    }

}