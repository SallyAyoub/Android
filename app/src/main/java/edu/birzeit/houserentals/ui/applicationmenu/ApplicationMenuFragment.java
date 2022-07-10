package  edu.birzeit.houserentals.ui.applicationmenu;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import  edu.birzeit.houserentals.DataBaseHelper;
import  edu.birzeit.houserentals.R;
import  edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.ui.postDetails.PostDetailsFragment;
import edu.birzeit.houserentals.ui.search.SearchFragment;


public class ApplicationMenuFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_applicationmenu, container, false);
        DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
        final Spinner minPrice = root.findViewById(R.id.spinnerMinPrice);
        final Spinner maxPrice = root.findViewById(R.id.spinnerMaxPrice);
        final Spinner minSurface = root.findViewById(R.id.spinnerMinSurface);
        final Spinner maxSurface = root.findViewById(R.id.spinnerMaxSurface);
        final Spinner minRooms = root.findViewById(R.id.spinnerMinRooms);
        final Spinner maxRooms = root.findViewById(R.id.spinnerMaxRooms);


        final CheckBox status=root.findViewById(R.id.isfurnished);
        final CheckBox balcony =root.findViewById(R.id.hasbalcony);
        final CheckBox garden=root.findViewById(R.id.hasgarden);
        final  Spinner country = root.findViewById(R.id.SearchCountry);
        final Spinner city =  root.findViewById(R.id.SearchCity);
        Button btnSearch = (Button) root.findViewById(R.id.btnSearch);
        SessionManager sessionManager = new SessionManager(getActivity());
        Cursor user = db.getUser(sessionManager.getSession());
        user.moveToNext();
        String Email=user.getString(0);


        String[] countryOptions = {"Palestine", "Jordan", "Turkey", "Russia", "Japan","Italy","Germany"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, countryOptions);
        country.setAdapter(countryAdapter);
        Map<String, String[]> citiesOfCountry = new HashMap<>();
        //country.setSelection(findIndex(countryOptions, user.getString(5)));
        citiesOfCountry.put("Palestine", new String[]{"Jerusalem", "Nablus","Hebron"});
        citiesOfCountry.put("Jordan", new String[]{"Amman", "Zarqa", "Irbid"});
        citiesOfCountry.put("Turkey", new String[]{"Istanbul", "Izmir", "Antalya"});
        citiesOfCountry.put("Russia", new String[]{"Moscow", "Saint Petersburg", "Novosibirsk"});
        citiesOfCountry.put("Japan", new String[]{"Tokyo", "Osaka", "Nara"});
        citiesOfCountry.put("Italy", new String[]{"Rome", "Milano", "Napoli"});
        citiesOfCountry.put("Germany", new String[]{"Berlin", "Munich", "Frankfurt"});
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = parent.getItemAtPosition(position).toString();
                ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, citiesOfCountry.get(itemSelected));
                city.setVisibility(View.VISIBLE);
                city.setAdapter(null);
                city.setAdapter(citiesAdapter);
                //city.setSelection(findIndex(citiesOfCountry.get(itemSelected), user.getString(6)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city.setVisibility(View.INVISIBLE);
            }
        });
        Cursor allPropByArea = db.getArea();
        Cursor allPropByPrice = db.getPrices();
        Cursor allPropByRooms = db.getRooms();
        Bundle bundle = this.getArguments();
        Cursor posts;
        Cursor properties;




        if(bundle != null){

            posts = db.getFilteredProperty(bundle.getString("minPrice"),bundle.getString("maxPrice"),bundle.getString("minArea"),bundle.getString("maxArea"),bundle.getString("minRooms"),bundle.getString("maxRooms"),bundle.getString("city"),bundle.getString("status"),bundle.getString("garden"),bundle.getString("balcony"));

        }
        else
           posts = db.getPosts();
        if(allPropByPrice!=null && allPropByPrice.getCount() > 0){
            String [] Min = new String[allPropByPrice.getCount()+1];
            String [] Max = new String[allPropByPrice.getCount()+1];
            Min[0]= "Select Minimum Price" ;
            Max[0]= "Select Maximum Price" ;
            int j = 1;
            while(allPropByPrice.moveToNext()) {
                Min[j] = Integer.toString((int) allPropByPrice.getLong(0));
                Max[j++] = Integer.toString((int) allPropByPrice.getLong(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Min);
            minPrice.setAdapter(adapter);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Max);
            maxPrice.setAdapter(adapter1);
        }

        if(allPropByArea!=null && allPropByArea.getCount() > 0){
            String [] Min = new String[allPropByArea.getCount()+1];
            String [] Max = new String[allPropByArea.getCount()+1];
            Min[0]= "Select Minimum Surface Area" ;
            Max[0]= "Select Maximum Surface Area" ;
            int j = 1;
            while(allPropByArea.moveToNext()) {
                Min[j] = Integer.toString(allPropByArea.getInt(0));
                Max[j++] = Integer.toString(allPropByArea.getInt(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Min);
            minSurface.setAdapter(adapter);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Max);
            maxSurface.setAdapter(adapter1);
        }
        if(allPropByRooms!=null && allPropByRooms.getCount() > 0){
            String [] Min = new String[allPropByRooms.getCount()+1];
            String [] Max = new String[allPropByRooms.getCount()+1];
            Min[0]= "Select Minimum BedRooms" ;
            Max[0]= "Select Maximum BedRooms" ;
            int j = 1;
            while(allPropByRooms.moveToNext()) {
                Min[j] = Integer.toString(allPropByRooms.getInt(0));
                Max[j++] = Integer.toString(allPropByRooms.getInt(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Min);
            minRooms.setAdapter(adapter);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Max);
            maxRooms.setAdapter(adapter1);
        }

        LinearLayout mainLayout = (LinearLayout) root.findViewById(R.id.mainLayout);
        LinearLayout top = (LinearLayout) root.findViewById(R.id.Layout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        LinearLayout.LayoutParams paramsTwo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsTwo.weight = 1.0f;
        Toolbar tb = (Toolbar) getActivity().findViewById(R.id.toolbar);

        if(posts == null || posts.getCount() == 0){
            if(bundle == null){
                TextView tv = new TextView(getActivity());
                tv.setText("No Properties In The Store");
                LinearLayout.LayoutParams paramsFavorites = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(paramsFavorites);
                tv.setTextSize(28);
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                mainLayout.addView(tv);
            }
            else{
                TextView tv = new TextView(getActivity());
                tv.setText("No Properties In The Store With Those Specifications");
                LinearLayout.LayoutParams paramsFavorites = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(paramsFavorites);
                tv.setTextSize(28);
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                mainLayout.addView(tv);
            }
            return root;
        }
        if(bundle!=null)
        while(posts.moveToNext()) {
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);

            TextView postData = new TextView(getActivity());
            postData.setText("Property Number: " + (posts.getInt(0)) + "\n");
            postData.setLayoutParams(paramsTwo);
            postData.setGravity(Gravity.CENTER);
            postData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(postData);
            LinearLayout buttonsLayout = new LinearLayout(getActivity());
            buttonsLayout.setOrientation(LinearLayout.VERTICAL);
            buttonsLayout.setLayoutParams(params);

            Button ViewButton = new Button(getActivity());
            ViewButton.setText("View Property");
            ViewButton.setTransformationMethod(null);

            buttonsLayout.addView(ViewButton);

            layout.addView(buttonsLayout);
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background
            border.setStroke(1, 0xFF000000); //black border with full opacity
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(border);
            } else {
                layout.setBackground(border);
            }
            ViewButton.setOnClickListener(getOnClickView(posts.getInt(0), top, tb));
            mainLayout.addView(layout);
        }
        tb.setTitle(" Rental Application Menu");
        btnSearch.setOnClickListener(applyFilters(city,minSurface,maxSurface,minPrice,maxPrice,minRooms,maxRooms,status,garden,balcony,top));
        return root;
    }
    private View.OnClickListener getOnClickView(int id,LinearLayout main, androidx.appcompat.widget.Toolbar tb)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("postId", id);
                SearchFragment postDetails = new SearchFragment();
                postDetails.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.Layout, postDetails, postDetails.getTag()).commit();

            }
        };
    }



    private View.OnClickListener applyFilters(Spinner citySpinner, Spinner minSurfaceSpinner, Spinner maxSurfaceSpinner, Spinner minPriceSpinner, Spinner maxPriceSpinner, Spinner minRoomsSpinner, Spinner maxRoomsSpinner,CheckBox Status, CheckBox Garden, CheckBox Balcony, LinearLayout main)  {
        return new View.OnClickListener() {
            public void onClick(View v) {

                String citysearch=citySpinner.getSelectedItem().toString();
                String minSurface = minSurfaceSpinner.getSelectedItem().toString();
                String maxSurface = maxSurfaceSpinner.getSelectedItem().toString();
                String minPrice = minPriceSpinner.getSelectedItem().toString();
                String maxPrice = maxPriceSpinner.getSelectedItem().toString();
                String maxRooms=maxRoomsSpinner.getSelectedItem().toString();
                String minRooms=minRoomsSpinner.getSelectedItem().toString();
                String Status_option;
                String Garden_option;
                String Balcony_option;


                if (Status.isChecked()){
                    Status_option="true";
                }
                else{
                    Status_option="false";
                }

                if(Garden.isChecked()){
                    Garden_option="true";

                }
                else{
                    Garden_option="false";

                }

                if(Balcony.isChecked()){
                    Balcony_option="true";
                }
                else{
                    Balcony_option="false";
                }







                DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);


                if((maxSurface.equals("Select Maximum Surface Area") &&!minSurface.equals("Select Maximum Surface Area"))||(!maxSurface.equals("Select Maximum Surface Area") &&minSurface.equals("Select Minimum Surface Area"))) {
                    Toast toast = Toast.makeText(getActivity(), "Choose Both Minimum and Maximum Surface Area  ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if((maxPrice.equals("Select Maximum Price") &&!minPrice.equals("Select Minimum Price"))||(!maxPrice.equals("Select Maximum Price") &&minPrice.equals("Select Minimum Price"))) {
                    Toast toast = Toast.makeText(getActivity(), "Choose both Minimum and Maximum prices ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                if((maxRooms.equals("Select Maximum BedRooms") &&!minRooms.equals("Select Minimum BedRooms"))||(!maxRooms.equals("Select Maximum BedRooms") &&minRooms.equals("Select Minimum BedRooms"))) {
                    Toast toast = Toast.makeText(getActivity(), "Choose Both Minimum and Maximum Number of BedRooms  ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if((!maxSurface.equals("Select Maximum Surface Area")&&Long.parseLong(maxSurface) < Long.parseLong(minSurface))){
                    Toast toast = Toast.makeText(getActivity(), "Maximum Surface Area should be greater than or equal the minimum one ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if((!maxPrice.equals("Select Maximum Price") && Long.parseLong(maxPrice) < Long.parseLong(minPrice))){
                    Toast toast = Toast.makeText(getActivity(), "Maximum price should be greater than or equal the minimum one ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if((!maxRooms.equals("Select Maximum BedRooms") && Long.parseLong(maxRooms) < Long.parseLong(minRooms))){
                    Toast toast = Toast.makeText(getActivity(), "Maximum Number of Bedrooms should be greater than or equal the minimum one ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Cursor MinPrice = db.getMinMaxPrice(0);
                Cursor MaxPrice = db.getMinMaxPrice(1);
                Cursor MinArea = db.getMinMaxArea(0);
                Cursor MaxArea = db.getMinMaxArea(1);
                Cursor MinRooms = db.getMinMaxRooms(0);
                Cursor MaxRooms = db.getMinMaxRooms(1);
                if(minPrice == null || MinPrice.getCount() == 0)
                    return;
                MinPrice.moveToNext();
                MaxPrice.moveToNext();
                MinArea.moveToNext();
                MaxArea.moveToNext();
                MinRooms.moveToNext();
                MaxRooms.moveToNext();
                String priceMin = Long.toString(MinPrice.getLong(0));
                String PriceMax = Long.toString(MaxPrice.getLong(0));
                String AreaMin = Long.toString(MinArea.getLong(0));
                String AreaMax = Long.toString(MaxArea.getLong(0));
                String RoomsMin = Integer.toString(MinRooms.getInt(0));
                String RoomsMax = Integer.toString(MaxRooms.getInt(0));
                if(!minPrice.equals("Select Minimum Price")) {
                    priceMin = minPrice;
                    PriceMax = maxPrice;
                }
                if(!minSurface.equals("Select Minimum Surface Area")) {
                    AreaMin = minSurface;
                    AreaMax = maxSurface;
                }
                if(!minRooms.equals("Select Minimum BedRooms")) {
                    RoomsMin = minRooms;
                    RoomsMax = maxRooms;
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("minPrice",priceMin);
                bundle1.putString("maxPrice",PriceMax);
                bundle1.putString("minArea",AreaMin);

                bundle1.putString("maxArea",AreaMax);
                bundle1.putString("minRooms",RoomsMin);
                bundle1.putString("maxRooms",RoomsMax);
                bundle1.putString("city",citysearch);
                bundle1.putString("status",Status_option);
                bundle1.putString("garden",Garden_option);
                bundle1.putString("balcony",Balcony_option);

                ApplicationMenuFragment postMenu= new ApplicationMenuFragment();
                postMenu.setArguments(bundle1);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.Layout, postMenu, postMenu.getTag()).commit();
            }
        };
    }

}