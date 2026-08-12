package com.system.traffic.presentation.screens.home

import androidx.annotation.StringRes
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.system.traffic.design.R
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.system.traffic.domain.usecase.like.ToggleLikeStationUseCase
import com.system.traffic.presentation.fake.FakeLikeStationRepository
import com.system.traffic.presentation.screens.home.viewmodel.HomeViewModel
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun 홈화면이_정상적으로_표시된다(){
        composeTestRule.setContent {
            HomeScreenRoute(
                homeViewModel = createViewModel()
            )
        }

        composeTestRule
            .onNodeWithTag(testTag = HomeTestTags.LIKE_TEXT)
            .assertIsDisplayed()
    }

    @Test
    fun 저장된_즐겨찾기가_없으면_빈안내가_보이고_리스트는_보이지_않는다(){
        composeTestRule.setContent {
            HomeScreenRoute(
                homeViewModel = createViewModel()
            )
        }

        composeTestRule
            .onNodeWithTag(testTag = HomeTestTags.EMPTY_LIKE)
            .assertIsDisplayed()
            .assert(matcher = hasAnyDescendant(matcher = hasText(getString(id = R.string.like_no_data))))
            .assert(matcher = hasAnyDescendant(matcher = hasText(getString(id = R.string.home_empty_action_search))))

        composeTestRule
            .onNodeWithTag(testTag = HomeTestTags.LIKE_STATION_LIST)
            .assertIsNotDisplayed()
    }

    @Test
    fun 즐겨찾기가_없을때_정류장_검색하러가기를_누르면_검색화면_이동_콜백이_호출된다(){
        var isGotoStation = false

        composeTestRule.setContent {
            HomeScreenRoute(
                homeViewModel = createViewModel(),
                onGotoStation = {
                    isGotoStation = true
                }
            )
        }

        composeTestRule
            .onNodeWithText(getString(id = R.string.home_empty_action_search))
            .performClick()

        assertTrue(isGotoStation)
    }

    @Test
    fun 즐겨찾기가_있으면_빈안내가_보이지않고_즐겨찾기_리스트가_보인다(){
        composeTestRule.setContent {
            HomeScreenRoute(
                homeViewModel = createViewModel(
                    initialStations = listOf(테스트_정류장)
                )
            )
        }

        composeTestRule
            .onNodeWithTag(testTag = HomeTestTags.EMPTY_LIKE)
            .assertIsNotDisplayed()

        composeTestRule
            .onNodeWithTag(testTag = HomeTestTags.LIKE_STATION_LIST)
            .assertIsDisplayed()
    }

    @Test
    fun 즐겨찾기_리스트에서_정류장_클릭시_이동콜백이_호출된다(){
        var isGotoBusArrive = false

        composeTestRule.setContent {
            HomeScreenRoute(
                onStationCardClick = { _, _ ->
                    isGotoBusArrive = true
                },
                homeViewModel = createViewModel(
                    initialStations = listOf(테스트_정류장)
                )
            )
        }

        composeTestRule
            .onNodeWithTag(testTag = HomeTestTags.STATION_CARD)
            .performClick()

        assertTrue(isGotoBusArrive)
    }

    @Test
    fun 저장된_즐겨찾기_데이터가_3개이면_리스트에_3개가_보인다(){
        val testStations = listOf(
            테스트_정류장,  // arsId = "5001", 광주역, selected = true
            테스트_정류장.copy(arsId = "5002", stationNum = "5678", busStopName = "광주송정역"),
            테스트_정류장.copy(arsId = "5003", stationNum = "9101", busStopName = "광주공항"),
        )

        composeTestRule.setContent {
            HomeScreenRoute(
                homeViewModel = createViewModel(
                    initialStations = testStations
                )
            )
        }

        composeTestRule
            .onAllNodesWithTag(testTag = HomeTestTags.STATION_CARD)
            .assertCountEquals(3)
    }

    @Test
    fun 즐겨찾기_리스트에서_즐겨찾기_아이콘_클릭시_즐겨찾기가_해제된다(){
        val testStations = listOf(
            테스트_정류장,  // arsId = "5001", 광주역, selected = true
            테스트_정류장.copy(arsId = "5002", stationNum = "5678", busStopName = "광주송정역"),
            테스트_정류장.copy(arsId = "5003", stationNum = "9101", busStopName = "광주공항"),
        )

        composeTestRule.setContent {
            HomeScreenRoute(
                homeViewModel = createViewModel(
                    initialStations = testStations
                )
            )
        }

        // 첫 번째 아이템(광주역)의 즐겨찾기 아이콘 클릭
        composeTestRule
            .onAllNodesWithTag(testTag = HomeTestTags.STATION_CARD_FAVORITE)[0]
            .performClick()

        // 화면 기준으로 검증: 광주역만 사라지고 2개 남는다
        composeTestRule.onNodeWithText("광주역").assertDoesNotExist()
        composeTestRule
            .onAllNodesWithTag(testTag = HomeTestTags.STATION_CARD_FAVORITE)
            .assertCountEquals(2)
    }

    private fun createViewModel(
        initialStations: List<StationModel> = emptyList(),
    ): HomeViewModel {
        val likeStationRepository = FakeLikeStationRepository(
            initialStations = initialStations
        )
        return HomeViewModel(
            getLikeStationListUseCase = GetLikeStationListUseCase(likeStationRepository),
            toggleLikeStationUseCase = ToggleLikeStationUseCase(likeStationRepository),
        )
    }

    // 테스트 데이터
    private val 테스트_정류장 = StationModel(
        stationNum = "1234",
        busStopName = "광주역",
        nextBusStop = "다음정류장",
        busStopId = "GJB123",
        arsId = "5001",
        longitude = "126.9",
        latitude = "35.1",
        selected = true,  // 즐겨찾기 상태
    )

    private fun getString(@StringRes id: Int): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
}