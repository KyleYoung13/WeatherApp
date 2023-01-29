package edu.oaklandcc.young.kyle1.project_3

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import edu.oaklandcc.young.kyle1.project_3.databinding.ActivityMainBinding
import edu.oaklandcc.young.kyle1.project_3.databinding.SecondFragmentBinding

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }
    private lateinit var binding: SecondFragmentBinding
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        var cityName = arguments?.getString("CITY").toString()
        binding.city.text = cityName
        var queue = Volley.newRequestQueue(context)
        viewModel.currentWeather(queue, cityName)


        val tempObserver = Observer<String>{temp-> binding.temp.text=temp}
        viewModel.getTemp().observe(viewLifecycleOwner,tempObserver)

        val dateObserver = Observer<String>{date-> binding.date.text=date}
        viewModel.getDate().observe(viewLifecycleOwner,dateObserver)

        val descObserver = Observer<String>{desc-> binding.desc.text=desc}
        viewModel.getDesc().observe(viewLifecycleOwner,descObserver)

        val iconObserver = Observer<String>{icon-> Picasso.with(context).load(icon).into(binding.icon)}
        viewModel.getIcon().observe(viewLifecycleOwner,iconObserver)

        val listObserver = Observer<ArrayList<Items>>{list->
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = RecyclerAdapter(list)
        }
        viewModel.getList().observe(viewLifecycleOwner,listObserver)



    }

}