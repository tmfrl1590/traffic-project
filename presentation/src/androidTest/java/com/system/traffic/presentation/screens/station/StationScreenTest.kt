package com.system.traffic.presentation.screens.station

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.system.traffic.domain.model.KeywordModel
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.usecase.keyword.ClearAllKeywordUseCase
import com.system.traffic.domain.usecase.keyword.DeleteKeywordUseCase
import com.system.traffic.domain.usecase.keyword.GetKeywordListUseCase
import com.system.traffic.domain.usecase.keyword.InsertKeywordUseCase
import com.system.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.system.traffic.domain.usecase.like.ToggleLikeStationUseCase
import com.system.traffic.domain.usecase.station.GetSearchStationUseCase
import com.system.traffic.presentation.event.UiEventBus
import com.system.traffic.presentation.fake.FakeKeywordRepository
import com.system.traffic.presentation.fake.FakeLikeStationRepository
import com.system.traffic.presentation.fake.FakeStationRepository
import com.system.traffic.presentation.screens.station.viewmodel.StationViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class StationScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun 검색화면이_정상적으로_표시된다(){
        composeTestRule.setContent {
            StationScreenRoute(
                stationViewModel = createViewModel()
            )
        }

        composeTestRule
            .onNodeWithTag(testTag = StationTestTags.SEARCH_BAR)
            .assertIsDisplayed()
    }

    @Test
    fun 검색어_입력창에_검색어를_입력하면_검색어가_보인다() {
        composeTestRule.setContent {
            StationScreenRoute(
                stationViewModel = createViewModel()
            )
        }

        val inputKeyword = "강남역"

        composeTestRule
            .onNodeWithTag(testTag = StationTestTags.SEARCH_INPUT)
            .performTextInput(text = inputKeyword)

        composeTestRule
            .onNodeWithTag(testTag = StationTestTags.SEARCH_INPUT)
            .assertTextContains(value = inputKeyword)
    }

    @Test
    fun 검색어_입력창에_검색어를_입력하고_전체삭제_아이콘을_누르면_검색어가_삭제된다(){
        composeTestRule.setContent {
            StationScreenRoute(
                stationViewModel = createViewModel()
            )
        }

        val inputKeyword = "강남역"

        composeTestRule
            .onNodeWithTag(testTag = StationTestTags.SEARCH_INPUT)
            .performTextInput(text = inputKeyword)

        composeTestRule
            .onNodeWithTag(testTag = StationTestTags.SEARCH_INPUT)
            .assertTextContains(value = inputKeyword)

        composeTestRule
            .onNodeWithTag(testTag = StationTestTags.SEARCH_CLEAR_ICON)
            .performClick()

        // 입력창이 비워졌는지 검증
        val editableText = composeTestRule
            .onNodeWithTag(StationTestTags.SEARCH_INPUT)
            .fetchSemanticsNode()
            .config[SemanticsProperties.EditableText]
            .text

        assertEquals("", editableText)
    }



    private fun createViewModel(
        searchableStations: List<StationModel> = emptyList(),
        initialLikeStations: List<StationModel> = emptyList(),
        initialKeywords: List<KeywordModel> = emptyList(),
    ): StationViewModel {
        val stationRepository = FakeStationRepository(stations = searchableStations)
        val likeStationRepository = FakeLikeStationRepository(initialStations = initialLikeStations)
        val keywordRepository = FakeKeywordRepository(initialKeywords = initialKeywords)

        return StationViewModel(
            getSearchStationUseCase = GetSearchStationUseCase(stationRepository),
            getLikeStationListUseCase = GetLikeStationListUseCase(likeStationRepository),
            insertKeywordUseCase = InsertKeywordUseCase(keywordRepository),
            getKeywordListUseCase = GetKeywordListUseCase(keywordRepository),
            toggleLikeStationUseCase = ToggleLikeStationUseCase(likeStationRepository),
            deleteKeywordUseCase = DeleteKeywordUseCase(keywordRepository),
            clearAllKeywordUseCase = ClearAllKeywordUseCase(keywordRepository),
            uiEventBus = UiEventBus(),  // @Inject constructor()라 그냥 생성 가능
        )
    }
}