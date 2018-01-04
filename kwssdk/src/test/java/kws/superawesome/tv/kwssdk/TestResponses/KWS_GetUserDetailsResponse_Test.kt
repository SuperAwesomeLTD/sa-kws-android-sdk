package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.responses.*
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Created by guilherme.mota on 04/01/2018.
 */
class KWS_GetUserDetailsResponse_Test {

    @Test
    fun Check_Response_Valid_User_Details() {

        //given
        val id: Int? = 25
        val username: String? = "coolgabriel12345"
        val firstName: String? = null
        val lastName: String? = null
        val addressResponse: UserAddressResponse? = null
        val dateOfBirth: String? = "2011-03-22"
        val gender: String? = null
        val language: String? = null
        val email: String? = null
        val hasSetParentEmail: Boolean? = null
        val applicationProfileResponse: UserApplicationProfileResponse? = null
        val applicationPermissionsResponse: ApplicationPermissionsResponse? = null
        val pointsResponse: PointsResponse? = null
        val createdAt: String? = null


        //when
        val getUserDetails = GetUserDetailsResponse(
                id = id,
                username = username,
                firstName = firstName,
                lastName = lastName,
                addressResponse = addressResponse,
                dateOfBirth = dateOfBirth,
                gender = gender,
                language = language,
                email = email,
                hasSetParentEmail = hasSetParentEmail,
                applicationProfileResponse = applicationProfileResponse,
                applicationPermissionsResponse = applicationPermissionsResponse,
                pointsResponse = pointsResponse,
                createdAt = createdAt
        )

        //then
        assertNotNull(getUserDetails)
        assertNotNull(getUserDetails.id)
        assertNotNull(getUserDetails.username)
        assertNotNull(getUserDetails.dateOfBirth)


    }

    @Test
    fun Check_Response_Not_Valid_User_Details() {


        //given
        val id: Int? = null
        val username: String? = null
        val firstName: String? = null
        val lastName: String? = null
        val addressResponse: UserAddressResponse? = null
        val dateOfBirth: String? = null
        val gender: String? = null
        val language: String? = null
        val email: String? = null
        val hasSetParentEmail: Boolean? = null
        val applicationProfileResponse: UserApplicationProfileResponse? = null
        val applicationPermissionsResponse: ApplicationPermissionsResponse? = null
        val pointsResponse: PointsResponse? = null
        val createdAt: String? = null


        //when
        val getUserDetails = GetUserDetailsResponse(
                id = id,
                username = username,
                firstName = firstName,
                lastName = lastName,
                addressResponse = addressResponse,
                dateOfBirth = dateOfBirth,
                gender = gender,
                language = language,
                email = email,
                hasSetParentEmail = hasSetParentEmail,
                applicationProfileResponse = applicationProfileResponse,
                applicationPermissionsResponse = applicationPermissionsResponse,
                pointsResponse = pointsResponse,
                createdAt = createdAt
        )
        //then
        assertNotNull(getUserDetails)
        assertNull(getUserDetails.id)
        assertNull(getUserDetails.username)
        assertNull(getUserDetails.dateOfBirth)

    }


}