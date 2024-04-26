package github.beenlong.jcachedemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import github.beenlong.jcache.JCache

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        JCache.init(this)

        val etValue = findViewById<AppCompatEditText>(R.id.et_value)
        val key = "key"

        findViewById<AppCompatButton>(R.id.btn_put).setOnClickListener {
            val value = etValue.text.toString()
            JCache.put(key, value)
            Toast.makeText(this, "Put value:$value", Toast.LENGTH_SHORT).show()
        }

        findViewById<AppCompatButton>(R.id.btn_get).setOnClickListener {
            val value = JCache.get<String>(key)
            Toast.makeText(this, "Get value:$value", Toast.LENGTH_SHORT).show()
        }

        findViewById<AppCompatButton>(R.id.btn_clear).setOnClickListener {
            JCache.clear()
            Toast.makeText(this, "Clear", Toast.LENGTH_SHORT).show()
        }
    }
}
