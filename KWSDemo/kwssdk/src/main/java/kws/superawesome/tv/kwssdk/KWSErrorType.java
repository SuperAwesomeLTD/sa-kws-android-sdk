package kws.superawesome.tv.kwssdk;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public enum KWSErrorType {
    ParentHasDisabledRemoteNotifications {
        @Override
        public String toString() {
            return "ParentHasDisabledRemoteNotifications";
        }
    },
    UserHasDisabledRemoteNotifications {
        @Override
        public String toString() {
            return "UserHasDisabledRemoteNotifications";
        }
    },
    UserHasNoParentEmail {
        @Override
        public String toString() {
            return "UserHasNoParentEmail";
        }
    },
    ParentEmailInvalid {
        @Override
        public String toString() {
            return "ParentEmailInvalid";
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
    FailedToCheckIfUserHasNotificationsEnabledInKWS {
        @Override
        public String toString() {
            return "FailedToCheckIfUserHasNotificationsEnabledInKWS";
        }
    },
    FailedToRequestNotificationsPermissionInKWS {
        @Override
        public String toString() {
            return "FailedToRequestNotificationsPermissionInKWS";
        }
    },
    FailedToSubmitParentEmail {
        @Override
        public String toString() {
            return "FailedToSubmitParentEmail";
        }
    },
    FailedToSubscribeTokenToKWS {
        @Override
        public String toString() {
            return "FailedToSubscribeTokenToKWS";
        }
    }
}
