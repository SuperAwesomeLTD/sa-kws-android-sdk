package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.models.*
import org.junit.Test

/**
 * Created by guilherme.mota on 04/01/2018.
 */
class KWS_UserDetailsResponse_Test {

    @Test
    fun Check_Response_Valid_User_Details() {

        //given
        val id: Int = 25
        val username: String? = "coolgabriel12345"
        val firstName: String? = null
        val lastName: String? = null
        val address: Address? = null
        val dateOfBirth: String? = "2011-03-22"
        val gender: String? = null
        val language: String? = null
        val email: String? = null
        val hasSetParentEmail: Boolean? = null
        val applicationProfile: ApplicationProfile? = null
        val permissions: Permissions? = null
        val points: Points? = null
        val createdAt: String? = null


        //when
        val getUserDetails = UserDetails(
                id = id,
                name = username,
                firstName = firstName,
                lastName = lastName,
                address = address,
                dateOfBirth = dateOfBirth,
                gender = gender,
                language = language,
                email = email,
                hasSetParentEmail = hasSetParentEmail,
                applicationProfile = applicationProfile,
                applicationPermissions = permissions,
                points = points,
                createdAt = createdAt
        )

        //then
//        assertNotNull(getUserDetails)
//        assertNotNull(getUserDetails.id)
//        assertNotNull(getUserDetails.name)
//        assertNotNull(getUserDetails.dateOfBirth)


    }

    @Test
    fun Check_Response_Not_Valid_User_Details() {


        //given
        val id: Int = 0
        val username: String? = null
        val firstName: String? = null
        val lastName: String? = null
        val address: Address? = null
        val dateOfBirth: String? = null
        val gender: String? = null
        val language: String? = null
        val email: String? = null
        val hasSetParentEmail: Boolean? = null
        val applicationProfile: ApplicationProfile? = null
        val permissions: Permissions? = null
        val points: Points? = null
        val createdAt: String? = null


        //when
        val getUserDetails = UserDetails(
                id = id,
                name = username,
                firstName = firstName,
                lastName = lastName,
                address = address,
                dateOfBirth = dateOfBirth,
                gender = gender,
                language = language,
                email = email,
                hasSetParentEmail = hasSetParentEmail,
                applicationProfile = applicationProfile,
                applicationPermissions = permissions,
                points = points,
                createdAt = createdAt
        )
        //then
//        assertNotNull(getUserDetails)
//        assertNull(getUserDetails.id)
//        assertNull(getUserDetails.name)
//        assertNull(getUserDetails.dateOfBirth)

    }


}