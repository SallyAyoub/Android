package edu.birzeit.houserentals.ui.agencyhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgencyHistoryViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AgencyHistoryViewModel () {
        mText = new MutableLiveData<>();
        mText.setValue("This is Agency History fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
