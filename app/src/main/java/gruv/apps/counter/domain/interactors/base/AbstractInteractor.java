package gruv.apps.counter.domain.interactors.base;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.executor.Executor;
import gruv.apps.counter.domain.executor.MainThread;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.impl.CallbacksImpl;

/**
 * Этот абстрактный класс реализует некоторые общие методы для всех интеракторов.
 * Отменяя интерактор (cancel), проверьте, работает ли он
 * и завершение интерактора содержит в основном один и тот же код, поэтому этот класс был создан.
 * Методы поля объявлены как volatile, так как мы могли бы использовать эти методы из разных потоков (в основном из пользовательского интерфейса).
 *
 * Например, когда активити уничтожается, нам, вероятно, следует отменить интерактор,
 * но запрос будет поступать из потока пользовательского интерфейса, если только запрос не был специально назначен фоновому потоку.
 *
 * @author Goncharenko Ruslan
 */
public abstract class AbstractInteractor implements Interactor {

    /**
     * public  - только для тестов
     * protected было бы достаточно для обеспечения видимости в наследуемых классах
     */
    @NonNull
    @Inject
    public CallbacksImpl mCallbacksImpl;

    /**
     * public  - только для тестов
     * protected было бы достаточно для обеспечения видимости в наследуемых классах
     */
    @NonNull
    @Inject
    public Repository mRepository;

    protected MainThread mMainThread;

    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;

    @Inject
    Executor mThreadExecutor;

    public AbstractInteractor(@NonNull MainThread mainThread) {
        mMainThread = mainThread;
        ComponentBuilder.getPresenterComponent(null, null).inject(this);
    }

    /**
     * Этот метод содержит актуальную бизнес-логику интерактора. ОН НЕ ДОЛЖНО ИСПОЛЬЗОВАТЬСЯ НАПРЯМУЮ, а вместо этого
     * разработчик должен вызвать метод execute () интерактора, чтобы убедиться, что операция выполняется в фоновом потоке.
     *
     *  Этот метод должен вызываться напрямую только при выполнении модульных / интеграционных тестов.
     * Это единственная причина, по которой он объявлен public  (чтобы упростить тестирование).
     */
    public abstract void run();

    public void cancel() {
        mIsCanceled = true;
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void onFinished() {
        mIsRunning = false;
        mIsCanceled = false;
    }

    public void execute() {

        // Помечаем этот интерактор как запущенный
        this.mIsRunning = true;

        // Запускаем интерактор в фоновом потоке
        mThreadExecutor.execute(this);
    }

}
