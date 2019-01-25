package com.chetan.myvision

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var cameraSource: CameraSource
    val textRecognizer by lazy { TextRecognizer.Builder(this).build() }
    val requestPermissionID = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startCameraSource()
    }

    private fun startCameraSource() {

        //Create the TextRecognizer
        val textRecognizer = TextRecognizer.Builder(applicationContext).build()

        if (!textRecognizer.isOperational) {
            Log.w("MainActivity", "Detector dependencies not loaded yet")
        } else {

            //Initialize camerasource to use high resolution and set Autofocus on.
            cameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build()

            /**
             * Add call back to SurfaceView and check if camera permission is granted.
             * If permission is granted we can start our cameraSource and pass it to surfaceView
             */
            surface_view.getHolder().addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    try {

                        if (ActivityCompat.checkSelfPermission(
                                applicationContext,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {

                            ActivityCompat.requestPermissions(
                                this@MainActivity,
                                arrayOf(Manifest.permission.CAMERA),
                                requestPermissionID
                            )
                            return
                        }
                        cameraSource.start(surface_view.getHolder())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

                /**
                 * Release resources for cameraSource
                 */
                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    cameraSource.stop()
                }
            })

            //Set the TextRecognizer's Processor.
            textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {}

                /**
                 * Detect all the text from camera using TextBlock and the values into a stringBuilder
                 * which will then be set to the textView.
                 */
                override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                    val items = detections.detectedItems
                    if (items.size() != 0) {

                        text_view.post(Runnable {
                            val stringBuilder = StringBuilder()
                            for (i in 0 until items.size()) {
                                val item = items.valueAt(i)
                                stringBuilder.append(item.value)
                                stringBuilder.append("\n")
                            }
                            text_view.setText(stringBuilder.toString())
                        })
                    }
                }
            })
        }
    }


    /* private fun createCameraSource(autoFocus: Boolean, useFlash: Boolean) {
        val context = applicationContext


        // TODO: Set the TextRecognizer's Processor.

        // TODO: Check if the TextRecognizer is operational.
        if (!textRecognizer.isOperational) {
            Log.w("mainactivity", "Detector dependencies are not yet available.")

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null

            if (hasLowStorage) {
                Toast.makeText(this, "Low storage", Toast.LENGTH_LONG).show()
                Log.w("mainactivity", "Low storage")
            }
        }

        createCamera()
    }*/

    /* private fun createCamera() {
         cameraSource = CameraSource.Builder(applicationContext, textRecognizer)
             .setFacing(CameraSource.CAMERA_FACING_BACK)
             .setRequestedPreviewSize(1280, 1024)
             .setRequestedFps(15.0f)
             .setAutoFocusEnabled(true)
             .build()
         */
    /**
     * Add call back to SurfaceView and check if camera permission is granted.
     * If permission is granted we can start our cameraSource and pass it to surfaceView
     *//*
        surface_view.getHolder().addCallback(SurfaceHolder.


            *//* SurfaceHolder . Callback () {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {

                    if (ActivityCompat.checkSelfPermission(
                            getApplicationContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String []{ Manifest.permission.CAMERA },
                            requestPermissionID
                        );
                        return;
                    }
                    mCameraSource.start(mCameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            *//**/
    /**
     * Release resources for cameraSource
     *//**//*
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCameraSource.stop();
            }
        });

        //Set the TextRecognizer's Processor.
        textRecognizer.setProcessor(new Detector . Processor < TextBlock >() {
            @Override
            public void release() {
            }

            *//**/
    /**
     * Detect all the text from camera using TextBlock and the values into a stringBuilder
     * which will then be set to the textView.
     * *//**//*
            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray < TextBlock > items = detections . getDetectedItems ();
                if (items.size() != 0) {

                    mTextView.post(new Runnable () {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0;i < items.size();i++){
                            TextBlock item = items . valueAt (i);
                            stringBuilder.append(item.getValue());
                            stringBuilder.append("\n");
                        }
                            mTextView.setText(stringBuilder.toString());
                        }
                    });
                }
            }
        });
    }*//*

    }*/
}
