package kws.superawesome.tv.kwsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kws.superawesome.tv.kwsdemo.environments.DemoEnvironments
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment


class Main2Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var kEnvironment: NetworkEnvironment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kEnvironment = DemoEnvironments()


    }

    override fun onClick(view: View?) {

        when (view?.id) {
        //auth
            R.id.OAuth -> oAuthFlow()
            R.id.CreateUser -> createNewUser()
            R.id.Login -> loginUser()
            R.id.Logout -> logoutUser()

        //username
            R.id.RandomName -> randomUsername()

        //actions
            R.id.SetAppData -> setAppData()
            R.id.GetAppData -> getAppData()
            R.id.InviteUser -> inviteUser()
            R.id.RequestPermissions -> requestUserPermissions()
            R.id.TriggerEvent -> triggerEvent()
            R.id.CheckEventWithId -> checkEventTriggered()

        //user
            R.id.GetUser -> getUserDetails()
            R.id.UpdateUser -> updateUserDetails()

        //scoring
            R.id.GetLeaderBoard -> getLeaderboard()
            R.id.GetScore -> getScore()

        //notifications
            R.id.CheckIfRegistered -> checkIfUserRegistered()
            R.id.UnregisterUser -> unregisterUser()
            R.id.RegisterUser -> registerUser()
        }

    }

    private fun oAuthFlow() {

    }

    private fun createNewUser() {
        //todo
    }

    private fun loginUser() {

    }

    private fun logoutUser() {
        //todo
    }

    //Notifications

    private fun randomUsername() {
    }

    private fun setAppData() {

    }

    private fun getAppData() {

    }

    private fun inviteUser() {

    }

    private fun requestUserPermissions() {

    }

    private fun triggerEvent() {

    }

    private fun checkEventTriggered() {

    }

    private fun getUserDetails() {

    }

    private fun updateUserDetails() {

    }

    private fun getLeaderboard() {

    }


    private fun getScore() {

    }

    //TODO WIP
    private fun checkIfUserRegistered() {}

    private fun unregisterUser() {}
    private fun registerUser() {}


}
