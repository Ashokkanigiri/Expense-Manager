package com.ashok.kanigiri.expensemanager.service.room.entity

enum class ExpenseTypes(val expenseId: Int, val expenseLitral: String){
    FOOD(0, "Food"),
    SHOPPING(1, "Shopping"),
    GYM(2, "Gym"),
    MEDICAL(3, "Medical"),
    HOUSE_RENT(4, "House Rent"),
    TRAVEL(5, "Travel"),
    FREE_HAND_MONEY(6, "Free Hand Money"),
    INVESTING(7, "Investing"),
    MONTHLY_EMI(8, "Monthly Expenses"),
    MISCELLANEOUS(9, "Miscellaneous")
}

 enum class BaseEnum