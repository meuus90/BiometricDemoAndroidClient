package com.demo.biometric.data.remote.api

import com.demo.biometric.data.entity.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RestAPI {
    @POST("enroll/request")
    suspend fun requestEnroll(@Body requestEnrollData: RequestEnrollData): EnrollCode

    @POST("enroll/verify")
    suspend fun verifyEnroll(@Body verifyEnrollData: VerifyEnrollData): ResponseResult

    @GET("challenge/request")
    suspend fun requestChallenge(@Query("uuid") uuid: String): Challenge

    @POST("challenge/verify")
    suspend fun verifyChallenge(@Body signedData: SignedData): ResponseResult
}