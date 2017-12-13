package kws.superawesome.tv.androidbaselib

interface AsyncTask <Input, Output>: Task<Input, Output> {
    fun execute (input: Input, callback: (Output?, Throwable?) -> Unit)
}