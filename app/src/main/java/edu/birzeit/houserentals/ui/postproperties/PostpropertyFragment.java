package edu.birzeit.houserentals.ui.postproperties;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.Property;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.databinding.FragmentPostpropertiesBinding;
import edu.birzeit.houserentals.ui.home.HomeFragment;

public class PostpropertyFragment extends Fragment {
    private PostpropertyViewModel postpropertyViewModel;
    private FragmentPostpropertiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        postpropertyViewModel =
                new ViewModelProvider(this).get(PostpropertyViewModel.class);

        binding = FragmentPostpropertiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        DataBaseHelper db = new DataBaseHelper(getActivity(), "HomeRent", null, 1);
        SessionManager sessionManager = new SessionManager(getActivity());
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        Cursor user = db.getUser(sessionManager.getSession());
        user.moveToNext();
        String Email = user.getString(0);
        String AgencyName = user.getString(12);


        final EditText city = root.findViewById(R.id.city);
        final EditText postalAddress = root.findViewById(R.id.postaladdress);
        final EditText surfaceArea = root.findViewById(R.id.propertySurfaceArea);
        final EditText constructionYear = root.findViewById(R.id.constructionYear);
        final EditText number_rooms = root.findViewById(R.id.rooms);
        final EditText rentalPrice = root.findViewById(R.id.postprice);
        final EditText url = root.findViewById(R.id.url);
        final EditText status = root.findViewById(R.id.statuspost);
        //final Spinner status = root.findViewById(R.id.poststatus);
        final EditText date = root.findViewById(R.id.date);
        final EditText description = root.findViewById(R.id.postdescription);
        //final Spinner garden = root.findViewById(R.id.garden);
        final EditText garden = root.findViewById(R.id.postgarden);
       // final Spinner balcony = root.findViewById(R.id.balcony);
        final EditText balcony = root.findViewById(R.id.balconypost);
        final Button postButton = root.findViewById(R.id.post);
        final ImageView imageView2 = root.findViewById(R.id.imgView2);
        String[] balconyOptions = {"True", "False"};
        ArrayAdapter<String> balconyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, balconyOptions);
        //balcony.setAdapter(balconyAdapter);

        String[] gardenOptions = {"True", "False"};
        ArrayAdapter<String> gardenAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, gardenOptions);
        //garden.setAdapter(gardenAdapter);

        String[] statusOptions = {"Furnished", " NotFurnished"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, statusOptions);
        //status.setAdapter(statusAdapter);


        postButton.setOnClickListener(new View.OnClickListener() {
            String Pro_city;
            String Pro_address;
            Long Pro_surfaceArea;
            int Pro_constructionYear;
            int Pro_rooms;
            Long Pro_price;
            String Pro_status;
            String Pro_description;
            String Pro_garden;
            String Pro_balcony;
            String Pro_Photo;


            @Override
            public void onClick(View view) {
                if (city.getText().toString() == null || postalAddress.getText().toString() == null ||
                        surfaceArea.getText().toString() == null || constructionYear.getText().toString() == null ||
                        number_rooms.getText().toString() == null || rentalPrice.getText().toString() == null ||
                        description.getText().toString() == null || url.getText().toString() == null) {
                    Toast message = Toast.makeText(getActivity(), "You Need To Fill Up  All Property Details", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                Pro_city = city.getText().toString();
                Pro_address = postalAddress.getText().toString();
                Pro_surfaceArea = Long.parseLong(surfaceArea.getText().toString());
                Pro_constructionYear = Integer.parseInt(constructionYear.getText().toString());
                Pro_rooms = Integer.parseInt(number_rooms.getText().toString());
                Pro_price = Long.parseLong(rentalPrice.getText().toString());
                Pro_description = description.getText().toString();
                //Pro_status = status.getSelectedItem().toString();
                Pro_status = status.getText().toString();
                //Pro_garden = Boolean.parseBoolean(garden.getSelectedItem().toString());
                Pro_garden = garden.getText().toString();
               // Pro_balcony = Boolean.parseBoolean(balcony.getSelectedItem().toString());
                Pro_balcony = balcony.getText().toString();
                String pro_date = date.getText().toString();
                Pro_Photo = url.getText().toString();
                Glide.with(PostpropertyFragment.this).load(Pro_Photo.trim()).into(imageView2);
                Property property = new Property(Pro_city, Pro_address, Pro_surfaceArea, Pro_rooms, Pro_price, Pro_status, Pro_balcony, Pro_garden);
                db.insertProperty(property);
                Cursor maxId = db.getMaxIDRproperties();
                maxId.moveToNext();
                int pro_id = maxId.getInt(0);
                db.insertPosts(pro_id, Email, Pro_description, Pro_constructionYear, pro_date, AgencyName, Pro_Photo);
                db.close();
            }

        });

        tb.setTitle("Post Property");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
