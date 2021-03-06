package pl.pwojcik.architecturecomponentstestproject.repository;

import android.arch.lifecycle.LiveData;

import pl.pwojcik.architecturecomponentstestproject.model.restEntity.GithubUser;

/**
 * Created by wojci on 29.01.2018.
 */

public interface GithubRestRepository {
    LiveData<GithubUser> getUser(String user);
    LiveData<GithubUser> getData();
    void deleteAll();
}
