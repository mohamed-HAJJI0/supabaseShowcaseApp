package com.testing.showcaseapp.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    isLoadingContent:@Composable ()->Unit ={
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = Color.White,
            strokeWidth = 2.dp
        ) },
    desabled: Boolean=false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = !isLoading||desabled
    ) {
        if (isLoading) {
            isLoadingContent()
        } else {
            Text(text = text)
        }
    }
}