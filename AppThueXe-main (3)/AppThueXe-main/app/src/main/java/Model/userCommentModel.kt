package Model

import android.media.Image

public class userCommentModel {
    private lateinit var userName: String
    private var userAvatar: Int
    private lateinit var userComment: String

    constructor(userName: String, userAvatar: Int, userComment: String) {
        this.userName = userName
        this.userAvatar = userAvatar
        this.userComment = userComment
    }

    fun userCommentModel(_useName: String, _userAvatar: Int, _userComment: String){
        this.userName = _useName
        this.userAvatar = _userAvatar
        this.userComment = _userComment
    }

    fun get_userName() : String{
        return this.userName
    }

    fun set_userName(_useName: String){
        this.userName = _useName
    }

    fun get_userAvatar() : Int{
        return this.userAvatar
    }

    fun set_userAvatar(_userAvatar: Int) {
        this.userAvatar = _userAvatar
    }

    fun get_userComment() : String {
        return this.userComment
    }

    fun set_userComment(_userComment: String) {
        this.userComment = _userComment
    }
}