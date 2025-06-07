package com.example.lightmusicplayer.entity.response;

import java.util.List;

public class PlaylistsResponse {

    private List<Playlist> playlists;

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public static class Playlist {
        private String name;
        private long id;
        private long trackNumberUpdateTime;
        private int status;
        private long userId;
        private long createTime;
        private long updateTime;
        private int subscribedCount;
        private int trackCount;
        private int cloudTrackCount;
        private String coverImgUrl;
        private long coverImgId;
        private String description;
        private int playCount;
        private long trackUpdateTime;
        private int specialType;
        private int totalDuration;
        private boolean newImported;
        private int adType;
        private boolean highQuality;
        private int privacy;
        private boolean ordered;
        private boolean anonimous;
        private int coverStatus;
        private Object recommendInfo;
        private Object socialPlaylistCover;
        private Object recommendText;
        private int shareCount;
        private String coverImgId_str;
        private int commentCount;
        private String copywriter;
        private String tag;

        private Creator creator;
        private List<Subscriber> subscribers;
        private Object subscribed;
        private String commentThreadId;

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }

        public long getTrackNumberUpdateTime() {
            return trackNumberUpdateTime;
        }

        public int getStatus() {
            return status;
        }

        public long getUserId() {
            return userId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public int getSubscribedCount() {
            return subscribedCount;
        }

        public int getTrackCount() {
            return trackCount;
        }

        public int getCloudTrackCount() {
            return cloudTrackCount;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public long getCoverImgId() {
            return coverImgId;
        }

        public String getDescription() {
            return description;
        }

        public int getPlayCount() {
            return playCount;
        }

        public long getTrackUpdateTime() {
            return trackUpdateTime;
        }

        public int getSpecialType() {
            return specialType;
        }

        public int getTotalDuration() {
            return totalDuration;
        }

        public boolean isNewImported() {
            return newImported;
        }

        public int getAdType() {
            return adType;
        }

        public boolean isHighQuality() {
            return highQuality;
        }

        public int getPrivacy() {
            return privacy;
        }

        public boolean isOrdered() {
            return ordered;
        }

        public boolean isAnonimous() {
            return anonimous;
        }

        public int getCoverStatus() {
            return coverStatus;
        }

        public Object getRecommendInfo() {
            return recommendInfo;
        }

        public Object getSocialPlaylistCover() {
            return socialPlaylistCover;
        }

        public Object getRecommendText() {
            return recommendText;
        }

        public int getShareCount() {
            return shareCount;
        }

        public String getCoverImgId_str() {
            return coverImgId_str;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public String getCopywriter() {
            return copywriter;
        }

        public String getTag() {
            return tag;
        }

        public Creator getCreator() {
            return creator;
        }

        public List<Subscriber> getSubscribers() {
            return subscribers;
        }

        public Object getSubscribed() {
            return subscribed;
        }

        public String getCommentThreadId() {
            return commentThreadId;
        }

        public static class Creator {
            private boolean defaultAvatar;
            private int province;
            private int authStatus;
            private boolean followed;
            private String avatarUrl;
            private int accountStatus;
            private int gender;
            private int city;
            private long birthday;
            private long userId;
            private int userType;
            private String nickname;
            private String signature;
            private String description;
            private String detailDescription;
            private long avatarImgId;
            private long backgroundImgId;
            private String backgroundUrl;
            private int authority;
            private boolean mutual;
            private Object expertTags;
            private Object experts;
            private int djStatus;
            private int vipType;
            private Object remarkName;
            private int authenticationTypes;
            private AvatarDetail avatarDetail;
            private String avatarImgIdStr;
            private String backgroundImgIdStr;
            private boolean anchor;
            private String avatarImgId_str;

            public boolean isDefaultAvatar() {
                return defaultAvatar;
            }

            public int getProvince() {
                return province;
            }

            public int getAuthStatus() {
                return authStatus;
            }

            public boolean isFollowed() {
                return followed;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public int getAccountStatus() {
                return accountStatus;
            }

            public int getGender() {
                return gender;
            }

            public int getCity() {
                return city;
            }

            public long getBirthday() {
                return birthday;
            }

            public long getUserId() {
                return userId;
            }

            public int getUserType() {
                return userType;
            }

            public String getNickname() {
                return nickname;
            }

            public String getSignature() {
                return signature;
            }

            public String getDescription() {
                return description;
            }

            public String getDetailDescription() {
                return detailDescription;
            }

            public long getAvatarImgId() {
                return avatarImgId;
            }

            public long getBackgroundImgId() {
                return backgroundImgId;
            }

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public int getAuthority() {
                return authority;
            }

            public boolean isMutual() {
                return mutual;
            }

            public Object getExpertTags() {
                return expertTags;
            }

            public Object getExperts() {
                return experts;
            }

            public int getDjStatus() {
                return djStatus;
            }

            public int getVipType() {
                return vipType;
            }

            public Object getRemarkName() {
                return remarkName;
            }

            public int getAuthenticationTypes() {
                return authenticationTypes;
            }

            public AvatarDetail getAvatarDetail() {
                return avatarDetail;
            }

            public String getAvatarImgIdStr() {
                return avatarImgIdStr;
            }

            public String getBackgroundImgIdStr() {
                return backgroundImgIdStr;
            }

            public boolean isAnchor() {
                return anchor;
            }

            public String getAvatarImgId_str() {
                return avatarImgId_str;
            }

            public static class AvatarDetail {
                private int userType;
                private int identityLevel;
                private String identityIconUrl;

                public int getUserType() {
                    return userType;
                }

                public int getIdentityLevel() {
                    return identityLevel;
                }

                public String getIdentityIconUrl() {
                    return identityIconUrl;
                }
            }
        }

        public static class Subscriber {
            private boolean defaultAvatar;
            private int province;
            private int authStatus;
            private boolean followed;
            private String avatarUrl;
            private int accountStatus;
            private int gender;
            private int city;
            private long birthday;
            private long userId;
            private int userType;
            private String nickname;
            private String signature;
            private String description;
            private String detailDescription;
            private long avatarImgId;
            private long backgroundImgId;
            private String backgroundUrl;
            private int authority;
            private boolean mutual;
            private Object expertTags;
            private Object experts;
            private int djStatus;
            private int vipType;
            private Object remarkName;
            private int authenticationTypes;
            private Object avatarDetail;
            private String avatarImgIdStr;
            private String backgroundImgIdStr;
            private boolean anchor;
            private String avatarImgId_str;

            public boolean isDefaultAvatar() {
                return defaultAvatar;
            }

            public int getProvince() {
                return province;
            }

            public int getAuthStatus() {
                return authStatus;
            }

            public boolean isFollowed() {
                return followed;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public int getAccountStatus() {
                return accountStatus;
            }

            public int getGender() {
                return gender;
            }

            public int getCity() {
                return city;
            }

            public long getBirthday() {
                return birthday;
            }

            public long getUserId() {
                return userId;
            }

            public int getUserType() {
                return userType;
            }

            public String getNickname() {
                return nickname;
            }

            public String getSignature() {
                return signature;
            }

            public String getDescription() {
                return description;
            }

            public String getDetailDescription() {
                return detailDescription;
            }

            public long getAvatarImgId() {
                return avatarImgId;
            }

            public long getBackgroundImgId() {
                return backgroundImgId;
            }

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public int getAuthority() {
                return authority;
            }

            public boolean isMutual() {
                return mutual;
            }

            public Object getExpertTags() {
                return expertTags;
            }

            public Object getExperts() {
                return experts;
            }

            public int getDjStatus() {
                return djStatus;
            }

            public int getVipType() {
                return vipType;
            }

            public Object getRemarkName() {
                return remarkName;
            }

            public int getAuthenticationTypes() {
                return authenticationTypes;
            }

            public Object getAvatarDetail() {
                return avatarDetail;
            }

            public String getAvatarImgIdStr() {
                return avatarImgIdStr;
            }

            public String getBackgroundImgIdStr() {
                return backgroundImgIdStr;
            }

            public boolean isAnchor() {
                return anchor;
            }

            public String getAvatarImgId_str() {
                return avatarImgId_str;
            }
        }
    }
}