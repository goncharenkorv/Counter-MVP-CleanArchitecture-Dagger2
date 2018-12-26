package gruv.apps.counter.domain.interactors.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.domain.interactors.base.Interactor;

/**
 * Интерактор сохранения значения счетчика (записи значения)
 *
 * @author Goncharenko Ruslan
 */
public class ValueSaveInteractorImpl extends AbstractInteractor implements Interactor {

    private Context mContext;

    public ValueSaveInteractorImpl(@NonNull MainThread mainThread,
                                   @NonNull Context context) {
        super(mainThread);
        mContext = context;
    }

    @Override
    public void run() {
        mRepository.save(mContext);
    }
}
