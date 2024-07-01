package ru.itis.summerpractice2024

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

open class Car(
    val brand: String,
    val model: String,
    val year: Int,
    val dye: String,
) {
    open fun carInfo() {
        println("Информация о машине: $brand, $model, $dye, $year")
    }

    override fun toString(): String {
        return "$brand $model ($year)"
    }
}

class Sedan(
    brand: String,
    model: String,
    year: Int,
    dye: String,
    val numberOfDoors: Int,
) : Car(brand, model, year, dye)

class Sport(
    brand: String,
    model: String,
    year: Int,
    dye: String,
    val isCabriolet: Boolean,
    val fullSpeed: Int,
) : Car(brand, model, year, dye)

class FourWD(
    brand: String,
    model: String,
    year: Int,
    dye: String,
    val mileage: Int,
    val enginesType: String,
) : Car(brand, model, year, dye)

class Cabriolet(
    brand: String,
    model: String,
    year: Int,
    dye: String,
    val seats: Int,
) : Car(brand, model, year, dye)

class CarBuilder(
    private var brand: String = "",
    private var model: String = "",
    private var year: Int = 0,
    private var dye: String = ""
) {
    fun setBrand(brand: String) = apply { this.brand = brand }
    fun setModel(model: String) = apply { this.model = model }
    fun setYear(year: Int) = apply { this.year = year }
    fun setDye(dye: String) = apply { this.dye = dye }

    fun build(): Car {
        return Car(brand, model, year, dye)
    }
}

fun comparison(car1: Car, car2: Car): Car {
    return if (car1.year > car2.year) car1 else car2
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val numCarsEditText = findViewById<EditText>(R.id.numCarsEditText)
        val startRaceButton = findViewById<Button>(R.id.startRaceButton)
        val raceResultsTextView = findViewById<TextView>(R.id.raceResultsTextView)

        startRaceButton.setOnClickListener {
            val numCars = numCarsEditText.text.toString().toInt()
            val cars = (1..numCars).map { randomCar() }.toMutableList()
            val results = StringBuilder()
            while (cars.size > 1) {
                val races = cars.chunked(2)
                val winners = mutableListOf<Car>()

                for ((index, comparison) in races.withIndex()) {
                    if (comparison.size == 2) {
                        val winner = comparison(comparison[0], comparison[1])
                        results.append("Гонка между ${comparison[0]} и ${comparison[1]}, Победитель: ${winner}\n")
                        println("Гонка между ${comparison[0]} и ${comparison[1]}, Победитель: ${winner}\n")
                        winners.add(winner)
                    } else {
                        results.append("${comparison[0]} - Нет пары, следующий круг\n")
                        println("${comparison[0]} - Нет пары, следующий круг\n")
                        winners.add(comparison[0])
                    }
                }

                cars.clear()
                cars.addAll(winners)
            }

            results.append("Финалист: ${cars[0]}")
            raceResultsTextView.text = results.toString()
            println("Финалист: ${cars[0]}")
        }
    }

    private fun randomCar(): Car {
        val brands = listOf("Mercedes", "BMW", "Lada", "Ferrari", "Chevrolet")
        val models = listOf("Model A", "Model B", "Model C")
        val years = (2000..2020).toList()
        val dyes = listOf("Black", "Gray", "White", "Orange")

        return Car(
            brand = brands.random(),
            model = models.random(),
            year = years.random(),
            dye = dyes.random()
        )
    }
}
