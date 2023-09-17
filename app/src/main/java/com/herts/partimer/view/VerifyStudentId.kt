package com.herts.partimer.view

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.partimer.R
import com.herts.partimer.utils.Navigator
import com.herts.partimer.viewmodel.StudentViewModel
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class VerifyStudentId : AppCompatActivity() {

    val REQUEST_UPLOAD_FRONT = 102
    private val IMAGE_CAPTURE_CODE = 1001
    var mFileList: File? = null
    private lateinit var vm: StudentViewModel
    var userID = 1

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, VerifyStudentId::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_student_id)
        vm = ViewModelProvider(this)[StudentViewModel::class.java]
        val save = findViewById<LinearLayout>(R.id.book_now)
        val upload = findViewById<Button>(R.id.upload)
        val change_package = findViewById<LinearLayout>(R.id.change_package)
        upload.setOnClickListener {
            selectImage()
        }
        save.setOnClickListener {
            uploadFiles()
//            Navigator.navigateToStudentHome(this)
        }
        change_package.setOnClickListener {
            Navigator.navigateToStudentHome(this)
        }
    }

    private fun uploadFiles() {
        val fileReqBody1: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mFileList!!)
        val body1: MultipartBody.Part =
            MultipartBody.Part.createFormData("profile", mFileList!!.name, fileReqBody1)
        setRequestData(body1)
    }

    private fun setRequestData(body1: MultipartBody.Part) {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        userID = sharedPref.getInt("USER_ID", 1)
        val token = sharedPref.getString("TOKEN", "")
        vm.addPhotos(token.toString(), userID, body1)
        vm.addPhotoLiveData?.observe(this, Observer {
            if (it.id != null) {
                Navigator.navigateToStudentHome(this)
            } else {
                showToast("Cannot add photos at the moment")
            }

        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Camera", "Photo", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Camera" -> {
                    dialog.dismiss()
                    selectCamera(IMAGE_CAPTURE_CODE)
                }

                options[item] == "Photo" -> {
                    dialog.dismiss()
                    openFile(REQUEST_UPLOAD_FRONT)
                }

                options[item] == "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun openFile(requestUploadFront: Int) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryActivityResultLauncher.launch(galleryIntent)
    }

    var galleryActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val image_uri = result.data!!.data
//            imageView.setImageURI(image_uri)
            val finalFile = File(getRealPathFromURI(image_uri))

            mFileList = finalFile

        }
    }

    private fun selectCamera(imageCaptureCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permission, 121)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
        true
    }

    var image_uri: Uri? = null
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        cameraActivityResultLauncher.launch(cameraIntent)
    }

    var cameraActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode === RESULT_OK) {
                val inputImage: Bitmap? = uriToBitmap(image_uri!!)
//                val rotated: Bitmap? = rotateBitmap(inputImage!!)
                val finalFile = File(getRealPathFromURI(image_uri))
                val user_profile_pic = findViewById<CircleImageView>(R.id.user_profile_pic)
                user_profile_pic.setImageBitmap(BitmapFactory.decodeFile(finalFile.absolutePath))
                mFileList = finalFile

            }
        })

    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor: Cursor? = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor?.moveToFirst()
        val idx: Int? = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return idx?.let { cursor?.getString(it) }
    }

}