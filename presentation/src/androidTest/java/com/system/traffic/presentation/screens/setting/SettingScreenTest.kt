package com.system.traffic.presentation.screens.setting

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.core.enums.AppThemeType
import com.system.traffic.domain.usecase.datastore.GetAppFontSizeUseCase
import com.system.traffic.domain.usecase.datastore.GetAppThemeTypeUseCase
import com.system.traffic.domain.usecase.datastore.SetAppThemeTypeUseCase
import com.system.traffic.domain.usecase.datastore.SetFontSizeUseCase
import com.system.traffic.domain.usecase.pinned_bus.ResetPinnedBusUseCase
import com.system.traffic.presentation.event.UiEventBus
import com.system.traffic.presentation.fake.FakeDataStoreRepository
import com.system.traffic.presentation.fake.FakePinnedBusRepository
import com.system.traffic.presentation.screens.setting.viewmodel.SettingViewModel
import org.junit.Rule
import org.junit.Test

class SettingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun 설정화면의_모든_섹션이_표시된다() {
        composeTestRule.setContent {
            SettingScreenRoute(
                viewModel = createViewModel()
            )
        }

        listOf(
            SettingTestTags.INQUIRE_SECTION,
            SettingTestTags.FONT_SIZE_SECTION,
            SettingTestTags.THEME_SECTION,
            SettingTestTags.RESET_PINNED_BUS_SECTION,
            SettingTestTags.APP_VERSION_SECTION,
            SettingTestTags.LICENSE_SECTION,
        ).forEach { tag ->
            composeTestRule
                .onNodeWithTag(testTag = tag)
                .performScrollTo()
                .assertIsDisplayed()
        }
    }

    @Test
    fun 핀_초기화_버튼을_누르면_Dialog가_표시된다() {
        composeTestRule.setContent {
            SettingScreenRoute(
                viewModel = createViewModel()
            )
        }

        composeTestRule
            .onNodeWithTag(testTag = SettingTestTags.RESET_BUTTON)
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithTag(testTag = SettingTestTags.RESET_PINNED_BUS_DIALOG)
            .assertIsDisplayed()
    }

    @Test
    fun Dialog_취소_버튼을_누르면_Dialog_닫힌다(){
        composeTestRule.setContent {
            SettingScreenRoute(
                viewModel = createViewModel()
            )
        }

        composeTestRule
            .onNodeWithTag(testTag = SettingTestTags.RESET_BUTTON)
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithTag(testTag = SettingTestTags.RESET_PINNED_BUS_DIALOG)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(testTag = SettingTestTags.RESET_PINNED_BUS_DIALOG_CANCEL_BUTTON)
            .performClick()

        composeTestRule
            .onNodeWithTag(testTag = SettingTestTags.RESET_PINNED_BUS_DIALOG)
            .assertIsNotDisplayed()
    }


    private fun createViewModel(
        initialFontSize: AppFontSize = AppFontSize.MEDIUM,
        initialThemeType: AppThemeType = AppThemeType.LIGHT,
    ): SettingViewModel {
        val dataStoreRepository = FakeDataStoreRepository(
            initialFontSize = initialFontSize,
            initialThemeType = initialThemeType,
        )
        return SettingViewModel(
            setFontSizeUseCase = SetFontSizeUseCase(dataStoreRepository),
            getAppFontSizeUseCase = GetAppFontSizeUseCase(dataStoreRepository),
            setAppThemeTypeUseCase = SetAppThemeTypeUseCase(dataStoreRepository),
            getAppThemeTypeUseCase = GetAppThemeTypeUseCase(dataStoreRepository),
            resetPinnedBusUseCase = ResetPinnedBusUseCase(FakePinnedBusRepository()),
            uiEventBus = UiEventBus(),
        )
    }
}