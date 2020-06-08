/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package kws.superawesome.tv.kwssdk.utils;

/**
 * This interface defines methods that correspond to the KWSAlert class.
 */
public interface KWSAlertInterface {

    /**
     * Method gets called when a user clicks on one of the buttons of an alert dialog.
     * It's used to send back information to the library user
     *
     * @param button    which button was clicked
     * @param message   the message that was recorded in the input box, if it exists
     */
    void saDidClickOnAlertButton(int button, String message);
}
