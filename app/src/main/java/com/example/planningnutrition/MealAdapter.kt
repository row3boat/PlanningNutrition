package com.example.planningnutrition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MealAdapter(val context: Context, val meals : MutableList<Meal>)
    : RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    fun clear(){
        meals.clear()
        notifyDataSetChanged()
    }

    fun addAll(mealList : List<Meal>){
        meals.addAll(mealList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_meal,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealAdapter.ViewHolder, position: Int) {
        val meal = meals.get(position)
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val ivImage: ImageView
        val tvCalories: TextView

        init {
            tvName = itemView.findViewById(R.id.tvMealName)
            ivImage = itemView.findViewById(R.id.ivMealPic)
            tvCalories = itemView.findViewById(R.id.tvCalories)
        }

        fun bind(meal: Meal) {
            tvName.text = meal.getName()
            tvCalories.text = "Calories: " + meal.getCalories()
            Glide.with(itemView.context).load(meal.getImage()?.url).into(ivImage)
        }
    }
}