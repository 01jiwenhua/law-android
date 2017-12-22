package com.shx.lawwh.libs.http;

public class ZCResponseData {
    private String message;
    private Result result;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  class Result {
        /**
         * data : "{\"token\":\"90b164495ccae2b46821eee91c8bb79a0d3b184452932d5c2beab80d75e3ecf0\"}"
         * pageCount : 0
         * recordCount : 0
         */
        private int size;
        private int currentPage;
        private String data;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public String getData() {

            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
