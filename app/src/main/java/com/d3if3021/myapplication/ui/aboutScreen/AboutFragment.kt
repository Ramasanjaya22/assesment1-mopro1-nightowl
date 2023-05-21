package com.d3if3021.myapplication.ui.aboutScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.d3if3021.myapplication.R
import com.d3if3021.myapplication.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about){
    private lateinit var viewBinding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAboutBinding.inflate(inflater, container, false)
        val view = viewBinding.root
        viewBinding.buttonContactUs.setOnClickListener {
            val phoneNumber = "089526167952" // Nomor WhatsApp dummy
            val textMessage = "Halo, saya ingin menghubungi Anda" // Pesan teks dummy

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("smsto:$phoneNumber")
            intent.setPackage("com.whatsapp")
            intent.putExtra("sms_body", textMessage)

            startActivity(intent)
        }


        return view
    }

}