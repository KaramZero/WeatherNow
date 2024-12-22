package com.vodafone.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodafone.core.domain.model.ErrorType

@Composable
fun ErrorState(error: ErrorType?, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val message = when (error) {
                is ErrorType.NetworkError -> "Check your internet connection"
                is ErrorType.RequestTimeoutError -> "Request timed out"
                is ErrorType.ServerError -> error.message
                else -> "Your request could not be completed"
            }

            // Error Message Text with enhanced style
            Text(
                text = message,
                style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(16.dp),
                maxLines = 3,
                color = Color.Red,
                textAlign = TextAlign.Center
            )

            // Retry Button with more styled appearance
            Button(
                onClick = onRetry,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_revert), // Optional icon
                        contentDescription = "Retry Icon",
                        tint = Color.White
                    )
                    Text("Retry", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkErrorPreview() {
    ErrorState(ErrorType.NetworkError("Network"), onRetry = {})
}

@Preview(showBackground = true)
@Composable
fun ServerErrorPreview() {
    ErrorState(ErrorType.ServerError(null, "ServerError"), onRetry = {})
}
