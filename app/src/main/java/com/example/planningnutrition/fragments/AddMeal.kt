package com.example.planningnutrition.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.planningnutrition.AddMealAdapter
import com.example.planningnutrition.Meal
import com.example.planningnutrition.MealAdapter
import com.example.planningnutrition.R
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets


open class AddMeal : Fragment() {

    lateinit var mealsRecyclerView: RecyclerView
    lateinit var adapter: AddMealAdapter
    var allMeals:MutableList<Meal> = mutableListOf()
    var photoFile: File? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_meal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mealsRecyclerView = view.findViewById(R.id.rvAddMeals)

        adapter = AddMealAdapter(requireContext(),allMeals)

        mealsRecyclerView.adapter = adapter

        mealsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<Button>(R.id.buttonSearchButton).setOnClickListener{
            queryMeal(view.findViewById<EditText>(R.id.etSearch).text.toString())
        }

        view.findViewById<Button>(R.id.buttonCustomMeal).setOnClickListener(){

            val newFragment = CustomMeal()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flContainer,newFragment)
            transaction.commit()
        }

    }

    open fun queryMeal(name : String) {

        allMeals.clear()

        Log.i(TAG, "button clicked, method launched")

        val queue = Volley.newRequestQueue(requireContext())

        val params = JSONObject()
        params.put("query",name)

        val url = "https://trackapi.nutritionix.com/v2/search/instant?query=$name"
        Log.i(TAG,url + params.toString())

// ...
        val volleyEnrollRequest =
            object : JsonObjectRequest(Request.Method.GET, url,
                null,
                Response.Listener { response ->

                    val jsonArray = response.getJSONArray("branded")
                    Log.i(TAG,jsonArray.toString())
                    val mealList  = ArrayList<Meal>()
                    for (i in 0 until 20){
                        val mealToAdd =  Meal()

                        val foodName = jsonArray.getJSONObject(i).getString("food_name")
                        val calories = jsonArray.getJSONObject(i).getString("nf_calories")
                        val stringUrl = (jsonArray.getJSONObject(i).getJSONObject("photo").getString("thumb").replace("\\",""))

                        mealToAdd.setImageURL(stringUrl)
                        mealToAdd.setName(foodName)
                        mealToAdd.setCalories(calories)

                        //val foodImage = ParseFile((jsonArray.getJSONObject(i).getString("photo")))
                        mealList.add(mealToAdd)
                        adapter.notifyDataSetChanged()
                    }
                    allMeals.addAll(mealList)
                    for (meal in allMeals){
                        Log.i(TAG, meal.getImageUrl().toString())
                    }
                },

                Response.ErrorListener { e->
                    try {
                        val responseBody = String(e.networkResponse.data,StandardCharsets.UTF_8)
                        val data = JSONObject(responseBody)
                        val errors = data.getJSONArray("errors")
                        val jsonMessage = errors.getJSONObject(0)
                        val message = jsonMessage.getString("message")
                        Log.e(TAG, message)
                    } catch (e: JSONException) {
                        Log.e(TAG,e.toString())
                    } catch (errorr: UnsupportedEncodingException) {
                        Log.e(TAG,e.toString())
                    }
                }
            ) {
                // Providing Request Headers
                override fun getHeaders(): Map<String, String> {
                    // Create HashMap of your Headers as the example provided below

                    val headers = HashMap<String, String>()
                    headers.put("Content-Type","application/json")
                    headers.put("x-app-id","66a8a7c1")
                    headers.put("x-app-key","5e49057e38e68354e0682b60318d9780")

                    Log.i(TAG,headers.toString())
                    return headers
                }
            }
        queue.add(volleyEnrollRequest)
    }


    companion object {
        const val TAG = "AddMeal Fragment"
    }



}
