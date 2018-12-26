package gruv.apps.counter.presentation.presenters.impl;

import android.support.annotation.NonNull;

import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.interactors.DecrementButtonEnabledInteractor;
import gruv.apps.counter.domain.interactors.IncrementButtonEnabledInteractor;
import gruv.apps.counter.domain.interactors.SoundVibrateInteractor;
import gruv.apps.counter.domain.interactors.ValueUpdaterInteractor;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.presentation.presenters.MainPresenter;

/**
 * Реализация колбэков
 *
 * @author Goncharenko Ruslan
 */
public class CallbacksImpl implements
        ValueUpdaterInteractor.Callback,
        IncrementButtonEnabledInteractor.Callback,
        DecrementButtonEnabledInteractor.Callback,
        SoundVibrateInteractor.Callback {

    private MainPresenter.View mView;

    public CallbacksImpl(@NonNull MainPresenter.View view) {
        mView = view;
    }

    @Override
    public void onValueUpdate(DomainModel domainModel) {
        mView.setValue(domainModel.getValue());
    }

    @Override
    public void onIncrementButtonEnabled(boolean value) {
        mView.setIncrementButtonEnabled(value);
    }

    @Override
    public void onDecrementButtonEnabled(boolean value) {
        mView.setDecrementButtonEnabled(value);
    }

    @Override
    public void vibrate(long duration) {
        mView.vibrate(duration);
    }

    @Override
    public void playSound(Constants.Sound sound) {
        mView.playSound(sound);
    }
}
