package com.robertconstantindinescu.woutapp.core.util

import android.content.Context
import android.content.Intent
import android.content.RestrictionsManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.getFileName
import com.yalantis.ucrop.UCrop
import java.io.File

class CropActivityResultContract(
    private val aspectRatioX: Float,
    private val aspectRatioY: Float
): ActivityResultContract<Uri, Uri?>() {

    override fun createIntent(context: Context, input: Uri): Intent {
        return UCrop.of(
            //the uri that contains the image
            input,
            //create a file in certain detination to
            Uri.fromFile(
                File(
                    //absolute path where the files are stored in the device.
                    context.cacheDir,
                    //pathname of the file
                    context.contentResolver.getFileName(input)
                )
            )
        ).withAspectRatio(aspectRatioX, aspectRatioY).getIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (intent == null){
            return null
        }

        if (resultCode == RestrictionsManager.RESULT_ERROR){
            val error = UCrop.getError(intent)
            error?.printStackTrace()
        }
        //get the cropped image uri from the intent of image picker and return it
        return UCrop.getOutput(intent)

    }
}