package com.example.mymiso.features.design_patterns.command

// Command Design Pattern
// Each command encapsulates a specific action, allowing for cleaner code and better organization of commands.
// Maintains a history of commands, enabling users to undo and redo actions.

interface Command {
    fun execute()
    fun undo()
}

class Order {
    private val items = mutableListOf<String>()
    fun addItem(item: String) {
        items.add(item)
    }

    fun removeItem(item: String) {
        items.remove(item)
    }

    fun getItems(): List<String> {
        return items
    }
}

class AddItemCommand(private val order: Order, private val item: String) : Command {
    override fun execute() {
        order.addItem(item)
    }

    override fun undo() {
        order.removeItem(item)
    }

}

class RemoveItemCommand(private val order: Order, private val item: String) : Command {
    override fun execute() {
        order.removeItem(item)
    }

    override fun undo() {
        order.addItem(item)
    }

}


// It contains a list of commands that are executed and can be undone.
class OrderManager {
    private val commandHistory = mutableListOf<Command>()

    fun executeCommand(command: Command) {
        command.execute()
        commandHistory.add(command)
    }

    fun undo() {
        if (commandHistory.isNotEmpty()) {
            val command = commandHistory.removeAt(commandHistory.size - 1)
            command.undo()
        }
    }
}

fun main() {
    val order = Order()
    val orderManager = OrderManager()

    val addItemCommand = AddItemCommand(order, "item1")
    orderManager.executeCommand(addItemCommand)

    val addItemCommand2 = AddItemCommand(order, "item2")
    orderManager.executeCommand(addItemCommand2)

    val removeItemCommand = RemoveItemCommand(order, "item1")
    orderManager.executeCommand(removeItemCommand)

    println(order.getItems())

    orderManager.undo()
    println(order.getItems())

    orderManager.undo()
    println(order.getItems())

}