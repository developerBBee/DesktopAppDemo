import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import draganddrop.DragAndDrop
import draganddrop.DroppedFileList
import java.io.File

@Composable
fun MainContent() {
    val files = remember { mutableStateListOf<File>() }

    Column(modifier = Modifier.fillMaxSize()) {
        DroppedFileList(
            modifier = Modifier.weight(1f),
            files = files,
            onClearButtonClick = files::clear
        )

        DragAndDrop(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth(),
            onFilesDropped = files::addAll
        )
    }
}
