package edu.birzeit.houserentals.ui.edit;

import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import edu.birzeit.houserentals.DataBaseHelper;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.databinding.FragmentEditBinding;
import edu.birzeit.houserentals.ui.postDetails.PostDetailsFragment;

public class EditpropertiesFragment extends Fragment {

        private EditpropertiesViewModel EditpropertiesViewModel;
        private FragmentEditBinding binding;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState)  {
            EditpropertiesViewModel =
                    new ViewModelProvider(this).get(EditpropertiesViewModel.class);

            binding = FragmentEditBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            DataBaseHelper db = new DataBaseHelper(getActivity(), "HomeRent", null, 1);
            SessionManager sessionManager = new SessionManager(getActivity());
            androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
            Cursor user = db.getUser(sessionManager.getSession());
            user.moveToNext();
            String Email=user.getString(0);
            String AgencyName=user.getString(12);
            Bundle bundle = this.getArguments();
            Cursor posts;
            LinearLayout mainLayout = (LinearLayout) root.findViewById(R.id.EditmainLayout);
            LinearLayout top = (LinearLayout) root.findViewById(R.id.EditLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            LinearLayout.LayoutParams paramsTwo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsTwo.weight = 1.0f;

            posts=db.getAllPosts(Email);
            


            
             while(posts.moveToNext()) {

                 String availabilityDate = posts.getString(6); // Start date
                 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                 Calendar c = Calendar.getInstance();
                 try {
                     c.setTime(Objects.requireNonNull(sdf.parse(availabilityDate)));
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }
                 c.add(Calendar.MONTH, 3);  // number of days to add
                 String availabilityDate3 = sdf.format(c.getTime());  // dt is now the new date which is a string

                 Date Date3 = new Date();
                 Date  now=new Date();;
                 try {
                     Date3 = sdf.parse(availabilityDate3);
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }


                 boolean greaterDate = (Date3.equals(now) || Date3.before(now));
                 Cursor cursor=db.checkReserved(posts.getInt(0));

                 int i=cursor.getCount();
                 boolean count=(cursor.getCount()==i);
                 boolean all=count&&greaterDate;


                 if (all&&posts.isLast()) {
                     db.removePost(posts.getInt(0));
                     db.removeProperty(posts.getInt(0));
                     break;

                 }else if (all &&  posts.moveToNext()){

                    posts.moveToNext();
                 }

                 String grt=String.valueOf(all);

                 LinearLayout layout = new LinearLayout(getActivity());
                 layout.setOrientation(LinearLayout.HORIZONTAL);
                 layout.setLayoutParams(params);
                 TextView postData = new TextView(getActivity());
                 postData.setText("Post ID: " + (posts.getInt(0)) + "\n" + "Posted At: " + posts.getString(4) + "\n" + "Post Description: " + grt/*posts.getString(7) */+ "\n");
                 postData.setLayoutParams(paramsTwo);
                 postData.setGravity(Gravity.CENTER);
                 postData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                 layout.addView(postData);
                 LinearLayout buttonsLayout = new LinearLayout(getActivity());
                 buttonsLayout.setOrientation(LinearLayout.VERTICAL);
                 buttonsLayout.setLayoutParams(params);
                 Button viewButton = new Button(getActivity());
                 viewButton.setText("View Property");
                 viewButton.setTransformationMethod(null);
                 buttonsLayout.addView(viewButton);
                 layout.addView(buttonsLayout);
                 GradientDrawable border = new GradientDrawable();
                 border.setColor(0xFFFFFFFF); //white background
                 border.setStroke(1, 0xFF000000); //black border with full opacity
                 if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                     layout.setBackgroundDrawable(border);
                 } else {
                     layout.setBackground(border);
                 }

                 viewButton.setOnClickListener(getOnClickView(posts.getInt(0), top, tb));
                 mainLayout.addView(layout);
             }
            tb.setTitle("Edit Properties");
            return root;
        }

    private View.OnClickListener getOnClickView(int id,LinearLayout main, androidx.appcompat.widget.Toolbar tb)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("postId", id);
               PostDetailsFragment postDetails = new PostDetailsFragment();
                postDetails.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                main.removeAllViews();
                fragmentManager.beginTransaction().add(R.id.EditLayout,  postDetails,  postDetails.getTag()).commit();

            }
        };
    }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
