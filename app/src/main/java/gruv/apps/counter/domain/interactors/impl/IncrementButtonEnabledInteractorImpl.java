package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.IncrementButtonEnabledInteractor;
import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.presentation.presenters.impl.CallbacksImpl;
import gruv.apps.counter.storage.DataValueRepository;
import gruv.apps.counter.storage.model.StorageModel;

/**
 * Интерактор доступности кнопки увеличения
 *
 * @author Goncharenko Ruslan
 */
public class IncrementButtonEnabledInteractorImpl extends AbstractInteractor implements IncrementButtonEnabledInteractor {

    public IncrementButtonEnabledInteractorImpl() {
        super();
    }

    /**
     * public  - только для тестов
     */
    public CallbacksImpl getCallbacksImpl() {
        return mCallbacksImpl;
    }

    @Override
    public void run() {
        // Получим значение
        //final boolean result = mRepository.getIncrementButtonEnabled();
        StorageModel storageModel = mRepository.get();
        int value = storageModel.getValue();
        final boolean result = (value < DataValueRepository.MAX_VALUE);

        // Мы получили наше значение, уведомим пользовательский интерфейс (UI) в главном потоке
        post(result);
    }

    private void post(final boolean value) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallbacksImpl.onIncrementButtonEnabled(value);
            }
        });
    }
}
