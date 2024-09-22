package com.example.homeworkattractions.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.ViewCustomToolbarBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: ViewCustomToolbarBinding? = null

    init {
        binding = ViewCustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomToolbar)
            val title = typedArray.getString(R.styleable.CustomToolbar_title)
            val showBackButton =
                typedArray.getBoolean(R.styleable.CustomToolbar_showBackButton, false)
            val showChangeLangButton =
                typedArray.getBoolean(R.styleable.CustomToolbar_showChangeLangButton, false)
            val showGoWebButton =
                typedArray.getBoolean(R.styleable.CustomToolbar_showGoWebButton, false)

            setTitle(title ?: "")
            showBackButton(showBackButton)
            showChangeLangButton(showChangeLangButton)
            showGoWebButton(showGoWebButton)
            typedArray.recycle()
        }

        binding?.imgBack?.setOnClickListener {
            backClickListener?.invoke()
        }
        binding?.imgGlobal?.setOnClickListener {
            changeLaguageClickListener?.invoke()
        }
        binding?.imgWebView?.setOnClickListener {
            goWebClickListener?.invoke()
        }
    }

    var backClickListener: (() -> Unit)? = null
    var changeLaguageClickListener: (() -> Unit)? = null
    var goWebClickListener: (() -> Unit)? = null

    fun setTitle(title: String) {
        binding?.textTitle?.text = title
    }

    fun showBackButton(show: Boolean) {
        binding?.imgBack?.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showChangeLangButton(show: Boolean) {
        binding?.imgGlobal?.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showGoWebButton(show: Boolean) {
        binding?.imgWebView?.visibility = if (show) View.VISIBLE else View.GONE
    }
}