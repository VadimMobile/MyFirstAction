package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = :: requireParentFragment)
        arguments?.textArg?.let { binding.edit.setText(it) }
        binding.edit.requestFocus()
        binding.ok.setOnClickListener{
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                viewModel.saveContent(text)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object{
        var Bundle.textArg by StringArg
    }

}
