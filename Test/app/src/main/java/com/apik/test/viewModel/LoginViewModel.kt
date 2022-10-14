package com.apik.test.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.tokend.sdk.api.TokenDApi
import org.tokend.sdk.keyserver.KeyServer
import org.tokend.sdk.keyserver.models.DecryptedWallet

class LoginViewModel() : ViewModel() {


    private var rootUrl = "https://api.demo.tokend.io"
    private val api = TokenDApi(rootUrl)

    private fun setRootURL(newUrl: String) {
        rootUrl = newUrl
    }


    fun logIn(network: String, email: String, password: String): Single<DecryptedWallet> {
        setRootURL(network)
        val wallet = Single.create<DecryptedWallet> { emmiter ->
            val wallet =
                KeyServer(api.wallets).getWallet(email, password.toCharArray()).execute().get()
            saveData(wallet)
            emmiter.onSuccess(wallet)
        }


        return wallet
    }

    private fun saveData(walet: DecryptedWallet) {
        Log.i("saveData", "DATA SAVED!!! ${walet.walletId}")
    }

}