package edu.birzeit.houserentals.ui.tenantHistoryView;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.graphics.Color;
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

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.databinding.TenantHistoryViewFragmentBinding;
import edu.birzeit.houserentals.ui.viewTenant.Viewtenant;

public class TenantHistoryView extends Fragment {

    private TenantHistoryViewViewModel mViewModel;
    private TenantHistoryViewFragmentBinding binding;

    public static TenantHistoryView newInstance() {
        return new TenantHistoryView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = TenantHistoryViewFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayout main = (LinearLayout) root.findViewById(R.id.reservations_tenant_view);
        Bundle bundle = this.getArguments();

        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);

        String loggedUser = bundle.getString("Email");
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
           main.addView(tv);
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

            LinearLayout buttonsLayout = new LinearLayout(getActivity());
            buttonsLayout.setOrientation(LinearLayout.VERTICAL);
            buttonsLayout.setLayoutParams(params);
            
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background
            border.setStroke(1, 0xFF000000); //black border with full opacity
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(border);
            } else {
                layout.setBackground(border);
            }
            main.addView(layout);
        }

        Button goback = new Button(getContext());

        goback.setText("GO Back");
        main.addView(goback);

        goback.setOnClickListener(getOnClickViewBack(main));

        tb.setTitle("Tenant Reservations View");
        return root;


    }
    private View.OnClickListener getOnClickViewBack(LinearLayout main)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Viewtenant back = new  Viewtenant();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.ReservationsView,  back,  back.getTag()).commit();

            }
        };
    }






}