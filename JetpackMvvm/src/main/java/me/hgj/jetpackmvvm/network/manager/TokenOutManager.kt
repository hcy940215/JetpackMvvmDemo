package me.hgj.jetpackmvvm.network.manager

import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData

class TokenOutManager private constructor() {

    val mTokenOutCallback = EventLiveData<String>()

    companion object {
        val instance: TokenOutManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TokenOutManager()
        }
    }

}