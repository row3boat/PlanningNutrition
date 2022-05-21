package com.example.planningnutrition
import com.parse.*

@ParseClassName("Meal")
class Meal : ParseObject() {

    fun getName(): String? {
        return getString(KEY_NAME)
    }

    fun setName(name: String){
        put(KEY_NAME, name)
    }

    fun getImage(): ParseFile?{
        return getParseFile(KEY_IMAGE)
    }
    fun getImageUrl() : String?{
        return getString(KEY_URL)
    }

    fun setImage(parsefile: ParseFile){
        put(KEY_IMAGE, parsefile)
    }

    fun setImageURL(url: String){
        put(KEY_URL, url)
    }

    fun getCalories(): String? {
        return getString(KEY_CALORIES)
    }

    fun setCalories(calories: String){
        put(KEY_CALORIES, calories)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser){
        put(KEY_USER, user)
    }

    companion object {
        const val KEY_USER = "user"
        const val KEY_NAME = "name"
        const val KEY_IMAGE =  "image"
        const val KEY_CALORIES = "calories"
        const val KEY_URL  = "imageURL"
    }
}