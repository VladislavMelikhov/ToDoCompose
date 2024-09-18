package com.example.to_docompose.navigation

enum class ToDoTaskAction(
    val id: Int
) {
    NO_ACTION(0),
    ADD(1),
    UPDATE(2),
    DELETE(3),
    DELETE_ALL(4),
    UNDO(5),
}
