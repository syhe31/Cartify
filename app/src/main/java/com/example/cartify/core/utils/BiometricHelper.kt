package com.example.cartify.core.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricHelper {

    fun authenticate(
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        onCancel: () -> Unit
    ) {
        val activity = context.findActivity()

        when {
            activity == null -> {
                onError("無法取得 Activity")
            }

            activity is FragmentActivity -> {
                // 支援 BiometricPrompt
                authenticateWithBiometricPrompt(activity, onSuccess, onError, onCancel)
            }

            else -> {
                // ComponentActivity 或其他，提供替代方案
                onError("此功能需要更新應用程式版本以支援完整的生物辨識")
                // 或者提供其他驗證方式
            }
        }
    }

    private fun authenticateWithBiometricPrompt(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        onCancel: () -> Unit
    ) {
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricPrompt(activity, onSuccess, onError, onCancel)
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                onError("此設備不支援生物辨識")
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                onError("生物辨識硬體暫時無法使用")
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                onError("未設定任何生物辨識方式")
            }

            else -> {
                onError("生物辨識暫時無法使用")
            }
        }
    }

    private fun showBiometricPrompt(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        onCancel: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED,
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> onCancel()

                        else -> onError(errString.toString())
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("辨識失敗，請重試")
                }
            }
        )

        // 設定標題內容
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("生物辨識驗證")
            .setSubtitle("請使用您的指紋進行驗證")
            .setNegativeButtonText("取消")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

// 擴展函數
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
