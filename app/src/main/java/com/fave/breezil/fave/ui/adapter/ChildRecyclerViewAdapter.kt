package com.fave.breezil.fave.ui.adapter

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.ArticleClickListener
import com.fave.breezil.fave.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.databinding.ChildMainItemBinding
import com.fave.breezil.fave.model.Articles
import java.util.ArrayList


class ChildRecyclerViewAdapter(private val mContext: Context, private val articleClickListener: ArticleClickListener,
                                       private val articleLongClickListener: ArticleLongClickListener)
    : ListAdapter<Articles,ChildRecyclerViewAdapter.ChildViewHolder>(DIFF_CALLBACK) {



    internal lateinit var binding: ChildMainItemBinding
    private var mArticleList: ArrayList<Articles>? = null
    internal lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ChildViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        binding = ChildMainItemBinding.inflate(layoutInflater, viewGroup, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(childViewHolder: ChildViewHolder, i: Int) {

        val articles = getItem(i)
        childViewHolder.bind(articles, articleClickListener, articleLongClickListener)
    }


    inner class ChildViewHolder(var binding: ChildMainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(articles: Articles,
                 articleClickListener: ArticleClickListener,
                 articleLongClickListener: ArticleLongClickListener) {

            itemView.setOnClickListener { articleClickListener.showDetails(articles) }
            itemView.setOnLongClickListener {
                articleLongClickListener.doSomething(articles)
                true
            }

            circularProgressDrawable = CircularProgressDrawable(mContext)
            circularProgressDrawable.strokeWidth = 10f
            circularProgressDrawable.centerRadius = 40f
            circularProgressDrawable.setColorSchemeColors(R.color.colorAccent, R.color.colorPrimary,
                    R.color.colorblue, R.color.hotPink)
            circularProgressDrawable.start()

            Glide.with(mContext)
                    .load(articles.urlToImage)
                    .apply(RequestOptions()
                            .placeholder(circularProgressDrawable)
                            .error(R.drawable.placeholder))
                    .into(binding.articleImage)

            binding.articleTitle.text = articles.title
            binding.sourcesText.text = articles.source!!.name
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                return (oldItem.title == newItem.title
                        && oldItem.description == newItem.description
                        && oldItem.publishedAt == newItem.publishedAt)
            }
        }
    }

}