package com.chiraranw.msbookservice.model

//data class Member(var id: Int, var name: String)
data class Book(var id: Int, var title: String)
data class ReadRecord(var id: Int, var mbId: Int, var bkId: Int)