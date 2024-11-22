package com.mcp.demomcp1.api

import android.content.ContentValues.TAG
import android.util.Base64
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.evergage.android.Evergage
import com.google.gson.Gson
import com.mcp.demomcp1.MCPApplication
import com.mcp.demomcp1.R
import org.json.JSONObject


class ServerSideCampaign {
    private val context = MCPApplication.instance.applicationContext
    private val url = context.getString(R.string.mcpEndpoint)
    private val rut = Evergage.getInstance().userId
    fun getCampaign(action : String, appName : String, responseCallback: (response: JSONObject) -> Unit)
    {
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val sourceMap = mutableMapOf("action" to action, "source" to mutableMapOf("channel" to "Server", "application" to appName), "user" to mutableMapOf("id" to rut))
        val json = JSONObject(Gson().toJson(sourceMap))

        val jRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.POST, url, json,
                Response.Listener<JSONObject?> {
                    val response = it.getJSONArray("campaignResponses").getJSONObject(0)
                    Log.d(TAG,"Server Side Campaign invoked successfully")
                    responseCallback(response)
                },
                Response.ErrorListener {
                    Log.d(TAG,"Error while invoking Server Side Campaign")
                    responseCallback(JSONObject())
                }) {

                override fun parseNetworkError(volleyError: VolleyError): VolleyError {
                    Log.d(TAG, "volleyError" + volleyError.message)
                    return super.parseNetworkError(volleyError)
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val credentials = context.getString(R.string.apiKeyId) + ":" + context.getString(R.string.apiKeySecret)

                    val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36"
                    headers["Content-Type"] = "application/json; charset=utf-8"
                    headers["Authorization"] = auth
                    return headers
                }
            }

        //Invoke request
        queue.add(jRequest)
    }
}