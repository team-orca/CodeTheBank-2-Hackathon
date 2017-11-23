package deneme.codethebank.com.bank;

/**
 * Created by ASUS on 2.04.2016.
 */
public class altinveboolean {


    /**
     * Represents an item in a ToDo list
     */


        /**
         * Item text
         */
        @com.google.gson.annotations.SerializedName("bakildimi")
        private int bakildimi=0;
        @com.google.gson.annotations.SerializedName("altin")
        private int altin=0;


        /**
         * Item Id
         */
        @com.google.gson.annotations.SerializedName("id")
        private String mId;

        /**
         * Indicates if the item is completed
         */






        public String getId() {
            return mId;
        }

        /**
         * Sets the item id
         *
         * @param id
         *            id to set
         */
        public final void setId(String id) {
            mId = id;
        }


        @Override
        public boolean equals(Object o) {
            return o instanceof altinveboolean && ((altinveboolean) o).mId == mId;
        }

    public int getBakildimi() {
        return bakildimi;
    }

    public void setBakildimi(int bakildimi) {
        this.bakildimi = bakildimi;
    }

    public int getAltin() {
        return altin;
    }

    public void setAltin(int altin) {
        this.altin = altin;
    }



}
