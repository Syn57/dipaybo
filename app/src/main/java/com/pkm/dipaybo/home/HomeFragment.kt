package com.pkm.dipaybo.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pkm.dipaybo.R
import com.pkm.dipaybo.camera.CameraActivity
import com.pkm.dipaybo.data.PadlockModel
import com.pkm.dipaybo.databinding.FragmentHomeBinding
import com.pkm.dipaybo.databinding.FragmentParkingBinding
import kotlin.math.log

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var db: FirebaseDatabase
    private lateinit var dbRf: DatabaseReference

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseDatabase.getInstance()
        dbRf = db.getReference("padlocks")
        val valueListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                var value = snapshot.getValue(PadlockModel::class.java)
////                binding.tvPointValue.text = value?.balance.toString()
//                Log.d("TAG", "onDataChange3: ${snapshot.value}")
                for (data in snapshot.children){
                    val value = data.getValue(PadlockModel::class.java)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }

        }
        db.reference.addValueEventListener(valueListener)


        initView()
        binding.btnPark.setOnClickListener {
            startActivity(Intent(requireActivity(), CameraActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {

    }

    companion object {

    }
}