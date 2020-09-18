package com.betterlyf.app.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.adapter.ArticlesAdapter
import com.betterlyf.app.adapter.CounsellorAdapter
import com.betterlyf.app.adapter.ReviewsAdapter
import com.betterlyf.app.adapter.SelfHelpAdapter
import com.betterlyf.app.helper.ClickListener
import com.betterlyf.app.ui.activity.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : androidx.fragment.app.Fragment(), ClickListener {

    var adapter: ReviewsAdapter? = null
    var counselorAdapter: CounsellorAdapter? = null
    var articleAdapter: ArticlesAdapter? = null
    var selfHelpdapter: SelfHelpAdapter? = null
    var list: IntArray = intArrayOf(
        R.drawable.wellness,
        R.drawable.books_new,
        R.drawable.true_stories,
        R.drawable.articles,
        R.drawable.videos
    )

    val listType = arrayOf(
        "Wellness Program",
        "Health and Wellness Books",
        "True Stories",
        "Videos",
        "Articles"
    )


    var view1: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_home, container, false)

        adapter = ReviewsAdapter()
        counselorAdapter = CounsellorAdapter()
        articleAdapter = ArticlesAdapter()
        selfHelpdapter = SelfHelpAdapter(list, listType)

        view1!!.rv_reviews.setHasFixedSize(false)
        view1!!.rv_reviews.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        view1!!.rv_reviews.adapter = adapter

        view1!!.rv_counsellor.setHasFixedSize(false)
        view1!!.rv_counsellor.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        view1!!.rv_counsellor.adapter = counselorAdapter

        view1!!.rv_articles.setHasFixedSize(false)
        view1!!.rv_articles.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        view1!!.rv_articles.adapter = articleAdapter

        view1!!.rv_self_help_new.setHasFixedSize(false)
        view1!!.rv_self_help_new.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        view1!!.rv_self_help_new.adapter = selfHelpdapter

        clickListeners(view1!!)

        counselorAdapter!!.setClickListener(this)
        adapter!!.setClickListener(this)
        selfHelpdapter!!.setClickListener(this)
        articleAdapter!!.setClickListener(this)

        return view1
    }

    private fun clickListeners(view: View) {
        view.txt_viewAll.setOnClickListener {
            val intent = Intent(activity, ReviewsActivity::class.java)
            startActivity(intent)
        }
        view.txt_start.setOnClickListener {
            val intent = Intent(activity, BookSessionActivity::class.java)
            startActivity(intent)
        }
        view.txt_viewAll_counsellor.setOnClickListener {
            val intent = Intent(activity, AllCounselorActivity::class.java)
            intent.putExtra("screen", "counselor")
            startActivity(intent)
        }
        view.txt_viewAll_article.setOnClickListener {
            val intent = Intent(activity, AllCounselorActivity::class.java)
            intent.putExtra("screen", "articles")
            startActivity(intent)
        }
        view.txt_read_more.setOnClickListener {
            val intent = Intent(activity, BuyBookActivity::class.java)
            startActivity(intent)
        }
        view.txt_start_journey.setOnClickListener {
            val intent = Intent(activity, BookSessionActivity::class.java)
            startActivity(intent)
        }
        view.txt_helps.setOnClickListener {
            val browserIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.betterlyf.com/how-counseling-helps/")
                )
            startActivity(browserIntent)
        }
        view.txt_viewAll_self.setOnClickListener {
            activity!!.txt_toolbar.text = "Self Improvement"
            activity!!.navigation.menu.getItem(3).isChecked = true
            fun newInstance(): SelfHelpFragment = SelfHelpFragment()
            fragment(newInstance())
        }
    }

    private fun fragment(fragmentLaunch: androidx.fragment.app.Fragment) {
        val fragment: androidx.fragment.app.Fragment = fragmentLaunch
        val transaction: androidx.fragment.app.FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_content, fragment).commit()
    }

    override fun itemClicked(view: View, position: Int) {
        when (view.id) {
            R.id.txt_book -> {
                val intent = Intent(activity, BookSessionActivity::class.java)
                startActivity(intent)
            }
            R.id.layout_selfHelp -> {
                when (position) {
                    4 -> {
                        val intent = Intent(activity, AllCounselorActivity::class.java)
                        intent.putExtra("screen", "articles")
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(activity, AllCounselorActivity::class.java)
                        intent.putExtra("screen", "videos")
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(activity, AllCounselorActivity::class.java)
                        intent.putExtra("screen", "story")
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(activity, BuyBookActivity::class.java)
                        startActivity(intent)
                    }
                    0 -> {
                        val intent = Intent(activity, WellnessTopicsActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            R.id.layout_review -> {
                val intent = Intent(activity, ReviewsActivity::class.java)
                startActivity(intent)
            }
            R.id.layout_article -> {
                val intent = Intent(activity, ArticleActivity::class.java)
                startActivity(intent)
            }
            else -> {
                val intent = Intent(activity, CounselorActivity::class.java)
                startActivity(intent)
            }
        }
    }
}