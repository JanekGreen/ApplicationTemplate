package pwojcik.pl.archcomponentstestproject.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import pwojcik.pl.archcomponentstestproject.model.TypesConverter;
import pwojcik.pl.archcomponentstestproject.model.dbEntity.AppDatabase;
import pwojcik.pl.archcomponentstestproject.model.dbEntity.GithubUserDao;
import pwojcik.pl.archcomponentstestproject.model.dbEntity.GithubUserDb;
import pwojcik.pl.archcomponentstestproject.model.restObject.GithubUser;
import pwojcik.pl.archcomponentstestproject.model.retrofit.GithubRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wojci on 29.01.2018.
 */

public class GitHubRestRepositoryImpl implements GithubRestRepository {
    private GithubRestInterface githubRestInterface;
    private MutableLiveData<GithubUser> data;
    private GithubUserDao githubUserDao;
    private ExecutorService executor;



    public GitHubRestRepositoryImpl(GithubRestInterface githubRestInterface, GithubUserDao githubUserDao, ExecutorService executor) {
        this.githubRestInterface = githubRestInterface;
        this.githubUserDao = githubUserDao;
        this.executor = executor;
        data = new MutableLiveData<>();
    }

    @Override
    public LiveData<GithubUser> getUser(final String user) {

       Future<GithubUserDb> githubUserDbFuture = executor.submit(new Callable<GithubUserDb>() {
            @Override
            public GithubUserDb call() throws Exception {
              return   githubUserDao.getUserByLogin(user);
            }
        });
        GithubUserDb githubUserDb = null;
        try {

            githubUserDb = githubUserDbFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (githubUserDb != null) {
            System.out.println("Found user " + user + "in database!");
            data.setValue(TypesConverter.makeGithubUser(githubUserDb));
        } else {
            Call<GithubUser> response = githubRestInterface.getUser(user);
            response.enqueue(new Callback<GithubUser>() {
                @Override
                public void onResponse(Call<GithubUser> call, final Response<GithubUser> response) {
                    if (response.isSuccessful()) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                githubUserDao.addUser(TypesConverter.makeGithubUserDb(response.body()));
                            }
                        });
                        data.setValue(response.body());

                        System.out.println("User " + user + "Saved into database");
                    } else {
                        data.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call<GithubUser> call, Throwable t) {
                }
            });
        }
        return data;
    }

    @Override
    public LiveData<GithubUser> getData() {
        return data;
    }

    }
