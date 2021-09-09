package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/9 11:52
 * Description:
 */
public class FollowUpListAllInfo {
    private List<FollowListInfo> list;
    /**
     * 待随访数量
     */
    private String following;
    /**
     * 失访数量
     */
    private String losting;
    /**
     * 完成随访
     */
    private String finished;

    public List<FollowListInfo> getList() {
        return list;
    }

    public void setList(List<FollowListInfo> list) {
        this.list = list;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getLosting() {
        return losting;
    }

    public void setLosting(String losting) {
        this.losting = losting;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }
}
