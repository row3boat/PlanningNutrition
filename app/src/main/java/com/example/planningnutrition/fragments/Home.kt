package com.example.planningnutrition.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planningnutrition.Meal
import com.example.planningnutrition.MealAdapter
import com.example.planningnutrition.R
import com.parse.*


open class Home : Fragment() {

    lateinit var mealsRecyclerView: RecyclerView
    lateinit var adapter: MealAdapter
    var allMeals:MutableList<Meal> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mealsRecyclerView = view.findViewById(R.id.rvHome)

        adapter = MealAdapter(requireContext(),allMeals)

        mealsRecyclerView.adapter = adapter

        mealsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryMeals()

    }

    open fun queryMeals() {
        val query: ParseQuery<Meal> = ParseQuery.getQuery(Meal::class.java).whereEqualTo("user", ParseUser.getCurrentUser())
        query.setLimit(20)
        query.include(Meal.KEY_USER)
        query.addAscendingOrder("createdAt")
        query.findInBackground(object: FindCallback<Meal> {
            override fun done(meals: MutableList<Meal>?, e: ParseException?) {
                if (e!= null){
                    Log.e(TAG, "Error fetching posts")
                }else{
                    if (meals!= null){
                        for (meal in meals) {
                            Log.i(TAG, "Meal: " + meal.getName() + "; calories: " + meal.getCalories())
                        }
                        allMeals.addAll(meals)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    companion object {
        const val TAG = "HomeFragment"
    }

}