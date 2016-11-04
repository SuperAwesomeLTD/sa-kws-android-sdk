package kws.superawesome.tv.kwssdk.services.kws;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public enum KWSPermissionType {
    accessEmail{
        @Override
        public String toString() {
            return "accessEmail";
        }
    },
    accessAddress{
        @Override
        public String toString() {
            return "accessAddress";
        }
    },
    accessFirstName{
        @Override
        public String toString() {
            return "accessFirstName";
        }
    },
    accessLastName{
        @Override
        public String toString() {
            return "accessLastName";
        }
    },
    accessPhoneNumber{
        @Override
        public String toString() {
            return "accessPhoneNumber";
        }
    },
    sendNewsletter {
        @Override
        public String toString() {
            return "sendNewsletter";
        }
    },
    sendPushNotification {
        @Override
        public String toString() {
            return "sendPushNotification";
        }
    }
}
