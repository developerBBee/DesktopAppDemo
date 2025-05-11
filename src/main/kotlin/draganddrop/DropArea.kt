package draganddrop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.awtTransferable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.datatransfer.DataFlavor
import java.io.File

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DropArea(
    modifier: Modifier = Modifier,
    onFilesDropped: (List<File>) -> Unit,
) {

    var showTargetBorder by remember { mutableStateOf(false) }
    var targetText by remember { mutableStateOf("Drop Here") }
    val coroutineScope = rememberCoroutineScope()
    val dragAndDropTarget = remember {
        object: DragAndDropTarget {
            var job: Job? = null

            // Highlights the border of a potential drop target
            override fun onEntered(event: DragAndDropEvent) {
                showTargetBorder = true
            }

            override fun onExited(event: DragAndDropEvent) {
                showTargetBorder = false
            }

            override fun onEnded(event: DragAndDropEvent) {
                showTargetBorder = false
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                job?.cancel()

                val transferable = event.awtTransferable
                if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    job = coroutineScope.launch {
                        targetText = transferable.getTransferData(DataFlavor.stringFlavor) as String
                        delay(2000)
                    }.apply { invokeOnCompletion { targetText = "Drop here" } }
                } else if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    try {
                        val dataList = transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<*>
                        val fileList = dataList.filterIsInstance<File>()
                        onFilesDropped(fileList)
                    } catch (_: Exception) {
                        return false
                    }
                }

                return true
            }
        }
    }

    Box(
        modifier = modifier
            .background(Color.LightGray)
            .then(
                if (showTargetBorder)
                    Modifier.border(BorderStroke(3.dp, Color.Black))
                else
                    Modifier
            )
            .dragAndDropTarget(
                // With "true" as the value of shouldStartDragAndDrop,
                // drag-and-drop operations are enabled unconditionally.
                shouldStartDragAndDrop = { true },
                target = dragAndDropTarget
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(targetText, Modifier.align(Alignment.Center))
    }
}
