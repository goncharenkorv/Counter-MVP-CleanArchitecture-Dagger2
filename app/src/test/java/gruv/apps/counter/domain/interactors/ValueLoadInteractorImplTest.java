package gruv.apps.counter.domain.interactors;

import android.content.Context;
import android.content.SharedPreferences;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.impl.ValueLoadInteractorImpl;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.MainPresenter;
import gruv.apps.counter.presentation.presenters.impl.CallbacksImpl;
import gruv.apps.counter.storage.model.StorageModel;
import gruv.apps.counter.threading.MainThreadTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора ValueLoadInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class ValueLoadInteractorImplTest {

    private MainThread mMainThread;
    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private ValueLoadInteractorImpl mInteractor;
    private Repository mRepository;
    private CallbacksImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new MainThreadTest();
        ComponentBuilder.getPresenterComponent(mView, mContext);

        mInteractor = new ValueLoadInteractorImpl(mMainThread, mContext);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mCallbacksImpl);
        mInteractor.mCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor dataEditor = Mockito.mock(SharedPreferences.Editor.class);//getSharedPreferences(context).edit();
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt()).edit()).thenReturn(dataEditor);

        int value = 1;

        StorageModel storageModel = new StorageModel(value);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.run();

        Mockito.verify(mRepository).load(mContext);

        Mockito.verify(mRepository).get();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        DomainModel domainModelNew = new DomainModel(String.valueOf(value));

        // Проверка вызова колбэков
        Mockito.verify(mCallbacksImpl).onValueUpdate(domainModelNew);

    }
}
