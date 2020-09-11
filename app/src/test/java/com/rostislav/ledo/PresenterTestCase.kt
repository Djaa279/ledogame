package com.rostislav.ledo

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.nhaarman.mockito_kotlin.reset
import com.rostislav.ledo.base.BasePresenter
import org.junit.Rule
import org.mockito.Mockito

abstract class PresenterTestCase<P : BasePresenter<T>, T> : BaseTestCase()
        where T : LifecycleOwner {

    abstract var presenter: P
    abstract var targetMock: T

    abstract val targetClazz: Class<T>

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @CallSuper
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        targetMock = Mockito.mock(targetClazz)
        presenter.takeTarget(targetMock)
        reset(targetMock)
    }

}
