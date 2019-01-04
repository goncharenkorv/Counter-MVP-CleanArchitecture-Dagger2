package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.base.Interactor;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.storage.DataValueRepository;
import gruv.apps.counter.storage.model.StorageModel;

/**
 * Интерактор уменьшения значения счетчика
 *
 * @author Goncharenko Ruslan
 */
public class ValueDecreaserInteractorImpl extends UpdaterInteractor implements Interactor {

    public ValueDecreaserInteractorImpl() {
        super();
    }

    @Override
    public void run() {
        StorageModel storageModel = mRepository.get();
        int value = storageModel.getValue();
        if (value > DataValueRepository.MIN_VALUE) {
            value--;
            storageModel.setValue(value);
            mRepository.set(storageModel);

            mCallbacksImpl.vibrate(Constants.VIBRATION_DECREASE_DURATION);
            mCallbacksImpl.playSound(Constants.Sound.DECREMENT_SOUND);
            updateModel();
        }
    }
}
