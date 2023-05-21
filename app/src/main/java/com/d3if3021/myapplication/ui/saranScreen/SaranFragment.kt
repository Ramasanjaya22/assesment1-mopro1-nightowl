package com.d3if3021.myapplication.ui.saranScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.d3if3021.myapplication.R
import com.d3if3021.myapplication.databinding.FragmentSaranBinding

class SaranFragment : Fragment() {
    private lateinit var binding: FragmentSaranBinding

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

    override fun onDestroyView() {
        super.onDestroyView()
        // Hentikan animasi saat Fragment dihancurkan
        binding.animationView.cancelAnimation()
    }
}

