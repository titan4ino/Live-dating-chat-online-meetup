package test_livedaitingchat.online.mem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

class MyPagerAdapter(
    private val mContext: Context,
    private val callback: MCallBack
) : PagerAdapter() {
    val bas = ""

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val link = when (position) {
            0 -> { bas + "" }
            1 -> { bas + "" }
            2 -> { bas + "" }
            else -> { bas + "" }
        }
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.vp_item, container, false) as ViewGroup
        val imageView = layout.findViewById<ImageView>(R.id.imageView)
        Glide.with(mContext).load(link).into(imageView)
        val back = layout.findViewById<ImageView>(R.id.back)
        if (position == 0) {
            back.visibility = View.GONE
        } else {
            back.visibility = View.VISIBLE
        }
        back.setOnClickListener {
            callback.onClick(position)
        }

        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return 4
    }

    interface MCallBack {
        fun onClick(position: Int)
    }
}