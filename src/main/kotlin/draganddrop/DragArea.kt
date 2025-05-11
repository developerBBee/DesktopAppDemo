package draganddrop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferAction
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.DragAndDropTransferable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import java.awt.datatransfer.StringSelection

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DragArea(
    modifier: Modifier = Modifier,
) {
    val exportedText = "Hello!"
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .dragAndDropSource(
                drawDragDecoration = {
                    drawRect(
                        color = Color.White,
                        topLeft = Offset(x = 0f, y = size.height/4),
                        size = Size(size.width, size.height/2)
                    )
                    val textLayoutResult = textMeasurer.measure(
                        text = AnnotatedString(exportedText),
                        layoutDirection = layoutDirection,
                        density = this
                    )
                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            x = (size.width - textLayoutResult.size.width) / 2,
                            y = (size.height - textLayoutResult.size.height) / 2,
                        )
                    )
                }
            ) {
                detectDragGestures(
                    onDragStart = { offset ->
                        startTransfer(
                            DragAndDropTransferData(
                                transferable = DragAndDropTransferable(
                                    StringSelection(exportedText)
                                ),

                                // List of actions supported by this drag source. A type of action
                                // is passed to the drop target together with data.
                                // The target can use this to reject an inappropriate drop operation
                                // or to interpret user expectations.
                                supportedActions = listOf(
                                    DragAndDropTransferAction.Copy,
                                    DragAndDropTransferAction.Move,
                                    DragAndDropTransferAction.Link,
                                ),
                                dragDecorationOffset = offset,
                                onTransferCompleted = { action ->
                                    println("Action at the source: $action")
                                }
                            )
                        )
                    },
                    onDrag = { _, _ -> },
                )
            }
            .size(200.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Drag me!")
    }
}
