package edu.birzeit.houserentals.ui.postproperties;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostpropertyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PostpropertyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Post a property fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}