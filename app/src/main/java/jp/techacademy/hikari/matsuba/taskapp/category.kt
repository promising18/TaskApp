package jp.techacademy.hikari.matsuba.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListAdapter
import android.widget.ListView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import jp.techacademy.hikari.matsuba.taskapp.databinding.ActivityCategoryBinding
import jp.techacademy.hikari.matsuba.taskapp.databinding.ActivityInputBinding

class category : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var realm: Realm
    private lateinit var task: Task
    private lateinit var category: category
    private lateinit var taskAdapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(this)
        binding.listView.adapter = taskAdapter

        binding.search.setOnClickListener {
            val config = RealmConfiguration.create(schema = setOf(Task::class))
            realm = Realm.open(config)
            val extraction = binding.editText.text.toString()
            val book = realm.query<Task>("category=$0",extraction).find()
            taskAdapter.updateTaskList(book)
        }

        binding.listView.setOnItemClickListener { parent, _, position, _ ->
            // 入力・編集する画面に遷移させる
            val task = parent.adapter.getItem(position) as Task
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra(EXTRA_TASK, task.id)
            startActivity(intent)
        }
    }
}
