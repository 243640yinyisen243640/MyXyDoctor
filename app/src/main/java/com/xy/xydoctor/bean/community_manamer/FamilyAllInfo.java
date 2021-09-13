package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/13 9:30
 * Description:
 */
public class FamilyAllInfo {
    private SearchInfo master;

    private List<SearchInfo> members;

    public SearchInfo getMaster() {
        return master;
    }

    public void setMaster(SearchInfo master) {
        this.master = master;
    }

    public List<SearchInfo> getMembers() {
        return members;
    }

    public void setMembers(List<SearchInfo> members) {
        this.members = members;
    }
}
