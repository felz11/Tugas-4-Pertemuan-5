package com.unsoed.informatikamobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
// import androidx.fragment.app.FragmentManager // No longer needed here as BottomSheetDialogFragment has its own show method
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.informatikamobile.adapter.BookAdapter
import com.unsoed.informatikamobile.adapter.OnBookClickListener
import com.unsoed.informatikamobile.data.model.BookDoc
import com.unsoed.informatikamobile.databinding.ActivityDaftarBukuBinding
import com.unsoed.informatikamobile.viewmodel.MainViewModel
import com.unsoed.informatikamobile.ui.fragment.BookDetailFragment

// Removed unused extension function
// private fun BookDetailFragment.show(
//    supportFragmentManager: FragmentManager,
//    tag: String
// ) {
// }

class DaftarBukuActivity : AppCompatActivity(), OnBookClickListener {

    private lateinit var binding: ActivityDaftarBukuBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = BookAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.books.observe(this) { books ->
            adapter.setData(books)
        }

        viewModel.fetchBooks("kotlin programming")
    }

    override fun onBookClick(book: BookDoc) {
        book.let { b ->
            val fragment = BookDetailFragment.newInstance(
                title = b.title ?: "-",
                author = b.authorName?.joinToString(separator = ", ") ?: "Unknown Author",
                year = b.firstPublishYear?.toString() ?: "N/A", // Added null safety for year
                coverId = b.coverId ?: 0
            )
            fragment.show(supportFragmentManager, BookDetailFragment::class.java.simpleName)
        }
    }
}
