package pl.pwojcik.architecturecomponentstestproject;

import android.app.Application;

import pl.pwojcik.architecturecomponentstestproject.di.AppModule;
import pl.pwojcik.architecturecomponentstestproject.di.DaggerNetworkComponent;
import pl.pwojcik.architecturecomponentstestproject.di.NetworkComponent;
import pl.pwojcik.architecturecomponentstestproject.di.NetworkModule;


/**
 * Created by pawel on 01.02.18.
 */

public class TemplateApplication extends Application {

    NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule())
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public NetworkComponent getNetworkComponent(){
        return networkComponent;
    }

}
