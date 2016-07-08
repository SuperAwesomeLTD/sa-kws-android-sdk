package kws.superawesome.tv;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public enum KWSErrorType {
    KWS_ParentHasDisabledRemoteNotifications {
        @Override
        public String toString() {
            return "KWS_ParentHasDisabledRemoteNotifications";
        }
    },
    System_UserHasDisabledRemoteNotifications {
        @Override
        public String toString() {
            return "System_UserHasDisabledRemoteNotifications";
        }
    },
    KWS_UserHasNoParentEmail {
        @Override
        public String toString() {
            return "KWS_UserHasNoParentEmail";
        }
    },
    KWS_ParentEmailInvalid {
        @Override
        public String toString() {
            return "KWS_ParentEmailInvalid";
        }
    },
    System_GoogleServicesNotFound {
        @Override
        public String toString() {
            return "System_GoogleServicesNotFound";
        }
    },
    System_FirebaseCouldNotGetToken {
        @Override
        public String toString() {
            return "System_FirebaseCouldNotGetToken";
        }
    },
    Network_ErrorCheckingIfUserHasRemoteNotificationsEnabledInKWS {
        @Override
        public String toString() {
            return "Network_ErrorCheckingIfUserHasRemoteNotificationsEnabledInKWS";
        }
    },
    Network_ErrorRequestingRemoteNotificationsPermissionInKWS {
        @Override
        public String toString() {
            return "Network_ErrorRequestingRemoteNotificationsPermissionInKWS";
        }
    },
    Network_ErrorSubmittingParentEmail {
        @Override
        public String toString() {
            return "Network_ErrorSubmittingParentEmail";
        }
    },
    Network_ErrorSubscribingTokenToKWS {
        @Override
        public String toString() {
            return "Network_ErrorSubscribingTokenToKWS";
        }
    },
    Network_ErrorUnsubscribingTokenFromKWS {
        @Override
        public String toString() {
            return "Network_ErrorUnsubscribingTokenFromKWS";
        }
    }
}
