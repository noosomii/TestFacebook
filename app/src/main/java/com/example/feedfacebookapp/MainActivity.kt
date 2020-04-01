package com.example.feedfacebookapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feedfacebookapp.model.Item
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FacebookCallback<LoginResult> {

    private lateinit var callbackManager: CallbackManager
    private val itemAdapter by lazy { ItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFacebook()

    }

    private fun initFacebook() {
        login_button.setReadPermissions(listOf("user_posts"))
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, this)
    }

    override fun onSuccess(result: LoginResult?) {
        getUserView()
    }

    override fun onCancel() {
        Log.d("LoginManager", "onCancel")
    }

    override fun onError(error: FacebookException?) {
        Log.d("LoginManager", "onError")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getUserView() {
        GraphRequest(
            AccessToken.getCurrentAccessToken(), "/me/feed", null, HttpMethod.GET,
            GraphRequest.Callback { response ->
                val feedList = response.jsonObject.getJSONArray("data")
                val listData = mutableListOf<Item>()
                for (i in 0 until feedList.length()) {
                    val item = feedList.getJSONObject(i)
                    if(item.has("message"))
                        listData.add(Item(item.getString("message")))
                }

                itemAdapter.addAll(listData)
                val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                itemsRecyclerView.apply {
                    addItemDecoration(divider)
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = itemAdapter
                }
            }
        ).executeAsync()
    }



}
