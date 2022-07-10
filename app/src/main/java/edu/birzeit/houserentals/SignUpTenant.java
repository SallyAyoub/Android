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

public class SignUpTenant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tenant);

        final EditText email = findViewById(R.id.signUpEmail);
        final EditText password = findViewById(R.id.password);
        final EditText confirmPassword = findViewById(R.id.confirmPassword);
        final EditText firstName = findViewById(R.id.signUpFirstName);
        final EditText lastName = findViewById(R.id.signUpLastName);
        final EditText salary = findViewById(R.id.salary);
        final EditText occupation = findViewById(R.id.occupation);
        final EditText familySize = findViewById(R.id.signUpsize);
        final EditText phone = findViewById(R.id.phone);
        final TextView zipCode = findViewById(R.id.ZIP_Code);
        final Button signUp = findViewById(R.id.btnSignUp);

        final Spinner nationality = findViewById(R.id.nationality);
        final Spinner gender = findViewById(R.id.gender);
        final Spinner country = findViewById(R.id.tenantCountry);
        final Spinner city = findViewById(R.id.tenantCity);

        String[] genderOptions = {"Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        gender.setAdapter(genderAdapter);

        String[] nationalityOptions = {"Palestinian", "Jordanian","Russian","Italian","Algerian","Canadian","Syrian"};
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nationalityOptions);
        nationality.setAdapter(nationalityAdapter);



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
                ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(SignUpTenant.this, android.R.layout.simple_spinner_item, citiesOfCountry.get(itemSelected));
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
                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                String phoneNumber = phone.getText().toString();
                String occupationText = occupation.getText().toString();
                String salaryText=salary.getText().toString();
                String size=familySize.getText().toString();
                String naionalityText=nationality.getSelectedItem().toString();
                String countryText = country.getSelectedItem().toString();
                String cityText = city.getSelectedItem().toString();
                String genderText = gender.getSelectedItem().toString();

                if (emailAddress == null || firstNameText == null || lastNameText == null || passwordText == null ||
                        confirmPasswordText == null || phoneNumber == null || occupationText == null || salaryText == null || size == null || naionalityText == null  ||countryText == null || cityText == null || genderText == null) {
                    Toast message = Toast.makeText(SignUpTenant.this, "You Need To Fill Up All Fields!!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }

                DataBaseHelper db = new DataBaseHelper(SignUpTenant.this, "HomeRent", null, 1);
                Cursor user = db.getUser(emailAddress);

                if (user != null && user.getCount() > 0) {
                    Toast message = Toast.makeText(SignUpTenant.this, "Email Already Exists!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                if (!emailAddress.matches(emailPattern) || emailAddress.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Email !!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!passwordText.equals(confirmPasswordText)) {
                    Toast message = Toast.makeText(SignUpTenant.this, "Passwords Doesn't Match Please Try Again", Toast.LENGTH_LONG);
                    message.show();
                    return;
                } else if (firstNameText.length() < 3 || firstNameText.length() > 20) {
                    Toast message = Toast.makeText(SignUpTenant.this, "First Name Must Be Between 3 to 20 Characters Length ", Toast.LENGTH_LONG);
                    message.show();
                    return;
                } else if (lastNameText.length() < 3 || lastNameText.length() > 20) {
                    Toast message = Toast.makeText(SignUpTenant.this, "Last Name Must Be Between 3 to 20 Characters Length", Toast.LENGTH_LONG);
                    message.show();
                    return;

                } else if (occupationText.length() > 20 ) {
                    Toast message = Toast.makeText(SignUpTenant.this, "Occupation  Must Be At Most 20 Characters Length", Toast.LENGTH_LONG);
                    message.show();
                    return;

                } else if (phoneNumber.length() == 0) {
                    Toast message = Toast.makeText(SignUpTenant.this, " Please Add Your Phone Number", Toast.LENGTH_LONG);
                    message.show();
                    return;
                } else if (!validatePassword(passwordText)) {
                    Toast message = Toast.makeText(SignUpTenant.this, "Password Must Be Between 8 to 15 Characters Length And Contains At least One Upper and Lower Case Character" +
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
               int  intsize=Integer.parseInt(size);
                int intsalary=Integer.parseInt(salaryText);
                User newUser = new User(emailAddress, firstNameText, lastNameText, genderText, hashedPass, countryText, cityText, phoneNumber,naionalityText,intsalary,occupationText,intsize,null);
                db.insertUser(newUser);

                Intent intent = new Intent(SignUpTenant.this, SignIn.class);
                SignUpTenant.this.startActivity(intent);
                finish();
            }
        });
        SignUpTenant.this.setTitle(" Tenant Sign up");
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
