package com.example.repository.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repository.R
import com.example.repository.databinding.DevbyteItemBinding
import com.example.repository.databinding.FragmentDevByteBinding
import com.example.repository.domain.DevByteVideo
import com.example.repository.util.VideoAdapter
import com.example.repository.viewmodels.DevByteViewModel
import com.example.repository.viewmodels.DevByteViewModelFactory


class DevByteFragment : Fragment() {


    private val viewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, DevByteViewModelFactory(activity.application))
            .get(DevByteViewModel::class.java)
    }

    private lateinit var binding : FragmentDevByteBinding

    private val DevByteVideo.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev_byte, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewmodel = viewModel

        binding.recyclerView.adapter = VideoAdapter(VideoAdapter.OnclickListener {

            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if(intent.resolveActivity(activity!!.packageManager) == null) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            }

            startActivity(intent)
        })


        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }


    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }



}
