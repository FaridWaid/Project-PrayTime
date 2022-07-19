package com.faridwaid.praytimeislamic

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faridwaid.praytimeislamic.adapter.DoaAdapter
import com.faridwaid.praytimeislamic.api.RetrofitClient
import com.faridwaid.praytimeislamic.model.DoaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoaFragment : Fragment() {

    //mendefinisikan variabel
    private lateinit var refreshData: SwipeRefreshLayout
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

        //menginisialisasi variable refreshData
        refreshData = view.findViewById(R.id.refreshData)

        //jika variable refreshData di refresh
        refreshData.setOnRefreshListener {
            // Loading selama beberapa waktu, ketika sudah selesa nilai refreshFrament menjadi false
            Handler().postDelayed(Runnable {
                refreshData.isRefreshing = false
            }, 2000)
        }

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
    }

}