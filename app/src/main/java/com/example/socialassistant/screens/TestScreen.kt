import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ModalBottomSheetM3() {
    var openBottomSheet by remember { mutableStateOf(false) }

    Button(onClick = { openBottomSheet = true }) {
        Text("Show Bottom Sheet")
    }

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = "Comments", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                }
            }
        ) {
            BottomSheetContent(
                onHideButtonClick = {

                }
            )
        }
    }
}

@Composable
fun BottomSheetContent(
    onHideButtonClick: () -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(50) {
            ListItem(
                headlineContent = { Text("Item $it") },
                leadingContent = {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                }
            )
        }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onHideButtonClick
                }) {
                Text("Cancel")
            }
        }
    }

}