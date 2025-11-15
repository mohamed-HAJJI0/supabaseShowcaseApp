package com.testing.showcaseapp.ui.components.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopBackTopBar(
    onPopBack:()->Unit,
    leftComponent:@Composable ()->Unit = {} ,
    title:String = ""
) {
    TopAppBar(
        actions = {
            leftComponent
        } ,
        title = { Text(title)},
        navigationIcon = {
            IconButton(
                onClick = {onPopBack()}
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        }
    )
}