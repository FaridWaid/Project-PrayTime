package com.faridwaid.praytimeislamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faridwaid.praytimeislamic.activity.DetailDoaActivity
import com.faridwaid.praytimeislamic.adapter.DoaAdapter
import com.faridwaid.praytimeislamic.api.RetrofitClient
import com.faridwaid.praytimeislamic.model.DoaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoaFragment : Fragment() {

    private lateinit var textName: TextView
    //list dari doa - doa
    private var list = ArrayList<DoaResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //menampilkan list doa
        var rvPost: RecyclerView = view.findViewById(R.id.rv_doa)
        rvPost.setHasFixedSize(true)
        rvPost.layoutManager = LinearLayoutManager(requireContext())
        RetrofitClient.instance.getPosts().enqueue(object : Callback<ArrayList<DoaResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DoaResponse>>,
                response: Response<ArrayList<DoaResponse>>
            ) {
                val listResponse = response.body()
                listResponse?.let { list.addAll(it) }
                val adapter = DoaAdapter(list)
                rvPost.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<DoaResponse>>, t: Throwable) {

            }
        })
//        showListDoa()

    }

    // Membuat fungsi "animationToTop" yang berisi animasi ketika pinday activity
    // fungsi ini digunakan pada adapter
//    fun animationToTop() {
//        requireActivity().overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top)
//    }


}