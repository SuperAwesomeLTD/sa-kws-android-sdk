package kws.superawesome.tv.kwssdk.process;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public enum KWSChildrenRegisterForRemoteNotificationsStatus {
    ParentDisabledNotifications {
        @Override
        public String toString() {
            return "ParentDisabledNotifications";
        }
    },
    UserDisabledNotifications {
        @Override
        public String toString() {
            return "UserDisabledNotifications";
        }
    },
    NoParentEmail {
        @Override
        public String toString() {
            return "NoParentEmail";
        }
    },
    FirebaseNotSetup {
        @Override
        public String toString() {
            return "FirebaseNotSetup";
        }
    },
    FirebaseCouldNotGetToken {
        @Override
        public String toString() {
            return "FirebaseCouldNotGetToken";
        }
    },
    NetworkError {
        @Override
        public String toString() {
            return "NetworkError";
        }
    },
    Success {
        @Override
        public String toString() {
            return "Success";
        }
    }
}
