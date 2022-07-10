package edu.birzeit.houserentals.ui.profile;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.PasswordEnc;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.User;
import edu.birzeit.houserentals.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        DataBaseHelper db = new DataBaseHelper(getActivity(), "HomeRent", null, 1);
        SessionManager sessionManager = new SessionManager(getActivity());

        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        Cursor user = db.getUser(sessionManager.getSession());
        user.moveToNext();
        String Email=user.getString(0);
        final EditText email = root.findViewById(R.id.signUpEmail);
        email.setText(Email);
        email.setEnabled(false);


        final EditText password = root.findViewById(R.id.password);
        final EditText confirmPassword = root.findViewById(R.id.confirmPassword);

        String First_name=user.getString(1);
        final TextView firstname=root.findViewById(R.id.textView8);
        final EditText FirstName = root.findViewById(R.id.signUpFirstName);
        if(First_name==null) {
            FirstName.setVisibility(View.GONE);
            firstname.setVisibility(View.GONE);
        }
        else {
            FirstName.setText(First_name);
        }
        String Last_name=user.getString(2);
        final TextView lastname=root.findViewById(R.id.textView11);
        final EditText LastName = root.findViewById(R.id.signUpLastName);
        if(Last_name==null) {
            LastName.setVisibility(View.GONE);
            lastname.setVisibility(View.GONE);
        }
        else {
            LastName.setText(Last_name);
        }




        String Phone=user.getString(7);
        final EditText phone = root.findViewById(R.id.phone);
        phone.setText(Phone);

        final TextView zipCode = root.findViewById(R.id.ZIP_Code);
        final Button update = root.findViewById(R.id.btnUpdateProfile);

        int Salary=user.getInt(9);
        final TextView salary_ten=root.findViewById(R.id.textView6);
        final EditText salary = root.findViewById(R.id.salary);
        if (Salary==0){
            salary_ten.setVisibility(View.GONE);
            salary.setVisibility(View.GONE);
        }
        else{

            salary.setText(String.valueOf(Salary));
        }



        String Occupation=user.getString(10);
        final TextView occupation_ten=root.findViewById(R.id.textView9);
        final EditText occupation = root.findViewById(R.id.occupation);
        if(Occupation==null){
            occupation_ten.setVisibility(View.GONE);
            occupation.setVisibility(View.GONE);
        }
        else{
            occupation.setText(Occupation);
        }


        int Family_size=user.getInt(11);
        final TextView famsize_ten=root.findViewById(R.id.textView10);
        final EditText familysize = root.findViewById(R.id.signUpsize);
        if(Family_size==0){
            famsize_ten.setVisibility(View.GONE);
            familysize.setVisibility(View.GONE);

        }
        else{
            familysize.setText(String.valueOf(Family_size));
        }

        final TextView natio=root.findViewById(R.id.textView4);
        final Spinner nationality = root.findViewById(R.id.nationality);
        String nat=user.getString(8);
        if ( nat==null){
            nationality.setVisibility(View.GONE);
            natio.setVisibility(View.GONE);
        }

        String gend=user.getString(3);
        final Spinner gender = root.findViewById(R.id.gender);
        final TextView gen=root.findViewById(R.id.textView12);
        if (gend==null){
            gender.setVisibility(View.GONE);
            gen.setVisibility(View.GONE);
        }


        String AgencyName=user.getString(12);
        final TextView agencyname_tv=root.findViewById(R.id.textView3);
        final EditText agencyName = root.findViewById(R.id.signUpAgencyName);
        if (AgencyName == null) {

            agencyname_tv.setVisibility(View.GONE);
            agencyName.setVisibility(View.GONE);
        }
        else if(AgencyName != null) {
            agencyName.setText(AgencyName);

        }


        final Spinner country = root.findViewById(R.id.profileCountry);
        final Spinner city = root.findViewById(R.id.ProfileCity);



        String[] genderOptions = {"Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genderOptions);
        gender.setAdapter(genderAdapter);

        String[] nationalityOptions = {"Palestinian", "Jordanian","Russian","Italian","Algerian","Canadian","Syrian"};
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nationalityOptions);
        nationality.setAdapter(nationalityAdapter);


        String[] countryOptions = {"Palestine", "Jordan", "Turkey", "Russia", "Japan","Italy","Germany"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, countryOptions);
        country.setAdapter(countryAdapter);
        Map<String, String[]> citiesOfCountry = new HashMap<>();
        Map<String, String> zipCodes = new HashMap<>();
        citiesOfCountry.put("Palestine", new String[]{"Jerusalem", "Nablus","Hebron"});
        citiesOfCountry.put("Jordan", new String[]{"Amman", "Zarqa", "Irbid"});
        citiesOfCountry.put("Turkey", new String[]{"Istanbul", "Izmir", "Antalya"});
        citiesOfCountry.put("Russia", new String[]{"Moscow", "Saint Petersburg", "Novosibirsk"});
        citiesOfCountry.put("Japan", new String[]{"Tokyo", "Osaka", "Nara"});
        citiesOfCountry.put("Italy", new String[]{"Rome", "Milano", "Napoli"});
        citiesOfCountry.put("Germany", new String[]{"Berlin", "Munich", "Frankfurt"});

        zipCodes.put("Palestine", "00970");
        zipCodes.put("Jordan", "00962");
        zipCodes.put("Turkey", "0090");
        zipCodes.put("Russia", "007");
        zipCodes.put("Japan", "0081");
        zipCodes.put("Italy", "00118");
        zipCodes.put("Germany", "10115");

        country.setSelection(findIndex(countryOptions, user.getString(5)));
        gender.setSelection(findIndex(genderOptions, user.getString(3)));
        nationality.setSelection(findIndex(nationalityOptions, user.getString(8)));
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = parent.getItemAtPosition(position).toString();
                ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, citiesOfCountry.get(itemSelected));
                city.setVisibility(View.VISIBLE);
                city.setAdapter(null);
                city.setAdapter(citiesAdapter);
                zipCode.setText(zipCodes.get(itemSelected));
                city.setSelection(findIndex(citiesOfCountry.get(itemSelected), user.getString(6)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city.setVisibility(View.INVISIBLE);
            }
        });
       // country.setEnabled(false);
      //  gender.setEnabled(false);
      //  city.setEnabled(false);
      //  nationality.setEnabled(false);


        update.setOnClickListener(new View.OnClickListener() {
            String genderText;
            String nationalityText;
            String agencyNameText;
            int salaryText;
            String occupationText;
            String firstNameText;
            String lastNameText;
            int familyText;



            @Override
            public void onClick(View v) {
                String emailAddress = email.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                String phoneNumber = phone.getText().toString();
                String countryText = country.getSelectedItem().toString();
                String cityText = city.getSelectedItem().toString();

                if(agencyName==null){
                    firstNameText = FirstName.getText().toString();
                    lastNameText = LastName.getText().toString();

                    salaryText = Integer.parseInt(salary.getText().toString());
                    familyText = Integer.parseInt(familysize.getText().toString());
                    occupationText = occupation.getText().toString();

                }
                if(gender.getSelectedItem()!=null&&nationality.getSelectedItem()!=null){
                    genderText = gender.getSelectedItem().toString();
                    nationalityText = nationality.getSelectedItem().toString();

                }




                if(agencyName!=null){
                    agencyNameText = agencyName.getText().toString();

                }


                String hashedPass = "";

                if (agencyNameText == null) {

                    if (emailAddress == null || firstNameText == null || lastNameText == null || passwordText == null ||
                            confirmPasswordText == null || phoneNumber == null || countryText == null || cityText == null || genderText == null || nationalityText == null || salaryText == 0 || occupationText == null || familyText == 0) {
                        Toast message = Toast.makeText(getActivity(), "You Need To Fill Up All Fields!!!", Toast.LENGTH_LONG);
                        message.show();
                        return;
                    }

                    if (firstNameText.length() < 3 || firstNameText.length() > 20) {
                        Toast message = Toast.makeText(getActivity(), "First Name Must Be Between 3 to 20 Characters Length ", Toast.LENGTH_LONG);
                        message.show();
                        return;
                    } else if (lastNameText.length() < 3 || lastNameText.length() > 20) {
                        Toast message = Toast.makeText(getActivity(), "Last Name Must Be Between 3 to 20 Characters Length", Toast.LENGTH_LONG);
                        message.show();
                        return;

                    } else if (occupationText.length() > 20) {
                        Toast message = Toast.makeText(getActivity(), "Occupation  Must Be At Most 20 Characters Length", Toast.LENGTH_LONG);
                        message.show();
                        return;
                    }

                }


                else if(agencyNameText != null){
                    if (emailAddress == null || agencyNameText == null || passwordText == null ||
                            confirmPasswordText == null || phoneNumber == null || countryText == null || cityText == null) {
                        Toast message = Toast.makeText(getActivity(), "You Need To Fill Up All Fields!!!", Toast.LENGTH_LONG);
                        message.show();
                        return;
                    }
                    if (agencyNameText.length() < 3 || agencyNameText.length() > 20) {
                        Toast message = Toast.makeText(getActivity(), "Agency Name Must Be Between 3 to 20 Characters Length ", Toast.LENGTH_LONG);
                        message.show();
                        return;
                    }
                }

                if (!passwordText.equals(confirmPasswordText)) {
                    Toast message = Toast.makeText(getActivity(), "Passwords Doesn't Match!!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }

                else if (phoneNumber.length() == 0) {
                    Toast message = Toast.makeText(getActivity(), " Please Add Your Phone Number", Toast.LENGTH_LONG);
                    message.show();
                    return;
                } else if (!validatePassword(passwordText)) {
                    Toast message = Toast.makeText(getActivity(), "Password Must Be Between 8 to 15 Characters Length And Contains At least One Upper and Lower Case Character" +
                            ", One Special Character And One Number", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }

                DataBaseHelper db = new DataBaseHelper(getActivity(), "HomeRent", null, 1);
                byte[] pass = passwordText.getBytes();
                BigInteger md5Data;
                try {
                    md5Data = new BigInteger(1, PasswordEnc.encryptMD5(pass));
                    hashedPass = md5Data.toString(16);
                    if (hashedPass.length() > 500)
                        hashedPass = hashedPass.substring(0, 498);
                } catch (Exception e) {
                    e.printStackTrace();
                }





                User newUser = new User(emailAddress, firstNameText, lastNameText, genderText, hashedPass, countryText, cityText, phoneNumber, nationalityText,salaryText,occupationText,familyText,agencyNameText);
                db.updateUser(newUser);


                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });







        tb.setTitle("Profile");



        return root;
    }


    private boolean validatePassword(String password) {
        if (password.length() < 8 || password.length() > 15)
            return false;
        boolean chars = false, specialChars = false, numbers = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9')
                numbers = true;
            else if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
                chars = true;
            else if (c=='$' || c=='#' || c=='%' || c=='@' || c=='{' || c== '}')
                specialChars = true;
        }
        return chars && specialChars && numbers;
    }

    private int findIndex(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target))
                return i;
        }
        return -1;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
