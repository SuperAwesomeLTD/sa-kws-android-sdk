package kws.superawesome.tv.kwssdk;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public enum KWSErrorType {
    NoKWSPermission {
        @Override
        public String toString() {
            return "NoKWSPermission";
        }
    },
    NoSystemPermission {
        @Override
        public String toString() {
            return "NoSystemPermission";
        }
    },
    ParentEmailNotFound {
        @Override
        public String toString() {
            return "ParentEmailNotFound";
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
    NetworkError {
        @Override
        public String toString() {
            return "NetworkError";
        }
    },
    CouldNotUnsubscribeInKWS {
        @Override
        public String toString() {
            return "CouldNotUnsubscribeInKWS";
        }
    }
}
