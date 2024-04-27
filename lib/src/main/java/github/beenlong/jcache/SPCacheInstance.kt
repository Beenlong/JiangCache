package github.beenlong.jcache

import android.content.Context

/**
 * Instance implement with SharedPreferences
 *
 * @param name Name of SharedPreferences
 */
class SPCacheInstance(context: Context, name: String) : CacheInstance {
    private val sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    override fun put(key: String, value: Any) {
        with(sp.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw Exception("Unsupported type: ${value.javaClass.name}")
            }
            apply()
        }
    }

    override fun <T> get(key: String, type: Class<T>?): T? {
        sp.all[key]?.let {
            @Suppress("UNCHECKED_CAST")
            return it as T?
        }
        return null
    }

    override fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    override fun clear() {
        sp.edit().clear().apply()
    }
}
