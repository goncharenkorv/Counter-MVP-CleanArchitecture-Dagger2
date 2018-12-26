package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.ValueUpdaterInteractor;

/**
 * Интерактор обновления значения счетчика.
 * Т.е. вывод обновленного значения в главном потоке
 *
 * @author Goncharenko Ruslan
 */
public class ValueUpdaterInteractorImpl extends UpdaterInteractor implements ValueUpdaterInteractor {

    public ValueUpdaterInteractorImpl(@NonNull MainThread mainThread) {
        super(mainThread);
    }

    @Override
    public void run() {
        updateModel();
    }
}
