package com.example.zadanie2340_diffutil

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zadanie2340_diffutil.databinding.ActivityMainBinding
import java.util.Collections
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // Объявляем биндинг, как поле класса
    private lateinit var binding: ActivityMainBinding

    // Добавляем adapter как поле класса. Это необходимо, чтобы fun mixing() могла
    // получить к нему доступ. Без этого fun mixing() не знает, какой адаптер использовать.
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Создаем экземпляр binding
        setContentView(binding.root)

        // Инициализация RecyclerView и Adapter
        val initialList = (1..30).toList() // Создаем начальный список чисел от 1 до 30
        adapter = MyAdapter(initialList) // Инициализируем адаптер с начальным списком
        binding.recyclerView.adapter = adapter // Устанавливаем адаптер для RecyclerView
        binding.recyclerView.layoutManager =
            GridLayoutManager(this, 7) // Устанавливаем LayoutManager

        // Вариант с DiffUtil
        fun mixing() {
            val newList = adapter.data.toMutableList() // Создаем MutableList на основе данных адаптера
            val r = Random(System.currentTimeMillis())

            // Перемешиваем список в случайном порядке
            for (i in newList.indices) {
                val newI = r.nextInt(newList.size)
                val temp = newList[i]
                newList[i] = newList[newI]
                newList[newI] = temp
            }
            val numbersDiff = NumbersDiff(adapter.data, newList) // Сравниваем старый список с новым
            val diffResult = DiffUtil.calculateDiff(numbersDiff) // Вычисляем разницу
            adapter.data = newList // Обновляем данные в адаптере
            diffResult.dispatchUpdatesTo(adapter) // Обновляем RecyclerView
        }
        val refresh = findViewById<Button>(R.id.button)  // Используем binding
        binding.button.setOnClickListener { // Используем binding для доступа к кнопке
            mixing()
        }

        // Вариант без DiffUtil
//        fun mixing() {
//            val newList = adapter.data.toMutableList()
//            val r = Random(System.currentTimeMillis())
//            // Использование Collections.shuffle для перемешивания списка
//            newList.shuffle(r)
//
//            adapter.data = newList
//            adapter.notifyDataSetChanged() // Полностью обновляем RecyclerView
//        }
//        binding.button.setOnClickListener {
//            mixing()
//        }
    }
}