package edu.birzeit.houserentals.ui.postDetails;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.Property;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.ui.edit.EditpropertiesFragment;

import edu.birzeit.houserentals.databinding.PostDetailsFragmentBinding;

public class PostDetailsFragment extends Fragment {

    private PostDetailsViewModel mViewModel;

    public PostDetailsFragment() {
    }

    public static PostDetailsFragment newInstance() {
        return new PostDetailsFragment();
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
    EditText txt12;
    TextView url;
    ImageView imageView3;
    private PostDetailsFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PostDetailsFragmentBinding.inflate(inflater, container, false);
     // View root=inflater.inflate(R.layout.post_details_fragment, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();
        int postId = bundle.getInt("postId");
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.componentsLayout);
        Toolbar tb = (Toolbar) getActivity().findViewById(R.id.toolbar);
        DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
        Cursor propertyID =db.propertyPostID(postId);
        propertyID.moveToNext();
        int pro_id=propertyID.getInt(0);
        Cursor property = db.getProperty(pro_id);

         Cursor post=db.getPost(postId);
         post.moveToNext();
         property.moveToNext();
         imageView3 = root.findViewById(R.id.imgView3);
         txt1 = (EditText) root.findViewById(R.id.propertycity);
         txt2 = (EditText) root.findViewById(R.id.propertyAddress);
         txt3 = (EditText) root.findViewById(R.id.propertyArea);
         txt4 = (EditText) root.findViewById(R.id.propertyYear);
         txt5 = (EditText) root.findViewById(R.id.propertyPrice);
         txt6 = (EditText) root.findViewById(R.id.propertyRooms);
         txt7 = (EditText) root.findViewById(R.id.propertyStatus);
         txt8 = (EditText) root.findViewById(R.id.propertyGarden);
         txt9 = (EditText) root.findViewById(R.id.propertyBalcony);
         txt10 = (EditText) root.findViewById(R.id.propertyDate);
         txt11 = (EditText) root.findViewById(R.id.propertyDescription);
         txt12= (EditText) root.findViewById(R.id.propertyURL);

         txt1.setText(property.getString(1));
         txt2.setText(property.getString(2));
         txt3.setText(property.getString(3));
         txt4.setText(post.getString(5));
         txt5.setText(property.getString(5));
         txt6.setText(property.getString(4));
         txt7.setText(property.getString(6) );
         txt8.setText( property.getString(8) );
         txt9.setText(  property.getString(7));

         txt10.setText(post.getString(6));
         txt11.setText(post.getString(7));
         String photoUrl=post.getString(8);

         Button delete = (Button) root.findViewById(R.id.remove_post);
         Button  edit = (Button) root.findViewById(R.id.edit_post);
         LinearLayout main = (LinearLayout) root.findViewById(R.id.PostDetails);
         delete.setOnClickListener(getOnClickDoSomething(main,0,postId,tb));
         edit.setOnClickListener(getOnClickDoSomething(main,1,postId,tb));
         Glide.with(PostDetailsFragment.this).load(photoUrl.trim()).into( imageView3);
         tb.setTitle("property Details");
        return root;
    }

    private View.OnClickListener getOnClickDoSomething(LinearLayout main,int flag,int id,androidx.appcompat.widget.Toolbar tb)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
              EditpropertiesFragment postMenu = new EditpropertiesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
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
                String pro_date;
                String Photo_Url;

                if(flag == 1){
                    DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
                    SessionManager sessionManager = new SessionManager(getActivity());
                    Cursor user = db.getUser(sessionManager.getSession());
                    user.moveToNext();
                    String Email=user.getString(0);
                    String AgencyName=user.getString(12);
                    Pro_city = txt1.getText().toString();
                    Pro_address = txt2.getText().toString();
                    Pro_surfaceArea = Long.parseLong(txt3.getText().toString());
                    Pro_constructionYear = Integer.parseInt(txt4.getText().toString());
                    Pro_rooms = Integer.parseInt(txt6.getText().toString());
                    Pro_price = Long.parseLong(txt5.getText().toString());
                    Pro_description = txt11.getText().toString();

                    Pro_status = txt7.getText().toString();
                    Pro_garden = txt8.getText().toString();
                    Pro_balcony =txt9.getText().toString();
                    pro_date = txt10.getText().toString();
                    Photo_Url=txt12.getText().toString();
                    Glide.with(PostDetailsFragment.this).load(Photo_Url.trim()).into(imageView3);


                    Property property = new Property(Pro_city, Pro_address, Pro_surfaceArea, Pro_rooms, Pro_price, Pro_status, Pro_balcony, Pro_garden);

                   Cursor proID =db.propertyPostID(id);
                   proID.moveToNext();
                   int pro_id=proID.getInt(0);

                    db.updatePosts(id,pro_id,Email, Pro_description, Pro_constructionYear, pro_date, AgencyName,Photo_Url);
                    db.updateProperty(property,pro_id);

                    db.close();








                    Toast toast =Toast.makeText(getActivity(), " Property Updated Successfully",Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (flag==0){
                    DataBaseHelper db = new DataBaseHelper(getActivity(),"HomeRent", null,1);
                    Cursor proID =db.propertyPostID(id);
                    proID.moveToNext();
                    int pro_id=proID.getInt(0);
                    db.removePost(id);
                    db.removeProperty(pro_id);

                    Toast toast =Toast.makeText(getActivity(), " Property Deleted Successfully",Toast.LENGTH_SHORT);
                    toast.show();

                }
               main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.PostDetails, postMenu, postMenu.getTag()).commit();
                tb.setTitle("Post Menu");
            }
        };
    }


   

    }


