package com.faridwaid.praytimeislamic

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class InfoFragment : Fragment() {

    private lateinit var infoApp: Button
    private lateinit var apiPrayTime: Button
    private lateinit var apiDoa: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set untuk link ke api pray time
        apiPrayTime = view.findViewById(R.id.apiPrayTime)
        apiPrayTime.setOnClickListener {
            val uri: Uri = Uri.parse("https://waktusholat.org/api/docs/")
            startActivity(Intent(Intent.ACTION_VIEW,uri))
        }

        //set untuk link ke api doa
        apiDoa = view.findViewById(R.id.apiDoa)
        apiDoa.setOnClickListener {
            val uri: Uri = Uri.parse("https://doa-doa-api-ahmadramadhan.fly.dev/")
            startActivity(Intent(Intent.ACTION_VIEW,uri))
        }

        infoApp = view.findViewById(R.id.infoApp)
        infoApp.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
            alertDialog.apply {
                setTitle("Pray Time Islamic")
                setMessage("Pray Time Islamic adalah aplikasi yang dikembangkan untuk membantu pengguna mengetahui jadwal sholat fardhu 5 waktu, aplikasi ini dapat memberikan jadwal sholat fardhu berdasarkan pilihan wilayah khusus untuk negara Indonesia. Aplikasi ini juga menyediakan informasi terkait doa sehari - hari.")
                setIcon(R.drawable.ic_info)
                setPositiveButton("Close", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
            }
            alertDialog.show()
        }

    }
}