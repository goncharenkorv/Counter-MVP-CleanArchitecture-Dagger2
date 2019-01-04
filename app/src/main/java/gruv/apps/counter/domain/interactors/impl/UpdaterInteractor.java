package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.presentation.presenters.impl.CallbacksImpl;
import gruv.apps.counter.storage.converters.StorageDomainModelConverter;
import gruv.apps.counter.storage.model.StorageModel;

/**
 * Абстрактный класс-интерактор обновления значения счетчика.
 * (вывод обновленного значения в главном потоке)
 *
 * @author Goncharenko Ruslan
 */
public abstract class UpdaterInteractor extends AbstractInteractor {

    public UpdaterInteractor(@NonNull MainThread mainThread) {
        super(mainThread);
    }

    /**
     * public  - только для тестов
     */
    public CallbacksImpl getCallbacksImpl() {
        return mCallbacksImpl;
    }

    protected void updateModel() {
        // Получим значение
        final StorageModel storageModel = mRepository.get();

        //конвертируем из модели хранилища в модель для домена
        DomainModel domainModel = StorageDomainModelConverter.convertToDomainModel(storageModel);

        // Мы получили наше значение, уведомим пользовательский интерфейс (UI) в главном потоке
        postValue(domainModel);
    }

    private void postValue(@NonNull final DomainModel domainModel) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallbacksImpl.onValueUpdate(domainModel);
            }
        });
    }
}
