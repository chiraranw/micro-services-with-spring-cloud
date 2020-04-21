package com.chiraranw.mslibrary.model

data class Member(var id: Int = 0, var name: String = "Default Member")
data class Book(var id: Int = 0, var title: String = "Default Boot")
data class Reading(var member: Member? = null, var bks: List<Book>? = null)