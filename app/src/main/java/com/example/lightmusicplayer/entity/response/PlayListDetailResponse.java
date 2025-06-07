/**
  * Copyright 2025 json.cn 
  */
package com.example.lightmusicplayer.entity.response;
import java.util.List;

public class PlayListDetailResponse {

    private int code;
    private String relatedVideos;
    private Playlist playlist;
    private String urls;
    private String sharedPrivilege;
    private String resEntrance;
    private String fromUsers;
    private int fromUserCount;
    private String songFromUsers;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setRelatedVideos(String relatedVideos) {
         this.relatedVideos = relatedVideos;
     }
     public String getRelatedVideos() {
         return relatedVideos;
     }

    public void setPlaylist(Playlist playlist) {
         this.playlist = playlist;
     }
     public Playlist getPlaylist() {
         return playlist;
     }

    public void setUrls(String urls) {
         this.urls = urls;
     }
     public String getUrls() {
         return urls;
     }

    public void setSharedPrivilege(String sharedPrivilege) {
         this.sharedPrivilege = sharedPrivilege;
     }
     public String getSharedPrivilege() {
         return sharedPrivilege;
     }

    public void setResEntrance(String resEntrance) {
         this.resEntrance = resEntrance;
     }
     public String getResEntrance() {
         return resEntrance;
     }

    public void setFromUsers(String fromUsers) {
         this.fromUsers = fromUsers;
     }
     public String getFromUsers() {
         return fromUsers;
     }

    public void setFromUserCount(int fromUserCount) {
         this.fromUserCount = fromUserCount;
     }
     public int getFromUserCount() {
         return fromUserCount;
     }

    public void setSongFromUsers(String songFromUsers) {
         this.songFromUsers = songFromUsers;
     }
     public String getSongFromUsers() {
         return songFromUsers;
     }

    public static class Playlist {

        private List<Track> tracks;
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

        private PlaylistsResponse.Playlist.Creator creator;
        private List<PlaylistsResponse.Playlist.Subscriber> subscribers;
        private Object subscribed;
        private String commentThreadId;

        public List<Track> getTracks() {
            return tracks;
        }

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

        public PlaylistsResponse.Playlist.Creator getCreator() {
            return creator;
        }

        public List<PlaylistsResponse.Playlist.Subscriber> getSubscribers() {
            return subscribers;
        }

        public Object getSubscribed() {
            return subscribed;
        }

        public String getCommentThreadId() {
            return commentThreadId;
        }


        public class Track {
            private String name;
            private Object mainTitle;
            private Object additionalTitle;
            private long id;
            private int pst;
            private int t;
            private Artist[] ar;
            private String[] alia;
            private int pop;
            private int st;
            private String rt;
            private int fee;
            private int v;
            private Object crbt;
            private String cf;
            private Album al;
            private int dt;
            private Quality h;
            private Quality m;
            private Quality l;
            private Quality sq;
            private Object hr;
            private Object a;
            private String cd;
            private int no;
            private Object rtUrl;
            private int ftype;
            private Object[] rtUrls;
            private long djId;
            private int copyright;
            private int s_id;
            private long mark;
            private int originCoverType;
            private Object originSongSimpleData;
            private Object tagPicList;
            private boolean resourceState;
            private int version;
            private Object songJumpInfo;
            private Object entertainmentTags;
            private Object awardTags;
            private Object displayTags;
            private int single;
            private Object noCopyrightRcmd;
            private Object alg;
            private Object displayReason;
            private int rtype;
            private Object rurl;
            private int mst;
            private int cp;
            private long mv;
            private long publishTime;

            public Track(int pop, String name, Object mainTitle, Object additionalTitle, long id, int pst, int t, Artist[] ar, String[] alia, int st, String rt, int fee, int v, Object crbt, String cf, Album al, int dt, Quality h, Quality m, Quality l, Quality sq, Object hr, Object a, String cd, int no, Object rtUrl, int ftype, Object[] rtUrls, long djId, int copyright, int s_id, long mark, int originCoverType, Object originSongSimpleData, Object tagPicList, boolean resourceState, int version, Object songJumpInfo, Object entertainmentTags, Object awardTags, Object displayTags, int single, Object noCopyrightRcmd, Object alg, Object displayReason, int rtype, Object rurl, int mst, int cp, long mv, long publishTime) {
                this.pop = pop;
                this.name = name;
                this.mainTitle = mainTitle;
                this.additionalTitle = additionalTitle;
                this.id = id;
                this.pst = pst;
                this.t = t;
                this.ar = ar;
                this.alia = alia;
                this.st = st;
                this.rt = rt;
                this.fee = fee;
                this.v = v;
                this.crbt = crbt;
                this.cf = cf;
                this.al = al;
                this.dt = dt;
                this.h = h;
                this.m = m;
                this.l = l;
                this.sq = sq;
                this.hr = hr;
                this.a = a;
                this.cd = cd;
                this.no = no;
                this.rtUrl = rtUrl;
                this.ftype = ftype;
                this.rtUrls = rtUrls;
                this.djId = djId;
                this.copyright = copyright;
                this.s_id = s_id;
                this.mark = mark;
                this.originCoverType = originCoverType;
                this.originSongSimpleData = originSongSimpleData;
                this.tagPicList = tagPicList;
                this.resourceState = resourceState;
                this.version = version;
                this.songJumpInfo = songJumpInfo;
                this.entertainmentTags = entertainmentTags;
                this.awardTags = awardTags;
                this.displayTags = displayTags;
                this.single = single;
                this.noCopyrightRcmd = noCopyrightRcmd;
                this.alg = alg;
                this.displayReason = displayReason;
                this.rtype = rtype;
                this.rurl = rurl;
                this.mst = mst;
                this.cp = cp;
                this.mv = mv;
                this.publishTime = publishTime;
            }

            public String getName() {
                return name;
            }

            public Object getMainTitle() {
                return mainTitle;
            }

            public Object getAdditionalTitle() {
                return additionalTitle;
            }

            public long getId() {
                return id;
            }

            public int getPst() {
                return pst;
            }

            public int getT() {
                return t;
            }

            public Artist[] getAr() {
                return ar;
            }

            public String[] getAlia() {
                return alia;
            }

            public int getPop() {
                return pop;
            }

            public int getSt() {
                return st;
            }

            public String getRt() {
                return rt;
            }

            public int getFee() {
                return fee;
            }

            public int getV() {
                return v;
            }

            public Object getCrbt() {
                return crbt;
            }

            public String getCf() {
                return cf;
            }

            public Album getAl() {
                return al;
            }

            public int getDt() {
                return dt;
            }

            public Quality getH() {
                return h;
            }

            public Quality getM() {
                return m;
            }

            public Quality getL() {
                return l;
            }

            public Quality getSq() {
                return sq;
            }

            public Object getHr() {
                return hr;
            }

            public Object getA() {
                return a;
            }

            public String getCd() {
                return cd;
            }

            public int getNo() {
                return no;
            }

            public Object getRtUrl() {
                return rtUrl;
            }

            public int getFtype() {
                return ftype;
            }

            public Object[] getRtUrls() {
                return rtUrls;
            }

            public long getDjId() {
                return djId;
            }

            public int getCopyright() {
                return copyright;
            }

            public int getS_id() {
                return s_id;
            }

            public long getMark() {
                return mark;
            }

            public int getOriginCoverType() {
                return originCoverType;
            }

            public Object getOriginSongSimpleData() {
                return originSongSimpleData;
            }

            public Object getTagPicList() {
                return tagPicList;
            }

            public boolean isResourceState() {
                return resourceState;
            }

            public int getVersion() {
                return version;
            }

            public Object getSongJumpInfo() {
                return songJumpInfo;
            }

            public Object getEntertainmentTags() {
                return entertainmentTags;
            }

            public Object getAwardTags() {
                return awardTags;
            }

            public Object getDisplayTags() {
                return displayTags;
            }

            public int getSingle() {
                return single;
            }

            public Object getNoCopyrightRcmd() {
                return noCopyrightRcmd;
            }

            public Object getAlg() {
                return alg;
            }

            public Object getDisplayReason() {
                return displayReason;
            }

            public int getRtype() {
                return rtype;
            }

            public Object getRurl() {
                return rurl;
            }

            public int getMst() {
                return mst;
            }

            public int getCp() {
                return cp;
            }

            public long getMv() {
                return mv;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public static class Artist {
                private long id;
                private String name;
                private String[] tns;
                private String[] alias;


                public Artist(long id, String name, String[] tns, String[] alias) {
                    this.id = id;
                    this.name = name;
                    this.tns = tns;
                    this.alias = alias;
                }


                public long getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public String[] getTns() {
                    return tns;
                }

                public String[] getAlias() {
                    return alias;
                }
            }

            public static class Album {
                private long id;
                private String name;
                private String picUrl;
                private String[] tns;
                private String[] alias;
                private String pic_str;
                private long pic;


                public Album(long id, String name, String picUrl, String[] tns, String[] alias, String pic_str, long pic) {
                    this.id = id;
                    this.name = name;
                    this.picUrl = picUrl;
                    this.tns = tns;
                    this.alias = alias;
                    this.pic_str = pic_str;
                    this.pic = pic;
                }


                public long getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public String getPicUrl() {
                    return picUrl;
                }

                public String[] getTns() {
                    return tns;
                }

                public String[] getAlias() {
                    return alias;
                }

                public String getPic_str() {
                    return pic_str;
                }

                public long getPic() {
                    return pic;
                }
            }

            public static class Quality {
                private int br;
                private int fid;
                private int size;
                private int vd;
            }
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
            private PlaylistsResponse.Playlist.Creator.AvatarDetail avatarDetail;
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

            public PlaylistsResponse.Playlist.Creator.AvatarDetail getAvatarDetail() {
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