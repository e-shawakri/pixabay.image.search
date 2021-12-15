package com.shawakri.pixabayimagesearch.data

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.pixabayimagesearch.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@BindingAdapter("android:loadImage")
fun loadImage(imageView: ImageView, url: String) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(24))

    Glide.with(imageView)
        .load(url)
        .apply(requestOptions)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_cancel)
        .into(imageView)

}

@BindingAdapter("android:loadUsernameImage")
fun loadUsernameImage(imageView: ImageView, url: String) {
    Glide.with(imageView)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_cancel)
        .circleCrop()
        .into(imageView)
}

@Parcelize
data class PixaBayPhoto(
    val id: Int,
    val user: String,
    val userImageURL: String,
    @SerializedName("webformatURL")
    val imageURL: String,
    val tags: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
) : Parcelable {


}