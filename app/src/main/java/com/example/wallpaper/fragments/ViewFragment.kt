package com.example.wallpaper.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wallpaper.adapters.PhotoAdapter
import com.example.wallpaper.adapters.SearchPhotoAdapter
import com.example.wallpaper.databinding.FragmentViewBinding
import com.example.wallpaper.models.s.Result
import com.example.wallpaper.retrofit.ApiClient
import com.example.wallpaper.viewmodel.MyViewModelFactory
import com.example.wallpaper.viewmodel.PhotoViewModel
import com.example.wallpaper.viewmodel.SearchPhotoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.launch
import retrofit2.Retrofit


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewFragment : Fragment(), CoroutineScope {
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

    lateinit var binding: FragmentViewBinding
    lateinit var job: Job
    lateinit var photoAdapter: SearchPhotoAdapter
    lateinit var photoViewModel: SearchPhotoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentViewBinding.inflate(inflater, container, false)
        job = Job()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setData()
    }

    private fun setData() {
        photoAdapter = SearchPhotoAdapter(object :SearchPhotoAdapter.onItemClickListener{
            override fun OnItemClick(result: Result) {

            }
        })
        binding.rv.adapter = photoAdapter
        photoViewModel = MyViewModelFactory(param1!!, order_by = param2 ?: "relevant").create(
            SearchPhotoViewModel::class.java)
        launch {
            photoViewModel.flow
                .catch {
                    Log.d("TAG", "onCreateView: " + this)
                    binding.progress.visibility = View.GONE
                }
                .collect {
                    Log.d("colect in ui", "setData: "+it)
                    photoAdapter.submitData(it)
                    binding.progress.visibility = View.GONE
                }
        }

    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, order_by: String) =
            ViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, order_by)
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = job
}