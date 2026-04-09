package com.example.catalogoexpress.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.catalogoexpress.CatalogoExpressApp
import com.example.catalogoexpress.R
import com.example.catalogoexpress.core.result.UiState
import com.example.catalogoexpress.domain.model.Product
import com.example.catalogoexpress.ui.common.asCurrency
import com.example.catalogoexpress.ui.common.asRating
import com.example.catalogoexpress.ui.common.asScore
import com.example.catalogoexpress.ui.common.toUiMessage
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels {
        (requireActivity().application as CatalogoExpressApp).appContainer.viewModelFactory
    }

    private val productId: Long by lazy {
        requireArguments().getLong(ARG_PRODUCT_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.detailRetry).setOnClickListener {
            viewModel.loadProduct(productId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { render(view, it) }
            }
        }

        viewModel.loadProduct(productId)
    }

    private fun render(root: View, state: UiState<Product>) {
        val loadingView = root.findViewById<CircularProgressIndicator>(R.id.detailLoading)
        val errorView = root.findViewById<TextView>(R.id.detailError)
        val retryView = root.findViewById<MaterialButton>(R.id.detailRetry)

        loadingView.isVisible = state is UiState.Loading
        errorView.isVisible = state is UiState.Error
        retryView.isVisible = state is UiState.Error

        when (state) {
            UiState.Loading -> Unit
            is UiState.Error -> {
                errorView.text = state.error.toUiMessage(requireContext())
            }

            is UiState.Success -> renderProduct(root, state.data)
        }
    }

    private fun renderProduct(root: View, product: Product) {
        root.findViewById<ImageView>(R.id.productImage).load(product.imageUrl)
        root.findViewById<TextView>(R.id.productTitle).text = product.title
        root.findViewById<TextView>(R.id.productDescription).text =
            product.description.ifBlank { getString(R.string.label_no_description) }
        root.findViewById<TextView>(R.id.productPrice).text =
            getString(R.string.label_price) + ": " + product.price.asCurrency()
        root.findViewById<TextView>(R.id.productStatus).text =
            getString(R.string.label_status) + ": " + product.status
        root.findViewById<TextView>(R.id.productScore).text =
            getString(R.string.label_score) + ": " + product.score.asScore()
        root.findViewById<TextView>(R.id.productRating).text =
            getString(R.string.label_rating) + ": " + product.rating.asRating()
        root.findViewById<TextView>(R.id.productStock).text =
            getString(R.string.label_stock) + ": " + product.stock
        root.findViewById<TextView>(R.id.productCategory).text =
            getString(R.string.label_category) + ": " + product.category
        root.findViewById<TextView>(R.id.productBrand).text =
            getString(R.string.label_brand) + ": " + product.brand
        root.findViewById<TextView>(R.id.productType).text =
            getString(R.string.label_type) + ": " + product.type
    }

    companion object {
        const val ARG_PRODUCT_ID = "product_id"
    }
}
