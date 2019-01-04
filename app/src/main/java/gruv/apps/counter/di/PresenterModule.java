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
    DecrementButtonEnabledInteractorImpl provideDecrementButtonEnabledInteractorImpl(@NonNull MainThread mainThread) {
        return new DecrementButtonEnabledInteractorImpl(
                mainThread);
    }

    @Provides
    @NonNull
    @Singleton
    IncrementButtonEnabledInteractorImpl provideIncrementButtonEnabledInteractorImpl(@NonNull MainThread mainThread) {
        return new IncrementButtonEnabledInteractorImpl(
                mainThread);
    }

    @Provides
    @NonNull
    @Singleton
    ValueUpdaterInteractorImpl provideValueUpdaterInteractorImpl(@NonNull MainThread mainThread) {
        return new ValueUpdaterInteractorImpl(
                mainThread);
    }

    @Provides
    @NonNull
    @Singleton
    ValueDecreaserInteractorImpl provideValueDecreaserInteractorImpl(@NonNull MainThread mainThread) {
        return new ValueDecreaserInteractorImpl(
                mainThread);
    }

    @Provides
    @NonNull
    @Singleton
    ValueIncreaserInteractorImpl provideValueIncreaserInteractorImpl(
            @NonNull MainThread mainThread) {
        return new ValueIncreaserInteractorImpl(
                mainThread);
    }

    @Provides
    @NonNull
    @Singleton
    ValueLoadInteractorImpl provideValueLoadInteractorImpl(@NonNull MainThread mainThread) {
        return new ValueLoadInteractorImpl(
                mainThread,
                mContext);
    }

    @Provides
    @NonNull
    @Singleton
    ValueSaveInteractorImpl provideValueSaveInteractorImpl(@NonNull MainThread mainThread) {
        return new ValueSaveInteractorImpl(
                mainThread,
                mContext);
    }

    @Provides
    @NonNull
    @Singleton
    CleanValueInteractorImpl provideCleanValueInteractorImpl(@NonNull MainThread mainThread) {
        return new CleanValueInteractorImpl(
                mainThread);
    }

    @Provides
    @NonNull
    @Singleton
    CallbacksImpl provideCallbacksImpl(@NonNull MainPresenter.View view) {
        return new CallbacksImpl(view);
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