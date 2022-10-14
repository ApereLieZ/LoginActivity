package com.apik.test.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.apik.test.R
import com.apik.test.databinding.ActivityMainBinding
import com.apik.test.features.validation.SignInValidation
import com.apik.test.viewModel.LoginViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.tokend.sdk.api.wallets.model.EmailNotVerifiedException
import org.tokend.sdk.api.wallets.model.InvalidCredentialsException

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = LoginViewModel()

        binding.sigIn.setOnClickListener {
            sigIn()
        }

    }

    private fun sigIn() {
        if(!SignInValidation.emailValidation(binding.emailEditText.editText?.text.toString())){
            binding.emailEditText.error = "Email is not valid"
            return
        }
        if(!SignInValidation.passwordValidation(binding.passwordEditText.editText?.text.toString())){
            binding.passwordEditText.error = "Password less then 6 symbols"
            return
        }

        setPropState(false)

        val res = viewModel.logIn(
            binding.editTextNetwork.editText?.text.toString(),
            binding.emailEditText.editText?.text.toString(),
            binding.passwordEditText.editText?.text.toString()
        )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(this, "Success ${it.walletId}", Toast.LENGTH_SHORT)
                    .show()
            }, {
                try {
                    throw it
                } catch (e1: EmailNotVerifiedException) {
                    binding.emailEditText.error = "Email is not verified"
                } catch (e2: InvalidCredentialsException) {
                    binding.emailEditText.error = "Invalid  credentials"
                }catch (e: Exception){
                    binding.editTextNetwork.error = "No service"
                }
                setPropState(true)
            }
            )
    }

    private fun setPropState(flag: Boolean) {
        binding.emailEditText.isEnabled = flag
        binding.editTextNetwork.isEnabled = flag
        binding.passwordEditText.isEnabled = flag
        binding.sigIn.isEnabled = flag
    }


}