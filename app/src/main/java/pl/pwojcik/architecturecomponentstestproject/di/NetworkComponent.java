package pl.pwojcik.architecturecomponentstestproject.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.pwojcik.architecturecomponentstestproject.ui.viewmodel.GitHubUserViewModel;

/**
 * Created by pawel on 11.02.18.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {
     void inject(GitHubUserViewModel viewModel);

}
