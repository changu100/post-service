package com.example.sessionpostservice.post.controller.response

data class PostResponse (
    val id: Long,
    val title: String,
    val content: String
){
    companion object {
        fun getMock(): PostResponse {
            return PostResponse(
                id = 1,
                title = "1223",
                content = "123"
            )
        }
    }
}