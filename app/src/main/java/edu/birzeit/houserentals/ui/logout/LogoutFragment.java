package edu.birzeit.houserentals.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.birzeit.houserentals.MainActivity;
import edu.birzeit.houserentals.R;
import edu.birzeit.houserentals.SessionManager;
import edu.birzeit.houserentals.SignIn;
import edu.birzeit.houserentals.databinding.FragmentLogoutBinding;

import android.view.View.OnClickListener;
public class LogoutFragment extends Fragment {

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    private LogoutViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        SessionManager sessionManager = new SessionManager(getActivity());
        sessionManager.removeSession();
        Intent intent = new Intent(getActivity(), SignIn.class);
        startActivity(intent);
    }
}