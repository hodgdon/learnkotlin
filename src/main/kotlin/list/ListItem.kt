package list

class ListItem<T>(override val value : T, override var next : ListItem<T>?) : ProvidesNext<T>, ProvidesValue<T>