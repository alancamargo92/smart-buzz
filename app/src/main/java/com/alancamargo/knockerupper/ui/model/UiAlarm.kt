package com.alancamargo.knockerupper.ui.model

import android.net.Uri
import android.os.Parcelable
import com.alancamargo.knockerupper.domain.entities.Day
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class UiAlarm(
        val id: String = UUID.randomUUID().toString(),
        val label: String,
        val triggerTime: Long,
        val ringtone: Uri?,
        val frequency: List<Day>,
        val vibrate: Boolean,
        val deleteOnDismiss: Boolean,
        val isActive: Boolean,
        val code: UiCode?
) : Parcelable