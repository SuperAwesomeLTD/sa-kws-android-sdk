package kws.superawesome.tv.androidbaselib

interface SyncTask <Input, Output>: Task<Input, Output> {
    fun execute (input: Input): Output
}