package com.xy.xydoctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 患者分组
 */
public class GroupListBean implements Serializable {

    /**
     * gid : 0
     * gname : 默认分组
     * num : 46
     * groupers : [{"userid":106,"userId":"78c80737453442e49e5f32e5553895c9","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15517558259","nickname":"程咬金","sex":1,"age":49,"diabeteslei":2},{"userid":107,"userId":"78c80737453442e49e5f32e5553895c01","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15939170682","nickname":"噢噢噢","sex":1,"age":0,"diabeteslei":2},{"userid":108,"userId":"78c80737453442e49e5f32e5553895c02","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13603442088","nickname":"王俊娜","sex":2,"age":34,"diabeteslei":0},{"userid":109,"userId":"78c80737453442e49e5f32e5553895c03","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"18588299991","nickname":"李志远","sex":1,"age":38,"diabeteslei":2},{"userid":110,"userId":"78c80737453442e49e5f32e5553895c04","picture":"http://ceshi.xiyuns.cn/public/uploads/20190313/b3eed08bd84ff1d83fa0649d04bc5c9c.png","username":"17600900989","nickname":"闫罗王","sex":1,"age":49,"diabeteslei":3},{"userid":111,"userId":"78c80737453442e49e5f32e5553895c05","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13652546855","nickname":"崔媛媛","sex":2,"age":43,"diabeteslei":3},{"userid":112,"userId":"78c80737453442e49e5f32e5553895c06","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15241265325","nickname":"吴常","sex":1,"age":48,"diabeteslei":2},{"userid":113,"userId":"78c80737453442e49e5f32e5553895c07","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15526532568","nickname":"姚成功","sex":1,"age":29,"diabeteslei":2},{"userid":114,"userId":"78c80737453442e49e5f32e5553895c08","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13023652879","nickname":"齐胜杰","sex":1,"age":28,"diabeteslei":2},{"userid":115,"userId":"78c80737453442e49e5f32e5553895c09","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"17025469896","nickname":"赵昆明","sex":1,"age":28,"diabeteslei":2},{"userid":116,"userId":"78c80737453442e49e5f32e5553895c10","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13365625879","nickname":"张伟","sex":1,"age":28,"diabeteslei":4},{"userid":117,"userId":"78c80737453442e49e5f32e5553895c11","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13132547787","nickname":"孙亚楠","sex":1,"age":0,"diabeteslei":0},{"userid":118,"userId":"78c80737453442e49e5f32e5553895c12","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15024569988","nickname":"周方圆","sex":1,"age":0,"diabeteslei":5},{"userid":119,"userId":"78c80737453442e49e5f32e5553895c013","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13133562549","nickname":"郑高兴","sex":1,"age":0,"diabeteslei":null},{"userid":120,"userId":"78c80737453442e49e5f32e5553895c014","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13854265987","nickname":"陈雨荷","sex":2,"age":0,"diabeteslei":null},{"userid":122,"userId":"78c80737453442e49e5f32e5553895c16","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13756425894","nickname":"测试数据11","sex":1,"age":0,"diabeteslei":2},{"userid":123,"userId":"56dcc01afa0c4a768d6f1e54e3e62asdf","picture":"http://ceshi.xiyuns.cn/public/uploads/20190131/92558d3ebc75a1bc4c3ab7925a07025a.png","username":"13137750608","nickname":"身体健康","sex":1,"age":49,"diabeteslei":0},{"userid":124,"userId":"78c80737453442e49e5f32e5553895c17","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15222367532","nickname":"","sex":2,"age":0,"diabeteslei":null},{"userid":126,"userId":"78c80737453442e49e5f32e5553895c18","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"18522071084","nickname":"","sex":2,"age":0,"diabeteslei":null},{"userid":127,"userId":"78c80737453442e49e5f32e5553895c19","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15620632105","nickname":"","sex":2,"age":0,"diabeteslei":null},{"userid":128,"userId":"78c80737453442e49e5f32e5553895c020","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15620866067","nickname":"阿3","sex":1,"age":0,"diabeteslei":0},{"userid":130,"userId":"78c80737453442e49e5f32e5553895c21","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13523575673","nickname":null,"sex":1,"age":0,"diabeteslei":null},{"userid":133,"userId":"78c80737453442e49e5f32e5553895c22","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15001329621","nickname":"盛帅达","sex":1,"age":0,"diabeteslei":3},{"userid":134,"userId":"78c80737453442e49e5f32e5553895c23","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"18515283368","nickname":"尹女士","sex":2,"age":0,"diabeteslei":3},{"userid":306,"userId":"325570af0805a9523cb866c4c59bf4c9","picture":null,"username":"xyxpj6fm_1543633721","nickname":"齐胜杰","sex":1,"age":0,"diabeteslei":4},{"userid":312,"userId":"78c80737453442e49e5f32e5553895c024","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15138685559","nickname":"张小华","sex":1,"age":33,"diabeteslei":2},{"userid":313,"userId":"78c80737453442e49e5f32e5553895c25","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"17600900988","nickname":"张三","sex":1,"age":27,"diabeteslei":2},{"userid":314,"userId":"78c80737453442e49e5f32e5553895c26","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13012341234","nickname":"张小二","sex":1,"age":31,"diabeteslei":2},{"userid":315,"userId":"78c80737453442e49e5f32e5553895c27","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"13526632219","nickname":"史磊","sex":2,"age":30,"diabeteslei":2},{"userid":338,"userId":"fbe256ad5f5bff88a5820fe845794d09","picture":"","username":"xy22c7ul_1545791364","nickname":"韵升","sex":1,"age":0,"diabeteslei":1},{"userid":369,"userId":"d5224adc00079ce805147360be396935","picture":"http://ceshi.xiyuns.cn/public/uploads/20190226/0fb1e52063825d78adfaca73353c9f14.png","username":"15939028174","nickname":"刘晓飞","sex":1,"age":49,"diabeteslei":1},{"userid":432,"userId":"78c80737453442e49e5f32e5553895c028","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15539885375","nickname":"汉光武","sex":1,"age":0,"diabeteslei":2},{"userid":433,"userId":"5d85686c398781287ae9f67941a340c1","picture":"http://ceshi.xiyuns.cn/public/uploads/20190305/86ee88ada93b7f0f624d07900da1a7cc.png","username":"18531379685","nickname":null,"sex":1,"age":0,"diabeteslei":4},{"userid":446,"userId":"360d657dc5dedd43419403d010d1a409","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"15539855370","nickname":"亭梨","sex":1,"age":0,"diabeteslei":2},{"userid":449,"userId":"1db6f47e0dbd4d6996243b8ec03df3fa","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"15539875370","nickname":"兰生","sex":1,"age":0,"diabeteslei":2},{"userid":450,"userId":"52e987f41aa2fe7fe321c5473803f97d","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"13137750611","nickname":"哈3","sex":1,"age":0,"diabeteslei":2},{"userid":460,"userId":"59d488a223c1e553e998e1309bb13664","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"13256485431","nickname":"105","sex":1,"age":0,"diabeteslei":2},{"userid":478,"userId":"91b26504cfdd74f2b55f77bf4ce9c121","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"17600900999","nickname":"患者一","sex":1,"age":0,"diabeteslei":2},{"userid":479,"userId":"2ddf3aa453d7c766b71c17bf8c6f7ddf","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"17600900889","nickname":"患者二","sex":1,"age":0,"diabeteslei":2},{"userid":480,"userId":"719ac7c5a19c87b34dc359863195780a","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"17600900887","nickname":"患者四","sex":1,"age":0,"diabeteslei":2},{"userid":481,"userId":"59396b986280c0b53b66d372bfe6d975","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"17600900879","nickname":"患者五","sex":1,"age":0,"diabeteslei":2},{"userid":484,"userId":"337ec0663c73e712f4cb5e8b4df872d5","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"17600900456","nickname":"张三李四","sex":1,"age":0,"diabeteslei":2},{"userid":485,"userId":"a05fe505fe117411ce838a89d39ed032","picture":"http://ceshi.xiyuns.cn/public/uploads/20190313/23ef8f5a8e1f56ee98d738629feae34e.png","username":"17600900234","nickname":"张三","sex":1,"age":0,"diabeteslei":4},{"userid":486,"userId":"0aff4760a60e32d0933df7a29bc7c2d5","picture":"http://ceshi.xiyuns.cn/public/images/userimg.png","username":"17600900963","nickname":null,"sex":1,"age":0,"diabeteslei":4},{"userid":487,"userId":"d0e5bfe73cc4c6797b1fdd4e0eb00690","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"17600900963","nickname":"123","sex":1,"age":0,"diabeteslei":2},{"userid":489,"userId":"963815f19cdfb8f1885e4786aa403e93","picture":"http://xydoc.xiyuns.cn/Public/images/navimg/userimg.png","username":"17600900985","nickname":"345","sex":1,"age":0,"diabeteslei":2}]
     */

    private int gid;
    private String gname;
    private int num;
    private List<GroupersBean> groupers;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<GroupersBean> getGroupers() {
        return groupers;
    }

    public void setGroupers(List<GroupersBean> groupers) {
        this.groupers = groupers;
    }

    public static class GroupersBean {
        /**
         * userid : 106
         * userId : 78c80737453442e49e5f32e5553895c9
         * picture : http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg
         * username : 15517558259
         * nickname : 程咬金
         * sex : 1
         * age : 49
         * diabeteslei : 2
         */

        private int userid;
        private String userId;
        private String picture;
        private String username;
        private String nickname;
        private int sex;
        private int age;
        private int diabeteslei;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getDiabeteslei() {
            return diabeteslei;
        }

        public void setDiabeteslei(int diabeteslei) {
            this.diabeteslei = diabeteslei;
        }
    }
}
