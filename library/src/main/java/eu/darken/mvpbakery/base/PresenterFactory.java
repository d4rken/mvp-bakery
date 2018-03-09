package eu.darken.mvpbakery.base;

public interface PresenterFactory<PresenterT extends Presenter> {
    FactoryResult<PresenterT> createPresenter();

    class FactoryResult<PresenterT extends Presenter> {
        final PresenterT presenter;
        final boolean retry;

        FactoryResult(PresenterT presenter, boolean retry) {
            this.presenter = presenter;
            this.retry = retry;
        }

        public static <PresenterT extends Presenter> FactoryResult<PresenterT> retry() {
            return new FactoryResult<>(null, true);
        }

        public static <PresenterT extends Presenter> FactoryResult<PresenterT> forPresenter(PresenterT presenter) {
            return new FactoryResult<>(presenter, false);
        }
    }
}
