package com.example.fit_nut.home.home_fragment

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import com.example.fit_nut.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.roundToInt

class AddWater : BottomSheetDialogFragment() {
    private var listener: OnAddWaterListener? = null


    lateinit var waterAmount : EditText
    lateinit var saveWater : AppCompatButton
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view= inflater.inflate(R.layout.add_water, container, false)
        waterAmount=view.findViewById(R.id.water_amount)
        saveWater = view.findViewById(R.id.save_water)
        saveWater.setOnClickListener {
            val waterAmountText = waterAmount.text.toString()
            if (waterAmountText.isNotEmpty()) {
                val amountInLiters = waterAmountText.toFloat() // القيمة المدخلة بالليتر
                val amountInMilliliters = amountInLiters / 1000 // تحويلها إلى مليلتر
                listener?.onWaterAdded(amountInMilliliters) // إرسال القيمة المحولة
                dismiss()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = dialog as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheet?.setBackgroundResource(R.drawable.rounded_bottom_sheet_background)

        }
        val behavior = dialog.behavior
        val layoutParams = view.layoutParams
        val displayMetrics = Resources.getSystem().displayMetrics
        layoutParams.height = (displayMetrics.heightPixels * 0.4).toInt()


        view.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
    fun setOnAddWaterListener(listener: OnAddWaterListener) {
        this.listener = listener
    }


}
