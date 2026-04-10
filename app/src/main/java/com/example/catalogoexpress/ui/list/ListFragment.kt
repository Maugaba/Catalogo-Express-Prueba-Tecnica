package com.example.catalogoexpress.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.catalogoexpress.CatalogoExpressApp
import com.example.catalogoexpress.R
import com.example.catalogoexpress.ui.common.CatalogoExpressTheme

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels {
        (requireActivity().application as CatalogoExpressApp).appContainer.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
                CatalogoExpressTheme {
                    ListScreen(
                        uiState = uiState,
                        onRetry = { viewModel.loadProducts(forceRefresh = true) },
                        onItemClick = { product ->
                            findNavController().navigate(
                                R.id.action_listFragment_to_detailFragment,
                                bundleOf("product_id" to product.id),
                            )
                        },
                    )
                }
            }
        }
    }
}
