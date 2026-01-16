package com.example.checkboxsnackbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextET: EditText
    private lateinit var outputTextTV: TextView

    private lateinit var saveBTN: Button
    private lateinit var deleteBTN: Button

    private lateinit var toolbarMain: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)

        inputTextET = findViewById(R.id.inputTextET)
        outputTextTV = findViewById(R.id.outputTextTV)

        saveBTN = findViewById(R.id.saveBTN)
        deleteBTN = findViewById(R.id.deleteBTN)

        saveBTN.setOnClickListener { saveData() }

        deleteBTN.setOnClickListener { showDeleteConfirmationDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.item_exit -> {
                showExitConfirmationDialog()
                Toast.makeText(
                    this,
                    "Выход из приложения",
                    Toast.LENGTH_LONG
                )
                    .show()
                true
            }

            R.id.item_settings -> {
                Toast.makeText(
                    this,
                    "Настройки открыты",
                    Toast.LENGTH_LONG
                )
                    .show()
                true
            }

            R.id.item_search -> {
                Toast.makeText(
                    this,
                    "Открыт поиск",
                    Toast.LENGTH_LONG
                )
                    .show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    //Метод для сохранения данных из поля ввода вполе вывода
    private fun saveData() {
        val inputText = inputTextET.text.toString().trim()

        if (inputText.isNotEmpty()) {
            //Если в поле вывода уже есть текст, добавляем новую строку

            val currentText = outputTextTV.text.toString()
            val newText = if (currentText.isEmpty()) {
                inputText
            } else {
                "$currentText\n$inputText"
            }

            outputTextTV.text = newText
            inputTextET.text.clear()

            //Показываю Snackbar с сообщением о сохранении

            Snackbar.make(
                findViewById(android.R.id.content),
                "Данные сохранены",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            //если поле ввода пустое, показываем сообщение
            Snackbar.make(
                findViewById(android.R.id.content),
                "Введите данные для сохранения",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
    //Метод для показа диалога подтверждения удаления
    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Подтвердите удаление")
        builder.setMessage("Вы уверены, что хотите удалить все данные?")

        //Кнопка "удалить"
        builder.setPositiveButton("Удалить") {dialog, which ->
            deleteData()
        }
        //Кнопка "Отмена"
        builder.setNegativeButton("Отмена") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    //Метод для показа диалога подтверждения выхода
    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Подтвердите удаление")
        builder.setMessage("Вы уверены, что хотите выйти из приложения?")

        //Кнопка "Выйти"
        builder.setPositiveButton("Выйти") {dialog, which ->
            finish() //закрываем приложение
        }
        //Кнопка "Отмена"
        builder.setNegativeButton("Отмена") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
    //Метод для удаления данных из поля вывода

    private fun deleteData() {
        //Очищаем поле вывода

        outputTextTV.text = ""

        //Показываем Snackbar с сообщением об удалении

        Snackbar.make(
            findViewById(android.R.id.content),
            "Данные удалены",
            Snackbar.LENGTH_LONG
        ).apply {
            //Добавляем действие "Отменить" в Snackbar
            setAction("Отменить") {
                //Восстанавливаем данные
                outputTextTV.text = "Данные восстановлены"
            }
            show()
        }
    }
}