package com.knocscore.access;

public enum BucketName {
    // apps
    APP_BUCKET() {
        @Override
        public String getName() {
            return "iooily-flashmonkey-app";
        }
    },
    // The user avatar image
    AVATAR_BUCKET() {
        @Override
        public String getName() {
            return "flashmonkey-avatar";
        }
    },
    // The decks
    DECK_BUCKET() {
        @Override
        public String getName() {
            return "iooily-flashmonkey-deck";
        }
    },
    // the deck descript image in the market-place
    DECK_THUMB_BUCKET() {
        @Override
        public String getName() {
            return "flashmonkey-deck-photo";
        }
    },
    // Media in the buckets
    MEDIA_BUCKET() {
        @Override
        public String getName() {
            return "iooily-flashmonkey-media";
        }
    },
    PROBLEM() {
        @Override
        public String getName() {
            return "problem";
        }
    };

    BucketName() { /* NO ARGS CONSTRUCTOR */ }

    public abstract String getName();
}
