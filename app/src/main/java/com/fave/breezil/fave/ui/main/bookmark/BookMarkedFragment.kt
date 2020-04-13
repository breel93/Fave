package com.fave.breezil.fave.ui.main.bookmark


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.BookMarkClickListener
import com.fave.breezil.fave.callbacks.BookMarkLongClickListener
import com.fave.breezil.fave.databinding.FragmentBookmarkedBinding
import com.fave.breezil.fave.model.BookMark
import com.fave.breezil.fave.ui.adapter.BookMarkRecyclerAdapter
import com.fave.breezil.fave.ui.bottom_sheets.BookMarkDescriptionFragment
import com.fave.breezil.fave.ui.bottom_sheets.BookmarkBottomSheetFragment
import dagger.android.support.AndroidSupportInjection


/**
 * A simple [Fragment] subclass.
 *
 */
class BookMarkedFragment : Fragment() {

    interface RefreshBookmark {
        fun getRefresh(refresh: Boolean)
    }

    lateinit var refreshBookmark: RefreshBookmark
    lateinit var adapter: BookMarkRecyclerAdapter
    lateinit var bookMarkViewModel: BookMarkViewModel
    lateinit var binding: FragmentBookmarkedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarked, container, false)
        binding.bookmarkList.setHasFixedSize(true)

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpAdapter()
        setUpViewModel()

    }

    private fun setUpAdapter() {
        val bookMarkClickListener = object : BookMarkClickListener {
            override fun showDetails(bookMark: BookMark) {
                val bookMarkDescriptionFragment = BookMarkDescriptionFragment.getBookMarked(bookMark)
                bookMarkDescriptionFragment.show(fragmentManager!!, getString(R.string.show))
            }
        }

        val bookMarkLongClickListener = object : BookMarkLongClickListener {
            override fun doSomething(bookMark: BookMark) {
                val bookmarkBottomSheetFragment = BookmarkBottomSheetFragment.getBookmark(bookMark)
                bookmarkBottomSheetFragment.show(fragmentManager!!, getString(R.string.show))
            }
        }

        adapter = BookMarkRecyclerAdapter(context!!, bookMarkClickListener, bookMarkLongClickListener)
        binding.bookmarkList.adapter = adapter
    }

    private fun setUpViewModel() {
        bookMarkViewModel = ViewModelProviders.of(this).get(BookMarkViewModel::class.java)
        bookMarkViewModel.bookmarkList.observe(this, Observer { bookMarks ->
            if (bookMarks!!.isNotEmpty()) {
                adapter.submitList(bookMarks)
            }
        })
    }


    private fun showDeleteAllDialog() {

        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        builder.setCancelable(false)
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_delete_all_bookmarks))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    deleteAll()
                    dialog.dismiss()
//            refresh()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(activity, getString(R.string.bookmark_list_emptied), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
        val alertDialog = builder.create()
        alertDialog.setTitle(getString(R.string.delete_all))
        alertDialog.show()

    }


    private fun deleteAll() {
        bookMarkViewModel.deleteAll()
    }

    private fun refresh() {
        val ft = fragmentManager!!.beginTransaction()

        ft.detach(this@BookMarkedFragment).attach(this@BookMarkedFragment).commit()
    }

    companion object {
        fun newInstance() = BookMarkedFragment()
    }

}
