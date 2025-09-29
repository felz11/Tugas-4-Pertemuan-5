package com.unsoed.informatikamobile.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.unsoed.informatikamobile.R
import com.unsoed.informatikamobile.databinding.FragmentBookDetailBinding

class BookDetailFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    private var bookTitle: String? = null
    private var bookAuthor: String? = null
    private var bookYear: String? = null
    private var bookCoverId: Int = 0

    override fun getTheme(): Int = R.style.ThemeOverlay_App_BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookTitle = it.getString(ARG_TITLE)
            bookAuthor = it.getString(ARG_AUTHOR)
            bookYear = it.getString(ARG_YEAR)
            bookCoverId = it.getInt(ARG_COVER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        if (!isAdded) return // Ensure fragment is attached to a context

        binding.textViewTitle.text = bookTitle ?: "N/A"
        binding.textViewAuthor.text = bookAuthor ?: "N/A"
        binding.textViewYear.text = bookYear ?: "N/A"

        if (bookCoverId != 0) {
            val url = "https://covers.openlibrary.org/b/id/$bookCoverId-M.jpg"
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.book_not_found) 
                .error(R.drawable.book_not_found) 
                .into(binding.imageViewCover)
        } else {
            binding.imageViewCover.setImageResource(R.drawable.book_not_found)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_AUTHOR = "arg_author"
        private const val ARG_YEAR = "arg_year"
        private const val ARG_COVER_ID = "arg_cover_id"

        @JvmStatic
        fun newInstance(title: String, author: String, year: String, coverId: Int) =
            BookDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_AUTHOR, author)
                    putString(ARG_YEAR, year)
                    putInt(ARG_COVER_ID, coverId)
                }
            }
    }
}
