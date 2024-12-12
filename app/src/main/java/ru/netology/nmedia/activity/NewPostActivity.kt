package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra(Intent.EXTRA_TEXT)
        val inputContent = intent.getStringExtra(Intent.EXTRA_TEXT)
        binding.edit.setText(inputContent)
        binding.ok.setOnClickListener{
            val text = binding.edit.text.toString()
            if (text.isBlank()){
                setResult(RESULT_CANCELED)
            } else{
                setResult(RESULT_OK, Intent().apply { putExtra("text", text) })
            }
            finish()
        }
    }
}

object NewPostContract: ActivityResultContract<String?, String?>(){
    override fun createIntent(context: Context, input: String?) = Intent(context, NewPostActivity::class.java)
        .putExtra(Intent.EXTRA_TEXT, input)

    override fun parseResult(resultCode: Int, intent: Intent?) = intent?.getStringExtra("text")

}