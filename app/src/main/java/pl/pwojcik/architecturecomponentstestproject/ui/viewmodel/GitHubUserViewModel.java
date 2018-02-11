package pl.pwojcik.architecturecomponentstestproject.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import pl.pwojcik.architecturecomponentstestproject.TemplateApplication;
import pl.pwojcik.architecturecomponentstestproject.model.persistence.GithubUserDao;
import pl.pwojcik.architecturecomponentstestproject.model.restEntity.GithubUser;
import pl.pwojcik.architecturecomponentstestproject.model.retrofit.GithubRestInterface;
import pl.pwojcik.architecturecomponentstestproject.repository.GitHubRestRepositoryImpl;
import pl.pwojcik.architecturecomponentstestproject.repository.GithubRestRepository;
import retrofit2.Retrofit;

/**
 * Created by wojci on 29.01.2018.
 */

public class GitHubUserViewModel extends AndroidViewModel {
    private GithubRestRepository githubRestRepository;
    @Inject
    GithubUserDao githubUserDao;
    @Inject
    Retrofit retrofit;


    public GitHubUserViewModel(@NonNull Application application) {
        super(application);
        ((TemplateApplication)getApplication())
                .getNetworkComponent()
                .inject(this);

        githubRestRepository = new GitHubRestRepositoryImpl(retrofit.create(GithubRestInterface.class), githubUserDao);
    }


    public LiveData<GithubUser> loadUser(String user) {
        return githubRestRepository.getUser(user);
    }

    public void deleteAll(){
        githubRestRepository.deleteAll();
    }
    public LiveData<GithubUser> getData() {
        return githubRestRepository.getData();
    }
}
