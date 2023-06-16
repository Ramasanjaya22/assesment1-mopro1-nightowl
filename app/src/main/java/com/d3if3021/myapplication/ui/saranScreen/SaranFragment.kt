package com.d3if3021.myapplication.ui.saranScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.d3if3021.myapplication.R
import com.d3if3021.myapplication.databinding.FragmentSaranBinding
import com.d3if3021.myapplication.network.ApiStatus
import com.d3if3021.myapplication.network.TebakGambarApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaranFragment : Fragment() {
    private lateinit var binding: FragmentSaranBinding
    private lateinit var tvData: TextView
    private lateinit var imgData: ImageView

    private val status = MutableLiveData<ApiStatus>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaranBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.animationView.setAnimation(R.raw.panda_sleep_anim)
        binding.animationView.playAnimation()
        binding.animationView.repeatCount = LottieDrawable.INFINITE
        binding.textView.text = getText(R.string.saran_tidur)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvData = view.findViewById(R.id.tvData)
        imgData = view.findViewById(R.id.imgData)
        retrieveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hentikan animasi saat Fragment dihancurkan
        binding.animationView.cancelAnimation()
    }

    private fun retrieveData() {
        CoroutineScope(Dispatchers.IO).launch {
            status.postValue(ApiStatus.LOADING)
            try {
                val response = TebakGambarApi.service.getTebakGambar()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        withContext(Dispatchers.Main) {
                            tvData.text = data.jawaban

                            Glide.with(requireContext())
                                .load(data.img)
                                .error(R.drawable.baseline_broken_image_24)
                                .into(imgData)
                        }
                        status.postValue(ApiStatus.SUCCESS)
                    } else {
                        Log.d("SaranFragment", "Failure: Response body is null")
                        status.postValue(ApiStatus.FAILED)
                    }
                } else {
                    Log.d("SaranFragment", "Failure: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("SaranFragment", "Failure: ${e.message}")
            }
        }
    }
    fun getStatus(): LiveData<ApiStatus> = status
}


