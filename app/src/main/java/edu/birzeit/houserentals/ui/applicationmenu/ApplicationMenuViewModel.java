package edu.birzeit.houserentals.ui.applicationmenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApplicationMenuViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ApplicationMenuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Agpplication Menu fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
