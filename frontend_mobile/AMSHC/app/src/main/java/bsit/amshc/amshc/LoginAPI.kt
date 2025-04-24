package bsit.amshc.amshc

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import io.github.jakepurple13.supabase.SupabaseClient
import io.github.jakepurple13.supabase.createSupabaseClient
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.serialization.Serializable
import kotlinx.coroutines.launch

class LoginAPI : AppCompatActivity() {

    private lateinit var supabase: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen_page) // Your XML file

        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<MaterialButton>(R.id.button3)

        supabase = createSupabaseClient(
            supabaseUrl = "https://xljhgkeshoowcuepyzlv.supabase.co",  // Replace this
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhsamhna2VzaG9vd2N1ZXB5emx2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ4NDg5ODYsImV4cCI6MjA2MDQyNDk4Nn0.ki4SZ2wQsHUaMxEn_SbhxVE3jaXn2dsxwX1CMGnhuFY"                         // Replace this
        )

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val user = supabase.from("app_user")
                    .select()
                    .eq("email", email)
                    .single()
                    .decodeSingle<AppUser>()

                val passwordMatches = BCrypt.verifyer()
                    .verify(password.toCharArray(), user.password)
                    .verified

                if (passwordMatches) {
                    Toast.makeText(this@LoginAPI, "Login successful! Welcome ${user.full_name}", Toast.LENGTH_LONG).show()

                    // Send user to next screen
                    val intent = Intent(this@LoginAPI, MainActivity::class.java).apply {
                        putExtra("USER_ID", user.id)
                        putExtra("USER_ROLE", user.role)
                        putExtra("USER_NAME", user.full_name)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginAPI, "Incorrect password", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@LoginAPI, "Login failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Serializable
data class AppUser(
    val id: Int,
    val email: String,
    val full_name: String? = null,
    val password: String,
    val role: String,
    val reset_code: String? = null,
    val profile_picture_url: String? = null
)