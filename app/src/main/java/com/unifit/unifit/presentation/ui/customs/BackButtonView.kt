package com.unifit.unifit.presentation.ui.customs

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.navigation.findNavController
import com.unifit.unifit.R
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper.updateMarginToStatusBarInsets

class BackButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageButton(context, attrs, defStyleAttr) {

    init {
        // Set the image resource
        setImageResource(R.drawable.baseline_arrow_back_ios_24)

        updateMarginToStatusBarInsets(this)

        // Set the content description
        contentDescription = resources.getString(R.string.back_button_description)

        // Set the click listener to navigate up in the navigation graph
        setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
