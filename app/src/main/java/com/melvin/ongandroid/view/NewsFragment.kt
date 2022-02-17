package com.melvin.ongandroid.view

import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.databinding.FragmentNewsBinding
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.view.adapters.NewsFragmentAdapter
import com.melvin.ongandroid.view.adapters.SlidesAdapter
import com.melvin.ongandroid.view.adapters.TestimonialsAdapter
import com.melvin.ongandroid.viewmodel.HomeViewModel
import com.melvin.ongandroid.viewmodel.VMFactory


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsFragmentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val items = mutableListOf<New>()
        //adding user
        items.add(New("Lorem Ipsum","Donec id sapien vel magna viverra vulputate at quis mauris.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet. Donec id sapien vel magna viverra vulputate at quis mauris. Etiam condimentum.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id sapien vel magna viverra vulputate at quis mauris.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id sapien vel magna viverra vulputate at quis mauris. Etiam condimentum.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet. Etiam condimentum.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id sapien vel magna viverra vulputate at quis mauris. Etiam condimentum.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id sapien vel magna viverra vulputate at quis mauris. Etiam condimentum.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id sapien vel magna viverra vulputate at quis mauris. Etiam condimentum.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))
        items.add(New("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id sapien vel magna viverra vulputate at quis mauris. Etiam condimentum.","https://i0.wp.com/developersdome.com/wp-content/uploads/2021/08/Classic-Black-and-Gold-LinkedIn-Post-Header-15.png?resize=2048%2C1152&ssl=1"))


        initRecyclerView(items)

        return binding.root
    }

    private fun initRecyclerView(items: List<New>) {
        adapter = NewsFragmentAdapter(items)
        binding.rvNews.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.rvNews.adapter = adapter
    }
}