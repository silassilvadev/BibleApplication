package com.studies.bibleapplication.ui.call

import androidx.biometric.BiometricPrompt

interface IBiometricResponse {
    fun onBiometricError(errorCode: Int, errMessage: String, nothing: Nothing?)
    fun onBiometricResponse(result: BiometricPrompt.AuthenticationResult)
}