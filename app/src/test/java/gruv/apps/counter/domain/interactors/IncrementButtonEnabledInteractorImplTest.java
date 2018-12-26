package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.impl.IncrementButtonEnabledInteractorImpl;
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

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора IncrementButtonEnabledInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class IncrementButtonEnabledInteractorImplTest {

    private MainThread mMainThread;
    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private IncrementButtonEnabledInteractorImpl mInteractor;
    private Repository mRepository;
    private CallbacksImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new MainThreadTest();
        ComponentBuilder.getPresenterComponent(mView, mContext);

        mInteractor = new IncrementButtonEnabledInteractorImpl(mMainThread);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mCallbacksImpl);
        mInteractor.mCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTrueTest() {
        int value = 5;

        StorageModel storageModel = new StorageModel(value);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.run();

        Mockito.verify(mRepository).get();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        Mockito.verify(mCallbacksImpl).onIncrementButtonEnabled(true);
    }

    @Test
    public void runFalseTest() {
        int value = 1000000;

        StorageModel storageModel = new StorageModel(value);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.run();

        Mockito.verify(mRepository).get();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        Mockito.verify(mCallbacksImpl).onIncrementButtonEnabled(false);
    }
}
