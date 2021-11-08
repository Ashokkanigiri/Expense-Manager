package com.ashok.kanigiri.expensemanager.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes

@BindingAdapter("setup_progressbar_tint")
fun setUpHomeProgressTint(view: ProgressBar?, progress: Int) {
    view?.let {
        when {
            progress > 80 -> {
                view.progressTintList = ColorStateList.valueOf(Color.parseColor("#a6f1a6"))
            }
            (progress > 30 && progress < 80) -> {
                view.progressTintList = ColorStateList.valueOf(Color.parseColor("#F1948A"))
            }
            progress < 30 -> {
                view.progressTintList = ColorStateList.valueOf(Color.parseColor("#F0F0F0"))
            }
        }
    }
}

@BindingAdapter("set_expense_title_background")
fun ConstraintLayout.setUpExpenseTileBackground(expenseCategory: String) {
    this.setBackgroundResource(
        when (expenseCategory) {
            ExpenseTypes.FOOD.expenseLitral -> R.color.food_expense_colour_bg
            ExpenseTypes.SHOPPING.expenseLitral -> R.color.shopping_expense_colour_bg
            ExpenseTypes.GYM.expenseLitral -> R.color.gym_expense_colour_bg
            ExpenseTypes.MEDICAL.expenseLitral -> R.color.medical_expense_colour_bg
            ExpenseTypes.HOUSE_RENT.expenseLitral -> R.color.house_rent_expense_colour_bg
            ExpenseTypes.TRAVEL.expenseLitral -> R.color.travel_expense_colour_bg
            ExpenseTypes.FREE_HAND_MONEY.expenseLitral -> R.color.free_hand_money_expense_colour_bg
            ExpenseTypes.INVESTING.expenseLitral -> R.color.investing_expense_colour_bg
            ExpenseTypes.MONTHLY_EMI.expenseLitral -> R.color.emi_expense_colour_bg
            ExpenseTypes.MISCELLANEOUS.expenseLitral -> R.color.misc_expense_colour_bg
            else -> R.color.black
        }
    )
}

@BindingAdapter("set_expense_category_image")
fun ImageView.setImageResource(expenseCategory: String) {
    this.setImageResource(
        when (expenseCategory) {
            ExpenseTypes.FOOD.expenseLitral -> R.drawable.ic_baseline_fastfood_24
            ExpenseTypes.SHOPPING.expenseLitral -> R.drawable.ic_baseline_shopping_basket_24
            ExpenseTypes.GYM.expenseLitral -> R.drawable.ic_baseline_accessibility_new_24
            ExpenseTypes.MEDICAL.expenseLitral -> R.drawable.ic_baseline_medical_services_24
            ExpenseTypes.HOUSE_RENT.expenseLitral -> R.drawable.ic_baseline_house_24
            ExpenseTypes.TRAVEL.expenseLitral -> R.drawable.ic_baseline_emoji_transportation_24
            ExpenseTypes.FREE_HAND_MONEY.expenseLitral -> R.drawable.ic_outline_money_24
            ExpenseTypes.INVESTING.expenseLitral -> R.drawable.ic_baseline_monetization_on_24
            ExpenseTypes.MONTHLY_EMI.expenseLitral -> R.drawable.ic_baseline_payments_24
            ExpenseTypes.MISCELLANEOUS.expenseLitral -> R.drawable.ic_baseline_kitesurfing_24
            else -> R.color.black
        }
    )
}


@BindingAdapter("set_total_expense_progress", "set_utilized_expense_progress")
fun ProgressBar.setUpProgress(totalExpense: String, utilizedExpense: String) {
    val percent = ((utilizedExpense.toDouble() / totalExpense.toDouble()) * 100).toInt()
    this.progress = percent
    this.progressTintList = when {
        percent > 80 -> {
            ColorStateList.valueOf(Color.parseColor("#EC1F1F"))
        }
        (percent > 30 && percent < 80) -> {
            ColorStateList.valueOf(Color.parseColor("#F1948A"))
        }
        percent < 30 -> {
            ColorStateList.valueOf(Color.parseColor("#a6f1a6"))
        }
        else -> ColorStateList.valueOf(Color.parseColor("#EC1F1F"))
    }
}

@BindingAdapter("setRandomBackground")
fun ConstraintLayout.setRandomBackground(expenseCategory: String) {
    val list = listOf<ColorStateList>(
        ColorStateList.valueOf(Color.parseColor("#C0BDFF")),
        ColorStateList.valueOf(Color.parseColor("#CAF1DE")),
        ColorStateList.valueOf(Color.parseColor("#E1F8DC")),
        ColorStateList.valueOf(Color.parseColor("#FEF8DD")),
        ColorStateList.valueOf(Color.parseColor("#FFE7C7")),
        ColorStateList.valueOf(Color.parseColor("#B7E5EB")),
        ColorStateList.valueOf(Color.parseColor("#FFE4E1")),
    )

    this.setBackgroundTintList(list.random());
}
