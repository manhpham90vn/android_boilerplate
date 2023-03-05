package com.manhpham.baseandroid.usecase

import com.manhpham.baseandroid.models.LoginResponse
import com.manhpham.baseandroid.repository.AppLocalDataRepositoryInterface
import com.manhpham.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.manhpham.baseandroid.service.ConnectivityService
import com.manhpham.baseandroid.service.SchedulerProvider
import io.mockk.* // ktlint-disable no-wildcard-imports
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var mockSchedule: SchedulerProvider
    private lateinit var mockConnectivityService: ConnectivityService
    private lateinit var mockAppLocalDataRepositoryInterface: AppLocalDataRepositoryInterface
    private lateinit var mockAppRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface
    private lateinit var loginUseCase: LoginUseCase
    private val testScheduler = Schedulers.trampoline()

    @Before
    fun setup() {
        mockSchedule = mockkClass(SchedulerProvider::class) {
            every { io() }.returns(testScheduler)
            every { ui() }.returns(testScheduler)
            every { computation() }.returns(testScheduler)
        }
        mockConnectivityService = mockkClass(ConnectivityService::class)
        mockAppLocalDataRepositoryInterface = mockkClass(AppLocalDataRepositoryInterface::class)
        mockAppRemoteDataRepositoryInterface = mockkClass(AppRemoteDataRepositoryInterface::class)

        loginUseCase = spyk(
            LoginUseCase(
                appRemoteDataRepositoryInterface = mockAppRemoteDataRepositoryInterface,
                localDataRepositoryInterface = mockAppLocalDataRepositoryInterface,
            ).apply {
                connectivityService = mockConnectivityService
                schedulerProvider = mockSchedule
            },
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testLoginSuccess() {
        val request = LoginUseCaseParams("test@test.com", "password")
        val mockResponse = mockk<LoginResponse> {
            every { token } returns "token"
            every { refreshToken } returns "refreshToken"
        }

        every { mockConnectivityService.isNetworkConnection }.returns(true)
        every { mockAppLocalDataRepositoryInterface.setToken(any()) } returns Unit
        every { mockAppLocalDataRepositoryInterface.setRefreshToken(any()) } returns Unit
        every { mockAppRemoteDataRepositoryInterface.callLogin(any(), any()) }.returns(
            Single.just(
                mockResponse,
            ),
        )

        val recorder = Recorder<Unit>()
        recorder.onNext(loginUseCase.complete)

        loginUseCase.execute(request)

        assert(recorder.result.size == 1)
    }

    @Test
    fun testLoginError() {
        val request = LoginUseCaseParams("test@test.com", "password")
        val mockResponse = mockk<LoginResponse>()

        every { mockConnectivityService.isNetworkConnection }.returns(false)
        every { mockAppLocalDataRepositoryInterface.setToken(any()) } returns Unit
        every { mockAppLocalDataRepositoryInterface.setRefreshToken(any()) } returns Unit
        every { mockAppRemoteDataRepositoryInterface.callLogin(any(), any()) }.returns(
            Single.just(
                mockResponse,
            ),
        )

        val recorder = Recorder<Throwable>()
        recorder.onNext(loginUseCase.failed)

        loginUseCase.execute(request)

        assert(recorder.result.size == 1)
    }
}
