package com.shawakri.pixabayimagesearch.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.pixabayimagesearch.R
import com.example.pixabayimagesearch.databinding.FragmentDetailsBinding
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(24))

            Glide.with(this@DetailsFragment)
                .load(photo.imageURL)
                .listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadDetailsProgressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadDetailsProgressBar.isVisible = false
                        usernameLL.isVisible = true
                        statsCard.isVisible = true
                        return false
                    }

                })
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_cancel)
                .into(fullImageView)

            Glide.with(this@DetailsFragment)
                .load(photo.userImageURL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_cancel)
                .circleCrop()
                .into(usernameImage)

            usernameTV.text = photo.user
            downloadsTV.text = prettyCount(photo.downloads)
            commentsTV.text = prettyCount(photo.comments)
            likesTV.text = prettyCount(photo.likes)
            tagsTV.text = photo.tags


        }
    }

    private fun prettyCount(number: Int): String {
        if (number < 1000) return "" + number
        val exp = (ln(number.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", number / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])

    }

}
