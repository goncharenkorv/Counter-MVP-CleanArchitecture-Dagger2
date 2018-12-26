package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.impl.DecrementButtonEnabledInteractorImpl;
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
 * Тест интерактора DecrementButtonEnabledInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class DecrementButtonEnabledInteractorImplTest {

    private MainThread mMainThread;
    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private DecrementButtonEnabledInteractorImpl mInteractor;
    private Repository mRepository;
    private CallbacksImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new MainThreadTest();
        ComponentBuilder.getPresenterComponent(mView, mContext);

        mInteractor = new DecrementButtonEnabledInteractorImpl(mMainThread);

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

        Mockito.verify(mCallbacksImpl).onDecrementButtonEnabled(true);
    }

    @Test
    public void runFalseTest() {
        int value = -1000000;

        StorageModel storageModel = new StorageModel(value);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.run();

        Mockito.verify(mRepository).get();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        Mockito.verify(mCallbacksImpl).onDecrementButtonEnabled(false);
    }
}
