CHANGELOG
=========

3.0.0
 - First major release of the new codebase
 - Massive clean up of the SDK, refactoring old code and fully relying on the new architecture
 - Removed old codebase
 - Added and improved tests

2.4.0
 - Merged master with new develop branch, which includes & expects Kotlin support
 - Improved internal SDK architecture (using services, providers and a facade to abstract them away whilst also maintaing the same public interface)
 - Improved WebView OAuth proess to make it more reliable
 - Added tests to the new services & providers
 - First build with new architecture!

2.3.3
 - Added WebView Auth

2.3.2
 - Simplified auth flow
 - Updated dependencies

2.2.2
 - Updated SAJsonParser reference to 0.2.6

2.2.1
 - Minor update to bring the SAUtils dependency to 0.3.4

2.2.0
 - Updated Networking, JSON & Utils dependencies to work with the latest versions (and benefit from all subsequent tests & improvements done)
 - Changed all models to expand on SABaseObject (and not implement SAJsonSerializable)
 - Changed the auth & create user token classes (KWSGetAccessTokenAuth and KWSGetAccessTokenCreate) to use the new features of the SAAsync - returning a precise templated response of type SANetworkResponse.

2.1.7
 - Removed the need to explicitly specify the App ID as an integer parameter when setting up the SDK
 - Right now the setup will be done by specifying just the KWS Url, the Client Id and the Client secret, all obtainable from the KWS Dashboard.
 - Refactored the Auth & Create user classes to simplify them. The KWSLoggedUser model, which holds an instance of a logged user (for 24h) and is used internally to get info needed to perform most network operations (that require a logged in user) has been stripped of usless data such as username, parent email, etc. This info can be obtained from the KWSUser model, by calling the appropriate SDK methods.
 - Moved services in related subfolders for better viewability

2.1.6
 - Added a random name generator method for the KWS SDK
 - Added a fourth paramter to the main "setup" method, which is a App Id (integer); This is needed in order to have random name generator work w/o a logged in user.
