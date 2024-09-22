package com.example.homeworkattractions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.FragmentAttractionDetailBinding
import com.example.homeworkattractions.view.adapter.AttractionImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionDetailFragment : Fragment() {
    private val binding by lazy {
        FragmentAttractionDetailBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val args: AttractionDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initView()
    }

    private fun initView() {
        val attraction = args.attraction
        binding.attraction = attraction
        val imagesAdapter = AttractionImagesAdapter(attraction.images)
        binding.viewpagerAttractionImages.adapter = imagesAdapter
        binding.viewToolbar.setTitle(args.attraction.name)
        binding.textBusinessHours.text =
            context?.getString(R.string.attraction_business_hours, attraction.open_time)
        binding.textAddress.text =
            context?.getString(R.string.attraction_address, attraction.address)
        binding.textPhone.text = context?.getString(R.string.attraction_phone, attraction.tel)
        binding.viewToolbar.backClickListener = {
            findNavController().popBackStack()
        }
        binding.viewToolbar.goWebClickListener = {
            val attraction = args.attraction
            findNavController().navigate(
                AttractionDetailFragmentDirections.actionAttractionDetailFragmentToWebFragment(
                    attraction.name, attraction.url
                )
            )
        }
    }
}