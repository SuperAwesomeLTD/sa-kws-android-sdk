package kws.superawesome.tv.androidbaselib.network

interface NetworkEnvironment {
    //TODO this will have to only have domain, then on the kwssdk have a class that extends this one and adds the app id and mobile key
    val domain: String
    val appID: String
    val mobileKey: String
}