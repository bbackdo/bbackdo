package com.bback.bbackdo

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this@KakaoApplication,"f34797735d54a48073a66f9a9c5ab107")
    }

}