package com.example.homeworkattractions.view

import android.app.Dialog
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.homeworkattractions.R


abstract class BaseFragment : Fragment() {
    var dialog: Dialog? = null

    fun Fragment.showLoadingDialog() {
        dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(view)
        dialog?.setCancelable(false)

        val loadingText = view.findViewById<TextView>(R.id.loadingText)
        loadingText.text = requireContext().getString(R.string.home_loading_hint)

        dialog?.show()
    }

    fun Fragment.dismissLoadingDialog() {
        if (isAdded && !isRemoving) {
            dialog?.dismiss()
        }
    }
}