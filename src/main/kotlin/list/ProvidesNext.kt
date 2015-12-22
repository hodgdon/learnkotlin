package list

interface ProvidesNext<T> {
    val next : ProvidesNext<T>?
}