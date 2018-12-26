package gruv.apps.counter.presentation.presenters.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import gruv.apps.counter.domain.executor.Executor;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.impl.CleanValueInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.DecrementButtonEnabledInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.IncrementButtonEnabledInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueDecreaserInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueIncreaserInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueLoadInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueSaveInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueUpdaterInteractorImpl;
import gruv.apps.counter.presentation.presenters.base.AbstractPresenter;
import gruv.apps.counter.presentation.presenters.MainPresenter;

/**
 * Реализация (имплементация) главного презентера
 *
 * @author Goncharenko Ruslan
 */
public class MainPresenterImpl extends AbstractPresenter implements MainPresenter {

    private CleanValueInteractorImpl mCleanValueInteractorImpl;
    private ValueSaveInteractorImpl mValueSaveInteractorImpl;
    private ValueLoadInteractorImpl mValueLoadInteractorImpl;
    private ValueIncreaserInteractorImpl mValueIncreaserInteractorImpl;
    private ValueDecreaserInteractorImpl mValueDecreaserInteractorImpl;
    private ValueUpdaterInteractorImpl mValueUpdaterInteractorImpl;
    private IncrementButtonEnabledInteractorImpl mIncrementButtonEnabledInteractorImpl;
    private DecrementButtonEnabledInteractorImpl mDecrementButtonEnabledInteractorImpl;

    public MainPresenterImpl(@NonNull Executor executor,
                             @NonNull MainThread mainThread,
                             @NonNull CleanValueInteractorImpl cleanValueInteractorImpl,
                             @NonNull ValueSaveInteractorImpl valueSaveInteractorImpl,
                             @NonNull ValueLoadInteractorImpl valueLoadInteractorImpl,
                             @NonNull ValueIncreaserInteractorImpl valueIncreaserInteractorImpl,
                             @NonNull ValueDecreaserInteractorImpl valueDecreaserInteractorImpl,
                             @NonNull ValueUpdaterInteractorImpl valueUpdaterInteractorImpl,
                             @NonNull IncrementButtonEnabledInteractorImpl incrementButtonEnabledInteractorImpl,
                             @NonNull DecrementButtonEnabledInteractorImpl decrementButtonEnabledInteractorImpl) {
        super(executor, mainThread);
        mCleanValueInteractorImpl = cleanValueInteractorImpl;
        mValueSaveInteractorImpl = valueSaveInteractorImpl;
        mValueLoadInteractorImpl = valueLoadInteractorImpl;
        mValueIncreaserInteractorImpl = valueIncreaserInteractorImpl;
        mValueDecreaserInteractorImpl = valueDecreaserInteractorImpl;
        mValueUpdaterInteractorImpl = valueUpdaterInteractorImpl;
        mIncrementButtonEnabledInteractorImpl = incrementButtonEnabledInteractorImpl;
        mDecrementButtonEnabledInteractorImpl = decrementButtonEnabledInteractorImpl;
    }

    @Override
    public void resume() {
        update();
    }

    @Override
    public void saveState(Context context) {
        mValueSaveInteractorImpl.execute();
    }

    @Override
    public void loadState(Context context) {
        mValueLoadInteractorImpl.execute();
    }

    @Override
    public void increase() {
        mValueIncreaserInteractorImpl.execute();
    }

    @Override
    public void decrease() {
        mValueDecreaserInteractorImpl.execute();
    }

    @Override
    public void clean() {
        mCleanValueInteractorImpl.execute();
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onError(String message) {
    }

    private void update() {
        // Обновляем значение счетчика
        updateValue();

        // Обновляем доступность кнопок:
        updateIncrementButtonEnabled();
        updateDecrementButtonEnabled();
    }

    private void updateValue() {
        mValueUpdaterInteractorImpl.execute();
    }

    private void updateIncrementButtonEnabled() {
        mIncrementButtonEnabledInteractorImpl.execute();
    }

    private void updateDecrementButtonEnabled() {
        mDecrementButtonEnabledInteractorImpl.execute();
    }
}
