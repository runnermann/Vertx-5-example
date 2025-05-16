package com.runnermann.wolf.example.web.access;

public enum BucketName {
    // apps
    APP_BUCKET() {
        @Override
        public String getName() {
            return "my-app-bucket";
        }
    },
    // The user avatar image
    AVATAR_BUCKET() {
        @Override
        public String getName() {
            return "my-avatar-bucket";
        }
    },
    // the deck descript image in the market-place
    DECK_THUMB_BUCKET() {
        @Override
        public String getName() {
            return "my-thumb-bucket";
        }
    },
    // Media in the buckets
    MEDIA_BUCKET() {
        @Override
        public String getName() {
            return "my-media-bucket";
        }
    };

    BucketName() { /* NO ARGS CONSTRUCTOR */ }

    public abstract String getName();
}
