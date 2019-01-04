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

    public ValueLoadInteractorImpl(@NonNull Context context) {
        super();
        mContext = context;
    }

    @Override
    public void run() {
        mRepository.load(mContext);
        updateModel();
    }
}
