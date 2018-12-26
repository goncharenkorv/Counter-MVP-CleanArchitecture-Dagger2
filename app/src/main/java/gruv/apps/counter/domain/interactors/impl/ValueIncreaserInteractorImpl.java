package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.base.Interactor;
import gruv.apps.counter.storage.DataValueRepository;
import gruv.apps.counter.storage.model.StorageModel;

/**
 * Интерактор увеличения значения счетчика
 *
 * @author Goncharenko Ruslan
 */
public class ValueIncreaserInteractorImpl extends UpdaterInteractor implements Interactor {

    public ValueIncreaserInteractorImpl(@NonNull MainThread mainThread) {
        super(mainThread);
    }

    @Override
    public void run() {
        StorageModel storageModel = mRepository.get();
        int value = storageModel.getValue();
        if (value < DataValueRepository.MAX_VALUE) {
            value++;
            storageModel.setValue(value);
            mRepository.set(storageModel);

            mCallbacksImpl.vibrate(Constants.VIBRATION_ENCREASE_DURATION);
            mCallbacksImpl.playSound(Constants.Sound.INCREMENT_SOUND);
            updateModel();
        }
    }
}
