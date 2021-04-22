package com.xy.xydoctor.bean;

import java.util.List;

public class FoodAndDrinkBean {
    /**
     * category : 2
     * datetime : 2019-07-01 16:03
     * foods : [{"foodname":"燕麦片","dosage":"10"},{"foodname":"小麦粉（标准粉）","dosage":"10"}]
     */

    private String category;
    private String datetime;
    private List<FoodsBean> foods;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<FoodsBean> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodsBean> foods) {
        this.foods = foods;
    }

    public static class FoodsBean {
        /**
         * foodname : 燕麦片
         * dosage : 10
         */

        private String foodname;
        private String dosage;

        public String getFoodname() {
            return foodname;
        }

        public void setFoodname(String foodname) {
            this.foodname = foodname;
        }

        public String getDosage() {
            return dosage;
        }

        public void setDosage(String dosage) {
            this.dosage = dosage;
        }
    }

}
