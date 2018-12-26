package gruv.apps.counter.domain.interactors.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.base.Interactor;

/**
 * Интерактор восстановления значения счетчика (чтения сохраненного значения)
 *
 * @author Goncharenko Ruslan
 */
public class ValueLoadInteractorImpl extends UpdaterInteractor implements Interactor {

    private Context mContext;

    public ValueLoadInteractorImpl(@NonNull MainThread mainThread,
                                   @NonNull Context context) {
        super(mainThread);
        mContext = context;
    }

    @Override
    public void run() {
        mRepository.load(mContext);
        updateModel();
    }
}
