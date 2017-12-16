package kws.superawesome.tv.kwssdk.services.kws.permissions;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public enum KWSChildrenPermissionType {
    AccessEmail {
        @Override
        public String toString() {
            return "accessEmail";
        }
    },
    AccessAddress {
        @Override
        public String toString() {
            return "accessAddress";
        }
    },
    AccessFirstName {
        @Override
        public String toString() {
            return "accessFirstName";
        }
    },
    AccessLastName {
        @Override
        public String toString() {
            return "accessLastName";
        }
    },
    AccessPhoneNumber {
        @Override
        public String toString() {
            return "accessPhoneNumber";
        }
    },
    SendNewsletter {
        @Override
        public String toString() {
            return "sendNewsletter";
        }
    },
    SendPushNotification {
        @Override
        public String toString() {
            return "sendPushNotification";
        }
    }
}
