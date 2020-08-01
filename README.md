# BaseActivity基类
集成了BaseActivity,BaseFragmentActivity,BaseFragment,BaseMVPActivity,BaseMVPFragment,RetorfitManager方便开发

## BaseActivity
继承自 AppCompatActivity，集成了一下功能：
1. 生命周期的检测
2. 使用ActivityManagerTool 管理Activity

## BaseFragmentActivity
主要功能如下:
1. 抽象方法设置承载Fragment的View（getFragmentViewId）
2. 集成 addFragment 和 removeFragment，需要注意的是，要传入是否加入回退栈的参数
3. 集成处理了返回键，按下的时候后优先返回上一级fragment


## BaseFragment
集成功能如下：
1. 使用mActivity，避免getActivity为null
2. 集成 addFragment和removeFragment，方便Fragemnt嵌套切换

## BaseMVPActivity和BaseMVPFragment
继承自BaseActivity和BaseFragment,主要集成了MVP设计模式的使用

示例代码

## Retorfit
集成了Retorfit + RxJava  的封装
使用方法如下：
 1. 创建一个RetorfitManager，继承自AbsRetrofitManager，重写getBaseUrl（），为Retorfit设置BaseUrl，
 根据需求重写getHttpClient(),设置自己的HttpClient对象，默认的HttpClient对象只添加了Log，没有任何拦截

 示例代码：
```
class RetrofitManager : AbsRetrofitManager() {

    var context : Context? = null

    override fun getBaseUrl(): String? {
        return "https://xl-mall.xilingbm.com/xl-api/"
    }


    override fun getHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url()
               // val appVersion: String = AppUtils.getAppVersionName(context)
                var builder = originalRequest.newBuilder()
                val httpUrl = originalHttpUrl.newBuilder() // 增加 API 版本号
                    .addQueryParameter("version", "1") //增加平台号
                    .addQueryParameter("platform", "1")
                   // .addQueryParameter("appVersion", appVersion )//增加App版本号
                    .build()
                builder = builder.url(httpUrl)

                // 重新构建请求
                chain.proceed(builder.build())
            }
            .cookieJar(object : CookieJar {
                override fun saveFromResponse(
                    url: HttpUrl,
                    cookies: List<Cookie>
                ) {
                    if (null != cookies && cookies.size > 0) {
                        for (cookie in cookies) {
                            /*if (cookie.name().equals(UserManager.OAUTH, ignoreCase = true)) {
                                UserManager.setOAuthToken(cookie.value())
                                //共享认证的Cookie
                                //shareCookies(cookie)
                                break
                            }*/
                        }
                    }
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookies: MutableList<Cookie> =
                        ArrayList()
                   /* val oAuthToken: String? = UserManager.getOAuthToken()
                    if (!TextUtils.isEmpty(oAuthToken)) {
                        val cookie = Cookie.Builder()
                            .name(UserManager.OAUTH).value(oAuthToken)
                            .path("/")
                            .hostOnlyDomain("*.beautysecret.cn")
                            .build()
                        cookies.add(cookie)
                    }*/
                    return cookies
                }
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

}
```

2. 使用 APIManager.startRequest进行请求

示例代码：
```
  lateinit var userService:UserService;
  var retrofit = RetrofitManager().getMyRetrofit()
  userService = retrofit!!.create(UserService ::class.java)

   @OnClick(R.id.btn)
      fun onViewClicked(view: View) {
          when (view.id) {
              R.id.btn -> {
                      APIManager.startRequest(userService.getLoginCode(0,"12345678"),object : RequestListener<Any>{
                            override fun onStart() {

                            }
                          override fun onSuccess(result: Any?) {
                              super.onSuccess(result)
                              Log.d("Request","login success")
                          }

                          override fun onComplete() {

                          }

                          override fun onError(e: Throwable?) {
                              Log.d("Request","login error")
                          }



                      })
              }
          }
      }


```






















