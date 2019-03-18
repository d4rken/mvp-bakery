package eu.darken.mvpbakery.example.screens.counting

class Counter(i: Int) {

    internal var current: Int = 0
        private set

    init {
        this.current = i
    }

    internal fun setCounter(i: Int) {
        this.current = i
    }

    internal fun countUp(): Int {
        return ++current
    }

}
