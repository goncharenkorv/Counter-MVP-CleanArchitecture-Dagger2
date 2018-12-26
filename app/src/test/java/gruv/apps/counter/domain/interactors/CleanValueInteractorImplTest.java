package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.impl.CleanValueInteractorImpl;
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

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора CleanValueInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class CleanValueInteractorImplTest {

    private static final long VIBRATION_CLEAR_DURATION = 100; // Milliseconds

    private MainThread mMainThread;
    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private CleanValueInteractorImpl mInteractor;
    private Repository mRepository;
    private CallbacksImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new MainThreadTest();
        ComponentBuilder.getPresenterComponent(mView, mContext);

        mInteractor = new CleanValueInteractorImpl(mMainThread);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mCallbacksImpl);
        mInteractor.mCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {

        StorageModel storageModel = new StorageModel(0);

        when(mRepository.get()).thenReturn(storageModel);

        DomainModel domainModel = new DomainModel(String.valueOf(0));

        mInteractor.run();

        Mockito.verify(mRepository).set(storageModel);
        Mockito.verify(mRepository).get();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        // Проверка вызова колбэков
        Mockito.verify(mCallbacksImpl).onValueUpdate(domainModel);
        Mockito.verify(mCallbacksImpl).vibrate(VIBRATION_CLEAR_DURATION);
        Mockito.verify(mCallbacksImpl).playSound(Constants.Sound.CLEAR_SOUND);
    }
}
