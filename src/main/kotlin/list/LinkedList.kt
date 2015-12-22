package list

fun <T> prepend(list : ListItem<T>?, value : T) : ListItem<T> = ListItem(value, list)

fun <T> append(list: ListItem<T>?, value : T) : ListItem<T> {
    val newItem = ListItem(value, null)
    if(list == null) {
        return newItem
    } else {
        var item = list
        var nextItem = item.next
        while(nextItem != null) {
            item = nextItem
            nextItem = nextItem.next
        }
        item!!.next = newItem
        return list
    }
}

fun <T> length(list: ProvidesNext<T>?) : Int{
    var listItem = list
    var count : Int = 0
    while(listItem != null) {
        listItem = listItem.next
        count++
    }
    return count
}

/**
 * @return value at 0-based index in list or null if {@code index >= length(list)}
 */
fun <T> valueAt(list : ListItem<T>?, index : Int) : T? {
    var listItem = list
    var i = 0
    while(listItem != null && i < index) {
        listItem = listItem.next
        i++
    }
    return if(listItem == null) {
        null
    } else {
        listItem.value
    }
}

fun <T> compare(listA : ListItem<T>?, listB : ListItem<T>?, compareFunc : (T,T) -> Boolean): Boolean {
    var a = listA
    var b = listB
    while(a != null && b != null && compareFunc(a.value,b.value)) {
        a = a.next
        b = b.next
    }
    return a == null && b == null
}

fun <T> copy(listStart : ListItem<T>?) : ListItem<T>? {
    var listItem = listStart
    var copyHead : ListItem<T>? = null
    var copyItem = copyHead

    while(listItem != null) {
        val newItem = ListItem(listItem.value,null)
        if(copyHead == null) {
            copyHead = newItem
        } else {
            copyItem!!.next = newItem
        }
        copyItem = newItem
        listItem = listItem.next
    }
    return copyHead
}

class ListItemWithPrevious<T>(val item : ListItem<T>, val prev: ListItem<T>?)

fun <T> find(listStart : ListItem<T>?, compareFunc : (T) -> Boolean) : ListItemWithPrevious<T>? {
    var listItem = listStart
    var prevItem : ListItem<T>? = null

    while(listItem != null && !compareFunc(listItem.value)) {
        prevItem = listItem
        listItem = listItem.next
    }

    return if(listItem == null) {
        null
    } else {
        ListItemWithPrevious(listItem, prevItem)
    }
}

fun <T,S> transform(listStart : ListItem<T>?, transformFn : (T)->(S)) : ListItem<S>? {
    var listItem = listStart
    var transListStart : ListItem<S>? = null
    var transListItem = transListStart

    while(listItem != null) {
        val newItem = ListItem(transformFn(listItem.value),null)

        if(transListStart == null) {
            transListStart = newItem
        } else {
            transListItem!!.next = newItem
        }

        transListItem = newItem
        listItem = listItem.next
    }
    return transListStart
}

class ListItemWithOperationSuccess<T>(val list : ListItem<T>?, val success : Boolean)

fun <T : Any> removeFirst(listStart: ListItem<T>?, value : T) : ListItemWithOperationSuccess<T> {
    val found = find(listStart, {x->value.equals(x)})
    if(found == null) {
        return ListItemWithOperationSuccess(listStart, false)
    } else {
        if(found.prev != null) {
            found.prev.next = found.item.next
            return ListItemWithOperationSuccess(listStart, true)
        } else {
            return ListItemWithOperationSuccess(found.item.next, true)
        }
    }
}

fun <T> contains(listStart : ListItem<T>?, cmpFunc : (T) -> Boolean) : Boolean {
    var listItem = listStart
    while(listItem != null && !cmpFunc(listItem.value)) {
        listItem = listItem.next
    }
    return listItem != null
}

fun <T : Any> contains(listStart : ListItem<T>?, value : T) : Boolean {
    var listItem = listStart
    while(listItem != null && value.equals(listItem.value)) {
        listItem = listItem.next
    }
    return listItem != null
}

fun <T> listify(items : Array<T>) : ListItem<T>? {
    var listItem : ListItem<T>? = null
    var listHead = listItem

    for(item in items) {
        val newItem = ListItem(item,null)
        if(listHead == null) {
            listHead = newItem
        } else {
            listItem!!.next = newItem
        }
        listItem = newItem
    }
    return listHead
}