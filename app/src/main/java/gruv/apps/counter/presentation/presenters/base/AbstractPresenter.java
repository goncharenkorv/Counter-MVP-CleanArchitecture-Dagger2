package gruv.apps.counter.presentation.presenters.base;

import javax.inject.Inject;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.executor.Executor;
import gruv.apps.counter.domain.executor.MainThread;

/**
 * Это базовый класс для всех презентеров, которые общаются с интеракторами. Этот базовый класс будет содержать
 * ссылку на объекты Executor и MainThread, необходимые для запуска интеракторов в фоновом потоке.
 *
 * @author Goncharenko Ruslan
 */
public abstract class AbstractPresenter {

    @Inject
    Executor mExecutor;
    @Inject
    MainThread mMainThread;

    public AbstractPresenter() {
        ComponentBuilder.getPresenterComponent(null, null).inject(this);
    }
}
