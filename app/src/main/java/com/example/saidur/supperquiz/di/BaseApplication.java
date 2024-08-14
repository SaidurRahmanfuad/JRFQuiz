package com.example.saidur.supperquiz.di;
import android.app.Application;
import com.example.saidur.supperquiz.utils.Consts;

public class BaseApplication extends Application {

    private DaggerComponent daggerComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        daggerComponent = DaggerDaggerComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(Consts.BASE_URL))
                .build();
    }

    public DaggerComponent getDaggerComponent(){
        return daggerComponent;
    }


}
