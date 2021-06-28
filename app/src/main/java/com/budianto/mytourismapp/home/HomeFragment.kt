package com.budianto.mytourismapp.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.budianto.mytourismapp.MainActivity
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.account.login.LoginActivity
import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.ui.TourismAdapter
import com.budianto.mytourismapp.databinding.FragmentHomeBinding
import com.budianto.mytourismapp.detail.DetailTourismActivity
import com.budianto.mytourismapp.utils.KeyGlobal.IS_LOGIN
import com.budianto.mytourismapp.utils.getDataPrefBoolean
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    companion object{
        fun newInstance() : Fragment{
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        if (activity != null) {

            val tourismAdapter = TourismAdapter()
            tourismAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailTourismActivity::class.java)
                intent.putExtra(DetailTourismActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            homeViewModel.tourism.observe(viewLifecycleOwner, { tourism ->
                if (tourism != null) {
                    when (tourism) {
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            progress_bar.visibility = View.GONE
                            tourismAdapter.setData(tourism.data)
                        }
                        is Resource.Error -> {
                            progress_bar.visibility = View.GONE
                            tv_error.visibility = View.VISIBLE
                            tv_error.text = tourism.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            })

            with(rv_tourism) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tourismAdapter
            }
        }
    }

    private fun initView(){
        binding.tvLogin.setOnClickListener{
            checkLogin()
        }
    }

    private fun checkLogin(){
        if (getDataPrefBoolean(requireContext(), IS_LOGIN)){
            val startMainActivity = Intent(context, MainActivity::class.java)
            startActivity(startMainActivity)
        } else {
            val startLoginActivity = Intent(context, LoginActivity::class.java)
            startActivity(startLoginActivity)
        }
    }
}