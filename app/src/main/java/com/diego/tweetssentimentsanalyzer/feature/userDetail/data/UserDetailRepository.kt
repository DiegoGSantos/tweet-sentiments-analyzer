package com.diego.tweetssentimentsanalyzer.feature.userDetail.data

import io.reactivex.Observable

class UserDetailRepository(private val searchUserRemoteDataSource: UserDetailRemoteDataSource) {
    fun getUserTweets(userName: String): Observable<List<Tweet>> {
        return searchUserRemoteDataSource.getUserTweets(userName)
    }
}