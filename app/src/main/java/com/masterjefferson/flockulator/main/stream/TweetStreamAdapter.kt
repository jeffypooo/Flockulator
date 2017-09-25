package com.masterjefferson.flockulator.main.stream

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.masterjefferson.flockulator.R
import com.masterjefferson.flockulator.main.stream.model.TweetStreamItem
import kotlinx.android.synthetic.main.rv_tweet_item.view.*
import java.text.SimpleDateFormat

/**
 * ${FILE_NAME}
 * Created by jeff on 9/24/17.
 */
class TweetStreamItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

  var nameText: TextView = itemView.nameText
  var contentText: TextView = itemView.contentText
  var timestampText: TextView = itemView.timestampText

  fun bind(to: TweetStreamItem) {
    nameText.text = to.name
    contentText.text = to.text
    timestampText.text = SimpleDateFormat("MM:dd:yy hh:mm:ss").format(to.timestamp)
  }
}

class TweetStreamAdapter(private val ctx: Context) : RecyclerView.Adapter<TweetStreamItemVH>() {
  var maxItemCount = 50
    set(value) {
      maxItemCountChanged()
    }
  private var items: MutableList<TweetStreamItem> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TweetStreamItemVH {
    val view = LayoutInflater.from(ctx).inflate(R.layout.rv_tweet_item, parent, false)
    return TweetStreamItemVH(view)
  }

  override fun onBindViewHolder(holder: TweetStreamItemVH?, position: Int) {
    holder?.bind(to = items[position])
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun add(newItems: List<TweetStreamItem>) {
    val insertStart = items.size
    items.addAll(newItems)
//    notifyItemRangeInserted(insertStart, newItems.size)
    val toRemove = items.size - maxItemCount
    if (toRemove > 0) {
      items = items.subList(toRemove - 1, items.size)
//      notifyItemRangeRemoved(0, toRemove)
    }
    notifyDataSetChanged()
  }

  fun add(item: TweetStreamItem) {
    if (items.size == maxItemCount) {
      items.removeAt(0)
      notifyItemRemoved(0)
    }
    items.add(item)
    val index = if (items.size > 0) items.indices.last else 0
    notifyItemInserted(index)
  }

  private fun maxItemCountChanged() {
    if (items.size > maxItemCount) {
      items = items.subList(0, maxItemCount)
      notifyDataSetChanged()
    }
  }
}