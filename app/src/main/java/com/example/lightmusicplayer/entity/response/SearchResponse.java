package com.example.lightmusicplayer.entity.response;


import java.util.List;

public class SearchResponse {
    private Result result;

    public Result getResult() {
        return result;
    }

    public static class Result{
        private List<Songs.Song> songs;
        public List<Songs.Song> getSongs() {
            return songs;
        }
    }

    // Song 数据结构定义开始
    public static class Songs {
        private List<Song> songs;

        public List<Song> getSongList() {
            return songs;
        }

        public static class Song {
            private long id;
            private String name;
            private List<Artist> artists;
            private Album album;
            private int duration;
            private int copyrightId;
            private int status;
            private List<String> alias;
            private int rtype;
            private int ftype;
            private long mvid;
            private int fee;
            private Object rUrl;
            private long mark;

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public List<Artist> getArtists() {
                return artists;
            }

            public Album getAlbum() {
                return album;
            }

            public int getDuration() {
                return duration;
            }

            public int getCopyrightId() {
                return copyrightId;
            }

            public int getStatus() {
                return status;
            }

            public List<String> getAlias() {
                return alias;
            }

            public int getRtype() {
                return rtype;
            }

            public int getFtype() {
                return ftype;
            }

            public long getMvid() {
                return mvid;
            }

            public int getFee() {
                return fee;
            }

            public Object getrUrl() {
                return rUrl;
            }

            public long getMark() {
                return mark;
            }

            // Artist 嵌套类
            public static class Artist {
                private long id;
                private String name;
                private Object picUrl;
                private List<String> alias;
                private int albumSize;
                private long picId;
                private Object fansGroup;
                private String img1v1Url;
                private long img1v1;
                private Object trans;

                public long getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public Object getPicUrl() {
                    return picUrl;
                }

                public List<String> getAlias() {
                    return alias;
                }

                public int getAlbumSize() {
                    return albumSize;
                }

                public long getPicId() {
                    return picId;
                }

                public Object getFansGroup() {
                    return fansGroup;
                }

                public String getImg1v1Url() {
                    return img1v1Url;
                }

                public long getImg1v1() {
                    return img1v1;
                }

                public Object getTrans() {
                    return trans;
                }
            }

            // Album 嵌套类
            public static class Album {
                private long id;
                private String name;
                private Artist artist;
                private long publishTime;
                private int size;
                private int copyrightId;
                private int status;
                private long picId;
                private int mark;

                public long getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public Artist getArtist() {
                    return artist;
                }

                public long getPublishTime() {
                    return publishTime;
                }

                public int getSize() {
                    return size;
                }

                public int getCopyrightId() {
                    return copyrightId;
                }

                public int getStatus() {
                    return status;
                }

                public long getPicId() {
                    return picId;
                }

                public int getMark() {
                    return mark;
                }
            }
        }
    }
}