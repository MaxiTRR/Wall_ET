package com.example.fragmentsandbottomnavview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentsandbottomnavview.R
import com.example.fragmentsandbottomnavview.databinding.FragmentTransBinding

class TransFragment : Fragment() {
    private lateinit var binding: FragmentTransBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}