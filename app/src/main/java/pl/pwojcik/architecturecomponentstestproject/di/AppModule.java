package pl.pwojcik.architecturecomponentstestproject.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.pwojcik.architecturecomponentstestproject.model.persistence.AppDatabase;
import pl.pwojcik.architecturecomponentstestproject.model.persistence.GithubUserDao;

/**
 * Created by pawel on 11.02.18.
 */

@Singleton
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    AppDatabase providesAppDatabase(Context context){
        return  Room
                .databaseBuilder(context, AppDatabase.class, "githubDb")
                .build();
    }

    @Provides
    @Singleton
    Context providesContext(){
        return  context;
    }

    @Provides
    @Singleton
    GithubUserDao providesGithubUserDao(AppDatabase appDatabase){
        return appDatabase.GithubUserDbDao();
    }

}
