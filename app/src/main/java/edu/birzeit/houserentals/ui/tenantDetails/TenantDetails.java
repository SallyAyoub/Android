package edu.birzeit.houserentals.ui.tenantDetails;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.databinding.PostDetailsFragmentBinding;
import edu.birzeit.houserentals.databinding.TenantDetailsFragmentBinding;
import edu.birzeit.houserentals.ui.postDetails.PostDetailsFragment;
import edu.birzeit.houserentals.ui.viewTenant.Viewtenant;

public class TenantDetails extends Fragment {

    private TenantDetailsViewModel mViewModel;

    public static TenantDetails newInstance() {
        return new TenantDetails();
    }

    EditText txt1;
    EditText txt2;
    EditText txt3;
    EditText txt4;
    EditText txt5;
    EditText txt6;
    EditText txt7;
    EditText txt8;
    EditText txt9;
    EditText txt10;
    EditText txt11;


    private TenantDetailsFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TenantDetailsFragmentBinding.inflate(inflater, container, false);
        // View root=inflater.inflate(R.layout.post_details_fragment, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();
        String Email = bundle.getString("tenant");
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.TenantcomponentsLayout);
        Toolbar tb = (Toolbar) getActivity().findViewById(R.id.toolbar);
        DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
        Cursor user =db.getUser(Email);
        user.moveToNext();
        txt1 = (EditText) root.findViewById(R.id.email);
        txt2 = (EditText) root.findViewById(R.id.firstname);
        txt3 = (EditText) root.findViewById(R.id.lastname);
        txt4 = (EditText) root.findViewById(R.id.Tenantgender);
        txt5 = (EditText) root.findViewById(R.id.TenantNationality);
        txt6 = (EditText) root.findViewById(R.id.Tenantsalary);
        txt7 = (EditText) root.findViewById(R.id.Tenantoccupation);
        txt8 = (EditText) root.findViewById(R.id.Tenantsize);
        txt9 = (EditText) root.findViewById(R.id.TenantViewCountry);
        txt10 = (EditText) root.findViewById(R.id.TenantViewCity);
        txt11 = (EditText) root.findViewById(R.id.TenantPhone);


        txt1.setText(user.getString(0));
        txt2.setText(user.getString(1));
        txt3.setText(user.getString(2));
        txt4.setText(user.getString(3));
        txt5.setText(user.getString(8));
        txt6.setText(user.getString(9));
        txt7.setText(user.getString(10) );
        txt8.setText(user.getString(11) );
        txt9.setText(user.getString(5) );
        txt10.setText(user.getString(6) );
        txt11.setText(user.getString(7) );

        LinearLayout top = (LinearLayout) root.findViewById(R.id.TenantDetails);
        Button goback=root.findViewById(R.id.Back);
        goback.setOnClickListener(getOnClickViewBack(top));
        tb.setTitle("Tenant Details");
        return root;

    }

    private View.OnClickListener getOnClickViewBack(LinearLayout main)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
               Viewtenant back = new  Viewtenant();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.TenantDetails,  back,  back.getTag()).commit();

            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TenantDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}