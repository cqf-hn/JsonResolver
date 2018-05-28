package com.hn.cqf.resolver;

import android.text.TextUtils;


import com.hn.cqf.lib.annotation.ResolveTarget;

import java.util.List;
import java.util.Map;

/**
 * Ver 2.0
 * Created by j on 2018/3/23 0023.
 */

@ResolveTarget
public class HouseDetailNewBean {


    /**
     * apartmentImage : ["http://192.168.13.242:8080/userfiles/1/images/rent/aptApartment/2018/05/meduim/15_watermark.jpg","http://192.168.13.242:8080/userfiles/1/images/rent/aptApartment/2018/05/meduim/23_watermark.jpg"]
     * rentMap : {}
     * payMoney : 1326.0
     * isReservation : 0
     * status : {"succeed":1}
     * apartment : {"id":"d9fbacffc3cd4944ac1cab06c1e4fb7c","isNewRecord":false,"remarks":"","name":"交租续租测试","area":"68m²","unit":"2居","style":"精装修","aroundIntroduce":"","facility":"WIFI，空调","feature":"","comments":"","traffic":"","address":"海南三亚-崖州区","position":"109.518183,18.258406","floor":"16层","orientation":"南","status":"1","region":{"id":"9a3d9289af024f5687352af102529750","isNewRecord":false,"name":"崖州区","sort":30,"parentId":"0"},"room":{"id":"43c92198dc874deaa4bbe8e28be213bc","isNewRecord":false,"name":"1603"},"roomName":"雅筑小区(小区)>A栋(楼栋)>A1单元(单元)>16(楼层)>1603(房间)","lease":{"id":"09e1ae958e854488b035967b064dfd02","isNewRecord":false,"name":"短租租约策略"},"aptCommunity":{"id":"6dc275f458cd417192eaec7ec27edddf","isNewRecord":false,"name":"雅筑小区","title":"雅筑小区绿化好，交通便利"},"dayPrice":1326,"isRetreat":"0","isChanged":"0","moneyList":{"isNewRecord":true,"payType":"短租","rentPrice":"1458.6元/晚"},"featureList":["独立阳台",""],"isTop":"1","isHousing":"1","isCollection":"false","pageSize":0,"pageNo":0,"keyStatus":"0"}
     * monthPrice : {"3":1458.6,"2":1326,"10":1458.6,"1":1326,"7":1458.6,"6":1458.6,"5":1458.6,"4":1458.6,"9":1458.6,"8":1458.6,"11":1326,"12":1326}
     * shareImage : http://192.168.13.242:8080/userfiles/1/images/rent/aptApartment/2018/05/thumbnail/15.jpg
     * isRent : {"2018-05-23":true}
     */

    private Map<String, String> rentMap;
    private String payMoney;
    private String isReservation;
    private StatusBean status;
    private ApartmentBean apartment;
    private Map<String, String> monthPrice;
    private String shareImage;
    private Map<String, Boolean> isRent;
    private List<String> apartmentImage;

    public Map<String, String> getRentMap() {
        return rentMap;
    }

    public void setRentMap(Map<String, String> rentMap) {
        this.rentMap = rentMap;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getIsReservation() {
        return isReservation;
    }

    public void setIsReservation(String isReservation) {
        this.isReservation = isReservation;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public ApartmentBean getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentBean apartment) {
        this.apartment = apartment;
    }

    public Map<String, String> getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(Map<String, String> monthPrice) {
        this.monthPrice = monthPrice;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public Map<String, Boolean> getIsRent() {
        return isRent;
    }

    public void setIsRent(Map<String, Boolean> isRent) {
        this.isRent = isRent;
    }

    public List<String> getApartmentImage() {
        return apartmentImage;
    }

    public void setApartmentImage(List<String> apartmentImage) {
        this.apartmentImage = apartmentImage;
    }

    public static class RentMapBean {
    }

    public static class StatusBean {
        /**
         * succeed : 1
         */

        private int succeed;

        public int getSucceed() {
            return succeed;
        }

        public void setSucceed(int succeed) {
            this.succeed = succeed;
        }
    }

    public static class ApartmentBean {
        /**
         * id : d9fbacffc3cd4944ac1cab06c1e4fb7c
         * isNewRecord : false
         * remarks :
         * name : 交租续租测试
         * area : 68m²
         * unit : 2居
         * style : 精装修
         * aroundIntroduce :
         * facility : WIFI，空调
         * feature :
         * comments :
         * traffic :
         * address : 海南三亚-崖州区
         * position : 109.518183,18.258406
         * floor : 16层
         * orientation : 南
         * status : 1
         * region : {"id":"9a3d9289af024f5687352af102529750","isNewRecord":false,"name":"崖州区","sort":30,"parentId":"0"}
         * room : {"id":"43c92198dc874deaa4bbe8e28be213bc","isNewRecord":false,"name":"1603"}
         * roomName : 雅筑小区(小区)>A栋(楼栋)>A1单元(单元)>16(楼层)>1603(房间)
         * lease : {"id":"09e1ae958e854488b035967b064dfd02","isNewRecord":false,"name":"短租租约策略"}
         * aptCommunity : {"id":"6dc275f458cd417192eaec7ec27edddf","isNewRecord":false,"name":"雅筑小区","title":"雅筑小区绿化好，交通便利"}
         * dayPrice : 1326.0
         * isRetreat : 0
         * isChanged : 0
         * moneyList : {"isNewRecord":true,"payType":"短租","rentPrice":"1458.6元/晚"}
         * featureList : ["独立阳台",""]
         * isTop : 1
         * isHousing : 1
         * isCollection : false
         * pageSize : 0
         * pageNo : 0
         * keyStatus : 0
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String name;
        private String area;
        private String unit;
        private String style;
        private String aroundIntroduce;
        private String facility;
        private String feature;
        private String comments;
        private String traffic;
        private String address;
        private String position;
        private String floor;
        private String orientation;
        private String status;
        private RegionBean region;
        private RoomBean room;
        private String roomName;
        private LeaseBean lease;
        private AptCommunityBean aptCommunity;
        private double dayPrice;
        private String isRetreat;
        private String isChanged;
        private MoneyListBean moneyList;
        private String isTop;
        private String isHousing;
        private boolean isCollection;
        private int pageSize;
        private int pageNo;
        private String keyStatus;
        private List<String> featureList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return TextUtils.isEmpty(remarks) ? "暂无" : remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getAroundIntroduce() {
            return TextUtils.isEmpty(aroundIntroduce) ? "暂无" : aroundIntroduce;
        }

        public void setAroundIntroduce(String aroundIntroduce) {
            this.aroundIntroduce = aroundIntroduce;
        }

        public String getFacility() {
            return facility;
        }

        public void setFacility(String facility) {
            this.facility = facility;
        }

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getTraffic() {
            return traffic;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RegionBean getRegion() {
            return region;
        }

        public void setRegion(RegionBean region) {
            this.region = region;
        }

        public RoomBean getRoom() {
            return room;
        }

        public void setRoom(RoomBean room) {
            this.room = room;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public LeaseBean getLease() {
            return lease;
        }

        public void setLease(LeaseBean lease) {
            this.lease = lease;
        }

        public AptCommunityBean getAptCommunity() {
            return aptCommunity;
        }

        public void setAptCommunity(AptCommunityBean aptCommunity) {
            this.aptCommunity = aptCommunity;
        }

        public double getDayPrice() {
            return dayPrice;
        }

        public void setDayPrice(double dayPrice) {
            this.dayPrice = dayPrice;
        }

        public String getIsRetreat() {
            return isRetreat;
        }

        public void setIsRetreat(String isRetreat) {
            this.isRetreat = isRetreat;
        }

        public String getIsChanged() {
            return isChanged;
        }

        public void setIsChanged(String isChanged) {
            this.isChanged = isChanged;
        }

        public MoneyListBean getMoneyList() {
            return moneyList;
        }

        public void setMoneyList(MoneyListBean moneyList) {
            this.moneyList = moneyList;
        }

        public String getIsTop() {
            return isTop;
        }

        public void setIsTop(String isTop) {
            this.isTop = isTop;
        }

        public String getIsHousing() {
            return isHousing;
        }

        public void setIsHousing(String isHousing) {
            this.isHousing = isHousing;
        }

        public boolean isCollection() {
            return isCollection;
        }

        public void setCollection(boolean collection) {
            isCollection = collection;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public String getKeyStatus() {
            return keyStatus;
        }

        public void setKeyStatus(String keyStatus) {
            this.keyStatus = keyStatus;
        }

        public List<String> getFeatureList() {
            return featureList;
        }

        public void setFeatureList(List<String> featureList) {
            this.featureList = featureList;
        }

        public static class RegionBean {
            /**
             * id : 9a3d9289af024f5687352af102529750
             * isNewRecord : false
             * name : 崖州区
             * sort : 30
             * parentId : 0
             */

            private String id;
            private boolean isNewRecord;
            private String name;
            private int sort;
            private String parentId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public boolean isNewRecord() {
                return isNewRecord;
            }

            public void setNewRecord(boolean newRecord) {
                isNewRecord = newRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }
        }

        public static class RoomBean {
            /**
             * id : 43c92198dc874deaa4bbe8e28be213bc
             * isNewRecord : false
             * name : 1603
             */

            private String id;
            private boolean isNewRecord;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isNewRecord() {
                return isNewRecord;
            }

            public void setNewRecord(boolean newRecord) {
                isNewRecord = newRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class LeaseBean {
            /**
             * id : 09e1ae958e854488b035967b064dfd02
             * isNewRecord : false
             * name : 短租租约策略
             */

            private String id;
            private boolean isNewRecord;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class AptCommunityBean {
            /**
             * id : 6dc275f458cd417192eaec7ec27edddf
             * isNewRecord : false
             * name : 雅筑小区
             * title : 雅筑小区绿化好，交通便利
             */

            private String id;
            private boolean isNewRecord;
            private String name;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return TextUtils.isEmpty(title) ? "暂无" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class MoneyListBean {
            /**
             * isNewRecord : true
             * payType : 短租
             * rentPrice : 1458.6元/晚
             */

            private boolean isNewRecord;
            private String payType;
            private String rentPrice;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getRentPrice() {
                return rentPrice;
            }

            public void setRentPrice(String rentPrice) {
                this.rentPrice = rentPrice;
            }
        }
    }
}
