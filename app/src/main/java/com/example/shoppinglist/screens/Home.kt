package com.example.shoppinglist.screens

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.components.Header
import com.example.shoppinglist.components.Modal
import com.example.shoppinglist.components.OpenModal
import com.example.shoppinglist.components.ProductItem
import com.example.shoppinglist.components.ProductList
import com.example.shoppinglist.database.Product
import com.example.shoppinglist.database.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen() {

    val (products, setProduct) = remember {
        mutableStateOf(emptyList<Product>())
    }

    val context = LocalContext.current
    val productScope = rememberCoroutineScope()
    val dao = ProductDatabase.getDatabase(context).productDao()

    val openDialog = remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }




    LaunchedEffect(products) {
        withContext( Dispatchers.IO ) {
            val dao = ProductDatabase.getDatabase( context ).productDao()
            setProduct( dao.getAll() )
        }
    }

    fun onCloseModal() {
        openDialog.value = false
    }

    fun onOpenModal() {
        openDialog.value = true
    }

    fun createProduct(name: String) {

    }

    Scaffold(
        topBar = { Header() },
        floatingActionButton = {
            OpenModal(onClick = {
                onOpenModal()
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ProductList()
            Modal(
                isOpen = openDialog.value,
                onCloseModal = { onCloseModal() }
            ) {
                createProduct("")
            }
        }
    }
}
