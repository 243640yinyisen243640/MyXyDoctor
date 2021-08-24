package com.xy.xydoctor.bean;

import java.util.List;

public class UseMedicineListBean {

    /**
     * morepage : true
     * data : [{"id":54,"times":9,"addtime":"2019-07-23 10:25","status":5},{"id":53,"times":7,"addtime":"2019-07-22 16:55","status":2},{"id":42,"times":4,"addtime":"2019-06-26 11:00","status":2},{"id":40,"times":2,"addtime":"2019-06-24 16:09","status":2}]
     */

    private boolean morepage;
    private List<DataBean> data;

    public boolean isMorepage() {
        return morepage;
    }

    public void setMorepage(boolean morepage) {
        this.morepage = morepage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * id : 54
         * times : 9
         * addtime : 2019-07-23 10:25
         * status : 5
         */

     /*   private int id;
        private int times;
        private String addtime;
        private String status;*/
        private String plan_name;
        private List<PlanListBean> plan_list;

        public List<PlanListBean> getPlan_list() {
            return plan_list;
        }

        public void setPlan_list(List<PlanListBean> plan_list) {
            this.plan_list = plan_list;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public static class PlanListBean {
            /**
             * id : 772
             * times : 1
             * addtime : 2021-03-18 11:18
             * status : 5
             * visittime : 1615996800
             * plan_id : 0
             * plan_name : 随访问卷
             */

            private int id;
            private int times;
            private String addtime;
            private int status;
            private int visittime;
            private int plan_id;
            private String plan_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getVisittime() {
                return visittime;
            }

            public void setVisittime(int visittime) {
                this.visittime = visittime;
            }

            public int getPlan_id() {
                return plan_id;
            }

            public void setPlan_id(int plan_id) {
                this.plan_id = plan_id;
            }

            public String getPlan_name() {
                return plan_name;
            }

            public void setPlan_name(String plan_name) {
                this.plan_name = plan_name;
            }
        }
    }
}
