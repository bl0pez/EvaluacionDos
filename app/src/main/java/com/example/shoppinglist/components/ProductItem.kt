package com.example.shoppinglist.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.example.shoppinglist.database.Product
import com.example.shoppinglist.database.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProductItem(product: Product, onSave: () -> Unit) {

    val context = LocalContext.current
    val productScope = rememberCoroutineScope()
    val dao = ProductDatabase.getDatabase(context).productDao()

    fun getIcon(isCompleted: Boolean): ImageVector {
        if (isCompleted) {
            return Icons.Sharp.Done
        }

        return Icons.Filled.ShoppingCart
    }

    fun onToggleStatus(isCompleted: Boolean) {
        productScope.launch(Dispatchers.IO) {
            if (isCompleted) {
                product.isCompleted = false
                dao.update(product)
            } else {
                product.isCompleted = true
                dao.update(product)
            }
            onSave()
        }
    }

    ListItem(
        leadingContent = {
            Icon(
                getIcon(product.isCompleted),
                modifier = Modifier.clickable {
                    onToggleStatus(product.isCompleted)
                },
                contentDescription = "Icono de corazon",
            )
        },
        headlineContent = { Text(text = product.name) },
        trailingContent = {
            IconButton(
                onClick = {
                    productScope.launch(Dispatchers.IO) {
                        dao.delete(product)
                        onSave()
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Red,
                )) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar Producto"
                )
            }
        }
    )
}