package kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_slide.view.*
import kr.ac.kpu.ce2017154024.mytamin.R

class DetailImageAdapter(private var context: Context, private var images: ArrayList<String>):
    RecyclerView.Adapter<DetailImageAdapter.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false)
    ) {
        val imageView: ImageView = itemView.slide_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(images[position])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}