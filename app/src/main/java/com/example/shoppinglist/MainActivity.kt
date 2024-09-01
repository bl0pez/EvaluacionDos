package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.components.ProductItem
import com.example.shoppinglist.database.Product
import com.example.shoppinglist.database.ProductDatabase
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
               Main()
            }
        }
    }
}

@Composable
fun Main() {

    val context = LocalContext.current
    val dao = ProductDatabase.getDatabase( context ).productDao()
    val (products, setProduct) = remember {
        mutableStateOf(emptyList<Product>())
    }
    val productScope = rememberCoroutineScope()

    var text by remember { mutableStateOf("") }

    LaunchedEffect(products) {
        withContext( Dispatchers.IO ) {
            setProduct( dao.getAll() )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { newName -> text = newName },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ingrese Producto") },
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                productScope.launch(Dispatchers.IO) {
                    if (text.length > 0) {
                        dao.create(Product(0, text, false))
                        setProduct(emptyList<Product>())
                        text = ""
                    }
                }
            }) {
            Text("Agregar")
        }

        LazyColumn(
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
}