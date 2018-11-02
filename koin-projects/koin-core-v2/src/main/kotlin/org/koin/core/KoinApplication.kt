package org.koin.core

import org.koin.core.error.KoinAlreadyStartedException
import org.koin.core.module.Module
import org.koin.core.standalone.StandAloneKoinApplication
import org.koin.core.time.logDuration


typealias KoinAppDeclaration = KoinApplication.() -> Unit

class KoinApplication {

    val koin = Koin()

    init {
        KoinApplication.log("[Koin] create")
    }

    fun loadModules(vararg modulesToLoad: Module) {
        KoinApplication.log("[Koin] load modules")
        logDuration("[Koin] modules loaded") {
            koin.beanRegistry.loadModules(koin, *modulesToLoad)
        }
    }

    fun start(): KoinApplication {
        if (StandAloneKoinApplication.app != null) {
            throw KoinAlreadyStartedException("KoinApplication is already started")
        }
        StandAloneKoinApplication.app = this
        KoinApplication.log("[Koin] standalone app start")

        //TODO launch eager instances
        return this
    }

    fun stop() {
        StandAloneKoinApplication.app = null
        KoinApplication.log("[Koin] standalone app stop")
    }

    companion object {
        fun log(msg: String) = println(msg)
        fun create() = KoinApplication()
    }
}