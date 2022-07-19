package com.faridwaid.praytimeislamic

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.faridwaid.praytimeislamic.adapter.PrayTimeAdapter
import com.faridwaid.praytimeislamic.api.RetrofitClient
import com.faridwaid.praytimeislamic.model.PrayTime
import com.faridwaid.praytimeislamic.model.PraysTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import android.os.CountDownTimer
import kotlin.properties.Delegates


class PrayFragment : Fragment() {

    //mendefinisikan variabel
    private lateinit var country: TextView
    private lateinit var dayText: TextView
    private lateinit var dateText: TextView
    private lateinit var nextButton: ImageView
    private lateinit var prevButton: ImageView
    private lateinit var btnLocation: Button
    private lateinit var refreshData: SwipeRefreshLayout
    private lateinit var tvSubuh : TextView
    private lateinit var tvDuhur : TextView
    private lateinit var tvAsar : TextView
    private lateinit var tvMaghrib : TextView
    private lateinit var tvIsya : TextView
    private lateinit var location: TextView
    private lateinit var mTextViewCountDown: TextView
    private lateinit var timeViewPager: ViewPager2
    private var hours by Delegates.notNull<Long>()
    private var minutes by Delegates.notNull<Long>()
    private var seconds by Delegates.notNull<Long>()
    private var countTime by Delegates.notNull<Int>()
    private var timeInMilies by Delegates.notNull<Long>()
    private var viewPager = ArrayList<PrayTime>()
    private var mCountDownTimer: CountDownTimer? = null
    private var mTimerRunning = false
    private var timeNext: Long = 53400000
    private var timeNow: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pray, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //menginisialisasi variable
        tvSubuh = view.findViewById(R.id.timeSubuh)
        tvDuhur = view.findViewById(R.id.timeDhuhur)
        tvAsar = view.findViewById(R.id.timeAshar)
        tvMaghrib = view.findViewById(R.id.timeMaghrib)
        tvIsya = view.findViewById(R.id.timeIsya)
        location = view.findViewById(R.id.location)
        country = view.findViewById(R.id.country)
        timeViewPager = view.findViewById(R.id.timeViewPager)
        mTextViewCountDown = view.findViewById(R.id.timeLeft)
        nextButton = view.findViewById(R.id.nextButton)
        prevButton = view.findViewById(R.id.prevButton)
        dayText = view.findViewById(R.id.day)
        dateText = view.findViewById(R.id.date)

        hours = 0
        minutes = 0
        seconds = 0
        countTime = 0
        timeInMilies = 0

        //memanggil fungsi showListPray dengan argumen city = "surabaya"
        showListPray("surabaya")

        //menginisialisasi variable refreshData
        refreshData = view.findViewById(R.id.refreshData)

        //jika variable refreshData di refresh
        refreshData.setOnRefreshListener {
            // Loading selama beberapa waktu, ketika sudah selesa nilai refreshFrament menjadi false
            Handler().postDelayed(Runnable {
                refreshData.isRefreshing = false
            }, 2000)
        }

        //mengambil nilai calender dan menentukan format calender
        var calendar: Calendar = Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("EEEE")
        var simpleDateFormat2 = SimpleDateFormat("dd LLLL yyyy")
        var dateTime: String = simpleDateFormat.format(calendar.time).toString()
        var dateTime2: String = simpleDateFormat2.format(calendar.time).toString()

        //mengisi textView dengan value dari dateTime calender
        dayText.text = dateTime
        dateText.text = dateTime2

        //jika btnLocation di klik maka akan dilakukan pergantian lokasi dengan alert dialog
        btnLocation = view.findViewById(R.id.btnLocation)
        btnLocation.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
            alertDialog.apply {
                // Menambahkan title dan pesan ke dalam alert dialog
                setTitle("GANTI LOKASI!")
                val input = EditText(requireContext())
                input.setHint("Masukkan Lokasi")
                input.inputType = InputType.TYPE_CLASS_TEXT
                val inputText = input.text.toString().trim()
                // Jika inputText kosong maka akan muncul error harus isi terlebih dahulu
                if (inputText.isEmpty()){
                    input.error = "Masukkan lokasi/wilayah terlebih dahulu!"
                    input.requestFocus()
                }
                alertDialog.setView(input)
                setIcon(R.drawable.ic_location)
                setPositiveButton(
                    "Ganti Lokasi",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                        val text = input.text.toString()
                        location.setText(text.toUpperCase())
                        showListPray(text)
                    })
            }
            alertDialog.show()
            nextButton.visibility = View.VISIBLE
        }
    }

    //membuat fungsi showListPray dengan parameter city
    fun showListPray(city: String){
        //menggunakan library retrofit client untuk memanggil data api
        RetrofitClient.instance.getPrayTime(city).enqueue(object : Callback<PraysTime>{
            override fun onResponse(call: Call<PraysTime>, response: Response<PraysTime>) {

                //memasukkan response dari api ke dalam variable oke
                val oke = response.body()!!.items[0]

                //mengosongkan viewPager
                viewPager.clear()

                //menambahkan data sholat subuh ke dalam viewPager
                val viewSubuh = PrayTime()
                viewSubuh.sholat = "Subuh"
                viewSubuh.waktu = "${oke.fajr.substring(0, 5)}"
                viewPager.add(viewSubuh)

                //menambahkan data sholat dhuhur ke dalam viewPager
                val viewDhuhur = PrayTime()
                viewDhuhur.sholat = "Dhuhur"
                viewDhuhur.waktu = "${oke.dhuhr.substring(0, 5)}"
                viewPager.add(viewDhuhur)

                //menambahkan data sholat ashar ke dalam viewPager
                val viewAshar = PrayTime()
                viewAshar.sholat = "Ashar"
                viewAshar.waktu = "${oke.asr.substring(0, 5)}"
                viewPager.add(viewAshar)

                //menambahkan data sholat maghrib ke dalam viewPager
                val viewMaghrib = PrayTime()
                viewMaghrib.sholat = "Maghrib"
                viewMaghrib.waktu = "${oke.maghrib.substring(0, 5)}"
                viewPager.add(viewMaghrib)

                //menambahkan data sholat isya ke dalam viewPager
                val viewIsya = PrayTime()
                viewIsya.sholat = "Isya'"
                viewIsya.waktu = "${oke.isha.substring(0, 5)}"
                viewPager.add(viewIsya)

                //memasukkan list viewPager ke dalam PrayTimeAdapter
                val adapter = PrayTimeAdapter(viewPager)
                timeViewPager.adapter = adapter

                //memberikan limit ke timeViewPager dengan value 5
                timeViewPager.offscreenPageLimit = 5
                //jika timeViewPager dipanggil maka akan ditampilkan mulai dari index ke 0
                timeViewPager.getChildAt(0)
                timeViewPager.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                //membuat variabel prevbutton menjadi invisible/tidak terlihat
                prevButton.visibility = View.INVISIBLE
                //memanggil fungsi updateTimeLeft dengan argumen waktu sholat subuh yang sudah dibuat menjadi miliesecond
                updateTimeLeft(oke.fajr.substring(0,1).toLong() * 3600000, oke.fajr.substring(2,4).toLong() * 60000)
                //membuat value dari countTime menjadi 0
                countTime = 0


                // Ketika "nextButton" diklik akan pindah ke data selanjutnya
                nextButton.setOnClickListener {
                    //jika berhasil pindah ke data selanjutnya maka nilai countTime +1
                    if (timeViewPager.currentItem + 1 < adapter.itemCount){
                        timeViewPager.currentItem += 1
                        if (countTime < 5 && countTime > -1){
                            countTime += 1
                        }
                        //jika countTime = 0, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat subuh yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 1, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat dhuhur yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 2, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat ashar yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 3, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat maghrib yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 4, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat isya yang sudah dibuat menjadi miliesecond
                        if (countTime == 0){
                            updateTimeLeft((oke.fajr.substring(0,1).toLong()) * 3600000, oke.fajr.substring(2,4).toLong() * 60000)
                        } else if (countTime == 1){
                            updateTimeLeft((oke.dhuhr.substring(0,2).toLong()) * 3600000, oke.dhuhr.substring(3,5).toLong() * 60000)
                            prevButton.visibility = View.VISIBLE
                        } else if (countTime == 2){
                            updateTimeLeft((oke.asr.substring(0,1).toLong()+12) * 3600000, oke.asr.substring(2,4).toLong() * 60000)
                        }  else if (countTime == 3){
                            updateTimeLeft((oke.maghrib.substring(0,1).toLong()+12) * 3600000, oke.maghrib.substring(2,4).toLong() * 60000)
                        }  else if (countTime == 4){
                            updateTimeLeft((oke.isha.substring(0,1).toLong()+12) * 3600000, oke.isha.substring(2,4).toLong() * 60000)
                            //membuat nextButton menjadi invisible, dan prevButton menjadi visible
                            nextButton.visibility = View.INVISIBLE
                            prevButton.visibility = View.VISIBLE
                        }
                    }
                }

                // Ketika "nextButton" diklik akan pindah ke data sebelumnya
                prevButton.setOnClickListener {
                    //jika berhasil pindah ke data selanjutnya maka nilai countTime -1
                    if (timeViewPager.currentItem - 1 < adapter.itemCount){
                        timeViewPager.currentItem -= 1
                        if (countTime > -1 && countTime < 5){
                            countTime -= 1
                        }
                        //jika countTime = 0, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat subuh yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 1, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat dhuhur yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 2, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat ashar yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 3, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat maghrib yang sudah dibuat menjadi miliesecond,
                        //jika countTime = 4, maka memanggil fungsi updateTimeLeft dengan argumen waktu sholat isya yang sudah dibuat menjadi miliesecond
                        if (countTime == 0){
                            updateTimeLeft((oke.fajr.substring(0,1).toLong()) * 3600000, oke.fajr.substring(2,4).toLong() * 60000)
                            prevButton.visibility = View.INVISIBLE
                        } else if (countTime == 1){
                            updateTimeLeft((oke.dhuhr.substring(0,2).toLong()) * 3600000, oke.dhuhr.substring(3,5).toLong() * 60000)
                        } else if (countTime == 2){
                            updateTimeLeft((oke.asr.substring(0,1).toLong()+12) * 3600000, oke.asr.substring(2,4).toLong() * 60000)
                        }  else if (countTime == 3){
                            updateTimeLeft((oke.maghrib.substring(0,1).toLong()+12) * 3600000, oke.maghrib.substring(2,4).toLong() * 60000)
                            nextButton.visibility = View.VISIBLE
                        }  else if (countTime == 4){
                            updateTimeLeft((oke.isha.substring(0,1).toLong()+12) * 3600000, oke.isha.substring(2,4).toLong() * 60000)
                        }
                    }
                }

                //mengisi value daro variable country dengan data dari API country
                country.text = response.body()!!.country

                //mengisi textView dengan value dari data yang didapat dengan API
                val text1: String = "${oke.fajr}"
                tvSubuh.text = text1
                val text2: String = "${oke.dhuhr}"
                tvDuhur.text = text2
                val text3: String = "${oke.asr}"
                tvAsar.text = text3
                val text4: String = "${oke.maghrib}"
                tvMaghrib.text = text4
                val text5: String = "${oke.isha}"
                tvIsya.text = text5
            }

            //jika gagal akan memberikan peringatan dengan alertdialog
            override fun onFailure(call: Call<PraysTime>, t: Throwable) {
                val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
                alertDialog.apply {
                    // Menambahkan title dan pesan ke dalam alert dialog
                    setTitle("PERMINTAAN GAGAL!")
                    setMessage("Gagal mengambil data, tolong cek kembali apakah lokasi/wilayah sudah benar!")
                    setIcon(R.drawable.ic_alert)
                    setPositiveButton(
                        "Coba lagi",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                }
                alertDialog.show()
            }

        })
    }

    //membuat fungsi startTimer
    private fun startTimer() {
        //jika terdapat CountDownTimer yang berjalan sebelumnya, maka akan dihentikan
        mCountDownTimer?.cancel()
        //mengisi CountDownTImer dengan value waktu dari timeInMilies
        mCountDownTimer = object : CountDownTimer(timeInMilies, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMilies = millisUntilFinished
                updateCountDownText()
            }

            //jika CountDownTImer sudah selesai maka mTextViewCountDown di set SELESAI
            override fun onFinish() {
                mTimerRunning = false
                mTextViewCountDown.setText("time left: SELESAI")
            }
        }.start()
        mTimerRunning = true
    }

    //membuat fungsi updateCountDownText untuk menghitung waktu dari CountDownTimer dari value timeInMilies
    private fun updateCountDownText() {
        hours = (timeInMilies / 1000) as Long / 3600
        minutes = ((timeInMilies / 1000) as Long / 60) - (hours*60)
        seconds = (timeInMilies / 1000) as Long % 60
        val timeLeftFormatted = java.lang.String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes.toInt(), seconds)
        mTextViewCountDown.setText("time left: $timeLeftFormatted")
    }

    //membuat fungsi updateTimeLeft untuk menghitung value dari timeInMilies
    private fun updateTimeLeft(hourPray: Long, minutePray: Long) {
        //mengambil nilai calender dan menentukan format calender
        var calendar: Calendar = Calendar.getInstance()
        var simpleDateHour = SimpleDateFormat("HH")
        var simpleDateMinutes = SimpleDateFormat("mm")
        var simpleDateSeconds = SimpleDateFormat("ss")

        var hourNow: Long = simpleDateHour.format(calendar.time).toLong()
        var minuteNow: Long = simpleDateMinutes.format(calendar.time).toLong()
        var secondNow: Long = simpleDateSeconds.format(calendar.time).toLong()

        hourNow *= 3600000
        minuteNow *= 60000
        secondNow *= 1000

        timeNow = hourNow + minuteNow + secondNow

        val hour = hourPray
        val minute = minutePray
        timeNext = hour + minute
        if (timeNext > 0){
            timeInMilies = timeNext - timeNow
            startTimer()
        } else {
            timeInMilies = 0
        }
    }

}