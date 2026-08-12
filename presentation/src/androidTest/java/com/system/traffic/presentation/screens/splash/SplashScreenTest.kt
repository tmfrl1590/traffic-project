package com.system.traffic.presentation.screens.splash

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.system.traffic.domain.usecase.datastore.GetIsFirstLoginUseCase
import com.system.traffic.domain.usecase.datastore.SetUpIsFirstLoginUseCase
import com.system.traffic.domain.usecase.file.InitializeDataUseCase
import com.system.traffic.presentation.fake.FakeDataStoreRepository
import com.system.traffic.presentation.fake.FakeFileRepository
import com.system.traffic.presentation.screens.splash.viewmodel.SplashViewModel
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * 스플래시 "화면 단위" 테스트.
 * Hilt 없이 fake 기반 ViewModel을 직접 주입해 격리된 상태로 검증한다.
 * (실제 앱 전체 흐름 검증은 app 모듈의 E2E 테스트 담당)
 */
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun 스플래시_화면이_정상적으로_표시된다() {
        composeTestRule.setContent {
            SplashScreenRoute(
                splashViewModel = createViewModel()
            )
        }

        // 로고 이미지가 화면에 렌더링되는지 확인
        composeTestRule
            .onNodeWithTag(testTag = SplashTestTags.LOGO)
            .assertIsDisplayed()
    }

    @Test
    fun 초기화와_애니메이션이_끝나면_홈_이동_콜백이_호출된다() {
        var navigated = false

        composeTestRule.setContent {
            SplashScreenRoute(
                onGoHomeScreen = { navigated = true },
                splashViewModel = createViewModel()
            )
        }

        // 애니메이션(1초) + 초기화 완료 대기
        composeTestRule.waitUntil(timeoutMillis = 5_000) { navigated }
        assertTrue(navigated)
    }

    private fun createViewModel(): SplashViewModel {
        val dataStoreRepository = FakeDataStoreRepository()
        return SplashViewModel(
            initializeDataUseCase = InitializeDataUseCase(repository = FakeFileRepository()),
            setUpIsFirstLoginUseCase = SetUpIsFirstLoginUseCase(dataStoreRepository),
            getIsFirstLoginUseCase = GetIsFirstLoginUseCase(dataStoreRepository),
        )
    }
}
