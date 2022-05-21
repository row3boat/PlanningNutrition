package com.example.planningnutrition

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

class AddMealAdapter(val context: Context, val meals : MutableList<Meal>)
    : RecyclerView.Adapter<AddMealAdapter.ViewHolder>() {

    fun clear(){
        meals.clear()
        notifyDataSetChanged()
    }

    fun addAll(mealList : List<Meal>){
        meals.addAll(mealList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMealAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_add_meal,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddMealAdapter.ViewHolder, position: Int) {
        val meal = meals.get(position)
        holder.bind(meal)

        holder.itemView.findViewById<Button>(R.id.btnAddMeal).setOnClickListener(){
            Log.i(TAG, meal.getName().toString())
        }
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val ivImage: ImageView
        val tvCalories: TextView
        val btnAddMeal : Button

        init {
            tvName = itemView.findViewById(R.id.tvMealName)
            ivImage = itemView.findViewById(R.id.ivMealPic)
            tvCalories = itemView.findViewById(R.id.tvCalories)
            btnAddMeal = itemView.findViewById(R.id.btnAddMeal)
        }

        fun bind(meal: Meal) {
            tvName.text = meal.getName()
            tvCalories.text = "Calories: " + meal.getCalories()
            Log.i(TAG, meal.getName() + " " + meal.getImageUrl().toString())
            if (meal.getImage() != null) {
                Glide.with(itemView.context).load(meal.getImage()?.url).into(ivImage)
            }
            else {
                Log.i(TAG, meal.getImageUrl().toString())
                Glide.with(itemView.context).load(meal.getImageUrl()).into(ivImage)
            }
        }
    }
    companion object{
        val TAG = "AddMealAdapter"
    }
}