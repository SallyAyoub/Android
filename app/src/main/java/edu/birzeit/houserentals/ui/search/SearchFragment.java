package  edu.birzeit.houserentals.ui.search;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import  edu.birzeit.houserentals.DataBaseHelper;
import  edu.birzeit.houserentals.R;
import  edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.databinding.FragmentSearchBinding;
import edu.birzeit.houserentals.ui.applicationmenu.ApplicationMenuFragment;
import edu.birzeit.houserentals.ui.edit.EditpropertiesFragment;
import edu.birzeit.houserentals.ui.home.HomeFragment;
import edu.birzeit.houserentals.ui.postDetails.PostDetailsFragment;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;
    TextView txt6;
    TextView txt7;
    TextView txt8;
    TextView txt9;
    TextView txt10;
    TextView txt11;
    TextView txt12;

    ImageView imageView4;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayout main = (LinearLayout) root.findViewById(R.id.SearchDetails);
        Bundle bundle = this.getArguments();

        DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
        SessionManager sessionManager = new SessionManager(getActivity());
        Cursor user = db.getUser(sessionManager.getSession());
        user.moveToNext();
        String Email=user.getString(0);
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);

        int pro_id = bundle.getInt("postId");

        Cursor post=db.getPostsbyID(pro_id);
        if(post!=null || post.getCount()>0) {
            post.moveToNext();
        }

        Cursor property = db.getProperty(pro_id);

        property.moveToNext();
        txt12= (TextView) root.findViewById(R.id.searchDetailsURL);
        txt12.setText(post.getString(8));
        String photoUrl=txt12.getText().toString();
        ImageView img=root.findViewById(R.id.imgView4);

        Glide.with(SearchFragment.this).load(photoUrl).into(binding.imgView4);

        txt1 = (TextView) root.findViewById(R.id.searchDetailsCity);
        txt2 = (TextView) root.findViewById(R.id.searchDetailsAddress);
        txt3 = (TextView) root.findViewById(R.id.searchDetailsSurface);
        txt4 = (TextView) root.findViewById(R.id.searchDetailsYear);
        txt5 = (TextView) root.findViewById(R.id.searchDetailsPrice);
        txt6 = (TextView) root.findViewById(R.id.searchDetailsRooms);
        txt7 = (TextView) root.findViewById(R.id.searchDetailsStatus);
        txt8 = (TextView) root.findViewById(R.id.searchDetailsGarden);
        txt9 = (TextView) root.findViewById(R.id.searchDetailsBalcony);
        txt10 = (TextView) root.findViewById(R.id.searchDetailsDate);
        txt11 = (TextView) root.findViewById(R.id.searchDetailsDescription);




        txt1.setText(property.getString(1));
        txt2.setText(property.getString(2));
        txt3.setText(property.getString(3));
        txt4.setText(post.getString(5));
        txt5.setText(property.getString(5));
        txt6.setText(property.getString(4));

        txt7.setText(property.getString(6) );

        txt8.setText( property.getString(8));

        txt9.setText (property.getString(7));
        txt10.setText(post.getString(6));
        txt11.setText(post.getString(7));

        Button apply=root.findViewById(R.id.apply);
        Button goback=root.findViewById(R.id.goBack);
        goback.setOnClickListener(getOnClickDoSomething(pro_id,0,main,tb));
        apply.setOnClickListener(getOnClickDoSomething(pro_id,1,main,tb));

        Cursor reservations=db.getReservationsID(pro_id);
        if ( reservations!=null ||  reservations.getCount()>0){
            while(reservations.moveToNext()){
                Toast toast =Toast.makeText(getActivity(), "The Property You're Trying to Rent is already Rented",Toast.LENGTH_LONG);
                toast.show();
                apply.setVisibility(View.GONE);

            }
        }



        tb.setTitle(" Search Menu");

        return root;
    }
    private View.OnClickListener getOnClickDoSomething(int id,int flag,LinearLayout main, androidx.appcompat.widget.Toolbar tb)  {
        return new View.OnClickListener() {
            public void onClick(View v) {


                ApplicationMenuFragment postMenu = new ApplicationMenuFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();



                if(flag == 1){
                    DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
                    SessionManager sessionManager = new SessionManager(getActivity());
                    String userEmail = sessionManager.getSession();
                    Cursor agency=db.getPostsbyID(id);
                    agency.moveToNext();
                    String agencyName=agency.getString(3);
                    Cursor user = db.getUser(userEmail);
                     user.moveToNext();

                        db.insertApplication(id,userEmail,user.getString(1),user.getString(2),agencyName);
                        Toast toast =Toast.makeText(getActivity(), " Your Property Rental Application Has been Sent Successfully TO the Renting Agency",Toast.LENGTH_LONG);
                        toast.show();



                }





                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.SearchDetails, postMenu, postMenu.getTag()).commit();

            }
        };
    }



}