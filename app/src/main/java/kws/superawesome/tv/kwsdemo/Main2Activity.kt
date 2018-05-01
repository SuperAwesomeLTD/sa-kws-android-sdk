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
import tv.superawesome.protobufs.actions.services.IUserActionsService
import tv.superawesome.protobufs.authentication.models.ILoggedUserModel
import tv.superawesome.protobufs.authentication.services.IAuthService
import tv.superawesome.protobufs.authentication.services.ISingleSignOnService
import tv.superawesome.protobufs.score.services.IScoringService
import tv.superawesome.protobufs.session.services.ISessionService
import tv.superawesome.protobufs.user.services.IUserService
import tv.superawesome.protobufs.usernames.services.IUsernameService
import java.util.*


class Main2Activity : AppCompatActivity(), View.OnClickListener {

    //environment for SDK
    lateinit var kEnvironment: NetworkEnvironment

    //text display
    lateinit var kLogText: TextView
    private val kErrorServiceText = "\nOoh uh!! Internal issue with Services\n"
    private val kNoValidUserCached = "\nOops! No valid user cached...\n"
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

    private fun defaultNoValidUserCachedMessage() {
        stringBuilder.append(kNoValidUserCached)
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
                saveUser(responseModel)
            } else {
                val errorWrapperModel = error as ErrorWrapperModel
                when {
                    errorWrapperModel.message != null -> stringBuilder.append("\nThe OAuth was NOT success '${errorWrapperModel.message}'\n")
                    errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe OAuth was NOT success '${errorWrapperModel.codeMeaning}'\n")
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
                    errorWrapperModel.message != null -> stringBuilder.append("\nThe Login User was NOT success '${errorWrapperModel.message}'\n")
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
                    errorWrapperModel.message != null -> stringBuilder.append("\nThe Random Username was NOT success '${errorWrapperModel.message}'\n")
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

        val sdk = ComplianceSDK(kEnvironment)
        val userActionsService = sdk.getService(IUserActionsService::class.java)

        val value = 1
        val key = "key_name"

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val appId = it.tokenData.appId
            val token = it.token

            userActionsService?.setAppData(value = value, key = key, userId = userId, appId = appId, token = token) { error ->

                if (error == null) {
                    stringBuilder.append("\nApp Data set with success!\n Value as '$value'\n Key as '$key'\n")
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Set App Data was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Set App Data was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Set App Data was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Set App Data was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }

    }

    private fun getAppData() {

        val sdk = ComplianceSDK(kEnvironment)
        val userActionsService = sdk.getService(IUserActionsService::class.java)

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val appId = it.tokenData.appId
            val token = it.token

            userActionsService?.getAppData(userId = userId, appId = appId, token = token) { responseModel, error ->

                if (responseModel != null) {
                    stringBuilder.append("\nGet App Data success!")

                    val results = responseModel.results
                    if (results.isNotEmpty()) {

                        for (i in results.indices) {
                            val item = results[i]
                            stringBuilder.append("\nPosition ${i + 1} has:\nName - '${item.name}'\nValue - '${item.value}'\n")
                        }
                    } else {
                        stringBuilder.append("\n...but no list of results to show! Set App Data first.")
                    }
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Get App Data was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Get App Data was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Get App Data was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Get App Data was NOT success...\n")
                    }
                }

                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }

    private fun inviteUser() {

        val sdk = ComplianceSDK(kEnvironment)
        val userActionsService = sdk.getService(IUserActionsService::class.java)

        val email = "mobile.dev.test+3@superawesome.tv"

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val token = it.token

            userActionsService?.inviteUser(email = email, userId = userId, token = token) { error ->

                if (error == null) {
                    stringBuilder.append("\nInvite user email - '$email' success!\n")
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Invite User was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Invite User was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Invite User was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Invite User was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }

    private fun requestUserPermissions() {

        val sdk = ComplianceSDK(kEnvironment)
        val userActionsService = sdk.getService(IUserActionsService::class.java)

        val permissions: List<String> = listOf("accessEmail", "accessAddress")

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val token = it.token

            userActionsService?.requestPermissions(permissions = permissions, userId = userId, token = token) { error ->

                if (error == null) {
                    stringBuilder.append("\nRequest permissions was success for")

                    for (i in permissions.indices) {
                        stringBuilder.append("\n-'${permissions[i]}'")
                    }
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Request permissions was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Request permissions was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Request permissions was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Request permissions was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }

    }

    private fun triggerEvent() {

        val sdk = ComplianceSDK(kEnvironment)
        val userActionsService = sdk.getService(IUserActionsService::class.java)

        val eventId = "x9C1QxRFj27D7uc8UdPFTOEjBSz7HqQH"
        val points = 20

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val token = it.token

            userActionsService?.triggerEvent(eventId = eventId, points = points, userId = userId, token = token) { error ->

                if (error == null) {
                    stringBuilder.append("\nTrigger Event success with\nEventId - '$eventId'\nPoints - '$points'\n")
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Trigger Event was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Trigger Event was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Trigger Event was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Trigger Event was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }

    private fun checkEventTriggered() {
        val sdk = ComplianceSDK(kEnvironment)
        val userActionsService = sdk.getService(IUserActionsService::class.java)

        val eventId = 807

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val token = it.token

            userActionsService?.hasTriggeredEvent(eventId = eventId, userId = userId, token = token) { responseModel, error ->

                if (responseModel != null) {
                    stringBuilder.append("\nResponse OK! Event '$eventId' was Triggered - '${responseModel.hasTriggeredModel}'!\n")
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Event Has Triggered was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Event Has Triggered  was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Event Has Triggered  was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Event Has Triggered was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }

    private fun getUserDetails() {

        val sdk = ComplianceSDK(kEnvironment)
        val userService = sdk.getService(IUserService::class.java)

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val token = it.token

            userService?.getUser(userId = userId, token = token) { responseModel, error ->

                if (responseModel != null) {
                    stringBuilder.append("\nSuccess Get User Details!\nDOB - '${responseModel.dateOfBirth}'\nCreated at - '${responseModel.createdAt}'\n")

                    responseModel.firstName?.let {
                        stringBuilder.append("First Name - '$it'\n")
                    }

                    responseModel.lastName?.let {
                        stringBuilder.append("Last Name - '$it'\n")
                    }

                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Get User Details was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Get User Details  was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Get User Details  was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Get User Details was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }

    private fun updateUserDetails() {
        val sdk = ComplianceSDK(kEnvironment)
        val userService = sdk.getService(IUserService::class.java)

        val details: Map<String, Any> = mapOf(
                "firstName" to "Droid",
                "lastName" to "John")

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val userId = it.id
            val token = it.token

            userService?.updateUser(details = details, userId = userId, token = token) { error ->

                if (error == null) {
                    stringBuilder.append("\nSuccess Update User Details with '$details'\n")
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Update User Details was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Update User Details  was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Update User Details  was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Update User Details was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }

    private fun getLeaderboard() {
        val sdk = ComplianceSDK(kEnvironment)
        val userService = sdk.getService(IScoringService::class.java)

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val token = it.token
            val appId = it.tokenData.appId

            userService?.getLeaderboard(appId = appId, token = token) { responseModel, error ->

                if (responseModel != null) {
                    stringBuilder.append("\nSuccess Get Leaderboard...\n")

                    val results = responseModel.results
                    if (results.isNotEmpty()) {

                        for (i in results.indices) {
                            val item = results[i]
                            stringBuilder.append("\nUser - '${item.name}' has:\nScore - '${item.score}'\nRank - '${item.rank}'\n")
                        }
                    } else {
                        stringBuilder.append("\n...but no list of results to show atm!")
                    }

                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Get Leaderboard was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Get Leaderboard was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Get Leaderboard was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Get Leaderboard was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }


    private fun getScore() {
        val sdk = ComplianceSDK(kEnvironment)
        val userService = sdk.getService(IScoringService::class.java)

        val cachedUser = getLoggedUser()
        cachedUser?.let {

            val token = it.token
            val appId = it.tokenData.appId

            userService?.getScore(appId = appId, token = token) { responseModel, error ->

                if (responseModel != null) {
                    stringBuilder.append("\nSuccess Get Score with\nRank - '${responseModel.rank}'\n Score - '${responseModel.score}'\n")
                } else {
                    val errorWrapperModel = error as ErrorWrapperModel
                    when {
                        errorWrapperModel.message != null -> stringBuilder.append("\nThe Get Score was NOT success '${errorWrapperModel.message}'\n")
                        errorWrapperModel.codeMeaning != null -> stringBuilder.append("\nThe Get Score was NOT success '${errorWrapperModel.codeMeaning}'\n")
                        errorWrapperModel.error != null -> stringBuilder.append("\nThe Get Score was NOT success: '${errorWrapperModel.error}'\n")
                        else -> stringBuilder.append("\nThe Get Score was NOT success...\n")
                    }
                }
                //update text
                updateText(stringBuilder.toString())
            } ?: run {
                defaultErrorServiceMessage()
            }
        } ?: run {
            defaultNoValidUserCachedMessage()
        }
    }


    //SESSION SERVICES
    private fun saveUser(user: ILoggedUserModel) {

        val sdk = ComplianceSDK(kEnvironment)
        val authService = sdk.getService(type = ISessionService::class.java)

        authService?.let {
            val success = it.saveLoggedUser(this, user)

            if (success) {
                stringBuilder.append("\n--Success caching user!!\n")
            } else {
                stringBuilder.append("\n--Failed caching user...\n")
            }
            //update text
            updateText(stringBuilder.toString())
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
            val cachedUser = getLoggedUser()
            cachedUser?.let {
                //if it exists, we failed to clear it
                stringBuilder.append("\n--Failed clearing user...\n")
            } ?: run {
                stringBuilder.append("\n--User cleared!\n")
            }
            //update text
            updateText(stringBuilder.toString())
        } ?: run {
            defaultErrorServiceMessage()
        }
    }
}
