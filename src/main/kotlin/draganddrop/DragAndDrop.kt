package draganddrop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun DragAndDrop(
    modifier: Modifier = Modifier,
    onFilesDropped: (List<File>) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 80.dp, vertical = 16.dp)
        ) {
            DragArea(modifier = Modifier.size(size = maxWidth))
        }

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 80.dp, vertical = 16.dp)
        ) {
            DropArea(
                modifier = Modifier.size(size = maxWidth),
                onFilesDropped = onFilesDropped,
            )
        }
    }
}
