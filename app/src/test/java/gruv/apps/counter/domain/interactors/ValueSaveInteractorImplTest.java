package gruv.apps.counter.domain.interactors;

import android.content.Context;
import android.content.SharedPreferences;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.impl.ValueSaveInteractorImpl;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.MainPresenter;
import gruv.apps.counter.presentation.presenters.impl.CallbacksImpl;
import gruv.apps.counter.threading.MainThreadTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Тест интерактора ValueSaveInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class ValueSaveInteractorImplTest {

    private MainThread mMainThread;
    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private ValueSaveInteractorImpl mInteractor;
    private Repository mRepository;
    private CallbacksImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new MainThreadTest();
        ComponentBuilder.getPresenterComponent(mView, mContext);

        mInteractor = new ValueSaveInteractorImpl(mMainThread, mContext);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mCallbacksImpl);
        mInteractor.mCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor dataEditor = Mockito.mock(SharedPreferences.Editor.class);
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt()).edit()).thenReturn(dataEditor);

        mInteractor.run();

        verify(mRepository).save(mContext);

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);
    }
}
