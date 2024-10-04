package com.example.to_docompose.ui.screens.taskDetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.ui.screens.tasksList.PriorityItem
import com.example.to_docompose.ui.theme.ContentAlpha
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.example.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_docompose.ui.theme.ToDoComposeTheme

@Composable
fun PriorityDropDown(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    val dropDownArrowAngle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "Drop-Down Arrow Icon Animation",
    )

    var rowSize by remember { mutableStateOf(Size.Zero) }

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor,
                shape = MaterialTheme.shapes.extraSmall,
            )
            .onGloballyPositioned { layoutCoordinates ->
                rowSize = layoutCoordinates.size.toSize()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(all = LARGE_PADDING),
        ) {
            Canvas(
                modifier = Modifier
                    .size(PRIORITY_INDICATOR_SIZE),
            ) {
                drawCircle(color = selectedPriority.color)
            }
        }
        Text(
            modifier = Modifier
                .width(0.dp)
                .weight(1f),
            text = stringResource(selectedPriority.titleId),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.MEDIUM)
                .rotate(dropDownArrowAngle),
            onClick = { expanded = true },
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.drop_down_arrow_icon),
            )
        }
        DropdownMenu(
            modifier = Modifier
                .width(with(LocalDensity.current) { rowSize.width.toDp() }),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            val priorities = remember {
                listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
            }
            for (priority in priorities) {
                DropdownMenuItem(
                    text = {
                        PriorityItem(priority)
                    },
                    onClick = {
                        expanded = false
                        onPrioritySelected(priority)
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun PriorityDropDownPreview() {
    ToDoComposeTheme(darkTheme = false) {
        PriorityDropDown(
            selectedPriority = Priority.MEDIUM,
            onPrioritySelected = {},
        )
    }
}
