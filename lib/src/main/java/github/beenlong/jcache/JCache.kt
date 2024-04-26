package github.beenlong.jcache

import android.content.Context
import java.lang.RuntimeException
import java.lang.ref.WeakReference

object JCache {
    private var context: WeakReference<Context>? = null

    private var instanceMap = mutableMapOf<String, CacheInstance>()

    const val GENERAL_TAG = "JCache.general"

    fun init(context: Context) {
        this.context = WeakReference(context)
    }

    private fun getGeneralInstance(): CacheInstance {
        val context = context?.get() ?: throw (RuntimeException("JCache not init"))
        var instance = instanceMap[GENERAL_TAG]
        if (instance == null) {
            instance = SPCacheInstance(context, GENERAL_TAG)
            instanceMap[GENERAL_TAG] = instance
        }
        return instance
    }


    fun put(key: String, value: Any, tag: String? = null) {
        val instance = getInstance(tag)
        instance.put(key, value)
    }

    fun clear(tag: String? = null) {
        val instance = getInstance(tag)
        instance.clear()
    }

    fun <T> get(key: String, defaultValue: T? = null, tag: String? = null): T? {
        val instance = getInstance(tag)
        val value = instance.get<T>(key)
        return value ?: defaultValue
    }

    private fun getInstance(tag: String?) = if (tag != null) {
        instanceMap[tag] ?: throw (RuntimeException("None instance with tag:$tag"))
    } else getGeneralInstance()

}
