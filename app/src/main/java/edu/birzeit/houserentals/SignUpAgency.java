package edu.birzeit.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;

public class SignUpAgency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_agency);


        final EditText email = findViewById(R.id.signUpEmail);
        final EditText password = findViewById(R.id.password);
        final EditText confirmPassword = findViewById(R.id.confirmPassword);
        final EditText agencyName = findViewById(R.id.signUpAgencyName);
        final EditText phone = findViewById(R.id.phone);
        final TextView zipCode = findViewById(R.id.ZIP_Code);
        final Button signUp = findViewById(R.id.btnSignUp);

        final Spinner country = findViewById(R.id.agencyCountry);
        final Spinner city = findViewById(R.id.AgencyCity);



        String[] countryOptions = {"Palestine", "Jordan", "Turkey", "Russia", "Japan","Italy","Germany"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryOptions);
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

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = parent.getItemAtPosition(position).toString();
                ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(SignUpAgency.this, android.R.layout.simple_spinner_item, citiesOfCountry.get(itemSelected));
                city.setVisibility(View.VISIBLE);
                city.setAdapter(null);
                city.setAdapter(citiesAdapter);
                zipCode.setText(zipCodes.get(itemSelected));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city.setVisibility(View.INVISIBLE);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String emailAddress = email.getText().toString();
                String agencyNameText = agencyName.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                String phoneNumber = phone.getText().toString();
                String countryText = country.getSelectedItem().toString();
                String cityText = city.getSelectedItem().toString();

                if (emailAddress == null || agencyNameText == null ||  passwordText == null ||
                        confirmPasswordText == null || phoneNumber == null   ||countryText == null || cityText == null) {
                    Toast message = Toast.makeText(SignUpAgency.this, "You Need To Fill Up All Fields!!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }

                DataBaseHelper db = new DataBaseHelper(SignUpAgency.this, "HomeRent", null, 1);
                Cursor user = db.getUser(emailAddress);

                if (user != null && user.getCount() > 0) {
                    Toast message = Toast.makeText(SignUpAgency.this, "Email Already Exists!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                if (!emailAddress.matches(emailPattern) || emailAddress.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Email !!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!passwordText.equals(confirmPasswordText)) {
                    Toast message = Toast.makeText(SignUpAgency.this, "Passwords Doesn't Match Please Try Again", Toast.LENGTH_LONG);
                    message.show();
                    return;
                } else if (agencyNameText.length() < 3 || agencyNameText.length() > 20) {
                    Toast message = Toast.makeText(SignUpAgency.this, "Agency Name Must Be Between 3 to 20 Characters Length ", Toast.LENGTH_LONG);
                    message.show();
                    return;

                } else if (phoneNumber.length() == 0) {
                    Toast message = Toast.makeText(SignUpAgency.this, " Please Add Your Phone Number", Toast.LENGTH_LONG);
                    message.show();
                    return;
                } else if (!validatePassword(passwordText)) {
                    Toast message = Toast.makeText(SignUpAgency.this, "Password Must Be Between 8 to 15 Characters Length And Contains At least One Upper and Lower Case Character" +
                            ", One Special Character And One Number", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }

                String hashedPass = "";

                byte[] pass = passwordText.getBytes();
                BigInteger md5Data;
                try {
                    md5Data = new BigInteger(1,PasswordEnc.encryptMD5(pass));
                    hashedPass = md5Data.toString(16);
                    if (hashedPass.length() > 500)
                        hashedPass = hashedPass.substring(0, 498);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //User newUser = new User(emailAddress, hashedPass, countryText, cityText, phoneNumber,agencyNameText);
                User newUser = new User(emailAddress, null, null, null, hashedPass, countryText, cityText, phoneNumber,null,0,null,0,agencyNameText);
                db.insertUser(newUser);

                Intent intent = new Intent(SignUpAgency.this, SignIn.class);
                SignUpAgency.this.startActivity(intent);
                finish();
            }
        });
        SignUpAgency.this.setTitle(" Agency Sign up");
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
}


