package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.base.Interactor;
import gruv.apps.counter.storage.model.StorageModel;

/**
 * Интерактор сброса в ноль значения счетчика (без сохранения значения) и обновления результата на экране
 *
 * @author Goncharenko Ruslan
 */
public class CleanValueInteractorImpl extends UpdaterInteractor implements Interactor {

    @Inject
    public CleanValueInteractorImpl(@NonNull MainThread mainThread) {
        super(mainThread);
    }

    @Override
    public void run() {
        StorageModel storageModel = new StorageModel(0);
        mRepository.set(storageModel);
        mCallbacksImpl.vibrate(Constants.VIBRATION_CLEAR_DURATION);
        mCallbacksImpl.playSound(Constants.Sound.CLEAR_SOUND);
        updateModel();
    }
}
