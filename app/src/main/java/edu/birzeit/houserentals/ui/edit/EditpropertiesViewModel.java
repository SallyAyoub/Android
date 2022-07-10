package edu.birzeit.houserentals.ui.edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditpropertiesViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public EditpropertiesViewModel () {
        mText = new MutableLiveData<>();
        mText.setValue("This is Edit properties fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
