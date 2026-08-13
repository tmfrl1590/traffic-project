package com.system.traffic.presentation.fake

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.core.enums.AppThemeType
import com.system.traffic.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 테스트용 DataStoreRepository fake.
 * 인메모리로 실제처럼 동작한다: set하면 해당 Flow가 바뀐 값을 방출한다.
 * 기본값은 "최초 로그인이 아닌 상태"(초기화 스킵).
 */
class FakeDataStoreRepository(
    private val isFirstLogin: Boolean = false,
    initialFontSize: AppFontSize = AppFontSize.MEDIUM,
    initialThemeType: AppThemeType = AppThemeType.LIGHT,
) : DataStoreRepository {

    private val fontSize = MutableStateFlow(initialFontSize)
    private val themeType = MutableStateFlow(initialThemeType)

    override suspend fun setUpIsFirstLogin() = Unit

    override suspend fun getIsFirstLogin(): Boolean = isFirstLogin

    override suspend fun setAppFontSize(fontSize: AppFontSize) {
        this.fontSize.value = fontSize
    }

    override fun getAppFontSize(): Flow<AppFontSize> = fontSize

    override suspend fun setAppThemeType(themeType: AppThemeType) {
        this.themeType.value = themeType
    }

    override fun getAppThemeType(): Flow<AppThemeType> = themeType
}
