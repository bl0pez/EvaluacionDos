package com.example.shoppinglist.components

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.database.Product
import com.example.shoppinglist.database.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProductList() {
    val context = LocalContext.current
    val (products, setProduct) = remember {
        mutableStateOf(emptyList<Product>())
    }

    LaunchedEffect(products) {
        withContext( Dispatchers.IO ) {
            val dao = ProductDatabase.getDatabase( context ).productDao()
            setProduct( dao.getAll() )
        }
    }

    LazyColumn(
        modifier = Modifier.padding(8.dp),
        flingBehavior = ScrollableDefaults.flingBehavior(),
        content = {
            items(products) {
                    product -> ProductItem(product) {
                setProduct(emptyList<Product>())
            }
            }
        }
    )
}