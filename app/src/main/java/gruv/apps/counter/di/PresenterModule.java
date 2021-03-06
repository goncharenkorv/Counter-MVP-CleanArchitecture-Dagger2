package gruv.apps.counter.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gruv.apps.counter.domain.executor.Executor;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.executor.impl.ThreadExecutor;
import gruv.apps.counter.domain.interactors.impl.CleanValueInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.DecrementButtonEnabledInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.IncrementButtonEnabledInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueDecreaserInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueIncreaserInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueLoadInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueSaveInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueUpdaterInteractorImpl;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.MainPresenter;
import gruv.apps.counter.presentation.presenters.impl.CallbacksImpl;
import gruv.apps.counter.presentation.presenters.impl.MainPresenterImpl;
import gruv.apps.counter.storage.DataValueRepository;
import gruv.apps.counter.threading.MainThreadImpl;

/**
 * Модуль презентера (для dagger 2)
 * Предоставляет MainPresenterImpl
 *
 * @author Goncharenko Ruslan
 */
@Module
public class PresenterModule {

    private MainPresenter.View mView;
    private Context mContext;

    public PresenterModule(MainPresenter.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    @NonNull
    @Singleton
    MainPresenter provideMainPresenter() {
        return new MainPresenterImpl();
    }

    @Provides
    @NonNull
    @Singleton
    DecrementButtonEnabledInteractorImpl provideDecrementButtonEnabledInteractorImpl() {
        return new DecrementButtonEnabledInteractorImpl();
    }

    @Provides
    @NonNull
    @Singleton
    IncrementButtonEnabledInteractorImpl provideIncrementButtonEnabledInteractorImpl() {
        return new IncrementButtonEnabledInteractorImpl();
    }

    @Provides
    @NonNull
    @Singleton
    ValueUpdaterInteractorImpl provideValueUpdaterInteractorImpl() {
        return new ValueUpdaterInteractorImpl();
    }

    @Provides
    @NonNull
    @Singleton
    ValueDecreaserInteractorImpl provideValueDecreaserInteractorImpl() {
        return new ValueDecreaserInteractorImpl();
    }

    @Provides
    @NonNull
    @Singleton
    ValueIncreaserInteractorImpl provideValueIncreaserInteractorImpl() {
        return new ValueIncreaserInteractorImpl();
    }

    @Provides
    @NonNull
    @Singleton
    ValueLoadInteractorImpl provideValueLoadInteractorImpl() {
        return new ValueLoadInteractorImpl(
                mContext);
    }

    @Provides
    @NonNull
    @Singleton
    ValueSaveInteractorImpl provideValueSaveInteractorImpl() {
        return new ValueSaveInteractorImpl(
                mContext);
    }

    @Provides
    @NonNull
    @Singleton
    CleanValueInteractorImpl provideCleanValueInteractorImpl() {
        return new CleanValueInteractorImpl();
    }

    @Provides
    @NonNull
    @Singleton
    CallbacksImpl provideCallbacksImpl() {
        return new CallbacksImpl();
    }

    @Provides
    @NonNull
    @Singleton
    Repository provideRepository() {
        return new DataValueRepository();
    }

    @Provides
    @NonNull
    @Singleton
    MainPresenter.View provideView() {
        return mView;
    }

    @Provides
    @NonNull
    @Singleton
    Executor provideExecutor() {
        return new ThreadExecutor();
    }

    @Provides
    @NonNull
    @Singleton
    MainThread provideMainThread() {
        return new MainThreadImpl();
    }
}