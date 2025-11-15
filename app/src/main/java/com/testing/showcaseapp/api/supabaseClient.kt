package com.testing.showcaseapp.api

import com.testing.showcaseapp.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

val supabase = createSupabaseClient(
    supabaseUrl = BuildConfig.API_URL,
    supabaseKey = BuildConfig.API_KEY
) {
    install(Auth){
        flowType = FlowType.PKCE
        // Enable auto-refresh of tokens
        autoLoadFromStorage = true
        autoSaveToStorage = true
        // Customize token refresh behavior
        alwaysAutoRefresh = true
    }
//    install(Postgrest)
//    install(Storage)
}
