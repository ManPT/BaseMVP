package com.lib.net

import com.lib.tools.LogTool
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

class HttpInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        // 在此添加header
        if (requestBuilder != null) {
            requestBuilder.header("Content-Type", "application/json")
        }
        request = requestBuilder.build()
        val requestBody = request.body()
        var body: String? = null
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            body = buffer.readString(charset)
        }
        LogTool.d(
            "requestCode：", String.format(
                "发送请求\nmethod：%s\nurl：%s\nheaders:%s\nRequestBody:%s",
                request.method(), request.url(), request.headers(), body
            )
        )
        var  s =  String.format(
            "发送请求\nmethod：%s\nurl：%s\nheaders:%s\nRequestBody:%s",
            request.method(), request.url(), request.headers(), body
        )
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val headers = response.headers()
        val requestId = headers["jsh-request-id"]
        val responseBody = response.body()
        var rBody: String? = null
        if (HttpHeaders.hasBody(response)) {
            val source = responseBody!!.source()
            // Buffer the entire body.
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer()
            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8)
                } catch (e: UnsupportedCharsetException) {
                    e.printStackTrace()
                }
            }
            rBody = buffer.clone().readString(charset)
        }
        LogTool.d(
            "requestCode：", String.format(
                "响应url:%s\n收到响应:%s%s %ss\nRequestId:%s\nResponseBody:%s",
                request.url(), response.code(), response.message(), tookMs, requestId, rBody
            )
        )
        return response

    }
}