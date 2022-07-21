package com.example.wallpaper.fragments

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.wallpaper.R
import com.example.wallpaper.adapters.SearchPhotoAdapter
import com.example.wallpaper.databinding.FragmentSearchBinding
import com.example.wallpaper.models.s.Result
import com.example.wallpaper.viewmodel.MyViewModelFactory
import com.example.wallpaper.viewmodel.SearchPhotoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment(), CoroutineScope {
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

    lateinit var binding: FragmentSearchBinding
    lateinit var job: Job
    lateinit var photoAdapter: SearchPhotoAdapter
    lateinit var photoViewModel: SearchPhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,
            container,
            false)
        job = Job()
        setHasOptionsMenu(true)
        setToolBar()
        return binding.root
    }

    private fun setToolBar() {
        binding.apply {
            toolbar.collapseActionView()
            toolbar.title="Search"
            searchview.queryHint = Html.fromHtml("<font color = #ffffff>" + "Search..." + "</font>")
            toolbar.setNavigationOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if (searchview.query.length > 2) {
                        setData(searchview.query.toString())
                    } else {
                        Toast.makeText(requireContext(),
                            "Query length must be more than 2 chars",
                            Toast.LENGTH_SHORT).show()
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }

    }

    private fun setData(query: String) {
        photoAdapter = SearchPhotoAdapter(object :SearchPhotoAdapter.onItemClickListener{
            override fun OnItemClick(result: Result) {
                val bundle = Bundle()
                bundle.putString("key",result.urls.full)
                bundle.putString("name",result.description)
                bundle.putString("size",result.description)
                bundle.putString("created",result.description)
                Navigation.findNavController(requireView()).navigate(R.id.showFragment,bundle)
            }
        })
        binding.rv.adapter = photoAdapter
        photoViewModel = MyViewModelFactory(query, order_by = param2 ?: "latest").create(
            SearchPhotoViewModel::class.java)
        launch {
            photoViewModel.flow
                .catch {
                    Log.d("TAG", "onCreateView: " + this)
                }
                .collect {
                    photoAdapter.submitData(it)
                }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)


    }

    override val coroutineContext: CoroutineContext
        get() = job


}