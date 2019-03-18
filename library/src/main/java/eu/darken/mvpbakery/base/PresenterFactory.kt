package eu.darken.mvpbakery.base

interface PresenterFactory<PresenterT : Presenter<*>> {
    fun createPresenter(): FactoryResult<PresenterT>

    class FactoryResult<PresenterT : Presenter<*>> internal constructor(internal val presenter: PresenterT?, internal val retry: Boolean) {
        companion object {

            fun <PresenterT : Presenter<*>> retry(): FactoryResult<PresenterT> {
                return FactoryResult(null, true)
            }

            fun <PresenterT : Presenter<*>> forPresenter(presenter: PresenterT): FactoryResult<PresenterT> {
                return FactoryResult(presenter, false)
            }
        }
    }
}
