package com.fave.breezil.fave.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fave.breezil.fave.callbacks.ArticleClickListener
import com.fave.breezil.fave.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.databinding.BreakingNewsListBinding
import com.fave.breezil.fave.databinding.ParentMainItemBinding
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.model.ParentModel
import com.fave.breezil.fave.utils.Constant.Companion.ONE
import com.fave.breezil.fave.utils.Constant.Companion.ZERO
import java.util.ArrayList


class ParentCategoryRecyclerAdapter(private val mContext: Context, private val articleClickListener: ArticleClickListener,
                                    private val articleLongClickListener: ArticleLongClickListener, private val seeMoreClickListener: SeeMoreClickListener)//        this.mParentList = mParentList;
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal lateinit var binding: ParentMainItemBinding
    internal lateinit var breakingNewBinding: BreakingNewsListBinding
    private var mParentList = ArrayList<ParentModel>()

    private var mArticles  = ArrayList<Articles>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return if(i == TYPE_BREAKING_NEWS){
            breakingNewBinding = BreakingNewsListBinding.inflate(layoutInflater, viewGroup, false)
            BreakingNewsViewHolder(breakingNewBinding)
        }else{
            binding = ParentMainItemBinding.inflate(layoutInflater, viewGroup, false)
            ParentViewHolder(binding)
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ParentViewHolder){
            val  parentModel = mParentList[position]
            holder.bind(parentModel, seeMoreClickListener)
        }else{
            (holder as BreakingNewsViewHolder).bind(mArticles)
        }
    }

    override fun getItemCount(): Int {
        return mParentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < ONE) {
            TYPE_BREAKING_NEWS
        } else {
            TYPE_ITEM
        }
    }
    fun setBreakingNews(articlesList : ArrayList<Articles>){
        mArticles = articlesList
    }


    fun setList(parentModelArrayList: ArrayList<ParentModel>) {
        mParentList = parentModelArrayList
        //        notifyDataSetChanged();
    }


    inner class ParentViewHolder(var binding: ParentMainItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(parentModel: ParentModel, seeMoreClickListener: SeeMoreClickListener) {
            binding.moreItemText.setOnClickListener{ seeMoreClickListener.showMoreCategory(parentModel.title!!) }
            binding.parentTitleText.text = parentModel.title

            val articles = parentModel.articles

            val childRecyclerAdapter = ChildRecyclerViewAdapter(mContext,
                    articleClickListener, articleLongClickListener)
            binding.parentRecyclerView.setHasFixedSize(true)
            binding.parentRecyclerView.layoutManager =LinearLayoutManager(mContext,
                    LinearLayoutManager.HORIZONTAL, false)
            binding.parentRecyclerView.adapter = childRecyclerAdapter
            childRecyclerAdapter.submitList(articles)

            binding.parentRecyclerView.isNestedScrollingEnabled = true

        }
    }
    inner class BreakingNewsViewHolder(var binding: BreakingNewsListBinding) : RecyclerView.ViewHolder(binding.root){
        internal fun bind(articles: List<Articles>){
            val breakingNewsRecyclerAdapter = BreakingNewsRecyclerAdapter(mContext,
                    articleClickListener, articleLongClickListener)
            binding.breakingNewsList.setHasFixedSize(true)
            binding.breakingNewsList.layoutManager = LinearLayoutManager(mContext,
                    LinearLayoutManager.HORIZONTAL, false)
            binding.breakingNewsList.adapter = breakingNewsRecyclerAdapter
            breakingNewsRecyclerAdapter.submitList(articles)

        }
    }

    companion object{
        private const val TYPE_BREAKING_NEWS = ZERO
        private const val TYPE_ITEM = ONE
    }
}
