package com.example.wallpaper.fragments.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.wallpaper.adapters.PhotoAdapter
import com.example.wallpaper.databinding.FragmentRandomBinding
import com.example.wallpaper.viewmodel.PhotoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RandomFragment : Fragment() ,CoroutineScope{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentRandomBinding
    lateinit var job: Job
    lateinit var photoAdapter: PhotoAdapter
    lateinit var photoViewModel: PhotoViewModel
    var client_Id: String = "LAB_2aCqcq2HKVLBKVu3S8JWBnwUv8eCPtf0s1E9lMo"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRandomBinding.inflate(inflater, container, false)
        job = Job()
        setData()
        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = job
    private fun setData() {
        photoAdapter = PhotoAdapter()
        binding.rv.adapter = photoAdapter
        photoViewModel = ViewModelProvider(requireActivity())[PhotoViewModel::class.java]
        launch {
            photoViewModel.flow
                .catch {
                    Log.d("TAG", "onCreateView: " + this)
                }
                .collect {
                    photoAdapter.submitData(it)
                    Log.d("TAG", "onCreateView: " + it)
                }
        }
    }

}