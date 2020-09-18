package com.betterlyf.app.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.core.content.FileProvider
import com.betterlyf.app.R
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object Utils {

    var token = "token"


    @Synchronized
    fun savePref(
        context: Context,
        field: String?,
        value: String?
    ) {
        val sp =
            context.getSharedPreferences("betterlyf", Context.MODE_PRIVATE)
        sp.edit().putString(field, value).commit()
    }

    @Synchronized
    fun clearAll(context: Context) {
        val sp =
            context.getSharedPreferences("betterlyf", Context.MODE_PRIVATE)
        sp.edit().clear().commit()
    }

    @Synchronized
    fun getPref(
        context: Context,
        field: String?,
        def: String?
    ): String? {
        val sp =
            context.getSharedPreferences("betterlyf", Context.MODE_PRIVATE)
        return sp.getString(field, def)
    }

    fun saveBooleanDataTosharedPrefences(
        mContext: Context,
        key: String?, data: Boolean
    ) {
        val pregName = "IsFirstRun"
        val spyAppData = mContext.getSharedPreferences(
            pregName, 0
        )
        val editor = spyAppData.edit()
        editor.putBoolean(key, data)
        editor.commit()
    }

    fun getImage(): RequestOptions? {
        return RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()
    }

    fun getBooleanDataFromsharedPrefences(
        mContext: Context,
        key: String?
    ): Boolean {
        val pregName = "IsFirstRun"
        val toolsAppData = mContext.getSharedPreferences(
            pregName, 0
        )
        return toolsAppData.getBoolean(key, false)
    }

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    fun openGallery(activity: Activity, galleryCode: Int) {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), galleryCode)
    }

    fun camera(activity: Activity, camCode: Int) {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (pictureIntent.resolveActivity(activity.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile(activity)
            } catch (ignored: IOException) {
            }
            if (photoFile != null) {
                val photoURI =
                    FileProvider.getUriForFile(activity, "com.betterlyf.app.provider", photoFile)
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                activity.startActivityForResult(pictureIntent, camCode)
            }
        }
    }

    fun rotateImage(photoPath: String?, bitmap: Bitmap): Bitmap? {
        var ei: ExifInterface? = null
        try {
            ei = ExifInterface(photoPath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val orientation =
            ei!!.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        Log.e("orientation", "" + orientation)
        val rotatedBitmap: Bitmap
        rotatedBitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(
                bitmap,
                90
            )
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(
                bitmap,
                180
            )
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(
                bitmap,
                270
            )
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> return flip(bitmap, true, false)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> return flip(bitmap, false, true)
            ExifInterface.ORIENTATION_NORMAL -> bitmap
            else -> bitmap
        }
        return rotatedBitmap
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg =
            Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    fun getPath(context: Context, uri: Uri?): String? {
        val filePathColumn =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            context.contentResolver.query(uri!!, filePathColumn, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()
        return picturePath
    }


    fun saveBitmapToFile(file: File): File? {
        return try { // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()
            // The new size we want to scale to
            val REQUIRED_SIZE = 75
            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()
            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: Exception) {
            null
        }
    }

    fun flip(bitmap: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap? {
        val matrix = Matrix()
        matrix.preScale(
            if (horizontal) (-1).toFloat() else 1.toFloat(),
            if (vertical) (-1).toFloat() else 1.toFloat()
        )
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }

    @Throws(IOException::class)
    fun createImageFile(activity: Activity): File? { // Create an image file name
        val timeStamp = System.currentTimeMillis().toString()
        val storageDir =
            activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            timeStamp,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        savePref(activity, "saved_img", image.absolutePath)
        return image
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}