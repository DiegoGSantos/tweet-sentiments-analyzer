package com.diego.tweetssentimentsanalyzer.feature.userDetail.view.adapter

import android.content.Context
import android.support.text.emoji.EmojiCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.Tweet
import com.diego.tweetssentimentsanalyzer.feature.userDetail.sentimentAnalyzer.naturalLanguage.Sentiments
import com.diego.tweetssentimentsanalyzer.util.dateFormat
import kotlinx.android.synthetic.main.view_tweet.view.*
import java.util.*

class TweetsAdapter(private val listener: (Int, Tweet) -> Unit) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    private var tweetsList: List<Tweet> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_tweet, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tweetsList[position], listener)

    override fun getItemCount(): Int = tweetsList.size

    fun setList(list: List<Tweet>) {
        tweetsList = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var context: Context = itemView.context

        internal fun bind(item: Tweet, listener: (Int, Tweet) -> Unit) = with(itemView) {

            tweetText.text = item.text
            tweetDate.text = item.createdAt.dateFormat()
            sentimentAnalysisProgress.visibility = GONE

            loadTweetEmotion(item.sentiment, analyzeSentiment, sentiment)
            analyzeSentiment.setOnClickListener {
                if (item.sentiment == 0) {
                    sentimentAnalysisProgress.visibility = VISIBLE
                    analyzeSentiment.text = ""

                    listener(adapterPosition, item)
                }
            }
        }

        private fun loadTweetEmotion(sentiment: Int, verify_emotion: Button, sentimentText: TextView) {
            when (sentiment) {
                Sentiments.NEUTRAL.sentiment ->
                    setTweetEmotion(verify_emotion, sentimentText,
                            "\uD83D\uDE10", R.string.neutral_emoji_text, R.color.neutral_color)
                Sentiments.SAD.sentiment ->
                    setTweetEmotion(verify_emotion, sentimentText,"\uD83D\uDE14", R.string.sad_emoji_text, R.color.sad_color)
                Sentiments.HAPPY.sentiment ->
                    setTweetEmotion(verify_emotion, sentimentText,"\uD83D\uDE03", R.string.happy_emoji_text, R.color.happy_color)
                else -> verify_emotion.text = context.getString(R.string.analyze_sentiment)
            }
        }

        private fun setTweetEmotion(verify_emotion: Button, textView: TextView, emoji: String, textResource: Int, bgColor: Int) {
            verify_emotion.text = EmojiCompat.get().process(emoji)
            verify_emotion.setBackgroundColor(context.resources.getColor(bgColor))
            textView.text = context.getText(textResource)
        }

    }
}