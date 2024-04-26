package github.beenlong.jcache

interface CacheInstance {
    fun put(key: String, value: Any)
    fun <T> get(key: String, type:Class<T>? = null): T?
    fun clear()
}
