package com.example.homeworkattractions.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.FragmentHomeBinding
import com.example.homeworkattractions.model.HomeItemList
import com.example.homeworkattractions.util.LocaleHelper
import com.example.homeworkattractions.view.adapter.HomeListAdapter
import com.example.homeworkattractions.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val showCount = 3
    private var adapter: HomeListAdapter? = null
    private val viewModel: MainViewModel by viewModels()
    private val binding by lazy {
        FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initObserver()
        initView()
        viewModel.fetchHomeData()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.layoutManager = layoutManager
        adapter = HomeListAdapter(onItemClickListener, onItemClickMoreListener)
        binding.rvHome.adapter = adapter
        binding.viewToolbar.changeLaguageClickListener = {
            showLanguageDialog()
        }
    }

    private val onItemClickListener: (Int, HomeItemList, Int) -> Unit =
        { viewType, homeItem, position ->
            when (viewType) {
                HomeListAdapter.DataType.NEWS -> {
                    if (homeItem is HomeItemList.NewsItemList) {
                        var item = homeItem.newsList.elementAtOrNull(position)
                        val action = item?.let {
                            HomeFragmentDirections.actionHomeFragmentToWebFragment(
                                context?.getString(R.string.home_news_title) ?: it.title, it.url
                            )
                        }
                        action?.let { findNavController().navigate(it) }
                    }
                }

                HomeListAdapter.DataType.ATTRACTIONS -> {
                    if (homeItem is HomeItemList.AttractionItemList) {
                        var item = homeItem.attractionList.elementAtOrNull(position)
                        val action = item?.let {
                            HomeFragmentDirections.actionHomeFragmentToAttractionDetailFragment(
                                it
                            )
                        }
                        action?.let { findNavController().navigate(it) }
                    }
                }
            }
        }

    private val onItemClickMoreListener: (Int?) -> Unit = { viewType ->
        val action = viewType?.let {
            HomeFragmentDirections.actionHomeFragmentToMoreListFragment(viewType)
        }
        action?.let { findNavController().navigate(it) }
    }

    private fun initObserver() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                if (it.size > showCount) {
                    adapter?.showNewsList(requireActivity(), it.subList(0, 3))
                } else {
                    adapter?.showNewsList(requireActivity(), it.subList(0, it.size))
                }
            }
        }

        viewModel.attractionsLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                if (it.size > showCount) {
                    adapter?.showAttractionsList(requireActivity(), it.subList(0, 3))
                } else {
                    adapter?.showAttractionsList(requireActivity(), it.subList(0, it.size))
                }

            }
        }

        viewModel.isViewLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog() else dismissLoadingDialog()
            if (!it && viewModel.newsLiveData.value.isNullOrEmpty() && viewModel.attractionsLiveData.value.isNullOrEmpty()) {
                adapter?.showEmptyView()
            }
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf(
            "繁體中文",
            "简体中文",
            "English",
            "日本語",
            "한국어",
            "español",
            "Indonesia",
            "ยาสุฟุมิ",
            "Tiếng Việt"
        )
        val languageCodes = arrayOf("TW", "CN", "en", "ja", "ko", "es", "id", "th", "vi")

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.home_select_language_title)
            .setItems(languages) { _, which ->
                val selectedLanguage = languageCodes[which]
                setLocale(selectedLanguage)
            }
            .show()
    }


    private fun setLocale(languageCode: String) {
        val currentLang = LocaleHelper.readSettingLang()
        val currentCountry = LocaleHelper.readSettingCountry()
        if (!currentLang.contains(languageCode) && !currentCountry.contains(languageCode)) {
            when (languageCode) {
                "TW" -> {
                    LocaleHelper.initLocale(requireContext(), "zh", "TW")
                }

                "CN" -> {
                    LocaleHelper.initLocale(requireContext(), "zh", "CN")
                }

                else -> {
                    LocaleHelper.initLocale(requireContext(), languageCode)
                }
            }
            requireActivity().recreate()
        }
    }
}