package com.vinz.playerpedia.activity.blur

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkInfo
import com.bumptech.glide.Glide
import com.vinz.playerpedia.R
import com.vinz.playerpedia.databinding.ActivityBlurBinding
import com.vinz.playerpedia.workers.KEY_IMAGE_URI
import com.vinz.playerpedia.workers.PROGRESS
import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays

@Suppress("DEPRECATION")
@AndroidEntryPoint
class BlurActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlurBinding
    private val viewModel: BlurViewModel by viewModels()
    private var blurLevel: Int = 1
    private var imageSelected: Uri? = null

    private val requestCodeImage = 100
    private val requestCodePermission = 101
    private val keyPermissionRequestCount = "KEY_PERMISSIONS_REQUEST_COUNT"
    private val maxNumberRequestPermission = 2
    private var permissionRequestCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            permissionRequestCount = it.getInt(keyPermissionRequestCount, 0)
        }

        requestPermissionsIfNecessary()

        binding.cancelButton.setOnClickListener { viewModel.cancelWork() }

        viewModel.outputWorkInfos.observe(this, workInfosObserver())

        viewModel.progressWorkInfoItems.observe(this, progressObserver())

        onClick()
    }

    private fun onClick() {
        binding.selectImageButton.setOnClickListener {
            val chooseIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(chooseIntent, requestCodeImage)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.goButton.setOnClickListener {
            if(imageSelected == null) {
                Toast.makeText(this, "Please select another image to process", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.applyBlur(blurLevel)
        }

        binding.seeFileButton.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                actionView.resolveActivity(packageManager)?.run {
                    startActivity(actionView)
                }
            }
        }

        binding.selectBlur.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                blurLevel = progress
                binding.selectAnswer.text = if (progress == 0) {
                    getString(R.string.little_blurred)
                } else if (progress == 1) {
                    getString(R.string.medium_blurred)
                } else {
                    getString(R.string.very_blurred)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    private fun progressObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            listOfWorkInfo.forEach { workInfo ->
                if (WorkInfo.State.RUNNING == workInfo.state) {
                    val progress = workInfo.progress.getInt(PROGRESS, 0)
                    binding.progressBar.progress = progress
                }
            }

        }
    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            val workInfo = listOfWorkInfo[0]

            if (workInfo.state.isFinished) {
                showWorkFinished()

                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

                if (!outputImageUri.isNullOrEmpty()) {

                    viewModel.setOutputUri(outputImageUri)
                    binding.seeFileButton.visibility = View.VISIBLE
                }
            } else {
                showWorkInProgress()
            }
        }
    }

    private fun showWorkInProgress() {
        with(binding) {
            selectImageButton.visibility = View.GONE
            cancelButton.visibility = View.VISIBLE
            goButton.visibility = View.GONE
            seeFileButton.visibility = View.GONE
        }
    }

    private fun showWorkFinished() {
        with(binding) {
            selectImageButton.visibility = View.VISIBLE
            goButton.visibility = View.VISIBLE
            cancelButton.visibility = View.GONE
            progressBar.progress = 0
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(keyPermissionRequestCount, permissionRequestCount)
    }

    private val permissions = Arrays.asList(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (permissionRequestCount < maxNumberRequestPermission) {
                permissionRequestCount += 1
                ActivityCompat.requestPermissions(
                    this,
                    permissions.toTypedArray(),
                    requestCodePermission
                )
            } else {
                Toast.makeText(
                    this,
                    R.string.set_permissions_in_settings,
                    Toast.LENGTH_LONG
                ).show()
                binding.selectImageButton.isEnabled = false
            }
        }
    }

    private fun checkAllPermissions(): Boolean {
        var hasPermissions = true
        for (permission in permissions) {
            hasPermissions = hasPermissions and isPermissionGranted(permission)
        }
        return hasPermissions
    }

    private fun isPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodePermission) {
            requestPermissionsIfNecessary() // no-op if permissions are granted already.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                requestCodeImage -> data?.let { handleImageRequestResult(data) }
                else -> Log.d("Error", "Unknown request code.")
            }
        } else {
            Log.e("Error", String.format("Unexpected Result code %s", resultCode))
        }
    }

    private fun handleImageRequestResult(intent: Intent) {
        // If clipdata is available, we use it, otherwise we use data
        val imageUri: Uri? = intent.clipData?.let {
            it.getItemAt(0).uri
        } ?: intent.data

        if (imageUri == null) {
            Log.e("Error", "Invalid input image Uri.")
            return
        }

        imageSelected = imageUri
        viewModel.setImageUri(imageUri.toString())
        Glide.with(this).load(imageUri).into(binding.imagePreview)
    }
}