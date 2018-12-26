package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.impl.ValueIncreaserInteractorImpl;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора ValueIncreaserInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class ValueIncreaserInteractorImplTest {

    private static final long VIBRATION_ENCREASE_DURATION = 40; // Milliseconds

    private MainThread mMainThread;
    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private ValueIncreaserInteractorImpl mInteractor;
    private Repository mRepository;
    private CallbacksImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new MainThreadTest();
        ComponentBuilder.getPresenterComponent(mView, mContext);

        mInteractor = new ValueIncreaserInteractorImpl(mMainThread);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mCallbacksImpl);
        mInteractor.mCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {
        int value = 5;

        StorageModel storageModel = new StorageModel(value);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.run();

        Mockito.verify(mRepository).set(storageModel);
        Mockito.verify(mRepository, times(2)).get();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        DomainModel domainModelNew = new DomainModel(String.valueOf(value + 1));

        // Проверка вызова колбэков
        Mockito.verify(mCallbacksImpl).onValueUpdate(domainModelNew);
        Mockito.verify(mCallbacksImpl).vibrate(VIBRATION_ENCREASE_DURATION);
        Mockito.verify(mCallbacksImpl).playSound(Constants.Sound.INCREMENT_SOUND);
    }
}
