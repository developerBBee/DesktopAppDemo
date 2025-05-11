package draganddrop

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun DroppedFileList(
    modifier: Modifier = Modifier,
    files: List<File>,
    onClearButtonClick: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    val scrollbarAdapter = rememberScrollbarAdapter(scrollState)

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
        ) {
            if (files.isNotEmpty()) {
                item {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = onClearButtonClick,
                    ) {
                        Text(text = "Clear Files")
                    }
                }
            }

            items(files) {
                Text(
                    text = it.absolutePath,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = scrollbarAdapter
        )
    }
}
