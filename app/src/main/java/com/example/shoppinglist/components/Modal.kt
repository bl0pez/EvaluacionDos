package com.example.shoppinglist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Modal(
    isOpen: Boolean,
    onCloseModal: () -> Unit,
    createProduct: (name: String) -> Unit) {

    if (isOpen) {

        var text by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                onCloseModal()
            },
            title = {
                Text(text = "Agregar Producto")
            },
            text = {
                Column {
                    TextField(value = text, onValueChange = {
                            newName -> text = newName
                    })
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        createProduct(text);
                    }
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onCloseModal()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}