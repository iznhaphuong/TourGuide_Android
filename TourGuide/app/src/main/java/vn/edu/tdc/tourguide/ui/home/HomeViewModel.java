package vn.edu.tdc.tourguide.ui.home;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> homeTitle;
    private final MutableLiveData<ImageView> homeLogo;

    public HomeViewModel() {
        homeTitle = new MutableLiveData<>();
        homeLogo = new MutableLiveData<>();

        homeTitle.setValue("This is home fragment");
//        homeLogo.setValue();
    }

    public LiveData<String> getText() {
        return homeTitle;
    }
}