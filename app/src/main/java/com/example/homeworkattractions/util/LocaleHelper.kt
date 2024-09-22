package com.example.homeworkattractions.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Locale


object LocaleHelper {
    private lateinit var mSharedPreferences: SharedPreferences
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private const val COUNTRY = "country"
    private const val LANG = "lang"

    fun onAttach(context: Context?): Context? {
        saveSettingLang(context, Locale.getDefault().language, Locale.getDefault().country)
        return initLocale(context, readSettingLang(), readSettingCountry())
    }

    fun initLocale(context: Context?, lang: String, country: String = ""): Context? {
        saveSettingLang(context, lang, country)

        if (isAfter24) {
            return updateResources(context, lang, country)
        }
        return updateResourcesLegacy(context, lang, country)
    }

    private fun updateResources(context: Context?, lang: String, country: String): Context? {
        val locale = Locale(lang, country)
        Locale.setDefault(locale)

        val configuration: Configuration? = context?.resources?.configuration
        if (configuration != null) {
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
        }

        return configuration?.let { context?.createConfigurationContext(it) }
    }

    private fun updateResourcesLegacy(context: Context?, lang: String, country: String): Context? {
        val locale = Locale(lang, country)
        Locale.setDefault(locale)

        val resource = context?.resources

        val configuration: Configuration? = resource?.configuration
        if (configuration != null) {
            configuration.locale = locale
            if (isAfter11)
                configuration.setLayoutDirection(locale)
            resource.updateConfiguration(configuration, resource.displayMetrics)
        }

        return context
    }

    fun readSettingLang(): String {
        return mSharedPreferences.getString(LANG, "") ?: ""
    }

    fun readSettingCountry(): String {
        return mSharedPreferences.getString(COUNTRY, "") ?: ""
    }

    private fun saveSettingLang(context: Context?, lang: String, country: String) {
        if (context != null) {
            mSharedPreferences = context.getSharedPreferences(SELECTED_LANGUAGE, 0)
        }
        mSharedPreferences.edit()
            .putString(LANG, lang)
            .putString(COUNTRY, country)
            .apply()
    }

    fun getCurrentLocale(): String {
        val lang = readSettingLang().lowercase()
        val country = readSettingCountry().lowercase()
        return when (lang) {
            "zh" -> if (country == "tw") "zh-tw" else "zh-cn"
            "en" -> if (country == "us") "en" else "en"
            "ja" -> "ja"
            "ko" -> "ko"
            "es" -> "es"
            "in" -> "id"
            "th" -> "th"
            "vi" -> "vi"
            else -> "en"
        }
    }

    val isAfter24: Boolean
        @RequiresApi(Build.VERSION_CODES.N)
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N


    val isAfter11: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB

}