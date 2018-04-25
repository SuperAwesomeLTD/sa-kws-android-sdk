package kws.superawesome.tv.kwsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import kws.superawesome.tv.kwsdemo.environments.DemoEnvironments
import kws.superawesome.tv.kwssdk.base.ComplianceSDK
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.UtilsHelper
import kws.superawesome.tv.kwssdk.base.common.models.error.ErrorWrapperModel
import kws.superawesome.tv.kwssdk.base.internal.LoggedUserModel
import tv.superawesome.protobufs.authentication.models.ILoggedUserModel
import tv.superawesome.protobufs.authentication.services.IAuthService
import tv.superawesome.protobufs.authentication.services.ISingleSignOnService
import tv.superawesome.protobufs.session.services.ISessionService
import tv.superawesome.protobufs.usernames.services.IUsernameService
import java.util.*


class Main2Activity : AppCompatActivity(), View.OnClickListener {

    //environment for SDK
    lateinit var kEnvironment: NetworkEnvironment

    //text display
    lateinit var kLogText: TextView
    val kErrorServiceText = "\nOoh uh!! Internal issue with Services\n"
    var stringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set text var
        kLogText = findViewById(R.id.TextLogs)
        kLogText.movementMethod = ScrollingMovementMethod()

        //set environment
        kEnvironment = DemoEnvironments()

        //set click listeners
        (findViewById<Button>(R.id.OAuth)).setOnClickListener(this)
        (findViewById<Button>(R.id.CreateUser)).setOnClickListener(this)
        (findViewById<Button>(R.id.Login)).setOnClickListener(this)
        (findViewById<Button>(R.id.Logout)).setOnClickListener(this)
        (findViewById<Button>(R.id.RandomName)).setOnClickListener(this)
        (findViewById<Button>(R.id.SetAppData)).setOnClickListener(this)
        (findViewById<Button>(R.id.GetAppData)).setOnClickListener(this)
        (findViewById<Button>(R.id.InviteUser)).setOnClickListener(this)
        (findViewById<Button>(R.id.RequestPermissions)).setOnClickListener(this)
        (findViewById<Button>(R.id.TriggerEvent)).setOnClickListener(this)
        (findViewById<Button>(R.id.CheckEventWithId)).setOnClickListener(this)
        (findViewById<Button>(R.id.GetUser)).setOnClickListener(this)
        (findViewById<Button>(R.id.UpdateUser)).setOnClickListener(this)
        (findViewById<Button>(R.id.GetLeaderBoard)).setOnClickListener(this)
        (findViewById<Button>(R.id.GetScore)).setOnClickListener(this)

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
        }
    }

    //HELPER METHODS  ::::::::::::::::::::::::::::::::::::::::::::::::::::

    private fun updateText(text: String) {
        kLogText.append(text)

        //scroll text view
        val scrollAmount = kLogText.layout.getLineTop(kLogText.lineCount) - kLogText.height;
        // if there is no need to scroll, scrollAmount will be <=0
        if (scrollAmount > 0) {
            kLogText.scrollTo(0, scrollAmount)
        } else {
            kLogText.scrollTo(0, 0)
        }

        //reset string builder
        stringBuilder.setLength(0)
    }

    private fun getRandomNumber(min: Int, max: Int): Int {
        val random = Random()
        return random.nextInt(max - min) + min

    }

    private fun defaultErrorServiceMessage() {
        stringBuilder.append(kErrorServiceText)
        updateText(stringBuilder.toString())
    }
    //END OF HELPER METHODS ::::::::::::::::::::::::::::::::::::::::::::::

    private fun oAuthFlow() {

        val sdk = ComplianceSDK(kEnvironment)
        val singleSignOnService = sdk.getService(type = ISingleSignOnService::class.java)

        val url = (kEnvironment as DemoEnvironments).singleSignOn

        singleSignOnService?.signOn(url = url, parent = this) { responseModel, error ->

            if (responseModel != null) {
                stringBuilder.append("\nThe OAuth was success! The UserId is ${responseModel.id} \n")
            } else {
                val errorWrapperModel = error as ErrorWrapperModel
                when {
                    errorWrapperModel.message != null -> stringBuilder.append("\nThe OAuth was NOT success '${errorWrapperModel.message}'\n")
                    errorWrapperModel.error != null -> stringBuilder.append("\nThe OAuth was NOT success: '${errorWrapperModel.error}'\n")
                    else -> stringBuilder.append("\nThe OAuth was NOT success...\n")
                }
            }
            //update text
            updateText(stringBuilder.toString())

        } ?: run {
            defaultErrorServiceMessage()
        }
    }

    private fun createNewUser() {

        val randomNumber = getRandomNumber(0, 1000)
        val username = "randomtestusr$randomNumber"
        val password = "testtest"
        val dob = "2012-02-02"
        val country = "US"
        val parentEmail = "mobile.dev.test@superawesome.tv"

        val sdk = ComplianceSDK(kEnvironment)
        val authService = sdk.getService(type = IAuthService::class.java)

        authService?.createUser(username = username, password = password, timeZone = null, dateOfBirth = dob, country = country, parentEmail = parentEmail) { responseModel, error ->

            if (responseModel != null) {
                stringBuilder.append("\nThe Create User was success with\nUsername: '$username'\nPassword: '$password'\n The new user's ID is ${responseModel.id} \n")
                saveUser(responseModel)
            } else {
                val errorWrapperModel = error as ErrorWrapperModel
                when {
                    errorWrapperModel.message != null -> stringBuilder.append("\nThe Create User was NOT success '${errorWrapperModel.message}'\n")
                    errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Create User was NOT success '${errorWrapperModel.codeMeaning}'\n")
                    errorWrapperModel.error != null -> stringBuilder.append("\nThe Create User was NOT success: '${errorWrapperModel.error}'\n")
                    else -> stringBuilder.append("\nThe Create User was NOT success...\n")
                }
            }

            //update text
            updateText(stringBuilder.toString())

        } ?: run {
            defaultErrorServiceMessage()
        }
    }

    private fun loginUser() {

        val username = "randomtestuser123"
        val password = "testtest"

        val sdk = ComplianceSDK(kEnvironment)
        val authService = sdk.getService(type = IAuthService::class.java)

        authService?.loginUser(username = username, password = password) { responseModel, error ->

            if (responseModel != null) {

                //parse the token
                UtilsHelper.getMetadataFromToken(responseModel.token)?.let {
                    stringBuilder.append("\nThe Login User was success with\nUsername: '$username'\nPassword: '$password'\n This ID is ${it.userId} \n")
                } ?: run {
                    stringBuilder.append("\nGot a user, but something went wrong parsing the token...\n")
                }

                //save user
                saveUser(responseModel)
            } else {
                val errorWrapperModel = error as ErrorWrapperModel
                when {
                    errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Login User was NOT success '${errorWrapperModel.codeMeaning}'\n")
                    errorWrapperModel.error != null -> stringBuilder.append("\nThe Login User was NOT success: '${errorWrapperModel.error}'\n")
                    else -> stringBuilder.append("\nThe Login User was NOT success...\n")
                }
            }
            //update text
            updateText(stringBuilder.toString())

        } ?: run {
            defaultErrorServiceMessage()
        }
    }

    private fun randomUsername() {

        val sdk = ComplianceSDK(kEnvironment)
        val usernameService = sdk.getService(IUsernameService::class.java)

        usernameService?.getRandomUsername { responseModel, error ->

            if (responseModel != null) {
                stringBuilder.append("\nThe Random Username was success with: '${responseModel.randomUsername}'\n")
            } else {
                val errorWrapperModel = error as ErrorWrapperModel
                when {
                    errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Random Username was NOT success '${errorWrapperModel.codeMeaning}'\n")
                    errorWrapperModel.error != null -> stringBuilder.append("\nThe Random Username was NOT success: '${errorWrapperModel.error}'\n")
                    else -> stringBuilder.append("\nThe Random Username was NOT success...\n")
                }
            }
            //update text
            updateText(stringBuilder.toString())
        } ?: run {
            defaultErrorServiceMessage()
        }

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


    //SESSION SERVICES
    private fun saveUser(user: ILoggedUserModel) {

        val sdk = ComplianceSDK(kEnvironment)
        val authService = sdk.getService(type = ISessionService::class.java)

        authService?.let {
            val success = it.saveLoggedUser(this, user)

            if (success) {
                stringBuilder.append("\nSuccess caching user!!\n")
            } else {
                stringBuilder.append("\nFailed caching user...\n")
            }
        } ?: run {
            defaultErrorServiceMessage()
        }
    }


    private fun getLoggedUser(): LoggedUserModel? {

        val sdk = ComplianceSDK(kEnvironment)
        val authService = sdk.getService(type = ISessionService::class.java)

        authService?.let {
            return it.getCurrentUser(this) as LoggedUserModel?
        } ?: run {
            defaultErrorServiceMessage()
            return null
        }
    }

    private fun logoutUser() {

        val sdk = ComplianceSDK(kEnvironment)
        val authService = sdk.getService(type = ISessionService::class.java)

        authService?.let {

            //clear user
            it.clearLoggedUser(this)

            //try and get a cached user
            getLoggedUser()?.let {
                //if it exists, we failed to clear it
                stringBuilder.append("\nFailed clearing user...\n")
            } ?: run {
                stringBuilder.append("\nUser cleared!\n")
            }
        } ?: run {
            defaultErrorServiceMessage()
        }
    }
}
