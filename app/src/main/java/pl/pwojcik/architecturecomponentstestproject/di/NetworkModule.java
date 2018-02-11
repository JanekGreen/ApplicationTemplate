package pl.pwojcik.architecturecomponentstestproject.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.pwojcik.architecturecomponentstestproject.utils.UrlUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pawel on 11.02.18.
 */

@Module
public class NetworkModule {

    public NetworkModule() { }

    @Singleton
    @Provides
    public Retrofit providesRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
