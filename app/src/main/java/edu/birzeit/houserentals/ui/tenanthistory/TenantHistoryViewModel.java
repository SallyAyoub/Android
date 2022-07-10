package edu.birzeit.houserentals.ui.tenanthistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TenantHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TenantHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Tenant History fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}