package com.example.wallpaper.fragments

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wallpaper.R
import com.example.wallpaper.databinding.CustomdialogBindingBinding
import com.example.wallpaper.databinding.FragmentShowBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.IOException
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.example.a4kw.models.PhotoData
import com.example.wallpaper.shared.SharedPreferencesManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.net.URL


private const val ARG_PARAM1 = "key"
private const val ARG_PARAM2 = "name"
private const val ARG_PARAM3 = "size"
private const val ARG_PARAM4 = "created"

class ShowFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
        }
    }

    lateinit var binding: FragmentShowBinding
    lateinit var list: ArrayList<PhotoData>
    lateinit var list1: ArrayList<PhotoData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentShowBinding.inflate(inflater, container, false)
        list = ArrayList()
        list1 = ArrayList()
        setImage()
        setBack()
        setDownload()
        setInstall()
//        setShare()
//        setInfo()
//
//        setHeart()
//        setLike()
//        setEditor()
//        setHistory()
        return binding.root
    }

    private fun setHistory() {
        var shared = SharedPreferencesManager().getHistory(requireContext())
        var type = object : TypeToken<ArrayList<PhotoData>>() {}.type
        if (SharedPreferencesManager().getHistory(requireContext())!=null){
            list1 = Gson().fromJson(shared.toString(), type)
        }
        var index = -1
        for (i in 0..list1.size - 1) {
            if (list1[i].urls.regular == param1) {
                index = i
            }
        }
        if (index == -1) {
//            list1.add(PhotoData(param1!!, param4!!, param2!!, param3!!))
            SharedPreferencesManager().setHistory(requireContext(), Gson().toJson(list1))
        }
    }

    private fun setEditor() {
        binding.picker.setOnClickListener {
            var bundle=Bundle()
            bundle.putString("key",param1)
            Navigation.findNavController(binding.root).navigate(R.id.filterFragment,bundle)
        }
    }

    private fun setHeart() {
//        var shared = SharedPreferencesManager().getLikes(requireContext())
//        var type = object : TypeToken<ArrayList<PhotoData>>() {}.type
//
//        if (shared != null) {
//            list = Gson().fromJson(shared.toString(), type)
//            var index = -1
//            for (i in 0..list.size - 1) {
//                if (list[i].urls.regular == param1) {
//                    index = i
//                }
//            }
//            if (index == -1) {
//                binding.seyLike.setImageResource(com.example.wallpaper.R.drawable.ic_like)
//            } else {
//                binding.seyLike.setImageResource(com.example.wallpaper.R.drawable.heart)
//
//            }
//        }
    }

    private fun setLike() {
        binding.like.setOnClickListener {
            var index = -1
            for (i in 0..list.size - 1) {
                if (list[i].urls.regular  == param1) {
                    index = i
                }
            }
            if (index == -1) {
                binding.seyLike.setImageResource(com.example.wallpaper.R.drawable.heart)
//                list.add(PhotoData(param1!!, param4!!, param2!!, param3!!))
                SharedPreferencesManager().setLikes(requireContext(), Gson().toJson(list))
            } else {
                binding.seyLike.setImageResource(com.example.wallpaper.R.drawable.ic_like)
                list.removeAt(index)
                SharedPreferencesManager().setLikes(requireContext(), Gson().toJson(list))
            }

        }
    }

    private fun setInfo() {
        binding.info.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setTitle("title")
            val customdialogBinding: CustomdialogBindingBinding =
                CustomdialogBindingBinding.inflate(layoutInflater)
            customdialogBinding.author.setText("Author: $param2")
            customdialogBinding.downloads.setText("Created at: $param4")
            customdialogBinding.size.setText("Size: $param3")
            customdialogBinding.liner.setBackgroundColor(Color.TRANSPARENT)
            customdialogBinding.website.setOnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(param1))
                startActivity(intent)
            }
            bottomSheetDialog.setContentView(customdialogBinding.getRoot())
            bottomSheetDialog.show()

        }
    }

    private fun setShare() {
        binding.shre.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, param1)
            sendIntent.type = "text/png"
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setInstall() {
        binding.wallpaper.setOnClickListener {
            Toast.makeText(requireContext(), "Installing...", Toast.LENGTH_SHORT).show()
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val wpm = WallpaperManager.getInstance(context)
            var ins: InputStream? = null
            try {
                ins =
                    URL(param1).openStream()
                wpm.setStream(ins)
                Toast.makeText(requireContext(), "Installed!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "IInstalling failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDownload() {
        binding.save.setOnClickListener {
            val cm = requireContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val NInfo = cm.activeNetworkInfo
            if (NInfo != null && NInfo.isConnectedOrConnecting) {
                try {
                    val dm = requireContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    val downloadUri = Uri.parse(param1)
                    val request = DownloadManager.Request(downloadUri)
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle("Download")
                        .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                            File.separator + "Download" + ".jpg")
                    dm.enqueue(request)
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.d("=====", "setInstall: ${e.message}")
                    Toast.makeText(requireContext(), "Image download failed.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "Internet", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun setBack() {
        binding.back.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    private fun setImage() {
        Glide.with(requireContext()).load(param1)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    Log.d("=======", "onLoadFailed: ${e!!.message}")
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    binding.image.setImageDrawable(resource)
                    return true
                }
            })
            .placeholder(R.drawable.defimage)
            .into(binding.image)
    }
}