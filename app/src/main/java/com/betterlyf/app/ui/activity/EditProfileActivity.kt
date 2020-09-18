package com.betterlyf.app.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.helper.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_profile.img_back
import java.io.*


class EditProfileActivity : BaseActivity() {

    var imgFile: File? = null
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        clickListener()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        }
    }

    private fun clickListener() {
        img_back.setOnClickListener {
            onBackPressed()
            layout_bottom_sheet.visibility = View.GONE
        }
        edt_email.setOnClickListener {
            layout_bottom_sheet.visibility = View.GONE
            var bundle = Bundle()
            bundle.putString("value", "Email")
            bundle.putString("result", "Nandita@gmail.com")
            launchIntent(this, NameEditActivity::class.java, bundle, false)
        }
        edt_mobile.setOnClickListener {
            layout_bottom_sheet.visibility = View.GONE
            var bundle = Bundle()
            bundle.putString("value", "Mobile")
            bundle.putString("result", "+919999999999")
            launchIntent(this, NameEditActivity::class.java, bundle, false)
        }
        img_profile.setOnClickListener {
            layout_bottom_sheet.visibility = View.VISIBLE
            val animation = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_in_bottom
            )
            layout_bottom_sheet.animation = animation
        }
        layout_main.setOnClickListener {
            layout_bottom_sheet.visibility = View.GONE
        }
        txt_gallery.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }
        txt_camera.setOnClickListener {
            val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (pictureIntent.resolveActivity(packageManager) != null) {
                var photoFile: File? = null
                try {
                    photoFile = Utils.createImageFile(this)
                } catch (ignored: IOException) {
                }
                if (photoFile != null) {
                    val photoURI =
                        FileProvider.getUriForFile(this, "com.betterlyf.app.provider", photoFile)
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(pictureIntent, 2)
                }
            }
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        val wtite = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val read = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("reach", "yes")
                layout_bottom_sheet.visibility = View.GONE
                onSelectFromGalleryResult(data)
            }
        } else if (requestCode == 2)
            if (resultCode == Activity.RESULT_OK) {
                layout_bottom_sheet.visibility = View.GONE
                onCaptureImageResult()
            }
    }

    private fun onCaptureImageResult() {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        val img: String? = let { Utils.getPref(it, "saved_img", "") }
        bmOptions.inJustDecodeBounds = false
        bmOptions.inPurgeable = true
        var thumbnail = BitmapFactory.decodeFile(img, bmOptions)
        if (thumbnail != null) {
            val bytes = ByteArrayOutputStream()
            thumbnail = Utils.rotateImage(img, thumbnail)
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val file = File(
                Environment.getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg"
            )
            val fo: FileOutputStream
            try {
                file.createNewFile()
                fo = FileOutputStream(file)
                fo.write(bytes.toByteArray())
                fo.close()
                Glide.with(this).asBitmap().load(thumbnail)
                    .into(img_profile)
                imgFile = file
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {

        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        if (data != null) {
            try {
                val uri = data.data
                val bytes = ByteArrayOutputStream()
                var thumbnail = MediaStore.Images.Media.getBitmap(
                    contentResolver,
                    uri
                )
                thumbnail =
                    Utils.rotateImage(let { Utils.getPath(it, uri) }, thumbnail)
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                val file = File(
                    cacheDir,
                    System.currentTimeMillis().toString() + ".jpg"
                )
                val fo: FileOutputStream
                file.createNewFile()
                fo = FileOutputStream(file)
                fo.write(bytes.toByteArray())
                fo.close()
                if (thumbnail != null) {
                    Glide.with(this).asBitmap().load(thumbnail)
                        .into(img_profile)
                    imgFile = Utils.saveBitmapToFile(file)
                } else
                    Toast.makeText(
                        this,
                        "Unable to get image",
                        Toast.LENGTH_LONG
                    ).show()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}
